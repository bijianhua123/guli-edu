package com.bijianhua.guli.service.ucenter.service;

import com.bijianhua.guli.service.base.dto.MemberDto;
import com.bijianhua.guli.service.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bijianhua.guli.service.ucenter.entity.vo.LoginVo;
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

    /**
     * 根据账号密码登录
     *
     * @param loginVo 账号密码实体类
     * @return 返回token信息
     */
    String login(LoginVo loginVo);

    /**
     * 根据会员id获取会员Dto信息
     *
     * @param memberId
     * @return
     */
    MemberDto getMemberDtoByMemberId(String memberId);
}
