package com.fish.offensivefish.controller;

import com.fish.offensivefish.pojo.Room;
import com.fish.offensivefish.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/Room")
public class RoomController {
    @Autowired
    RoomService roomService;
    @RequestMapping("/createNewRoom")
    public Map<String,Object> createNewRoom(String userName){
        return roomService.createNewRoom(userName);
    }
    @RequestMapping("/selectRoomById")
    public Map<String,Object> selectRoomById(int roomId){
        return roomService.selectRoomById(roomId);
    }
    @RequestMapping("/selectAllRoom")
    public Map<String,Object> selectAllRoom() {
        return roomService.selectAllRoom();
    }//返回所有的房间,房间返回的类型为RetRoom
    @RequestMapping("/entryRoom")
    public Map<String,Object> entryRoom(String code,int roomId,String playerId){
        return roomService.entryRoom(code,roomId,playerId);
    }
    @RequestMapping("/ExitRoom")
    public Map<String,Object> ExitRoom(int roomId){
        return roomService.ExitRoom(roomId);
    }
    @RequestMapping("/getRoomId")
    public int getRoomId(String code){
        return roomService.getRoomId(code);
    }
}
