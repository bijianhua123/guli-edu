package com.bijianhua.guli.service.sms.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.common.base.result.ResultCodeEnum;
import com.bijianhua.guli.common.base.util.ExceptionUtils;
import com.bijianhua.guli.common.base.util.RedisConstants;
import com.bijianhua.guli.common.base.util.RegexUtils;
import com.bijianhua.guli.service.base.exception.GuliException;
import com.bijianhua.guli.service.sms.service.SmsService;
import com.bijianhua.guli.service.sms.util.SmsProperties;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.bijianhua.guli.common.base.util.RedisConstants.LOGIN_CODE_KEY;
import static com.bijianhua.guli.common.base.util.RedisConstants.LOGIN_CODE_TTL;

/**
 * @author bijianhua
 * @since 2023/3/18
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SmsProperties smsProperties;


    @Override
    public R sendCode(String phone) {
        //1、检验手机号是否满足正则
        if (RegexUtils.isPhoneInvalid(phone)) {
            //2、如果不满足直接抛出异常
            log.error("手机号不正确");
            throw new GuliException(ResultCodeEnum.LOGIN_PHONE_ERROR);
        }
        //3、生成随机6位数验证码
        String randomNumbers = RandomUtil.randomNumbers(6);

        //4、先存储到redis中 设置有效TTL为5分钟
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, randomNumbers, LOGIN_CODE_TTL, TimeUnit.MINUTES);
        //5、调用阿里云SMS短信发送服务
        //send(phone, randomNumbers);

        //测试环境:发送验证码
        log.info("发送了验证码:" + randomNumbers);
        return R.ok().message("短信发送成功");
    }

    @Override
    public void send(String phone, String checkCode) {
        //调用短信发送SDK 创建client对象
        DefaultProfile profile = DefaultProfile.getProfile(
                smsProperties.getRegionId(),
                smsProperties.getKeyId(),
                smsProperties.getKeySecret()
        );
        IAcsClient client = new DefaultAcsClient(profile);
        //组装请求参数
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", smsProperties.getRegionId());
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", smsProperties.getSignName());
        request.putQueryParameter("TemplateCode", smsProperties.getTemplateCode());

        Map<String, Object> param = new HashMap<>();
        param.put("code", checkCode);

        //将包含验证码的集合转换为json字符串
        Gson gson = new Gson();
        request.putQueryParameter("TemplateParam", gson.toJson(param));

        //发送短信
        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
        } catch (ClientException e) {
            log.error(ExceptionUtils.getMessage(e));
        }

        //得到json字符串格式的响应结果
        String data = response.getData();

        //解析json字符串格式的响应结果
        HashMap<String, String> map = gson.fromJson(data, HashMap.class);
        String code = map.get("Code");
        String message = map.get("Message");

        //配置参考：短信服务->系统设置->国内消息设置
        //错误码参考：
        //https://help.aliyun.com/document_detail/101346.html?spm=a2c4g.11186623.6.613.3f6e2246sDg6Ry
        //控制所有短信流向限制（同一手机号：一分钟一条、一个小时五条、一天十条）
        if ("isv.BUSINESS_LIMIT_CONTROL".equals(code)) {
            log.error("短信发送过于频繁 " + "【code】" + code + ", 【message】" + message);
            throw new GuliException(ResultCodeEnum.SMS_SEND_ERROR_BUSINESS_LIMIT_CONTROL);
        }

        if (!"OK".equals(code)) {
            log.error("短信发送失败 " + " - code: " + code + ", message: " + message);
            throw new GuliException(ResultCodeEnum.SMS_SEND_ERROR);
        }
    }
}
