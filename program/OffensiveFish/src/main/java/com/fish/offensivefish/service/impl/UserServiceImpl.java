package com.fish.offensivefish.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.fish.offensivefish.mapper.UserMapper;
import com.fish.offensivefish.pojo.User;
import com.fish.offensivefish.service.UserService;
import com.fish.offensivefish.util.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.fish.offensivefish.util.AESUtil.parseByte2HexStr;
import static com.fish.offensivefish.util.AESUtil.parseHexStr2Byte;
import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    public SaResult login(String userName, String passwd) {
        if(userMapper.selectUser(userName)!=null)
        {       User user=userMapper.selectUser(userName);;
                byte[] twoStrResult = parseHexStr2Byte(user.getPasswd());
                System.out.println("二进制"+twoStrResult);
                byte[] password1=AESUtil.decrypt(twoStrResult,"password123");
                String pwd=new String(password1);
                if(pwd.equals(passwd))
                {
                    userMapper.updateStatus(1,userName);
                    StpUtil.login(userName);
                    SaTokenInfo tokenInfo = StpUtil.getTokenInfo();;
                    return  SaResult.ok("登入成功！");
                }
                return  SaResult.error("登入失败，密码输入错误！");
            }

            return SaResult.error("登入失败，账号输入错误！");


    }
    public Map<String,Object> changePasswordByTelephone(HttpServletRequest request,String userName,String validate,String passwd){

        Map<String,Object> msg=new HashMap<>();
        if(userMapper.selectUser(userName)!=null)
        {
            Object verifyCode=request.getSession().getAttribute("verifyCode");
            if(validate.equals(verifyCode)||verifyCode==null)
            {
                byte[] password1= AESUtil.encrypt(passwd,"password123");
                String hexStrResult = parseByte2HexStr(password1);
                userMapper.updateUserPasswd(userName,hexStrResult);
                msg.put("msg","更改密码成功！");
            }

            else msg.put("msg","验证码输入错误!");;
        }
        else msg.put("msg","账号输入错误!");
        return msg;
    }
    public Map<String,Object> enroll(HttpServletRequest request, String userName, String passwd, String name,String validate){
       Map<String,Object> map=new HashMap<>();
        Object verifyCode=request.getSession().getAttribute("verifyCode");
        System.out.println(verifyCode);
        if(userMapper.selectUser(userName)==null)
        {
            User user=new User();
            if(validate.equals(verifyCode))
            {
                byte[] password= AESUtil.encrypt(passwd,"password123");
                String hexStrResult = parseByte2HexStr(password);
                user.setName(name);
                user.setUserName(userName);
                user.setPasswd(hexStrResult);
                userMapper.insertUser(user);
                map.put("msg","注册成功！");
           }
            else map.put("msg","验证码输入错误!");
        }
        else map.put("msg","该账号已经注册");
        return map;
    }
    public  SaResult loginOut(String userName){
        User user=userMapper.selectUser(userName);
        userMapper.updateStatus(0,userName);
        StpUtil.logout();
        return SaResult.ok("登出成功！");
    }

}
