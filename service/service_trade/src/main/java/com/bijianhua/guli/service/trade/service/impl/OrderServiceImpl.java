package com.bijianhua.guli.service.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bijianhua.guli.common.base.result.ResultCodeEnum;
import com.bijianhua.guli.common.base.util.RedisIdWorker;
import com.bijianhua.guli.service.base.dto.CourseDto;
import com.bijianhua.guli.service.base.dto.MemberDto;
import com.bijianhua.guli.service.base.exception.GuliException;
import com.bijianhua.guli.service.trade.entity.Order;
import com.bijianhua.guli.service.trade.feign.EduCourseService;
import com.bijianhua.guli.service.trade.feign.UcenterMemberService;
import com.bijianhua.guli.service.trade.mapper.OrderMapper;
import com.bijianhua.guli.service.trade.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    //注入远程调用
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private UcenterMemberService ucenterMemberService;

    //注入redis操作
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String createOrderByCourseIdAndUserId(String courseId, String memberId) {
        //查询用户是否已经拥有当前课程,如果有直接返回查询到的订单信息
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getCourseId, courseId).eq(Order::getMemberId, memberId);
        Order orderExist = baseMapper.selectOne(queryWrapper);
        if (orderExist != null) {
            return orderExist.getId();
        }
        //通过远程调用查询课程信息
        CourseDto courseDtoById = eduCourseService.getCourseDtoById(courseId);
        //如果为空表示要么没查到 要么远程接口熔断保护 直接抛出异常
        if (courseDtoById == null) {
            throw new GuliException(ResultCodeEnum.PARAM_ERROR);
        }
        //同上
        MemberDto memberDto = ucenterMemberService.getMemberDtoByMemberId(memberId);
        if (memberDto == null) {
            throw new GuliException(ResultCodeEnum.PARAM_ERROR);
        }

        //redis生成全局唯一ID
        RedisIdWorker redisIdWorker = new RedisIdWorker(stringRedisTemplate);
        long id = redisIdWorker.nextId("order");
        //创建订单
        Order order = new Order();
        //订单号 使用redis全局唯一Id订单号
        order.setOrderNo(String.valueOf(id));
        order.setCourseId(courseId);
        order.setCourseTitle(courseDtoById.getTitle());
        order.setCourseCover(courseDtoById.getCover());
        order.setTeacherName(courseDtoById.getTeacherName());
        order.setTotalFee(courseDtoById.getPrice().multiply(new BigDecimal(100)));//分
        order.setMemberId(memberId);
        order.setMobile(memberDto.getMobile());
        order.setNickname(memberDto.getNickname());
        order.setStatus(0);//未支付
        order.setPayType(1);//微信支付
        baseMapper.insert(order);
        return order.getId();

    }

    @Override
    public Order selectOrderByOrderIdAndUserId(String orderId, String memberId) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getId, orderId)
                .eq(Order::getMemberId, memberId);

        return baseMapper.selectOne(queryWrapper);


    }

    @Override
    public boolean isBuyByCourseIdAndMemberId(String courseId, String memberId) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getCourseId, courseId)
                .eq(Order::getMemberId, memberId)
                .eq(Order::getStatus, 1);
        Integer isBuy = baseMapper.selectCount(queryWrapper);

        return isBuy > 0;

    }
}
