package com.bijianhua.guli.service.ucenter.controller.api;

import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.service.ucenter.entity.vo.RegisterVo;
import com.bijianhua.guli.service.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author bijianhua
 * @since 2023/3/18
 */
@Api(tags = "会员管理")
@CrossOrigin
@RestController
@RequestMapping("/api/ucenter/member")
@Slf4j
public class ApiMemberController {
    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(
            @ApiParam(value = "注册信息", required = true)
            @RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }
}
