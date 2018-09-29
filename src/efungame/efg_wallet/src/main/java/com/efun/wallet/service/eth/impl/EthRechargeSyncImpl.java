package com.efun.wallet.service.eth.impl;

import com.alibaba.fastjson.JSON;
import com.efun.wallet.dao.*;
import com.efun.wallet.entity.*;
import com.efun.wallet.enums.RechargeSyncTaskStatus;
import com.efun.wallet.enums.TaskResult;
import com.efun.wallet.service.ConfigService;
import com.efun.wallet.service.btc.impl.BtcRechargeSyncImpl;
import com.efun.wallet.service.eth.EthRechargeSyncInteface;
import com.efun.wallet.util.EthApiUtil;
import com.mexc.common.util.ThressDescUtil;
import com.mexc.common.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Numeric;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;


/**
 * Created by Administrator on 2018/3/31.
 */
@Service
public class EthRechargeSyncImpl implements EthRechargeSyncInteface {

    private static final Logger LOGGER = LoggerFactory.getLogger(BtcRechargeSyncImpl.class);

    @Autowired
    private ConfigService configService;

    @Resource
    private AssetTransDAO assetTransDAO;

    @Resource
    private MemberAssetDAO memberAssetDAO;

    @Resource
    private AssetRechargeDAO assetRechargeDAO;

    @Resource
    private AddressLibDAO addressLibDAO;

    @Autowired
    private VcoinDAO vcoinDAO;

    @Autowired
    private EthApiUtil ethApiUtil;

    @Resource
    private EthRechargeSyncTaskDetailDAO ethRechargeSyncTaskDetailDAO;

    @Override
    public TaskResult run(String taskParam) {
        LOGGER.debug("taskParam={}", taskParam);

        String[] params = taskParam.split(":");
        if (2 != params.length) {
            LOGGER.error("eth类充值任务明细执行失败, 任务参数错误");
            return TaskResult.FAILED;
        }

        long blockNumber = Long.parseLong(params[0]);
        long transactionIndex = Long.parseLong(params[1]);

        EthRechargeSyncTaskDetail detail = ethRechargeSyncTaskDetailDAO.selectByBlockNumberAndTransactionIndex(blockNumber, transactionIndex);
        if (null == detail) {
            LOGGER.error("eth类充值任务明细执行失败, 任务明细记录不存在");
            return TaskResult.FAILED;
        }
        if (detail.getStatus() == RechargeSyncTaskStatus.SUCCESS.getValue()) {
            LOGGER.error("eth类充值任务明细执行失败, 任务明细已处理成功");
            return TaskResult.FAILED;
        }

        try {
            Transaction transaction = ethApiUtil.eth_getTransactionByBlockNumberAndIndex(blockNumber, transactionIndex);
            updateEthRechargeSyncTaskDetail(detail, transaction);

            String toAddress = transaction.getTo();
            String transValue = transaction.getValue().toString();

            int result = assetTransDAO.countByTransNo(transaction.getHash());
            if (result > 0) {
                LOGGER.error("交易信息已存在,hash={}", transaction.getHash());
                updateEthRechargeSyncTaskDetail(detail, RechargeSyncTaskStatus.FAIL, "AssetTransExistedError");
                return TaskResult.FAILED;
            }

            String encodeAddress = ThressDescUtil.encodeAssetAddress(toAddress);
            AddressLib addressLib = addressLibDAO.selectByAddress(encodeAddress);
            if (null == addressLib) {
                LOGGER.error("ETH充值失败,钱包没有该地址,address={},encodeAddress={}", toAddress, encodeAddress);
                updateEthRechargeSyncTaskDetail(detail, RechargeSyncTaskStatus.FAIL, "AddressError");
                return TaskResult.FAILED;
            }

            Vcoin vCoin = vcoinDAO.selectByContractAddress(encodeAddress);
            if (null != vCoin) {
                String input = transaction.getInput();
                if (input.length() < 5) {
                    LOGGER.info("合约交易信息异常,input={}", input);
                    updateEthRechargeSyncTaskDetail(detail, RechargeSyncTaskStatus.FAIL, "ContractInputError");
                    return TaskResult.FAILED;
                }
                toAddress = "0x" + input.substring(34, 74);
                transValue = "0x" + input.substring(74, 138);
            } else {
                vCoin = vcoinDAO.queryByEth();
            }

            if (StringUtils.isEmpty(detail.getCoinName())) {
                detail.setCoinName(vCoin.getVcoinNameEn());
                ethRechargeSyncTaskDetailDAO.updateByPrimaryKey(detail);
            }

            MemberAsset memberAsset = memberAssetDAO.queryAsset(ThressDescUtil.encodeAssetAddress(toAddress), vCoin.getId());
            if (null == memberAsset) {
                LOGGER.error("ETH充值失败, 资产与币种不匹配,address={},vcoin={}", toAddress, vCoin.getId());
                updateEthRechargeSyncTaskDetail(detail, RechargeSyncTaskStatus.FAIL, "MemberAssetNotExistedError");
                return TaskResult.FAILED;
            }

            try {
                boolean checkResult = ethApiUtil.parity_testPassword(toAddress, ThressDescUtil.decodeAssetPwd(memberAsset.getAccountPwd(), memberAsset.getIv()));
                if (!checkResult) {
                    LOGGER.error("eth资产地址:{},钱包信息不匹配", toAddress);
                    updateEthRechargeSyncTaskDetail(detail, RechargeSyncTaskStatus.FAIL, "ValidatePasswordError");
                    return TaskResult.FAILED;
                }
            } catch (Exception e) {
                LOGGER.error("eth资产地址:{},校验密码查询失败", toAddress, e);
                return TaskResult.RETRIED;
            }

            // 判断确认区块数
            int ethRechargeSuccessfulConfirmCount = configService.getSysRechargeBlockByVcoinId(memberAsset.getVcoinId());
            long ethLastBlockNumber = ethApiUtil.eth_blockNumber();
            if ((ethLastBlockNumber - blockNumber) < ethRechargeSuccessfulConfirmCount) {
                LOGGER.info("ETH充值区块确认数不足, ethblockNum={}, localBlock={}, ethRechargeSuccessfulConfirmCount={}",ethLastBlockNumber, blockNumber, ethRechargeSuccessfulConfirmCount);
                return TaskResult.RETRIED;
            }

            // 充值确认，入账
            AssetTrans assetTrans = buildAssetTrans(memberAsset.getId(), transaction, transValue, vCoin);
            AssetRecharge assetRecharge = buildAssetRecharge(memberAsset, assetTrans, encodeAddress, vCoin);
            assetTransDAO.insert(assetTrans);
            assetRechargeDAO.insert(assetRecharge);
            memberAssetDAO.rechargeUpdate(memberAsset.getId(), assetTrans.getTransAmount());
            return TaskResult.SUCCESSFUL;
        } catch (Exception e) {
            LOGGER.error("eth区块同步异常:{}", e);
            return TaskResult.RETRIED;
        }
    }


