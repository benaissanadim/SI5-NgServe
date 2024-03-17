package fr.unice.bff;

import fr.unice.bff.service.MenuService;
import fr.unice.bff.controller.dto.TableCreate;
import fr.unice.bff.service.MenuService;
import fr.unice.bff.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class StartUpLogic implements ApplicationRunner {

    @Autowired
    MenuService menuService;

    public void run(ApplicationArguments args)  {
        menuService.retrieveMenu();
    }

}