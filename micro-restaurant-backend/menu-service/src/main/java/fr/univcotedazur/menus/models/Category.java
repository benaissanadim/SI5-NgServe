package fr.univcotedazur.menus.models;

import org.springframework.data.annotation.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.List;
import java.util.UUID;


public class Category {

    @Id
    private UUID id;
    @NotBlank
    private String name;

    private int level;

    private List<MenuItem> menuItems;

    private List<Category> subCategories;

    @NotNull
    private URL image;

    public Category() {}

    public Category(String name) {
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
