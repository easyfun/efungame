package com.efun.wallet.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.efun.game.common.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/30.
 */
@Component
public class BtcApiUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(BtcApiUtil.class);

    @Value("${btc.wallet.parity.url}")
    private String bitCoinUrl;
//    private String bitCoinUrl ="http://192.168.1.233:8332";

    @Value("${btc.wallet.rpc.user}")
    private String rpcUser;

    @Value("${btc.wallet.prc.password}")
    private String rpcPassword;

//    private String rpcPort;

    public long getBlockCount() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("method", "getblockcount");
        map.put("params", new ArrayList<String>());

        JSONObject json = requestBitCoinCore(map);
        if (null == json) {
            return -1;
        }

        String number = json.getString("result");
        long blockNumber = -1;
        if (number.startsWith("0x") || number.startsWith("0X")) {
            blockNumber = Long.parseLong(number.substring(2), 16);
        } else {
            blockNumber = Long.parseLong(number);
        }
        return blockNumber;
    }

    public JSONObject listTransactions(String account, Long count, Long from) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("method", "listtransactions");

        List<Object> params = new ArrayList<>();
        params.add(account);
        params.add(count);
        params.add(from);
        map.put("params", params);

        return requestBitCoinCore(map);
    }

    public JSONObject gettransaction(String txid) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("method", "gettransaction");

        List<Object> params = new ArrayList<>();
        params.add(txid);
        map.put("params", params);

        return requestBitCoinCore(map);
    }


    public JSONObject requestBitCoinCore(Map<String, Object> map) {
        map.put("id", new Integer(1));
        map.put("jsonrpc", "2.0");

        String result = null;
        try {
            result = HttpUtil.postRequest2(bitCoinUrl, JSON.toJSONString(map), "UTF-8", rpcUser, rpcPassword);
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
}
