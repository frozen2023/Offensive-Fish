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
        List<RankList> rankLists=rankListMapper.selectAllRankList();
        for(int i=0;i<rankLists.size();i++){
            for(int j=i;j<rankLists.size()-1;j++){
                if(rankLists.get(j).getScore()<rankLists.get(j+1).getScore()){
                    RankList temp=rankLists.get(j+1);
                    rankLists.set(j+1,rankLists.get(j));
                    rankLists.set(j,temp);
                }
            }
        }
        for(int i=0;i<rankLists.size();i++){
            rankLists.get(i).setRank(i+1);
            System.out.println("rank: "+i+1+"       "+rankLists.get(i));
            rankListMapper.updateRankList(rankLists.get(i));
        }
        }
//
//        System.out.println(rankList.size());
//
//        for(int i=0;i<rankList.size();i++) {
//            if (rank.getScore() > rankList.get(i).getScore()||i+1==rankList.size()) {
//                System.out.println("12");
//                RankList rank1=rankListMapper.selectRankListByTwo(rank.getFirstPlayerId(), rank.getSecondPlayerId());
//                System.out.println(rank1);
//                //如果小于则删除原有记录
//                //不小于则不更新
//                if (rank1 != null&&rank1.getScore()<rank.getScore()) {
//                    rank.setRank(i + 1);
//                    //删除固有记录
//                    rankListMapper.deleteRankList(rank.getFirstPlayerId(), rank.getSecondPlayerId());
//                    //将固有记录后续的排名前移
//                    for(int t=i;t<rankList.size();t++)
//                    {
//                        if(rankList.get(t).getFirstPlayerId().equals(rank.getFirstPlayerId())&&rankList.get(t).getSecondPlayerId().equals(rank.getSecondPlayerId()))
//                        {
//                            for(int p=t+1;p<rankList.size();p++)
//                            {
//                                rankList.get(p).setRank(rankList.get(p).getRank()-1);
//                                rankListMapper.updateRankList(rankList.get(p));
//                            }
//                        }
//                    }
//                    //插入新的记录
//                    map.put("msg","插入成功1");
//                    rankListMapper.insertRankList(rank);
//                    //更改后续的排名
//                        for (int j = i+1; j < rankList.size(); j++) {
//                            RankList rank2 = rankList.get(j);
//                            rank2.setRank(j + 1);
//                            rankListMapper.updateRankList( rank2);
//                        }
//                        break;
//                }
//                else if (rank1==null){
//                    System.out.println("12");
//                    rank.setRank(i + 1);
//                    if(i+1==rankList.size())
//                        rank.setRank(i+2);
//                    rankListMapper.insertRankList(rank);
//                    map.put("msg","插入成功2");
//                    //更改后续的排名
//                    for (int j = i; j < rankList.size(); j++) {
//                        RankList rank2 = rankList.get(j);
//                        rank2.setRank(j + 2);
//                        rankListMapper.updateRankList(rank2);
//                    }
//                    break;
//                }
//            }
//        }
            return map;
    }
    public List<RankList> selectRankListBySelf(String userName){
        return rankListMapper.selectRankListBySelf(userName);
    }

    public List<RankList> selectAllRankList(){
        return rankListMapper.selectAllRankList();
    }
}
