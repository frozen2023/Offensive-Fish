package com.fish.offensivefish.handle;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fish.offensivefish.Cache.ClientCache;
import com.fish.offensivefish.mapper.UserMapper;
import com.fish.offensivefish.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
public class RoomHandler {
    //加入房间
    @Autowired
    private SocketIOServer socketIoServer;
    @Autowired
    UserMapper userMapper;
    @OnEvent("joinRoom")
    public void joinRoom(SocketIOClient client, int roomId, AckRequest ackRequest) throws JsonProcessingException {
        String roomId1= String.valueOf(roomId);
        client.joinRoom(roomId1);
//        String userId=client.getHandshakeData().getSingleUrlParam("userId");
//        User user=userMapper.selectUser(userId);
        System.out.println("加入房间");
        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("加入房间","!");
        }

    }
    //离开房间
    @OnEvent("leaveRoom")
    public void leaveRoom(SocketIOClient client, String data, AckRequest ackRequest) throws JsonProcessingException {

        client.leaveRoom(data);

        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("离开房间","成功");
        }

    }
    @OnEvent("sendRoomMessage")
    public void sendRoomMessage(SocketIOClient client,String roomId, String data, AckRequest ackRequest) throws JsonProcessingException {
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        Set<String> allRooms = client.getAllRooms();
        for (String room:allRooms){
            if(roomId.equals(room)){
                log.info("房间：{}",room);
                //发送给指定空间名称以及房间的人，并且排除不发给自己
                // socketIoServer.getNamespace("/socketIO").getRoomOperations(room).sendEvent("message",client, data);
                //发送给指定空间名称以及房间的人，包括自己
                socketIoServer.getRoomOperations(room).sendEvent("message", data);;
            }

        }

        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("发送消息到指定的房间","成功");
        }

    }


}
