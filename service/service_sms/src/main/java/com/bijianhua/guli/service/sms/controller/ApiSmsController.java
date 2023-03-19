package com.bijianhua.guli.service.sms.controller;

import com.aliyuncs.exceptions.ClientException;
import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.sms.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author bijianhua
 * @since 2023/3/18
 */
@CrossOrigin
@Slf4j
@Api(tags = "阿里云短信服务")
@RestController
@RequestMapping("/api/sms")
public class ApiSmsController {

    @Autowired
    private SmsService smsService;


    @GetMapping("send/{phone}")
    public R sendCode(
            @ApiParam(value = "手机号", required = true)
            @PathVariable String phone) {

        return smsService.sendCode(phone);


    }
}
