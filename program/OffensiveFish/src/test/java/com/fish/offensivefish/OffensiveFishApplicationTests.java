package com.fish.offensivefish;

import com.fish.offensivefish.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
class OffensiveFishApplicationTests {
    @Autowired
    UserController userController;
    @Test
    void contextLoads() {
        //0代表左边，1代表右边
        Random random1 = new Random(10);//随机数种子为10
        for(int i=0;i<500;i++){
            int lr= random1.nextInt(100)%2;
        System.out.println(lr);}
    }

}
