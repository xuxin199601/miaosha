package com.xuxin.miaosha.controller;

import com.xuxin.miaosha.domain.MiaoshaOrder;
import com.xuxin.miaosha.domain.MiaoshaUser;
import com.xuxin.miaosha.domain.OrderInfo;
import com.xuxin.miaosha.result.CodeMsg;
import com.xuxin.miaosha.service.GoodsService;
import com.xuxin.miaosha.service.MiaoshaService;
import com.xuxin.miaosha.service.OrderService;
import com.xuxin.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String miaosha(Model model, MiaoshaUser user, @RequestParam("goodsId") Long goodsId) {
        // 用户未登陆
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);

        // 判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            // 库存不足，秒杀已结束，跳转到秒杀失败页面
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER);
            return "miaosha_fail";
        }

        // 判断之前是否已经秒杀到商品
        // 根据用户ID和商品ID查找订单，若不为空，则说明之前已经秒杀到商品，此次秒杀为重复秒杀
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA);
            return "miaosha_fail";
        }

        // 通过之前的校验，开始秒杀
        // 减库存，下订单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);

        return "goods_detail";
    }
}
