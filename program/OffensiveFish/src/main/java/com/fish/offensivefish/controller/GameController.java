package com.fish.offensivefish.controller;

import com.fish.offensivefish.pojo.Game;
import com.fish.offensivefish.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/Game")
public class GameController {
    @Autowired
    GameService gameService;
    @RequestMapping("/addGame")
    public Map<String,Object> addGame(Game game){
        return gameService.addGame(game);
    }
    @RequestMapping("/selectGameById")
    public Map<String,Object> selectGameById(int gameId){
        return gameService.selectGameById(gameId);
    }
    @RequestMapping("/IsEnd")
    public Map<String,Object> IsEnd(int gameId){
        return gameService.IsEnd(gameId);
    }//结束则调用删除Game的函数以及增加记录
}
