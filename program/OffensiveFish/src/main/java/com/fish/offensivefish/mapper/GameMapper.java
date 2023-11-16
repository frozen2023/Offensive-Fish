package com.fish.offensivefish.mapper;

import com.fish.offensivefish.pojo.Game;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameMapper {
    public void InsertGame(Game game);
    public void DeleteGame(Game game);
    public Game SelectGameById(int GameId);
}
