package com.fish.offensivefish.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record{
    int rank;
    int score;
    int gameId;
    String firstPlayer;
    String secondPlayer;
}
