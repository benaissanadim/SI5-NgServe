package fr.univcotedazur.menus.repositories;

import fr.univcotedazur.menus.models.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface IngredientRepository extends MongoRepository<Ingredient, UUID> {

    Optional<Ingredient> findByName(@Param("name") String name);

}
