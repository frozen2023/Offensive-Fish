package com.fish.offensivefish.controller;

import com.fish.offensivefish.pojo.RankList;
import com.fish.offensivefish.service.RankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/RankList")
public class RankListController {
    @Autowired
    RankListService rankListService;
    @RequestMapping("/insertRankList")
    public Map<String,Object> insertRankList(RankList rank){
        return rankListService.insertRankList(rank);
    }
    @RequestMapping("/selectRankListBySelf")
    public List<RankList> selectRankListBySelf(String userName){
        return rankListService.selectRankListBySelf(userName);
    }
    @RequestMapping("/selectAllRankList")
    public List<RankList> selectAllRankList(){
        return rankListService.selectAllRankList();
    }
}
