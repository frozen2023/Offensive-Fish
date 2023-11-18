package com.fish.offensivefish;

import com.corundumstudio.socketio.SocketIOServer;
import com.fish.offensivefish.handle.GameHandler;
import com.fish.offensivefish.handle.RoomHandler;
import com.fish.offensivefish.handle.TestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


@SpringBootApplication
public class OffensiveFishApplication {

    public static void main(String[] args) {
        SpringApplication.run(OffensiveFishApplication.class, args);
    }

}
@Component
@Slf4j
class SocketIOServerRunner implements CommandLineRunner, DisposableBean {

    @Autowired
    private SocketIOServer socketIOServer;
    @Autowired
    GameHandler gameHandler;
    @Autowired
    RoomHandler roomHandler;
    @Autowired
    TestHandler testHandler;
    @Override
    public void run(String... args) throws Exception {
        socketIOServer.getNamespace("/game").addListeners(gameHandler);
        socketIOServer.getNamespace("/room").addListeners(roomHandler);
        socketIOServer.getNamespace("/test").addListeners(testHandler);
        socketIOServer.start();

        log.info("SocketIOServer==============================启动成功");
    }


    @Override
    public void destroy() throws Exception {
        //如果用kill -9  这个监听是没用的，有可能会导致你服务kill掉了，但是socket服务没有kill掉
        socketIOServer.stop();
        log.info("SocketIOServer==============================关闭成功");
    }
}
