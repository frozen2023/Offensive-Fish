package com.fish.offensivefish.service.impl;

import com.fish.offensivefish.mapper.RankListMapper;
import com.fish.offensivefish.pojo.RankList;
import com.fish.offensivefish.service.RankListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RankListServiceImpl implements RankListService {
    @Autowired
    RankListMapper rankListMapper;
    public Map<String,Object> insertRankList(RankList rank){
        System.out.println(rank);
        Map<String,Object> map=new HashMap<>();
        Date day = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        rank.setEndTime(sdf.format(day));
        rank.setRank(0);
        List<RankList> rankList=rankListMapper.selectAllRankList();
        rankListMapper.insertRankList(rank);
        return map;
    }
    public List<RankList> selectRankListBySelf(String userName){
        return rankListMapper.selectRankListBySelf(userName);
    }

    public List<RankList> selectAllRankList(){
        List<RankList> l1 =rankListMapper.selectAllRankList();
        for(int i=0;i<l1.size();i++){
            l1.get(i).setRank(i+1);
        }
        return l1;
    }
}
