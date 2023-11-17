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
        Map<String,Object> map=new HashMap<>();
        Date day = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        rank.setEndTime(sdf.format(day));
        List<RankList> rankList=rankListMapper.selectAllRankList();
        if (rankList.isEmpty()) {
            rank.setRank(1);
            System.out.println(3);
            rankListMapper.insertRankList(rank);
        }
        else {
            map.put("msg", "插入成功");
            RankList rank1=rankListMapper.selectRankListByTwo(rank.getFirstPlayerId(),rank.getSecondPlayerId());
            for(int i=0;i<rankList.size();i++)
            {
                if(rank1!=null&&rank1.getScore()<rank.getScore()){
                rankListMapper.deleteRankList(rank.getFirstPlayerId(),rank.getSecondPlayerId());
                rankListMapper.insertRankList(rank);
                break;
            }
            else if(rank1==null){
                rankListMapper.insertRankList(rank);
                break;
            }
            }

        }

            return map;
    }
    public List<RankList> selectRankListBySelf(String userName){
        return rankListMapper.selectRankListBySelf(userName);
    }

    public List<RankList> selectAllRankList(){
        return rankListMapper.selectAllRankList();
    }
}
