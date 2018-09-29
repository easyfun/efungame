package com.mexc.sun.core.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.mexc.sun.framework.common.dto.base.BaseResultDTO;
import com.mexc.sun.framework.common.exception.BusinessException;
import com.mexc.sun.framework.common.exception.SystemErrorCode;
import com.mexc.sun.framework.redis.RedisService;
import com.mexc.sun.core.member.dao.MemberDAO;
import com.mexc.sun.core.service.dto.MemberRegisterParamDTO;
import com.mexc.sun.core.service.dubbo.MemberDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by easyfun on 2018/4/23.
 */
@Service
public class MemberServiceDubboImpl implements MemberDubboService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceDubboImpl.class);

    @Autowired
    private RedisService redisService;

    @Resource
    private MemberDAO memberDAO;

    @Override
    public BaseResultDTO register(MemberRegisterParamDTO paramDTO) {
        try {
            return doRegister(paramDTO);
        } catch (BusinessException e) {
            LOGGER.error("注册失败. paramDTO={}", JSON.toJSONString(paramDTO) ,e);
            return BaseResultDTO.fail(e.getErrorCode().getFailCode());
        } catch (Exception e) {
            LOGGER.error("注册失败. paramDTO={}", JSON.toJSONString(paramDTO), e);
            return BaseResultDTO.fail(SystemErrorCode.systemException.getFailCode());
        }
    }

    private BaseResultDTO doRegister(MemberRegisterParamDTO paramDTO) {
//        String registerKey = RedisKey.Member.buildMemberRegisterKeyByEmail(paramDTO.getEmail());
//        String sMemberPwdSalt = (String) redisService.hGet(registerKey, RedisKey.Member.F_MEMBER_PWD_SALT);
//        String sTradePwdSalt = (String) redisService.hGet(registerKey, RedisKey.Member.F_TRADE_PWD_SALT);
//
//        // 检查验证码
//        validateCaptchaCode(paramDTO);
//
//        // 检查用户是否已经注册
//        validateMemberRegistered(paramDTO);

        BaseResultDTO resultDTO = BaseResultDTO.success();
        return resultDTO;
    }

    private void validateCaptchaCode(MemberRegisterParamDTO paramDTO) {
//
//        String captchaCode = (String) redisService.hGet(registerKey, RedisKey.Member.F_CAPTCHA_CODE);
//        if (StringUtils.isBlank(captchaCode)) {
//            // 验证码失效
//            throw new BusinessException(MemberErrorCode.registerCaptchaCodeTimeout);
//        }
//
//        String registerCaptchaCode = paramDTO.getEncodeRegisterCaptchaCode();
//        // TODO: base64解码bytes, bytes按位异或memberPwdSalt, bytes按位异或memberPwdSalt
//        if (!captchaCode.equalsIgnoreCase(registerCaptchaCode)) {
//            // 验证码错误
//            throw new BusinessException(MemberErrorCode.registerCaptchaCodeError);
//        }
    }

    private void validateMemberRegistered(MemberRegisterParamDTO paramDTO) {
//        Member member = memberDAO.selectByEmail(paramDTO.getEmail());
//        if (member != null) {
//            // Email用户已注册
//            throw new BusinessException(MemberErrorCode.emailMemberHasRegistered);
//        }
    }
}
