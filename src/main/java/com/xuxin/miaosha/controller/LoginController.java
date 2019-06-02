package com.xuxin.miaosha.controller;

import com.xuxin.miaosha.result.Result;
import com.xuxin.miaosha.service.MiaoshaUserService;
import com.xuxin.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    // 日志记录
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    // 跳转到登录页面
    @RequestMapping("to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse respons, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());
        // 登录
        miaoshaUserService.login(respons, loginVo);
        return Result.success(true);
    }
}
