package com.fish.offensivefish.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRoom {
    String userName;
    String name;
    String msg;
    String data;
    int isMaster;
}
