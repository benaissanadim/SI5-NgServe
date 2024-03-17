package fr.univcotedazur.dining.components.dto.menuproxy;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.net.URL;
import java.util.UUID;


public class MenuItem {

        private String id;

        @NotBlank
        private String fullName;

        @NotBlank
        private String shortName;

        @Positive
        private double price; // in euro

        private String ingredientList;


        @NotNull
        private URL image;

        public String getId() {
                return id;
        }

        public void setId(String id) {
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


        public URL getImage() {
                return image;
        }

        public void setImage(URL image) {
                this.image = image;
        }

        @Override
        public String toString() {
                return "MenuItem{" + "id='" + id + '\'' + ", fullName='" + fullName + '\'' + ", shortName='" + shortName + '\'' + ", price=" + price + ", ingredientList='" + ingredientList + '\'' + ", image=" + image + '}';
        }
}