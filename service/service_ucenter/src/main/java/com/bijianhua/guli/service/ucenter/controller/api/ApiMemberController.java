package com.bijianhua.guli.service.ucenter.controller.api;

import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.common.base.result.ResultCodeEnum;
import com.bijianhua.guli.common.base.util.ExceptionUtils;
import com.bijianhua.guli.common.base.util.JwtInfo;
import com.bijianhua.guli.common.base.util.JwtUtils;
import com.bijianhua.guli.service.base.dto.MemberDto;
import com.bijianhua.guli.service.base.exception.GuliException;
import com.bijianhua.guli.service.ucenter.entity.vo.LoginVo;
import com.bijianhua.guli.service.ucenter.entity.vo.RegisterVo;
import com.bijianhua.guli.service.ucenter.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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


    @ApiOperation("会员登录")
    @PostMapping("login")
    public R login(
            @ApiParam(value = "登录对象", required = true)
            @RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return R.ok().data("token", token).message("登录成功");
    }

    @ApiOperation("获取登录用户信息")
    @GetMapping("get-login-info")
    public R getLoginInfo(HttpServletRequest request) {
        try {
            JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
            return R.ok().data("userInfo", jwtInfo);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new GuliException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }
    }

    @ApiOperation("根据会员id查询会员信息")
    @GetMapping("inner/get-member-dto/{memberId}")
    public MemberDto getMemberDtoByMemberId(
            @ApiParam(value = "会员ID", required = true)
            @PathVariable String memberId) {
        MemberDto memberDto = memberService.getMemberDtoByMemberId(memberId);
        return memberDto;
    }

}
