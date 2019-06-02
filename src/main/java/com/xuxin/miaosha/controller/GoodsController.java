package com.xuxin.miaosha.controller;

import com.xuxin.miaosha.domain.MiaoshaUser;
import com.xuxin.miaosha.service.GoodsService;
import com.xuxin.miaosha.service.MiaoshaUserService;
import com.xuxin.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/to_list")
    public String list(Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        // 查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    /**
     * 跳转到详情页，restful风格
     */
    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, MiaoshaUser user, @PathVariable Long goodsId) {
        model.addAttribute("user", user);

        // 根据商品 ID 查询商品详情
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        // 获取秒杀开始与结束的时间，当前时间
        Long startAt = goods.getStartDate().getTime();
        Long endAt = goods.getEndDate().getTime();
        Long now = System.currentTimeMillis();

        // 获取秒杀商品的秒杀状态，并计算距离秒杀开始的剩余时间
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if (now < startAt) {
            // 秒杀未开始
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {
            // 秒杀已结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            // 秒杀正在进行
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);

        return "goods_detail";
    }
}
