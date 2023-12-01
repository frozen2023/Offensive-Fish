package com.fish.offensivefish.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageGame {
    String userName;
    String name;
    String msg;
    int isPrepare;
    int isOpen;
}
