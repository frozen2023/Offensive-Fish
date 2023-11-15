package com.fish.offensivefish.service;

import com.fish.offensivefish.pojo.RankList;

import java.util.List;
import java.util.Map;

public interface RankListService {
    public Map<String,Object> insertRankList(RankList rank);
    public List<RankList> selectRankListBySelf(String userName);
    public List<RankList> selectAllRankList();
}