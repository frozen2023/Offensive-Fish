package com.fish.offensivefish.service;

import cn.dev33.satoken.util.SaResult;
import com.fish.offensivefish.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {
    public Map<String,Object> login(String userName, String passwd);
    public Map<String,Object> changePasswordByTelephone(HttpServletRequest request,String userName,String validate,String passwd);
    public Map<String,Object> enroll(HttpServletRequest request, String userName, String password, String name,String validate);
    public  Map<String,Object> loginOut(String userName);
}