package com.fish.offensivefish.service.impl;

import com.fish.offensivefish.mapper.FriendLinkMapper;
import com.fish.offensivefish.mapper.UserMapper;
import com.fish.offensivefish.pojo.Friend;
import com.fish.offensivefish.pojo.FriendLink;
import com.fish.offensivefish.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FriendLinkServiceImpl implements FriendLinkService {
    @Autowired
    FriendLinkMapper friendLinkMapper;
    @Autowired
    UserMapper userMapper;
    public Map<String,Object> addFriendLink(String oneFriendId, String twoFriendId){
        Map<String,Object> msg=new HashMap<>();
        FriendLink friendLink = new FriendLink();
        friendLink.setOneFriendId(oneFriendId);
        friendLink.setTwoFriendId(twoFriendId);
        if(friendLinkMapper.selectOne(friendLink)==null){
            friendLinkMapper.insertFriendLink(friendLink);
            msg.put("msg","添加成功！");
        }
        else
            msg.put("msg","已有该好友！");
        return msg;
    }
    public Map<String,Object> deleteFriendLink(String userName,String friendId){
        Map<String,Object> msg=new HashMap<>();
        FriendLink friendLink = new FriendLink();
        friendLink.setOneFriendId(userName);
        friendLink.setTwoFriendId(friendId);
        if(friendLinkMapper.selectOne(friendLink)==null)
            msg.put("msg","无该好友");
        else{
            friendLinkMapper.deleteAFriendLink(friendLink);
            msg.put("msg","删除成功！");
        }
        return msg;
    }
    public List<Friend> selectAllFriend(String userName){
        List<FriendLink> friendLinks=friendLinkMapper.selectAll(userName);
        List<Friend> friends=new ArrayList<>();
        if(friendLinks==null)
            friends=null;
        else {
            for (int i = 0; i < friendLinks.size(); i++) {
                Friend friend=new Friend();
                if(friendLinks.get(i).getOneFriendId().equals(userName)){
                    friend.setName(userMapper.selectUser(friendLinks.get(i).getTwoFriendId()).getName());
                    friend.setStatus(userMapper.selectUser(friendLinks.get(i).getOneFriendId()).getStatus());
                }
               else {
                    friend.setName(userMapper.selectUser(friendLinks.get(i).getOneFriendId()).getName());
                    friend.setStatus(userMapper.selectUser(friendLinks.get(i).getTwoFriendId()).getStatus());
                }
                System.out.println(friend);
                friends.add(friend);
            }
        }
        return friends;
    }
    public Friend selectOneFriend(String userName,String friendId){
        FriendLink friendLink=new FriendLink();
        friendLink.setOneFriendId(userName);
        friendLink.setTwoFriendId(friendId);
        FriendLink f=friendLinkMapper.selectOne(friendLink);
        Friend friend=new Friend();
        if(f.getOneFriendId().equals(userName)){
            friend.setName(userMapper.selectUser(f.getTwoFriendId()).getName());
            friend.setStatus(userMapper.selectUser(f.getOneFriendId()).getStatus());
        }
        else {
            friend.setName(userMapper.selectUser(f.getOneFriendId()).getName());
            friend.setStatus(userMapper.selectUser(f.getTwoFriendId()).getStatus());
        }
        return friend;
    }
    public Map<String,Object> inviteFriend(String friendId){
        return null;
    }
}
