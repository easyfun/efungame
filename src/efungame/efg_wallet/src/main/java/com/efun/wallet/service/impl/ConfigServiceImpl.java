package com.efun.wallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.efun.wallet.dao.VcoinDAO;
import com.efun.wallet.service.ConfigService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/4/2.
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServiceImpl.class);

    public static final String SYS_DICT = "sys_dict";

    @Resource
    private VcoinDAO vcoinDAO;

    @Autowired
    private StringRedisTemplate redisZeroTemplate;

    @Override
    public int getSysRechargeBlockByVcoinId(String vcoinId) throws RuntimeException {
        String config = (String) redisZeroTemplate.opsForHash().get(SYS_DICT, vcoinId);
        if (StringUtils.isEmpty(config) || StringUtils.isBlank(config)) {
            LOGGER.error("config=null, vcoinId={}", vcoinId);
            throw new RuntimeException("getSysRechargeBlockByVcoinId exception");
        }

        JSONObject configJson = null;
        try {
            configJson = JSON.parseObject(config);
        }catch (Exception e) {
            LOGGER.error("configJson parse error, vcoinId={}", vcoinId, e);
            throw new RuntimeException("getSysRechargeBlockByVcoinId exception");
        }

        String sysRechargeBlock = configJson.getString("sysRechargeBlock");
        if (StringUtils.isEmpty(sysRechargeBlock) || StringUtils.isBlank(sysRechargeBlock)) {
            LOGGER.error("sysRechargeBlock=null, vcoinId={}", vcoinId);
            throw new RuntimeException("getSysRechargeBlockByVcoinId exception");
        }

        int confirmtions = -1;
        try {
            confirmtions = Integer.valueOf(sysRechargeBlock);
        } catch (Exception e) {
            LOGGER.error("sysRechargeBlock parse error.", e);
            confirmtions = -1;
        }

        if (-1 == confirmtions) {
            throw new RuntimeException("getSysRechargeBlockByVcoinId exception");
        }

        return confirmtions;
    }
}
