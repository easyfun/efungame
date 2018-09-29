package com.efun.wallet.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.efun.game.common.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/30.
 */
@Component
public class EthApiUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EthApiUtil.class);

    @Value("${eth.wallet.parity.url}")
    private String parityUrl;// ="http://192.168.1.232:8588";
//    private String parityUrl ="http://192.168.1.232:8588";

//    private String rpcUser;

//    private String rpcPassword;

//    private String rpcPort;

    public JSONObject requestParity(Map<String, Object> map) {
        map.put("id", new Integer(1));
        map.put("jsonrpc", "2.0");

        String result = null;
        try {
            result = HttpUtil.postRequest2(parityUrl, JSON.toJSONString(map), "UTF-8");
        } catch (Exception e) {
            LOGGER.error("请求失败.", e);
            return null;
        }

        if (StringUtils.isEmpty(result) || StringUtils.isBlank(result)) {
            LOGGER.error("应答为空.");
            return null;
        }

        return JSON.parseObject(result);
    }

    public long eth_blockNumber() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("method", "eth_blockNumber");
        map.put("params", new ArrayList<String>());

        JSONObject json = requestParity(map);
        if (null == json) {
            return -1;
        }

        String number = json.getString("result");
        long blockNumber = -1;
        if (number.startsWith("0x") || number.startsWith("0X")) {
            blockNumber = Long.parseLong(number.substring(2), 16);
        } else {
            blockNumber = Long.parseLong(number.substring(2), 16);
        }
        return blockNumber;
    }

    public long eth_getBlockTransactionCountByNumber(long number) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("method", "eth_getBlockTransactionCountByNumber");
        ArrayList<String> params = new ArrayList<String>();
        params.add("0x"+Long.toHexString(number));
        map.put("params", params);

        JSONObject json = requestParity(map);
        if (null == json) {
            return -1;
        }

        String count = json.getString("result");
        long transactionCount = -1;
        if (count.startsWith("0x") || count.startsWith("0X")) {
            transactionCount = Long.parseLong(count.substring(2), 16);
        } else {
            transactionCount = Long.parseLong(count.substring(2), 16);
        }
        return transactionCount;
    }

    public Transaction eth_getTransactionByBlockNumberAndIndex(long blockNumber, long transactionIndex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("method", "eth_getTransactionByBlockNumberAndIndex");
        ArrayList<String> al = new ArrayList<String>();
        al.add("0x" + Long.toHexString(blockNumber));
        al.add("0x" + Long.toHexString(transactionIndex));
        map.put("params", al);

        JSONObject json = requestParity(map);

        Transaction transaction =JSONObject.parseObject(json.getString("result"),Transaction.class);

        return transaction;
    }

    public Boolean parity_testPassword(String address, String pwd) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("method", "parity_testPassword");
        ArrayList<String> al = new ArrayList<String>();
        al.add(address);
        al.add(pwd);
        map.put("params", al);

        JSONObject json = requestParity(map);
        Boolean result = (Boolean) json.get("result");

        return result;
    }
 }
