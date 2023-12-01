package com.fish.offensivefish.service.impl;

import com.fish.offensivefish.mapper.RoomMapper;
import com.fish.offensivefish.mapper.UserMapper;
import com.fish.offensivefish.pojo.RetRoom;
import com.fish.offensivefish.pojo.Room;
import com.fish.offensivefish.pojo.User;
import com.fish.offensivefish.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    UserMapper userMapper;
    public Map<String,Object> createNewRoom(String userName){
        Map<String,Object> map=new HashMap<>();
        Random random=new Random();
        String code= String.valueOf(random.nextInt(9000)+1000);//三位验证码
        Room room=new Room();
        room.setRoomOwnerId(userName);
        room.setIsOpen(1);
        room.setNumbers(1);
        room.setPlayerId("");
        room.setCode(code);
        roomMapper.insertRoom(room);
        int roomId=roomMapper.selectRoomByCode(code);
        map.put("msg", "房间创建成功");
        Room room1=roomMapper.selectRoomById(roomId);
        map.put("object",room1);
        return map;
    }
    public Map<String,Object> selectRoomById(int RoomId){
        Map<String,Object> map=new HashMap<>();
        Room room=roomMapper.selectRoomById(RoomId);
        RetRoom retRoom=new RetRoom();
        retRoom.setRoomOwnerId(room.getRoomOwnerId());
        retRoom.setRoomId(room.getRoomId());
        User user=userMapper.selectUser(room.getRoomOwnerId());
        retRoom.setRoomOwnerName(user.getName());
        retRoom.setIsOpen(room.getIsOpen());
        retRoom.setPlayerId(room.getPlayerId());
        retRoom.setNumbers(room.getNumbers());
        if(room==null)
            map.put("msg", "无该房间");
        else
            map.put("msg", retRoom);
        return map;
    }
    public Map<String,Object> selectAllRoom() {
            List<Room> rooms=roomMapper.selectAllRoom();
            Map<String,Object> map=new HashMap<>();
            int numbers=rooms.size();
            if (rooms == null) {
                map.put("msg","房间列表为空。");
            }
            else{
                for(int i=0;i<numbers;i++){
                    System.out.println(rooms.get(i));
                    RetRoom retRoom=new RetRoom();
                    String userName=rooms.get(i).getRoomOwnerId();
                    System.out.println("eqwewq"+userMapper.selectUser(userName));
                    retRoom.setRoomOwnerName(userMapper.selectUser(rooms.get(i).getRoomOwnerId()).getName());
                    retRoom.setRoomOwnerId(rooms.get(i).getRoomOwnerId());
                    retRoom.setRoomId(rooms.get(i).getRoomId());
                    retRoom.setNumbers(rooms.get(i).getNumbers());
                    retRoom.setIsOpen(rooms.get(i).getIsOpen());
                    map.put(String.valueOf(rooms.get(i).getRoomId()), retRoom);
                }
            }
            return map;
    }//返回所有的房间,房间返回的类型为RetRoom
    public Map<String,Object> entryRoom(String code,String playerId){
       int roomId=roomMapper.selectRoomByCode(code);
        Map<String,Object> map=new HashMap<>();
        System.out.println("entry"+roomId);
        Room room=roomMapper.selectRoomById(roomId);
        room.setPlayerId(playerId);
        if(!room.getCode().equals(code)){
            map.put("msg", "邀请码错误");
        }
        else if(room.getIsOpen()==0){
            map.put("msg", "房间已满");
        }
        else{
            roomMapper.updateRoomStatusInc(roomId,playerId);
            map.put("msg", "成功进入房间");
            map.put("object",room);
        }
        return map;

    }//进入房间，进入后房间人数自增1
    public Map<String,Object> ExitRoom(int roomId,String userId) {
        Map<String,Object> map=new HashMap<>();
        System.out.println("exit"+roomId);
        System.out.println(roomMapper.selectRoomById(roomId));
        if(roomMapper.selectRoomById(roomId).getRoomOwnerId().equals(userId)){
            roomMapper.deleteRoom(roomId);
            map.put("msg", "房间销毁");
        }
        else {
            roomMapper.updateRoomStatusDec(roomId, "");
            map.put("msg", "退出房间成功");
        }
        return map;
    }//退出房间，退出后房间人数减一，此时房间可以进入

    public int getRoomId(String code) {
        System.out.println(code);
        return roomMapper.selectRoomByCode(code);
    }
}
