package fr.univcotedazur.inttest.dto;

import java.net.URL;
import java.util.List;
import java.util.UUID;

public class MenuItemDTO {

        private UUID id;

        private String fullName;

        private String shortName;

        private double price;

        private CategoryDTO category;
        private List<IngredientDTO> ingredientList;

        private URL image;

        public UUID getId() {
                return id;
        }

        public void setId(UUID id) {
                this.id = id;
        }

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public String getShortName() {
                return shortName;
        }

        public void setShortName(String shortName) {
                this.shortName = shortName;
        }

        public double getPrice() {
                return price;
        }

        public void setPrice(double price) {
                this.price = price;
        }

        public CategoryDTO getCategory() {
                return category;
        }

        public void setCategory(CategoryDTO category) {
                this.category = category;
        }

        public URL getImage() {
                return image;
        }

        public void setImage(URL image) {
                this.image = image;
        }

}
