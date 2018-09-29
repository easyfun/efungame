package com.efun.wallet.util;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import net.iharder.Base64;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/30.
 */
public class BtcApiUtilTest {
    public static void main(String[] args) {
        BtcApiUtil btcApiUtil = new BtcApiUtil();
        System.out.println(btcApiUtil.getBlockCount());
    }

    private static void jsonRpc() throws Throwable {
        String cred = Base64.encodeBytes(("mexc_user" + ":" +"1q2w3e4r$1").getBytes());
        Map<String, String> headers = new HashMap<String, String>(1);
        //身份认证
        headers.put("Authorization", "Basic " + cred);
        JsonRpcHttpClient client = new JsonRpcHttpClient(new URL("http://192.168.1.233:8332"),headers);
        String result = (String) client.invoke("getblockhash",new Object[]{1},Object.class);

        System.out.println(result);
    }
}