    private AssetTrans buildAssetTrans(String assetId, Transaction transaction, String transValue, Vcoin vCoin) {
        AssetTrans assetTrans = new AssetTrans();
        assetTrans.setId(UUIDUtil.get32UUID());
        assetTrans.setTransType(1);
        assetTrans.setTradeType("1");
        assetTrans.setTransReceipt(JSON.toJSONString(transaction));
        assetTrans.setTransNo(transaction.getHash());
        assetTrans.setTransTime(new Date());
        assetTrans.setAssetId(assetId);
        assetTrans.setTransAmount(parseAmount(vCoin.getScale(), transValue));
        return assetTrans;
    }

    private AssetRecharge buildAssetRecharge(MemberAsset memberAsset, AssetTrans assetTrans, String encodeAddress, Vcoin vCoin) {
        AssetRecharge recharge = new AssetRecharge();
        recharge.setId(UUIDUtil.get32UUID());
        recharge.setVcoinId(memberAsset.getVcoinId());
        recharge.setTxReceipt(assetTrans.getTransReceipt());
        recharge.setReceiptTime(assetTrans.getTransTime());
        recharge.setTxHash(assetTrans.getTransNo());
        recharge.setTxToken(vCoin.getVcoinToken());
        recharge.setRechargeAddress(encodeAddress);
        recharge.setMemberId(memberAsset.getMemberId());
        recharge.setAssetAddress(encodeAddress);
        recharge.setAssetId(assetTrans.getAssetId());
        recharge.setRechargeValue(assetTrans.getTransAmount());
        return recharge;
    }

    private BigDecimal parseAmount(Integer scale, String transValue) {
        BigInteger decodeValue = null;
        if (transValue.indexOf("0x") > -1) {
            decodeValue = Numeric.decodeQuantity(transValue);
        } else {
            decodeValue = new BigInteger(transValue);
        }
        String divid = String.valueOf(Math.pow(10, scale));
        BigDecimal transDecimal = new BigDecimal(decodeValue);
        return transDecimal.divide(new BigDecimal(divid));
    }

    private void updateEthRechargeSyncTaskDetail(EthRechargeSyncTaskDetail detail, RechargeSyncTaskStatus status, String failReason) {
        if (null != status) {
            detail.setStatus(status.getValue());
        }
        detail.setFailReason(failReason);
        Date current = new Date();
        detail.setUpdateTime(current);
        ethRechargeSyncTaskDetailDAO.updateByPrimaryKey(detail);
    }

    private void updateEthRechargeSyncTaskDetail(EthRechargeSyncTaskDetail detail, Transaction transaction) {
        if (detail.getTransactionHash().isEmpty()) {
            detail.setTransactionHash(transaction.getHash());
            detail.setFromAddress(transaction.getFrom());
            detail.setToAddress(transaction.getTo());
            detail.setAmount(transaction.getValue().toString());
            Date current = new Date();
            detail.setUpdateTime(current);
            ethRechargeSyncTaskDetailDAO.updateByPrimaryKey(detail);
        }
    }

}
