package com.fish.offensivefish.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankList {
    String firstPlayerId;//第一个玩家
    String secondPlayerId;
    int score;//得分
    int rank;//排名
    String endTime;//生成时间
}
