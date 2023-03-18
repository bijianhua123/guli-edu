package com.bijianhua.guli.service.ucenter.service;

import com.bijianhua.guli.service.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bijianhua.guli.service.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-18
 */
public interface MemberService extends IService<Member> {

    /**
     * 用户注册
     *
     * @param registerVo
     */
    void register(RegisterVo registerVo);
}
