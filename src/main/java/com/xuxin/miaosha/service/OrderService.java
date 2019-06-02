package com.xuxin.miaosha.service;

import com.xuxin.miaosha.dao.OrderDao;
import com.xuxin.miaosha.domain.MiaoshaOrder;
import com.xuxin.miaosha.domain.MiaoshaUser;
import com.xuxin.miaosha.domain.OrderInfo;
import com.xuxin.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long id, Long goodsId) {
        return orderDao.getMiaoshaOrderByUserIdGoodsId(id, goodsId);
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
        // 创建 order_info 表
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(user.getId());
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setCreateDate(new Date());
        // 插入数据库
        Long orderId = orderDao.insertOrderInfo(orderInfo);

        // 创建 miaosha_order 表
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderId);
        // 插入数据库
        orderDao.insertMiaoshaOrder(miaoshaOrder);
        return orderInfo;
    }
}
