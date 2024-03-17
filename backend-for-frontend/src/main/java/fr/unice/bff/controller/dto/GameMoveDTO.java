package fr.unice.bff.controller.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GameMoveDTO {
    private String color;
    private int tableNumber;
    private int seatNumber;
}
