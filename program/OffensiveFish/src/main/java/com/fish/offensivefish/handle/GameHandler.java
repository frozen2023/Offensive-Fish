package com.fish.offensivefish.handle;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;


@Slf4j
@Component
public class GameHandler {
    @OnEvent("testHandler")
    public void testHandler(SocketIOClient client, String data, AckRequest ackRequest) throws JsonProcessingException {

        log.info("MyTestHandler:{}",data);

        if(ackRequest.isAckRequested()){
            //返回给客户端，说我接收到了
            ackRequest.sendAckData("MyTestHandler",data);
        }

    }


}
