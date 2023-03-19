package com.bijianhua.guli.service.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.bijianhua.guli.common.base.result.ResultCodeEnum;
import com.bijianhua.guli.common.base.util.JwtInfo;
import com.bijianhua.guli.common.base.util.JwtUtils;
import com.bijianhua.guli.common.base.util.MD5;
import com.bijianhua.guli.common.base.util.RegexUtils;
import com.bijianhua.guli.service.base.dto.MemberDto;
import com.bijianhua.guli.service.base.exception.GuliException;
import com.bijianhua.guli.service.ucenter.entity.Member;
import com.bijianhua.guli.service.ucenter.entity.vo.LoginVo;
import com.bijianhua.guli.service.ucenter.entity.vo.RegisterVo;
import com.bijianhua.guli.service.ucenter.mapper.MemberMapper;
import com.bijianhua.guli.service.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.bijianhua.guli.common.base.util.RedisConstants.LOGIN_CODE_KEY;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-18
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        //先判断手机号是否正确
        if (StringUtils.isEmpty(mobile) || RegexUtils.isPhoneInvalid(mobile)) {
            throw new GuliException(ResultCodeEnum.LOGIN_PHONE_ERROR);
        }
        //判断code、nickname、password是否为空
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new GuliException(ResultCodeEnum.PARAM_ERROR);
        }
        //先去redis中判断
        String redisCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(ResultCodeEnum.CODE_ERROR);
        }
        //再去数据库中判断
        Member member = query().eq("mobile", mobile).one();
        if (member != null) {
            throw new GuliException(ResultCodeEnum.REGISTER_MOBLE_ERROR);
        }
        member = new Member();
        //用户设置默认头像
        member.setAvatar("https://guli-file-bjh.oss-cn-beijing.aliyuncs.com/avatar/v.webp");
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setMobile(mobile);
        member.setDisabled(false);
        //保存
        save(member);
    }

    @Override
    public String login(LoginVo loginVo) {

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //1.先判断输入数据是否合法
        if (StringUtils.isEmpty(mobile) || RegexUtils.isPhoneInvalid(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(ResultCodeEnum.PARAM_ERROR);
        }

        LambdaQueryWrapper<Member> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Member::getMobile, mobile);
        Member member = baseMapper.selectOne(lambdaQueryWrapper);
        //2.判断用户是否存在
        if (member == null) {
            throw new GuliException(ResultCodeEnum.LOGIN_MOBILE_ERROR);
        }
        //3.判断密码是否正确
        if (!MD5.encrypt(password).equals(member.getPassword())) {
            throw new GuliException(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        if (member.getDisabled()) {
            throw new GuliException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }
        //4.如果都正确 保存到JWT中
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setId(member.getId());
        jwtInfo.setAvatar(member.getAvatar());
        jwtInfo.setNickname(member.getNickname());
        //返回Jwt信息 有效时间30分钟
        return JwtUtils.getJwtToken(jwtInfo, 1800);
        //return null;
    }

    @Override
    public MemberDto getMemberDtoByMemberId(String memberId) {
        Member member = baseMapper.selectById(memberId);
        MemberDto memberDto = new MemberDto();
        BeanUtils.copyProperties(member, memberDto);
        return memberDto;
    }
}
