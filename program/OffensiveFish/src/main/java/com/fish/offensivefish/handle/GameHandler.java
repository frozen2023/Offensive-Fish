package com.fish.offensivefish.handle;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fish.offensivefish.mapper.RoomMapper;
import com.fish.offensivefish.mapper.UserMapper;
import com.fish.offensivefish.pojo.MessageGame;
import com.fish.offensivefish.pojo.MessageRoom;
import com.fish.offensivefish.pojo.RoleMessage;
import com.fish.offensivefish.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;


@Slf4j
@Component
public class GameHandler {
    public  int Num=0;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoomMapper roomMapper;
    @Autowired
   SocketIOServer socketIoServer;
    @OnEvent("prepare")
    public void pregame(SocketIOClient client,int roomId,AckRequest ackRequest) throws JsonProcessingException {
        String roomId1= String.valueOf(roomId);
        client.joinRoom(roomId1);
        Set<String> allRooms = client.getAllRooms();
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        User user=userMapper.selectUser(userId);
        for (String room:allRooms){
            if(roomId1.equals(room)){
                //发送给指定空间名称以及房间的人，并且排除不发给自己
                // socketIoServer.getNamespace("/socketIO").getRoomOperations(room).sendEvent("message",client, data);
                //发送给指定空间名称以及房间的人，包括自己
                int isPrepare=0;//是否是房主
                if(roomMapper.selectRoomById(roomId).getRoomOwnerId().equals(userId)){
                    isPrepare=1;
                }
                socketIoServer.getRoomOperations(room).sendEvent("message",new MessageGame(userId,user.getName(),"已经准备!",isPrepare,0));;
            }

        }
        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("准备","成功");
        }

    }
    @OnEvent("unprepared")
    public void unpregame(SocketIOClient client,int roomId,AckRequest ackRequest) throws JsonProcessingException {
        String roomId1= String.valueOf(roomId);
        client.joinRoom(roomId1);
        Set<String> allRooms = client.getAllRooms();
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        User user=userMapper.selectUser(userId);
        for (String room:allRooms){
            if(roomId1.equals(room)){
                //发送给指定空间名称以及房间的人，并且排除不发给自己
                // socketIoServer.getNamespace("/socketIO").getRoomOperations(room).sendEvent("message",client, data);
                //发送给指定空间名称以及房间的人，包括自己

                int isPrepare=0;//是否是房主
                if(roomMapper.selectRoomById(roomId).getRoomOwnerId().equals(userId)){
                    isPrepare=1;
                }
                socketIoServer.getRoomOperations(room).sendEvent("message",new MessageGame(userId,user.getName(),"取消准备!",isPrepare,0));;

            }

        }
        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("取消准备","成功");
        }

    }
    @OnEvent("chooseRole")
    void chooseRole(SocketIOClient client,int roomId,int role,AckRequest ackRequest){
        String userId=client.getHandshakeData().getSingleUrlParam("userId");
        String roomId1= String.valueOf(roomId);
        client.joinRoom(roomId1);
        Set<String> allRooms = client.getAllRooms();
        User user=userMapper.selectUser(userId);
        for (String room:allRooms){
            if(roomId1.equals(room)){

                socketIoServer.getRoomOperations(room).sendEvent("message",new RoleMessage(role,userId,user.getName(),"选择英雄成功!"));;
            }
        }
        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("取消准备","成功");
        }
    }
    @OnEvent("playGame")
    void playGame(SocketIOClient client,int roomId,AckRequest ackRequest){
        String roomId1= String.valueOf(roomId);
        client.joinRoom(roomId1);
        Set<String> allRooms = client.getAllRooms();
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        User user=userMapper.selectUser(userId);
        for (String room:allRooms){
            if(roomId1.equals(room)){

                //发送给指定空间名称以及房间的人，并且排除不发给自己
                // socketIoServer.getNamespace("/socketIO").getRoomOperations(room).sendEvent("message",client, data);
                //发送给指定空间名称以及房间的人，包括自己
                socketIoServer.getRoomOperations(room).sendEvent("message",new MessageGame(userId,user.getName(),"开始游戏!",1,1));;
            }

        }
        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("开始游戏!","成功");
        }
    }
    @OnEvent("realPlayGame")
    void realplayGame(SocketIOClient client,int roomId,AckRequest ackRequest){
        String roomId1= String.valueOf(roomId);
        client.joinRoom(roomId1);
        Set<String> allRooms = client.getAllRooms();
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        User user=userMapper.selectUser(userId);
        for (String room:allRooms){
            if(roomId1.equals(room)){

                //发送给指定空间名称以及房间的人，并且排除不发给自己
                // socketIoServer.getNamespace("/socketIO").getRoomOperations(room).sendEvent("message",client, data);
                //发送给指定空间名称以及房间的人，包括自己
                socketIoServer.getRoomOperations(room).sendEvent("message",new MessageGame(userId,user.getName(),"真正开始游戏!",1,1));;
            }

        }
        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("开始游戏!","成功");
        }
    }
    @OnEvent("chooseMap")
    void chooseMap(SocketIOClient client,int roomId,int map,AckRequest ackRequest){
        String userId=client.getHandshakeData().getSingleUrlParam("userId");
        String roomId1= String.valueOf(roomId);
        client.joinRoom(roomId1);
        Set<String> allRooms = client.getAllRooms();
        User user=userMapper.selectUser(userId);
        for (String room:allRooms){
            if(roomId1.equals(room)){

                socketIoServer.getRoomOperations(room).sendEvent("message",new MapMessage(map,userId,user.getName(),"选择地图成功!"));;
            }
        }
        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("取消准备","成功");
        }
    }
    @OnEvent("certainMap")
    void certainMap(SocketIOClient client,int roomId,AckRequest ackRequest){
        String userId=client.getHandshakeData().getSingleUrlParam("userId");
        String roomId1= String.valueOf(roomId);
        client.joinRoom(roomId1);
        Set<String> allRooms = client.getAllRooms();
        User user=userMapper.selectUser(userId);
        for (String room:allRooms){
            if(roomId1.equals(room)){

                socketIoServer.getRoomOperations(room).sendEvent("message",new MapMessage(1,userId,user.getName(),"开始选择英雄!"));;
            }
        }
        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("取消准备","成功");
        }
    }
    @OnEvent("fishSpace")
    void retFishSpace(SocketIOClient client,int roomId){
        Num++;
        int lr= (int) ((Math.random()*2));//0代表左边，1代表右边
        int SerialNum= (int) ((Math.random()*roomId)%13)+3;//返回鱼的序号
        double y= ((Math.random())*100)/100;//返回鱼的y坐标
        String roomId1= String.valueOf(roomId);
        String userId=client.getHandshakeData().getSingleUrlParam("userId");
        Set<String> allRooms = client.getAllRooms();
        for (String room:allRooms){
            if(roomId1.equals(room)){
               // log.info("房间：{}",room);
                socketIoServer.getRoomOperations(room).sendEvent("fish",new FishSpace(lr,SerialNum,y,Num));;
            }
        }
    }
    @OnEvent("eatFish")
    public void eatFish(SocketIOClient client,int roomId,int index){
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        String roomId1= String.valueOf(roomId);
        Set<String> allRooms = client.getAllRooms();
        for (String room:allRooms){
            if(roomId1.equals(room)){

                //   log.info("房间：{}",room);
                //发送给指定空间名称以及房间的人，并且排除不发给自己

                // socketIoServer.getNamespace("/socketIO").getRoomOperations(room).sendEvent("message",client, data);
                //发送给指定空间名称以及房间的人，包括自己
                socketIoServer.getRoomOperations(room).sendEvent("anotherEat",userId,index);
            }

        }

    }
    @OnEvent("health")
    public void health(SocketIOClient client,int roomId,int nowHealth){
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        String roomId1= String.valueOf(roomId);
        Set<String> allRooms = client.getAllRooms();
        for (String room:allRooms){
            if(roomId1.equals(room)){

                //   log.info("房间：{}",room);
                //发送给指定空间名称以及房间的人，并且排除不发给自己

                // socketIoServer.getNamespace("/socketIO").getRoomOperations(room).sendEvent("message",client, data);
                //发送给指定空间名称以及房间的人，包括自己
                socketIoServer.getRoomOperations(room).sendEvent("health",userId,nowHealth);
            }
        }
    }
}
@Data
@AllArgsConstructor
@NoArgsConstructor
class MapMessage{
    int map;
    String userId;
    String name;
    String msg;
}
@Data
@AllArgsConstructor
@NoArgsConstructor
class FishSpace{
    int lr;
    int SerialNum;
    double y;
    int Num;
}