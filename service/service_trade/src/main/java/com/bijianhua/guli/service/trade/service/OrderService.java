package com.bijianhua.guli.service.trade.service;

import com.bijianhua.guli.service.trade.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-19
 */
public interface OrderService extends IService<Order> {

    /**
     * 根据课程Id和会员Id创建订单
     *
     * @param courseId 课程Id
     * @param memberId 会员Id
     * @return 订单号
     */
    String createOrderByCourseIdAndUserId(String courseId, String memberId);

    /**
     * 根据订单Id和会员Id获取订单信息
     *
     * @param orderId  订单Id
     * @param memberId 会员Id
     * @return 订单信息
     */
    Order selectOrderByOrderIdAndUserId(String orderId, String memberId);

    /**
     * 根据课程Id和会员Id判断该会员是否购买了此课程
     *
     * @param courseId 课程Id
     * @param memberId 会员id
     * @return
     */
    boolean isBuyByCourseIdAndMemberId(String courseId, String memberId);
}
