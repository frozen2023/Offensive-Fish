package com.fish.offensivefish.handle;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fish.offensivefish.Cache.ClientCache;
import com.fish.offensivefish.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;


@Slf4j
@Component
public class GameHandler {
    @Autowired
    private SocketIOServer socketIoServer;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ClientCache clientCache;

    @OnEvent("move")
    public void gameHandler(SocketIOClient client,String roomId,String data, AckRequest ackRequest) throws JsonProcessingException {
        Set<String> allRooms = client.getAllRooms();
        for (String room:allRooms){
            if(roomId.equals(room)){
                socketIoServer.getRoomOperations(room).sendEvent("antherMove",client.getHandshakeData().getSingleUrlParam("userId"),data);
            }

        }
        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("gameHandler",data);
        }

    }


}
