package com.efun.wallet.service.btc.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.efun.game.common.mybatis.IntegerValuedEnum;
import com.efun.wallet.dao.*;
import com.efun.wallet.entity.*;
import com.efun.wallet.enums.BitCoinTransactionCategory;
import com.efun.wallet.enums.RechargeRegionSyncTaskStatus;
import com.efun.wallet.enums.TaskResult;
import com.efun.wallet.service.btc.BtcRechargeSyncInteface;
import com.efun.wallet.service.ConfigService;
import com.efun.wallet.util.BtcApiUtil;
import com.efun.wallet.util.RedisData;
import com.mexc.common.util.ThressDescUtil;
import com.mexc.common.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/31.
 */
@Service
public class BtcRechargeSyncImpl implements BtcRechargeSyncInteface {
    private static final Logger LOGGER = LoggerFactory.getLogger(BtcRechargeSyncImpl.class);

    @Resource
    private BtcRechargeRegionSyncTaskDetailDAO rechargeRegionSyncTaskDetailDAO;

    @Autowired
    private BtcApiUtil btcApiUtil;

    @Autowired
    private RedisData redisData;

    @Resource
    private AssetTransDAO assetTransDAO;

    @Resource
    private MemberAssetDAO memberAssetDAO;

    @Resource
    private AssetRechargeDAO assetRechargeDAO;

    @Resource
    private AddressLibDAO addressLibDAO;

    @Autowired
    private ConfigService configService;

    @Override
    public TaskResult run(String batchId) throws RuntimeException {
        LOGGER.info("batchId={}", batchId);

        if (StringUtils.isEmpty(batchId) || StringUtils.isBlank(batchId)) {
            return TaskResult.FAILED;
        }

        BtcRechargeRegionSyncTaskDetail detail = rechargeRegionSyncTaskDetailDAO.selectByBatchId(Long.valueOf(batchId));
        if (null == detail) {
            return TaskResult.FAILED;
        }

        String category = detail.getCategory();
        // 检查收款
        if (null == category || !BitCoinTransactionCategory.RECEIVE.getValue().equals(category)) {
            updateRechargeRegionSyncTaskDetail(detail, RechargeRegionSyncTaskStatus.FAIL, "CategoryError");
            return TaskResult.FAILED;
        }

        if (detail.getStatus() == RechargeRegionSyncTaskStatus.SUCCESS.getValue()) {
            return TaskResult.FAILED;
        }

        // 查询交易信息
        JSONObject result = btcApiUtil.gettransaction(detail.getTxid());
        if (null != result.getString("error")) {
            LOGGER.error("查询交易记录失败, {}", batchId);
            return TaskResult.RETRIED;
        }

        JSONObject transaction = result.getJSONObject("result");
        if (!checkTransaction(detail, transaction)) {
            return TaskResult.FAILED;
        }

        // 检查地址
        String encodeAddress = ThressDescUtil.encodeAssetAddress(detail.getAddress());
        AddressLib addressLib = addressLibDAO.selectByAddress(encodeAddress);
        if (null == addressLib) {
            LOGGER.error("BTC充值失败, 地址不匹配, batchId={}, address={}", batchId, detail.getAddress());
            updateRechargeRegionSyncTaskDetail(detail, RechargeRegionSyncTaskStatus.FAIL, "AddressError");
            return TaskResult.FAILED;
        }

        // 检查充值流水
        int assetTransCount = assetTransDAO.countByTransNo(detail.getTxid());
        if (assetTransCount > 0) {
            LOGGER.info("BTC充值, 充值流水已存在, batchId={}, transNo={}", batchId, detail.getTxid());
            updateRechargeRegionSyncTaskDetail(detail, RechargeRegionSyncTaskStatus.FAIL, "RechargeAassetTransExisted");
            return TaskResult.FAILED;
        }

        // 检查资产
        MemberAsset memberAsset = memberAssetDAO.selectByAddressAndToken(encodeAddress, "BTC");
        if (null == memberAsset) {
            LOGGER.info("BTC充值, 用户资产记录不存在, batchId={}, address={}", batchId, detail.getAddress());
            updateRechargeRegionSyncTaskDetail(detail, RechargeRegionSyncTaskStatus.FAIL, "MemberAssetNotExisted");
            return TaskResult.FAILED;
        }

        // 判断确认区块数
        int btcRechargeSuccessfulConfirmCount = configService.getSysRechargeBlockByVcoinId(memberAsset.getVcoinId());
        int confirmations = transaction.getInteger("confirmations");
        if (confirmations < btcRechargeSuccessfulConfirmCount) {
            LOGGER.info("BTC充值区块确认数不足, batchId={}, confirmations={}, btcRechargeSuccessfulConfirmCount={}", batchId, confirmations, btcRechargeSuccessfulConfirmCount);
            updateRechargeRegionSyncTaskDetail(detail, RechargeRegionSyncTaskStatus.CONFIRMING, null);
            return TaskResult.RETRIED;
        }

        // 充值确认，入账
        AssetTrans assetTrans = buildAssetTrans(memberAsset.getId(), detail, transaction);
        AssetRecharge assetRecharge = buildAssetRecharge(memberAsset, assetTrans, detail);
        assetTransDAO.insert(assetTrans);
        assetRechargeDAO.insert(assetRecharge);
        memberAssetDAO.rechargeUpdate(memberAsset.getId(), assetTrans.getTransAmount());

        // 任务设为成功
        updateRechargeRegionSyncTaskDetail(detail, RechargeRegionSyncTaskStatus.SUCCESS, null);
        return TaskResult.SUCCESSFUL;
    }

