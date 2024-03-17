package fr.univcotedazur.inttest.dto;


import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;

public class IngredientDTO {

    @Id
    private Long id;

    @NotBlank
    private String name;


    public IngredientDTO() {}

    public IngredientDTO(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
