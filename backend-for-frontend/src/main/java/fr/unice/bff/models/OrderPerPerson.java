package fr.unice.bff.models;

import fr.unice.bff.controller.dto.ItemDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@Setter
@ToString
public class OrderPerPerson {
    String name;
    List<ItemDTO> items;
    int price;
    boolean billed;
}