package fr.univcotedazur.menus.repositories;

import fr.univcotedazur.menus.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends MongoRepository<Category, UUID> {

    Optional<Category> findByName(@Param("name") String name);
    List<Category> findByLevel(int level);
}