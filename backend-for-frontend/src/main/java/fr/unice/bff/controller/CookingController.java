package fr.unice.bff.controller;

import fr.unice.bff.controller.dto.Category;
import fr.unice.bff.controller.dto.CategoryDTO;
import fr.unice.bff.controller.dto.Ingredient;
import fr.unice.bff.controller.dto.MenuItem;
import fr.unice.bff.controller.dto.Preparation;
import fr.unice.bff.controller.dto.PreparationResult;
import fr.unice.bff.controller.dto.PreparationSent;
import fr.unice.bff.service.CookingService;
import fr.unice.bff.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = CookingController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class CookingController {
    public static final String BASE_URI = "/preparations";
    private final Logger LOG = LoggerFactory.getLogger(CookingController.class);
    @Autowired
    private CookingService cookingService;

    @GetMapping
    public ResponseEntity<PreparationResult[]> getAllPreparations(@RequestParam("state") String stateName) {
        LOG.info("Received request to get all preparations with state "+ stateName);
        switch (stateName) {
            case "readyToBeServed":
                return ResponseEntity.ok(cookingService.preparationFinished());
            case "preparationStarted":
                return ResponseEntity.ok(cookingService.preparationStarted());
            default:
                return ResponseEntity.ok().build();
            }
    }

    @GetMapping("cooking/{num}")
    public void startCooking(@PathVariable String num) throws InterruptedException {
        cookingService.startCooking(num);
        cookingService.endCooking(num);

    }

    @GetMapping("take/{num}")
    public void take(@PathVariable String num) throws InterruptedException {
        cookingService.servedToTable(num);
    }

}