package com.fish.offensivefish.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    private String name;
    private String userName;//使用手机号注册
    private String passwd;//密码
    private int status;//用户状态，0表示未登录，1表示已经登录
}