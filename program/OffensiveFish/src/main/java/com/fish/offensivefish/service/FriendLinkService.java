package com.fish.offensivefish.service;

import com.fish.offensivefish.pojo.FriendLink;
import java.util.Map;

public interface FriendLinkService {
    public Map<String,Object> addFriendLink(String oneFriendId, String twoFriendId);
    public Map<String,Object> deleteFriendLink(String userName,String friendId);
    public Map<String,FriendLink> selectAllFriend(String userName);
    public FriendLink selectOneFriend(String userName,String friendId);
    public Map<String,Object> inviteFriend(String friendId);
}