package com.fish.offensivefish.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room{
    int roomId;//房间名，从10001开始，自增（主键）
    String roomOwnerId;//房主账号
    String playerId;//第二个人的id
    String code;//邀请码
    int numbers;//当前房间人数
    int isOpen;
}