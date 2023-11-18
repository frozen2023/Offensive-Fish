package com.fish.offensivefish.service;

import java.util.Map;
import com.fish.offensivefish.pojo.Room;

public interface RoomService {
    public Map<String,Object> createNewRoom(String userName);
    public Map<String,Object> selectRoomById(int roomId);
    public Map<String,Object> selectAllRoom();//返回所有的房间,房间返回的类型为RetRoom
    public Map<String,Object> entryRoom(String code,int roomId,String playerId);//进入房间，进入后房间人数自增1
    public Map<String,Object> ExitRoom(int roomId);//退出房间，退出后房间人数减一，此时房间可以进入
    public int getRoomId(String code);
}
