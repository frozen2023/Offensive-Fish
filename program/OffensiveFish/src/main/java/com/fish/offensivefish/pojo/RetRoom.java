package com.fish.offensivefish.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//room大厅的返回类
public class RetRoom{
    int roomId;
    String roomOwnerId;//房主账号
    String roomOwnerName;//房主姓名
    String playerId;//第二个人的id
    int numbers;
    int isOpen;//是否开始运行游戏
}