package fr.unice.bff.controller.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.net.URL;
import java.util.List;

@Data
public class Category {
    private String id;
    private String name;
    private int level;
    @JsonProperty("menuItems")
    private List<MenuItem> menuItems;
    @JsonProperty("subCategories")
    private List<Category> subCategories;
    private URL image;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }
    public URL getImage() {
        return image;
    }

    public void setImage(URL image) {
        this.image = image;
    }



}
