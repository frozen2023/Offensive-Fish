package com.fish.offensivefish.mapper;

import com.fish.offensivefish.pojo.RetRoom;
import com.fish.offensivefish.pojo.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoomMapper {
    public void insertRoom(Room room);
    public Room selectRoomById(int RoomId);
    public List<Room> selectAllRoom();
    public void updateRoomStatusInc(int RoomId,String playerId);
    public void updateRoomStatusDec(int RoomId,String playerId);
    public void deleteRoom(int RoomId);
<<<<<<< HEAD

=======
    public int selectRoomByCode(String code);
>>>>>>> 82d483a090b55de31235726d8b0ba40bc425d7d7
}
