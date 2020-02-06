package com.wyl.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description LoginController
 * @Author YiLong Wu
 * @Date 2020/2/5 15:55
 * @Version 1.0.0
 */
@RestController
public class LoginController {

    @RequestMapping(value = "/loginSuccess",produces = {"text/plain;charset=utf-8"})
    public String loginSuccess() {

        return getUsername()+ "登录成功";
    }

    @GetMapping(value = "/r/r1",produces = {"text/plain;charset=utf-8"})
    public String getResource() {
        return "访问资源";
    }

    @GetMapping(value = "/r/r2",produces = {"text/plain;charset=utf-8"})
    public String getResource1() {
        return "访问资源2";
    }

    // 获取用户的身份信息
    private String getUsername() {

        // 通过security这个上下文的环境获得用户的身份
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        System.out.println("***************"+principal);
        if(principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            return userDetails.getUsername();
        }else {
            return principal.toString();
        }

    }
}
