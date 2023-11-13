package com.fish.offensivefish.mapper;

import com.fish.offensivefish.pojo.RankList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RankListMapper {
    public void insertRankList(RankList rank);
    public List<RankList> selectRankListBySelf(String userName);
    public RankList selectRankListByTwo(String firstPlayerId,String secondPlayerId);
    public  List<RankList> selectAllRankList();
    public void deleteRankList(String firstPlayerId,String secondPlayerId);
    public void updateRankList(RankList rankList);

}
