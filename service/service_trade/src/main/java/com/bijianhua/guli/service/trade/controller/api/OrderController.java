package com.bijianhua.guli.service.trade.controller.api;


import com.bijianhua.guli.common.base.result.R;
import com.bijianhua.guli.common.base.util.JwtInfo;
import com.bijianhua.guli.common.base.util.JwtUtils;
import com.bijianhua.guli.service.trade.entity.Order;
import com.bijianhua.guli.service.trade.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Bijianhua
 * @since 2023-03-19
 */
@Slf4j
@CrossOrigin
@Api(tags = "网站订单管理")
@RestController
@RequestMapping("api//trade/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("新增订单")
    @PostMapping("auth/save/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);

        String orderId = orderService.createOrderByCourseIdAndUserId(courseId, jwtInfo.getId());
        return R.ok().data("orderId", orderId).message("创建订单成功");
    }

    @ApiOperation("获取订单")
    @GetMapping("auth/get/{orderId}")
    public R getOrderById(@PathVariable String orderId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        Order order = orderService.selectOrderByOrderIdAndUserId(orderId, jwtInfo.getId());
        return R.ok().data("item", order).message("获取订单信息成功");
    }

    @ApiOperation("判断课程是否购买")
    @GetMapping("auth/is-buy/{courseId}")
    public R isBuyByCourseIdAndMemberId(@PathVariable String courseId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        boolean isBuy = orderService.isBuyByCourseIdAndMemberId(courseId, jwtInfo.getId());
        return R.ok().data("isBuy", isBuy);
    }

}

