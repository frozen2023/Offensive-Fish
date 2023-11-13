package com.fish.offensivefish.controller;

import cn.dev33.satoken.util.SaResult;
import com.fish.offensivefish.service.UserService;
import com.fish.offensivefish.util.SendCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/User")
public class UserController{
    @Autowired
    UserService userService;
    @RequestMapping("/sendMsg")
    String sendMsg(HttpServletRequest request, HttpServletResponse response, String userName)
    {
        SendCode sendCode=new SendCode();
        return sendCode.SendCode(response,request,userName);
    }
    @RequestMapping("/login")
    public Map<String,Object> login(String userName, String passwd){
        return userService.login(userName,passwd);
    }
    @RequestMapping("/changePasswordByTelephone")
    public Map<String,Object> changePasswordByTelephone(HttpServletRequest request, String userName, String validate, String passwd){
        return userService.changePasswordByTelephone(request,userName,validate,passwd);
    }
    @RequestMapping("/enroll")
    public Map<String,Object> enroll(HttpServletRequest request, String userName, String passwd, String name,String validate){
        return userService.enroll(request,userName,passwd,name,validate);
    }
    @RequestMapping("/loginOut")
    public  Map<String,Object> loginOut(String userName){
        return userService.loginOut(userName);
    }

}
