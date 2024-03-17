package fr.unice.bff.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import fr.unice.bff.aspects.ControllerLogger;
import fr.unice.bff.controller.dto.Category;
import fr.unice.bff.controller.dto.CategoryDTO;
import fr.unice.bff.controller.dto.Ingredient;
import fr.unice.bff.controller.dto.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

import static fr.unice.bff.util.ExternalCall.call;

@Service
public class MenuService {
    @Value("${menu.service.url}")
    private String menuUrl;
    private final Logger LOG = LoggerFactory.getLogger(MenuService.class);
    private   Category[] menu;
    @Autowired
    private CacheManager cacheManager;
    public static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());


    @Cacheable("menu")
    public Category[] retrieveMenu() {
        try {
            String json = call(menuUrl+"/menu");
            this.menu = objectMapper.readValue(json, Category[].class);
            LOG.info("Successfully git pretrieved menu information on startup for cache.");
            return menu;
        } catch (JsonProcessingException e) {
            return null;
        }
    }
    @Cacheable("categories")
    public  Category[] getMenuByCategory( String category){
        return Arrays.stream(this.menu)
                .filter(c -> c.getName().equals(category))
                .toArray(Category[]::new);
    }
    @Cacheable("subCategories")
    public List<CategoryDTO> getMSubCategory( String category){
        List<CategoryDTO> categories = new ArrayList<>();
        for (Category c : this.menu) {
            if (c.getName().equals(category)) {
                if (c.getSubCategories() != null) {
                    for (Category sub : c.getSubCategories()) {
                        CategoryDTO categoryDTO = new CategoryDTO(sub.getId(), sub.getName(), sub.getImage(), sub.getSubCategories() != null);
                        categories.add(categoryDTO);
                    }
                }
            }
        }
        return categories;
    }
    @Cacheable("subCategories")
    public  List<Category> getMenuBySubCategory( String subCategory){
        return Arrays.stream(this.menu)
                .flatMap(category -> Optional.ofNullable(category.getSubCategories()).orElse(Collections.emptyList()).stream())
                .filter(Objects::nonNull)
                .filter(c -> c.getName().equals(subCategory))
                .collect(Collectors.toList());
    }
    @Cacheable("items")
    public List<MenuItem> getItemsByCategory( String categorie){
        return Arrays.stream(this.menu)
                .filter(Objects::nonNull)
                .flatMap(category -> {
                    if (category.getName().equals(categorie)) {
                        return Optional.ofNullable(category.getMenuItems()).orElse(Collections.emptyList()).stream();
                    } else {
                        List<MenuItem> subCategoryItems = new ArrayList<>();
                        Optional.ofNullable(category.getSubCategories())
                                .ifPresent(subCategories -> {
                                    subCategories.stream()
                                            .filter(subCategory -> subCategory.getName().equals(categorie))
                                            .forEach(subCategory -> subCategoryItems.addAll(Optional.ofNullable(subCategory.getMenuItems()).orElse(Collections.emptyList())));
                                });
                        return subCategoryItems.stream();
                    }
                })
                .collect(Collectors.toList());
    }
    public List<Ingredient> getItemIngredients(String item) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (Category category : this.menu) {
            MenuItem menuItem = findMenuItem(category, item);
            if (menuItem != null) {
                LOG.info("Returning ingredients for item: " + item);
                return menuItem.getIngredientList();
            }
        }
        return ingredients;
    }
    @Cacheable("categories")
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> categories = new ArrayList<>();
        for(Category category : this.menu){
            CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getName(),
                    category.getImage(), category.getSubCategories() != null);
            categories.add(categoryDTO);
        }
        return categories;
    }


        public MenuItem findMenuItem(Category category, String itemName) {
        Optional<MenuItem> foundMenuItem = Optional.ofNullable(category.getMenuItems())
                .stream()
                .flatMap(List::stream)
                .filter(menuItem -> menuItem.getShortName().equals(itemName))
                .findFirst();

        if (foundMenuItem.isPresent()) {
            return foundMenuItem.get();
        }
        if (category.getSubCategories() != null) {
            for (Category subCategory : category.getSubCategories()) {
                MenuItem menuItem = findMenuItem(subCategory, itemName);
                if (menuItem != null) {
                    return menuItem;
                }
            }
        }
        return null;
    }
}
