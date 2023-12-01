package com.fish.offensivefish.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMessage {
    int role;
    String userId;
    String name;
    String msg;
}
