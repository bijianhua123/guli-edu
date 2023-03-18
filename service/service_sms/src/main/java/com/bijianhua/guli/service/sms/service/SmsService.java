package com.bijianhua.guli.service.sms.service;

import com.aliyuncs.exceptions.ClientException;
import com.bijianhua.guli.common.base.result.R;

import java.util.Map;

/**
 * @author bijianhua
 * @since 2023/3/18
 */
public interface SmsService {


    /**
     * 根据手机号发送验证码
     *
     * @param phone 手机号
     * @return
     */
    R sendCode(String phone);

    /**
     * 阿里云短信服务发送
     *
     * @param phone     发送的手机号
     * @param checkCode 发送的验证码
     */
    void send(String phone, String checkCode) throws ClientException;
}
