package fr.unice.bff.controller;

import fr.unice.bff.controller.dto.Category;

import fr.unice.bff.controller.dto.CategoryDTO;
import fr.unice.bff.controller.dto.Ingredient;
import fr.unice.bff.controller.dto.MenuItem;
import fr.unice.bff.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = MenuController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class MenuController {
    public static final String BASE_URI = "/menu";
    private final Logger LOG = LoggerFactory.getLogger(MenuController.class);
    @Autowired
    private MenuService menuService;
    @Autowired
    private CacheManager cacheManager;

    @GetMapping
    public ResponseEntity<Category[]> getTheFullMenu() {
        LOG.info("Received request to get the full menu.");
        Category[] menuItems = menuService.retrieveMenu();
        if (menuItems == null) {
            LOG.warn("Menu items are null.");
            return ResponseEntity.notFound().build();
        } else {
            LOG.info("Returning full menu.");
            return ResponseEntity.ok(menuItems);
        }
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<Category[]> getMenuByCategory(@PathVariable String category) {
        LOG.info("Received request to get the menu for category: " + category);
        Category[] categories = this.menuService.getMenuByCategory(category);
        if (categories.length == 0) {
            LOG.warn("No menu items found for category: " + category);
            return ResponseEntity.notFound().build();
        } else {
            LOG.info("Returning menu items for category: " + category);
            return ResponseEntity.ok(categories);
        }
    }

    @GetMapping("/categories/{category}/sub")
    public ResponseEntity<List<CategoryDTO>> getMSubCategory(@PathVariable String category) {
        LOG.info("Received request to get the menu for sub-category: " + category);
        List<CategoryDTO> categories = this.menuService.getMSubCategory(category);

        LOG.info("Returning sub-categories for category: " + category);
        return ResponseEntity.ok(categories);

    }


    @GetMapping("/subCategories/{subCategory}")
    public ResponseEntity<Category[]> getMenuBySubCategory(@PathVariable String subCategory) {
        LOG.info("Received request to get the menu for sub-category: " + subCategory);
        List<Category> matchingCategories = this.menuService.getMenuBySubCategory(subCategory);
        if (matchingCategories.isEmpty()) {
            LOG.warn("No menu items found for sub-category: " + subCategory);
            return ResponseEntity.notFound().build();
        } else {
            LOG.info("Returning menu items for sub-category: " + subCategory);
            return ResponseEntity.ok(matchingCategories.toArray(new Category[0]));
        }
    }


    @GetMapping("/categories/{categorie}/items")
    public ResponseEntity<MenuItem[]> getItemsByCategory(@PathVariable String categorie) {
        LOG.info("Received request to get items for category " + categorie + " sorted by alphabetic order: ");
        List<MenuItem> matchingItems = menuService.getItemsByCategory(categorie);
        if (matchingItems.isEmpty()) {
            LOG.warn("No menu items found for category: " + categorie);
            return ResponseEntity.ok(new MenuItem[]{});
        } else {
            matchingItems = matchingItems.stream()
                    .sorted(Comparator.comparing(MenuItem::getFullName))
                    .collect(Collectors.toList());
            LOG.info("Returning sorted menu items for category: " + categorie);
            return ResponseEntity.ok(matchingItems.toArray(new MenuItem[0]));
        }
    }

    @GetMapping("{item}/ingredients")
    public ResponseEntity<List<Ingredient>> getItemIngredients(@PathVariable String item) {
        LOG.info("Received request to get ingredients for item: " + item);
        List<Ingredient> ingredients = this.menuService.getItemIngredients(item);
        if (ingredients.size() > 0) {
            LOG.info("Returning ingredients for item: " + item);
            return ResponseEntity.ok(ingredients);
        }
        LOG.warn("No menu items found. for item: " + item);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        LOG.info("Received request to get all categories ");
        List<CategoryDTO> categories = this.menuService.getAllCategories();
        if (categories.size() == 0) {
            LOG.warn("No categories found.");
            return ResponseEntity.notFound().build();
        } else {
            LOG.info("Returning all categories.");
            return ResponseEntity.ok(categories);
        }
    }
}
