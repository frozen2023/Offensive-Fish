package com.fish.offensivefish.util;

import com.zhenzi.sms.ZhenziSmsClient;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SendCode {
   public String SendCode(HttpServletRequest request, String userName)
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
            request.getSession().setAttribute("verifyCode", verifyCode);
            System.out.println("sendMsg"+request.getSession().getId());
            // 将认证码存入SESSION
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
