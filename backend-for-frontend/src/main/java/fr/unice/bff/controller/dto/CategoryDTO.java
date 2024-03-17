package fr.unice.bff.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URL;

@AllArgsConstructor
@Data
public class CategoryDTO {
    private String id;
    private String name;
    private URL image;
    private boolean hasSubCategory;
}
