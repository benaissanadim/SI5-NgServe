package fr.unice.bff.controller.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;

@Data

public class Ingredient {
    @NotBlank
    private String name;
    private String id;


    public Ingredient() {}

    public Ingredient(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
