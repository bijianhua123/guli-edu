package com.bijianhua.guli.service.edu.controller;


import com.bijianhua.guli.common.base.result.R;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("user")
public class LoginController {

    /**
     * 登录
     *
     * @return
     */
    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    /**
     * 返回用户信息
     *
     * @return
     */
    @GetMapping("info")
    public R info() {
        return R.ok().data("name", "admin").
                data("roles", "[admin]").
                data("avatar", "https://guli-file-bjh.oss-cn-beijing.aliyuncs.com/avatar/v.webp");
    }


    /**
     * 登出
     *
     * @return
     */
    @PostMapping("logout")
    public R logout() {
        return R.ok();
    }
}
