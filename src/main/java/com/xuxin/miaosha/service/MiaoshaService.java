package com.xuxin.miaosha.service;

import com.xuxin.miaosha.domain.MiaoshaUser;
import com.xuxin.miaosha.domain.OrderInfo;
import com.xuxin.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    /**
     * 秒杀服务，减库存，下单（订单、秒杀订单）；作为一个事务进行处理
     */
    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        // 减库存
        goodsService.reduceStock(goods);
        // 下订单（两单），并返回订单信息
        return orderService.createOrder(user, goods);
    }
}
