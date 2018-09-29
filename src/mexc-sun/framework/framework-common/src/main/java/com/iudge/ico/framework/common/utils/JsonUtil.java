package com.iudge.ico.framework.common.utils;

import com.alibaba.fastjson.JSON;

import java.util.Date;

/**
 * Created by easyfun on 2018/6/5.
 */
public class JsonUtil {

    public static String toJSONString(Object object) {
        return JSON.toJSONStringWithDateFormat(object, DateUtil.YYYY_MM_DD_HH_MM_SS);
    }

//    public static void main(String[] args) {
//        System.out.println(toJSONString(new Date()));
//    }
}
