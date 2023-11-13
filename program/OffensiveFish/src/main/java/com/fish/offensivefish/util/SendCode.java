package com.fish.offensivefish.util;

import com.zhenzi.sms.ZhenziSmsClient;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SendCode {
   public String SendCode(HttpServletResponse response, HttpServletRequest request, String userName)
    {
        String result="";
        try {
            //生成6位验证码
            String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
            //发送短信
            ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com","113480","YjljNDJjZjItYjUzMi00MGEwLTgzODAtNjdjNDliMGIyYTkz");
            Map<String, Object> map=new HashMap<>();
            map.put("number", userName);
            map.put("templateId", "12195");
            String[] templateParams = new String[2];
            templateParams[0] = verifyCode;
            templateParams[1] = "5分钟";
            map.put("templateParams", templateParams);
            result = client.send(map);
            String userAgent = request.getHeader("USER-AGENT").toLowerCase();
            String verifyCode1 ;
            // 如果是火狐浏览器
            if (userAgent.contains("firefox")) {
                 verifyCode1 = new String(verifyCode.getBytes(), "ISO8859-1");
            } else {

               verifyCode1= URLEncoder.encode(verifyCode, "UTF-8");
            }
            request.getSession().setAttribute("VerifyCode", verifyCode);
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-disposition", "attachment;VerifyCode" +
                    "=" + verifyCode);
            System.out.println("sendMsg"+request.getSession().getAttribute("VerifyCode"));
            // 将认证码存入SESSION
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
