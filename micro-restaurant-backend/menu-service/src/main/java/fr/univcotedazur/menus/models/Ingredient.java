package fr.univcotedazur.menus.models;


import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import java.util.UUID;

public class Ingredient {

    @Id
    private UUID id;

    @NotBlank
    private String name;


    public Ingredient() {}

    public Ingredient(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}