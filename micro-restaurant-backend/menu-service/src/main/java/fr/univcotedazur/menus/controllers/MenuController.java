package fr.univcotedazur.menus.controllers;

import fr.univcotedazur.menus.exceptions.MenuItemShortNameAlreadyExistsException;
import fr.univcotedazur.menus.models.Category;
import fr.univcotedazur.menus.models.MenuItem;
import fr.univcotedazur.menus.repositories.CategoryRepository;
import fr.univcotedazur.menus.repositories.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin
@RequestMapping(path = MenuController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class MenuController {

    public static final String BASE_URI = "/menu";

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody @Valid MenuItem menuItem)
            throws MenuItemShortNameAlreadyExistsException {
        if (menuItemRepository.findByShortName(menuItem.getShortName()).isPresent()) {
            throw new MenuItemShortNameAlreadyExistsException(menuItem.getShortName());
        } else{
            menuItem.setId(UUID.randomUUID());
            return ResponseEntity.status(HttpStatus.CREATED).body(menuItemRepository.save(menuItem));
        }
    }

    @GetMapping
    public ResponseEntity<List<Category>> getTheFullMenu() {
        return ResponseEntity.ok(categoryRepository.findByLevel(1));
    }



}