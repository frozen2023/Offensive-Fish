package com.fish.offensivefish.service.impl;

import com.fish.offensivefish.mapper.GameMapper;
import com.fish.offensivefish.mapper.RankListMapper;
import com.fish.offensivefish.pojo.Game;
import com.fish.offensivefish.pojo.RankList;
import com.fish.offensivefish.pojo.Record;
import com.fish.offensivefish.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    GameMapper gameMapper;
    @Autowired
    RankListMapper rankListMapper;
    public Map<String, Object> addGame(Game game) {
        Date day = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        game.setRunTime(day);
        gameMapper.InsertGame(game);
        Map<String,Object> map=new HashMap<>();
        map.put("msg", "加入游戏成功");
        return map;
    }

    public Map<String, Object> selectGameById(int gameId) {
        Game game=gameMapper.SelectGameById(gameId);;
        Map<String,Object> map=new HashMap<>();
        if(game==null)
            map.put("msg", "无该游戏记录");
        else
            map.put(String.valueOf(gameId), game);
        return map;
    }

    public Map<String, Object> IsEnd(int gameId) {
        Game game=new Game();
        Record record=new Record();
        Map<String,Object> map=new HashMap<>();
        game=gameMapper.SelectGameById(gameId);
        RankList rankList=new RankList();
        if(game==null)
            map.put("msg", "该游戏未进行");
        else{
            rankList.setScore(game.getScore());
            rankList.setFirstPlayerId(game.getFirstPlayerId());
            rankList.setSecondPlayerId(game.getSecondPlayerId());
            Date day = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            rankList.setEndTime(sdf.format(day));
            rankListMapper.insertRankList(rankList);
            record.setGameId(gameId);
            record.setScore(game.getScore());
            record.setFirstPlayer(game.getFirstPlayerId());
            record.setSecondPlayer(game.getSecondPlayerId());
            record.setRank(rankList.getRank());
            gameMapper.DeleteGame(game);
            map.put("msg", record);
        }
        return map;
    }
}
