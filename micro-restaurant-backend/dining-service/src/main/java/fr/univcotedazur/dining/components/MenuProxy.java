package fr.univcotedazur.dining.components;

import fr.univcotedazur.dining.components.dto.menuproxy.Category;
import fr.univcotedazur.dining.components.util.CategoryUtils;
import fr.univcotedazur.dining.models.OrderingItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.awt.MenuItem;
import java.util.*;

@Component
public class MenuProxy {

    @Value("${menu.host.baseurl:}")
    private String menuHostandPort;

    private RestTemplate restTemplate = new RestTemplate();

    private Map<String, OrderingItem> menuItemMap;

    public List<OrderingItem> findAll() {
        populateMenuItemMapIfNeeded();
        return new ArrayList<>(menuItemMap.values());
    }

    public Optional<OrderingItem> findByShortName(String shortName) {
        populateMenuItemMapIfNeeded();
        System.out.println("finding short name" + shortName);
        System.out.println(menuItemMap);
        System.out.println(menuItemMap.get(shortName));
        return Optional.ofNullable(menuItemMap.get(shortName));
    }

    private void populateMenuItemMapIfNeeded() {
        System.err.println("###################### acessing menus from the dining service #################");
        try{
            if(menuItemMap == null){

            Category[] categories = restTemplate.getForEntity(menuHostandPort+"/menu", Category[].class).getBody();
            System.err.println("###################### categories #################");
            System.err.println(categories);
            for(Category category : categories){
                System.err.println(category.getName());
                System.err.println(category.getMenuItems());
            }
            OrderingItem[] menuItems = CategoryUtils.extractOrderingItemsFromCategories(categories);
            System.out.println("menuItems");

            menuItemMap = new HashMap<>(menuItems.length * 4 / 3);
            for(int i=0; i < menuItems.length; i++) {
                System.out.println("item" + i);
                System.out.println(menuItems[i]);
                menuItemMap.put(menuItems[i].getShortName(), menuItems[i]);
            }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }




    }

}