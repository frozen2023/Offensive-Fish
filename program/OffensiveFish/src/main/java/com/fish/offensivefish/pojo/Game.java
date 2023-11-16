package com.fish.offensivefish.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
        int roomName;//房间号
        int gameId;//游戏号
        Date runTime;//运行时间
        String firstPlayerId;//第一个玩家
        String secondPlayerId;//第二个玩家
        int score;//得分
}
