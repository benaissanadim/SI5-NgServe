package fr.univcotedazur.dining.components.util;

import fr.univcotedazur.dining.components.dto.menuproxy.Category;
import fr.univcotedazur.dining.components.dto.menuproxy.MenuItem;
import fr.univcotedazur.dining.models.OrderingItem;

import java.util.ArrayList;
import java.util.List;

public class CategoryUtils {
    public static OrderingItem[] extractOrderingItemsFromCategories(Category[] categories) {
        List<OrderingItem> orderingItems = new ArrayList<>();

        for (Category category : categories) {
            List<MenuItem> categoryMenuItems = extractMenuItems(category);
            for (MenuItem menuItem : categoryMenuItems) {
                OrderingItem orderingItem = new OrderingItem();
                orderingItem.setId(menuItem.getId());
                orderingItem.setShortName(menuItem.getShortName());
                orderingItem.setCategory(category.getName());
                orderingItems.add(orderingItem);
            }
        }

        return orderingItems.toArray(new OrderingItem[0]);
    }

    public static List<MenuItem> extractMenuItems(Category category) {
        List<MenuItem> menuItems = new ArrayList<>();
        extractMenuItemsRecursive(category, menuItems);
        return menuItems;
    }

    private static void extractMenuItemsRecursive(Category category, List<MenuItem> menuItems) {
        if (category != null) {
            if (category.getMenuItems() != null) {
                menuItems.addAll(category.getMenuItems());
            }

            if (category.getSubCategories() != null) {
                for (Category subCategory : category.getSubCategories()) {
                    extractMenuItemsRecursive(subCategory, menuItems);
                }
            }
        }
    }

}