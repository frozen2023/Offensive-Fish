package com.fish.offensivefish;

import com.fish.offensivefish.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OffensiveFishApplicationTests {
    @Autowired
    UserController userController;
    @Test
    void contextLoads() {
        System.out.println(userController.login("15392287939","1234"));;
    }

}
