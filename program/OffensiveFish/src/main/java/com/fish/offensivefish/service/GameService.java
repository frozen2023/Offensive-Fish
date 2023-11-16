package com.fish.offensivefish.service;

import com.fish.offensivefish.pojo.Game;

import java.util.Map;

public interface GameService {
    public Map<String,Object> addGame(Game game);
    public Map<String,Object> selectGameById(int gameId);
    public Map<String,Object> IsEnd(int gameId);//结束则调用删除Game的函数以及增加记录
}
