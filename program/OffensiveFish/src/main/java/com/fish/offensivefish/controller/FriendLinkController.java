package com.fish.offensivefish.controller;

import com.fish.offensivefish.pojo.Friend;
import com.fish.offensivefish.pojo.FriendLink;
import com.fish.offensivefish.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/FriendLink")
public class FriendLinkController {
    @Autowired
    FriendLinkService friendLinkService;
    @RequestMapping("/addFriendLink")
    public Map<String,Object> addFriendLink(String oneFriendId, String twoFriendId){
        return friendLinkService.addFriendLink(oneFriendId, twoFriendId);
    }
    @RequestMapping("/deleteFriendLink")
    public Map<String,Object> deleteFriendLink(String userName,String friendId){
        return friendLinkService.deleteFriendLink(userName, friendId);
    }
    @RequestMapping("/selectAllFriend")
    public List<Friend> selectAllFriend(String userName){
        return friendLinkService.selectAllFriend(userName);
    }
    @RequestMapping("/selectOneFriend")
    public Friend selectOneFriend(String userName,String friendId){
        return friendLinkService.selectOneFriend(userName,friendId);
    }
}
