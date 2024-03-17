package fr.univcotedazur.menus;

import fr.univcotedazur.menus.models.Category;
import fr.univcotedazur.menus.models.Ingredient;
import fr.univcotedazur.menus.models.MenuItem;
import fr.univcotedazur.menus.repositories.CategoryRepository;
import fr.univcotedazur.menus.repositories.IngredientRepository;
import fr.univcotedazur.menus.repositories.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.UUID;


@Component
public class MenuStartUpLogic implements ApplicationRunner {

    @Autowired
    MenuItemRepository menuItemRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    IngredientRepository ingredientRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (menuItemRepository.findAll().size() == 0) {

            Category Starter = createCategory("STARTER",1, new URL("https://www.cuisineactuelle.fr/imgre/fit" +
                    "/http.3A.2F.2Fprd2-bone-image.2Es3-website-eu-west-1.2Eamazonaws.2Ecom.2Fcac" +
                    ".2F2018.2F09.2F25.2F20e819df-6169-4f0c-b2cd-8b13537937cf.2Ejpeg/1200x900/quality/" +
                    "80/crop-from/center/8-idees-d-entrees-froides-ingenieuses-et-super-rapides.jpeg"), null);
            categoryRepository.save(Starter);

            Ingredient ingredientPizza1 = createIngredient("Tomato Sauce");
            Ingredient ingredientPizza2 = createIngredient("Fresh Mozzarella Cheese");
            Ingredient ingredientPizza3 = createIngredient("Fresh Basil Leaves");
            Ingredient ingredientPizza4 = createIngredient("Ham Slices");
            Ingredient ingredientPizza5 = createIngredient("Sliced Mushrooms");
            Ingredient ingredientPizza6 = createIngredient("Sliced Bell Peppers");
            ingredientRepository.saveAll(List.of(ingredientPizza1,ingredientPizza2,ingredientPizza3, ingredientPizza4
                    ,ingredientPizza5, ingredientPizza6));

            MenuItem menuPizza1 = createMenuItem("Pizza Margherita", "pizza_marg", 12,
                    new URL("https://images.prismic.io/eataly-us/ed3fcec7-7994-426d-a5e4-a24be5a95afd_" +
                            "pizza-recipe-main.jpg?auto=compress,format.jog"),
                    List.of(ingredientPizza1,ingredientPizza2,ingredientPizza3));

            MenuItem menuPizza2 = createMenuItem("Pizza Hawaii", "pizza_h", 14,
                    new URL("https://czasopismo.klarstein.pl/wp-content/uploads/2023/02/KS_Magazine_0223" +
                            "_Pizza-Hawaii_1300x1300px.jpg"),
                    List.of(ingredientPizza1,ingredientPizza2,ingredientPizza4));

            MenuItem menuPizza3 = createMenuItem("Pizza Vegetarian", "pizza_v", 15,
                    new URL("https://i0.wp.com/www.thursdaynightpizza.com/wp-content/" +
                            "uploads/2022/06/veggie-pizza-side-view-out-of-oven.png?resize=720%2C480&ssl=1"),
                    List.of(ingredientPizza1,ingredientPizza2,ingredientPizza5, ingredientPizza6));

            Ingredient ingredientS1 = createIngredient("Fresh Salmon Fillet");
            Ingredient ingredientS2 = createIngredient("Fresh Dill");
            Ingredient ingredientS3 = createIngredient("Lemon Zest");
            Ingredient ingredientS4 = createIngredient("Beef Chuck");
            Ingredient ingredientS5 = createIngredient("Tuna");
            Ingredient ingredientS6 = createIngredient("Olive Oil");

            ingredientRepository.saveAll(List.of(ingredientS1,ingredientS2,ingredientS3, ingredientS4, ingredientS5, ingredientS6));

            MenuItem menuSalamon=createMenuItem("dill salmon gravlax", "salmon", 25,
                    new URL("https://cdn.pixabay.com/photo/2014/11/05/15/57/salmon-518032_960_720.jpg"),
                    List.of(ingredientS1,ingredientS2,ingredientS3));
            MenuItem m2 = createMenuItem("Beef chuck", "beef chuck", 24,
                    new URL("https://cdn.pixabay.com/photo/2017/01/23/15/36/eat-2002918_960_720.jpg"),
                    List.of(ingredientS4,ingredientS3));
            MenuItem m3 = createMenuItem("Half cooked tuna", "half cooked tuna",
                    23,
                    new URL("https://cdn.pixabay.com/photo/2019/09/20/05/53/tuna-4490877_960_720.jpg"),
                    List.of(ingredientS5,ingredientS6));

            menuItemRepository.saveAll(List.of(menuPizza1, menuPizza2, menuPizza3, menuSalamon, m2, m3));

            Category Pizza = createCategory("PIZZA",2,
                    new URL("https://brigade-hocare.com/info/wp-content/uploads/2022/12/pizza-italienne" +
                            "-traditionnelle.jpg"), null);
            Pizza.setMenuItems(List.of(menuPizza1, menuPizza2, menuPizza3));
            categoryRepository.save(Pizza);

            Category Burger = createCategory("BURGER",2,
                    new URL("https://images.radio-canada.ca/v1/alimentation/recette/4x3/burger-jamaicaine.jpg"),
                    null);
            categoryRepository.save(Burger);


            Category Pasta = createCategory("PASTA",2,
                    new URL("https://www.budgetbytes.com/wp-content/uploads/2013/07/Creamy-Spinach-Tomato-Pasta-bowl-500x500.jpg"),
                    null);
            categoryRepository.save(Pasta);

            MenuItem lasagna = createMenuItem("Lasagna al forno", "lasagna", 16,
                    new URL("https://cdn.pixabay.com/photo/2017/02/15/15/17/meal-2069021_1280.jpg"), null);

            Category Fish = createCategory("MEAT & FISH",2,
                    new URL("https://www.licious.in/blog/wp-content/uploads/2020/12/Tandoori-Fish-750x750.jpg"),
                    null);
            Fish.setMenuItems(List.of(menuSalamon, m2, m3));
            categoryRepository.save(Fish);

            Ingredient ingredient1 = createIngredient("Ground Beef");
            Ingredient ingredient2 = createIngredient("Vegetarian Patty");
            Ingredient ingredient3 = createIngredient("Burger Bun");
            Ingredient ingredient4 = createIngredient("Lettuce");
            Ingredient ingredient5 = createIngredient("Tomato");
            ingredientRepository.saveAll(List.of(ingredient1,ingredient2,ingredient3, ingredient4, ingredient5));

            MenuItem m1 = createMenuItem("Homemade beef burger", "beef burger", 19,
                    new URL("https://cdn.pixabay.com/photo/2022/01/17/19/24/burger-6945571_960_720.jpg"),
                    List.of(ingredient1, ingredient3, ingredient4, ingredient5));
            MenuItem m11 = createMenuItem("Homemade beef burger", "beef burger", 17,
                    new URL("https://www.eatingwell.com/thmb/Clm50Llj-uQGfbLUGnevTY0mzWU=/1500x0/" +
                            "filters:no_upscale():max_bytes(150000):strip_icc()/6683924-cab8a11ae8c24c50b48aae8ed95049e6.jpg"),
                    List.of(ingredient2, ingredient3, ingredient4, ingredient5));
            menuItemRepository.saveAll(List.of(m1, m11));

            Burger.setMenuItems(List.of(m1, m11));
            categoryRepository.save(Burger);

            Category Main = createCategory("MAIN",1,
                    new URL("https://assets.afcdn.com/recipe/20180326/78166_w1024h768c1cx2592cy1728.png"),
                    List.of(Pizza, Burger, Pasta, Fish ));
            categoryRepository.save(Main);

            MenuItem menuItem1=createMenuItem("Homemade foie gras terrine", "foie gras", 18,
                    new URL("https://cdn.pixabay.com/photo/2016/11/12/15/28/restaurant-1819024_960_720.jpg"), null);
            menuItemRepository.save(menuItem1);

            MenuItem menuItem2= createMenuItem("Soft-boiled egg breaded with breadcrumbs and nuts", "soft-boiled egg"
                    , 16,
                    new URL("https://cdn.pixabay.com/photo/2019/06/03/22/06/eggs-4250077_960_720.jpg"), null);
            menuItemRepository.save(menuItem2);
            MenuItem menuItem3=createMenuItem("Goat cheese", "goat cheese", 15,
                    new URL("https://cdn.pixabay.com/photo/2016/09/15/19/24/salad-1672505_960_720.jpg"), null);
            menuItemRepository.save(menuItem3);
            MenuItem menuItem5=createMenuItem("Crab maki with fresh mango", "crab maki", 16,
                    new URL("https://cdn.pixabay.com/photo/2016/03/05/22/23/asian-1239269_960_720.jpg"), null);
            menuItemRepository.save(menuItem5);
            MenuItem menuItem6=createMenuItem("Burrata Mozzarella", "burrata", 16,
                    new URL("https://cdn.pixabay.com/photo/2021/02/08/12/40/burrata-5994616_960_720.jpg"), null);
            menuItemRepository.save(menuItem6);
            Starter.setMenuItems(List.of(menuItem1, menuItem3, menuItem2, menuItem5, menuItem6));
            categoryRepository.save(Starter);



            MenuItem menuItem13 = createMenuItem("Brownie (home made)", "brownie", 6.5,
                    new URL("https://cdn.pixabay.com/photo/2014/11/28/08/03/brownie-548591_1280.jpg"), null);
            MenuItem menuItem12 = createMenuItem("Valrhona chocolate declination with salted chocolate ice cream",
                    "chocolate", 12,
                    new URL("https://cdn.pixabay.com/photo/2020/07/31/11/53/ice-cream-5452794_960_720.jpg"), null);
            MenuItem menuItem11 = createMenuItem("Marmalade of Menton\'s lemon - Lemon cream - Limoncello jelly and " +
                            "sorbet - Homemade meringue", "lemon", 12,
                    new URL("https://cdn.pixabay.com/photo/2018/05/01/18/19/eat-3366425_960_720.jpg"),null);
            MenuItem menuItem10= createMenuItem("Fresh raspberries and peaches", "rasp and peaches", 12,
                    new URL("https://cdn.pixabay.com/photo/2020/05/15/17/28/fruit-plate-5174414_960_720.jpg"),null);
            MenuItem menuItem9= createMenuItem("Dessert of fresh strawberries and vanilla mascarpone mousse",
                    "strawberries", 12,
                    new URL("https://cdn.pixabay.com/photo/2018/04/09/18/20/strawberry-3304967_960_720.jpg"), null);
            MenuItem menuItem8 = createMenuItem("Fresh seasonal fruit", "seasonal fruit", 12,
                    new URL("https://cdn.pixabay.com/photo/2016/08/09/19/03/fruit-1581400_960_720.jpg"),null);
            MenuItem menuItem7 =  createMenuItem("Speculoos tiramisu", "tiramisu", 10,
                    new URL("https://cdn.pixabay.com/photo/2017/03/19/18/22/italian-food-2157246_960_720.jpg"),null);
            List<MenuItem> menuItems = List.of(menuItem7, menuItem8, menuItem9, menuItem9, menuItem10, menuItem11,
                    menuItem12, menuItem13);
            menuItemRepository.saveAll(menuItems);

            Category Dessert = createCategory("DESSERT",1, new URL("https://assets.bonappetit.com/photos/57d315fabd794aa91326387e/master" +
                    "/w_1600%2Cc_limit/liholiho-baked-hawaiian.jpg"), null);
            Dessert.setMenuItems(menuItems);
            categoryRepository.save(Dessert);


            MenuItem beverage1 =createMenuItem("Bottled coke (33cl)", "coke", 3.5,
                    new URL("https://cdn.pixabay.com/photo/2019/11/14/15/47/coke-4626458_1280.jpg"), null);
            MenuItem beverage2 = createMenuItem("Ice Tea (33cl)", "ice tea", 3.5,
                    new URL("https://cdn.pixabay.com/photo/2022/04/11/08/52/iced-tea-7125271_960_720.jpg"),null);
            MenuItem beverage3 = createMenuItem("Bottled water", "bottled water", 1,
                    new URL("https://cdn.pixabay.com/photo/2014/12/11/09/49/water-564048_960_720.jpg"), null);
            MenuItem beverage4 = createMenuItem("Spritz", "spritz", 5,
                    new URL("https://cdn.pixabay.com/photo/2020/05/12/21/17/spritz-5164971_960_720.jpg"), null);
            MenuItem beverage5 = createMenuItem("Margarita", "margarita", 6.5,
                    new URL("https://cdn.pixabay.com/photo/2014/08/11/08/37/margarita-415360_960_720.jpg"), null);
            MenuItem beverage6 = createMenuItem("Tequila sunrise", "tequila", 7,
                    new URL("https://cdn.pixabay.com/photo/2018/01/25/19/33/summer-3106910_960_720.jpg"), null);
            MenuItem beverage8 = createMenuItem("Mojito", "mojito", 6,
                    new URL("https://cdn.pixabay.com/photo/2015/03/30/12/35/mojito-698499_960_720.jpg"), null);
            MenuItem beverage7 = createMenuItem("Martini", "martini", 7,
                    new URL("https://cdn.pixabay.com/photo/2015/10/19/07/50/cocktail-995574_960_720.jpg"), null);
            MenuItem beverage9 = createMenuItem("Lemonade", "lemonade", 3,
                    new URL("https://cdn.pixabay.com/photo/2016/07/21/11/17/drink-1532300_960_720.jpg"), null);
            MenuItem beverage10 =createMenuItem("Apple juice", "apple juice", 3,
                    new URL("https://cdn.pixabay.com/photo/2016/11/28/22/07/punch-1866178_960_720.jpg"), null);
            MenuItem beverage11 =createMenuItem("Café", "café", 1.8,
                    new URL("https://cdn.pixabay.com/photo/2014/12/11/02/56/coffee-563797_960_720.jpg"), null);
            List<MenuItem> menuItems1 =List.of(beverage1, beverage2, beverage3, beverage4, beverage5, beverage6,
                    beverage7, beverage8, beverage9, beverage10, beverage11);
            Category Beverage = createCategory("BEVERAGE",1,
                    new URL("https://thumbs.dreamstime.com/b/cans-beverages-19492376.jpg"), null);
            menuItemRepository.saveAll(menuItems1);
            Beverage.setMenuItems(menuItems1);
            categoryRepository.save(Beverage);
        }
    }

    private static Category createCategory(String name,int level, URL image,
                                           List<Category> subCategories) {
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setImage(image);
        category.setSubCategories(subCategories);
        category.setName(name);
        category.setLevel(level);
        return category;
    }

    private static Ingredient createIngredient(String name){
        Ingredient ingredient = new Ingredient();
        ingredient.setId(UUID.randomUUID());
        ingredient.setName(name);
        return ingredient;
    }

    private static MenuItem createMenuItem(String fullName, String shortName, double price,
                                           URL image, List<Ingredient> ingredients) {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(UUID.randomUUID());
        menuItem.setFullName(fullName);
        menuItem.setShortName(shortName);
        menuItem.setPrice(price);
        menuItem.setImage(image);
        menuItem.setIngredientList(ingredients);
        return menuItem;
    }

}