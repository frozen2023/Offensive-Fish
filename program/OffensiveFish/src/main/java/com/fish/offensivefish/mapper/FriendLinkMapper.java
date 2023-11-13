package com.fish.offensivefish.mapper;

import com.fish.offensivefish.pojo.FriendLink;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendLinkMapper {
    public void insertFriendLink(FriendLink friendlink);
    public void deleteAFriendLink(FriendLink friendlink);
    public List<FriendLink> selectAll(String userName);
    public FriendLink selectOne(FriendLink friendlink);
}
