package com.fish.offensivefish.service.impl;

import com.fish.offensivefish.mapper.FriendLinkMapper;
import com.fish.offensivefish.pojo.FriendLink;
import com.fish.offensivefish.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FriendLinkServiceImpl implements FriendLinkService {
    @Autowired
    FriendLinkMapper friendLinkMapper;
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
    public Map<String, FriendLink> selectAllFriend(String userName){
        Map<String, FriendLink> friendMap = new HashMap<>();
        List<FriendLink> friendLinks=friendLinkMapper.selectAll(userName);
        if(friendLinks.size()==0)
            friendMap.put("暂无好友", null);
        else
             for (int i = 0; i < friendLinks.size(); i++) {
                if(friendLinks.get(i).getOneFriendId().equals(userName))
                  friendMap.put(friendLinks.get(i).getTwoFriendId(),friendLinks.get(i));
               else
                   friendMap.put(friendLinks.get(i).getOneFriendId(),friendLinks.get(i));
              }
        return friendMap;
    }
    public FriendLink selectOneFriend(String userName,String friendId){
        Map<String, FriendLink> friendMap = new HashMap<>();
        FriendLink friendLink=new FriendLink();
        friendLink.setOneFriendId(userName);
        friendLink.setTwoFriendId(friendId);
        FriendLink f=friendLinkMapper.selectOne(friendLink);
        if(f==null)
            friendMap.put("无该好友",null);
        else
            friendMap.put(friendId,f);
        return f;
    }
    public Map<String,Object> inviteFriend(String friendId){
        return null;
    }
}