    private AssetTrans buildAssetTrans(String assetId, BtcRechargeRegionSyncTaskDetail detail, JSONObject result) {
        AssetTrans assetTrans = new AssetTrans();
        assetTrans.setId(UUIDUtil.get32UUID());
        assetTrans.setTransType(1);
        assetTrans.setTradeType("1");
        assetTrans.setTransReceipt(result.toJSONString());
        assetTrans.setTransNo(detail.getTxid());
        assetTrans.setTransTime(new Date());
        assetTrans.setAssetId(assetId);
        assetTrans.setTransAmount(new BigDecimal(detail.getAmount()));
        return assetTrans;
    }

    private AssetRecharge buildAssetRecharge(MemberAsset memberAsset, AssetTrans assetTrans, BtcRechargeRegionSyncTaskDetail detail) {
        AssetRecharge recharge = new AssetRecharge();
        recharge.setId(UUIDUtil.get32UUID());
        recharge.setVcoinId(memberAsset.getVcoinId());
        recharge.setTxReceipt(assetTrans.getTransReceipt());
        recharge.setReceiptTime(assetTrans.getTransTime());
        recharge.setTxHash(assetTrans.getTransNo());
        recharge.setTxToken("BTC");
        recharge.setRechargeAddress(detail.getAddress());
        recharge.setMemberId(memberAsset.getMemberId());
        recharge.setAssetAddress(detail.getAddress());
        recharge.setRechargeValue(assetTrans.getTransAmount());
        return recharge;
    }

    private void updateRechargeRegionSyncTaskDetail(BtcRechargeRegionSyncTaskDetail detail, IntegerValuedEnum status, String failReason) {
        detail.setStatus(status.getValue());
        detail.setFailReason(failReason);
        detail.setUpdateTime(new Date());
        rechargeRegionSyncTaskDetailDAO.updateByPrimaryKey(detail);
    }

    private boolean checkTransaction(BtcRechargeRegionSyncTaskDetail detail, JSONObject transaction) {
        if (!detail.getCategory().equals(BitCoinTransactionCategory.RECEIVE.getValue())) {
            return false;
        }

        JSONArray details = transaction.getJSONArray("details");
        JSONObject detailT = null;
        for (int i=0; i<details.size(); i++) {
            JSONObject d = details.getJSONObject(i);
            if (detail.getCategory().equals(d.getString("category"))
                    && detail.getAddress().equals(d.getString("address"))
                    && detail.getAccount().equals(d.getString("account"))
                    && 0 == d.getBigDecimal("amount").compareTo(new BigDecimal(detail.getAmount()))
                    ) {
                detailT = d;
                break;
            }
        }
        if (null == detailT) {
            LOGGER.error("BTC充值失败, 交易信息不存在, detail={}, transaction={}", JSON.toJSONString(detail), transaction.toJSONString());
            updateRechargeRegionSyncTaskDetail(detail, RechargeRegionSyncTaskStatus.FAIL, "TransactionInfoNotExsited");
            return false;
        }
        return true;
     }
}
