package services;

import models.MenuItem;
import models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    private List<Restaurant> restaurants;

    public RestaurantService() {
        restaurants = new ArrayList<>();
        seedData();
    }

    private void seedData() {
        Restaurant r1 = new Restaurant(1, "Pizza Palace", "Italian", 4.5, 30);
        r1.addMenuItem(new MenuItem(1, "Garlic Bread",      "Toasted with herbs",           3.99, "STARTERS"));
        r1.addMenuItem(new MenuItem(2, "Chicken Wings",     "Crispy buffalo wings",          7.99, "STARTERS"));
        r1.addMenuItem(new MenuItem(3, "Margherita Pizza",  "Classic tomato & mozzarella",  12.99, "MAINS"));
        r1.addMenuItem(new MenuItem(4, "Pepperoni Pizza",   "Loaded with pepperoni",        14.99, "MAINS"));
        r1.addMenuItem(new MenuItem(5, "Cola",              "Chilled can",                   1.99, "DRINKS"));

        Restaurant r2 = new Restaurant(2, "Burger Barn", "American", 4.2, 20);
        r2.addMenuItem(new MenuItem(6, "Onion Rings",       "Golden & crispy",               4.49, "STARTERS"));
        r2.addMenuItem(new MenuItem(7, "Classic Burger",    "Beef patty, lettuce & tomato",  9.99, "MAINS"));
        r2.addMenuItem(new MenuItem(8, "Double Smash Burger","Double patty smash style",    13.99, "MAINS"));
        r2.addMenuItem(new MenuItem(9, "Milkshake",         "Vanilla or chocolate",           4.99, "DRINKS"));

        Restaurant r3 = new Restaurant(3, "Sushi World", "Japanese", 4.8, 45);
        r3.addMenuItem(new MenuItem(10, "Edamame",          "Salted steamed soybeans",       3.49, "STARTERS"));
        r3.addMenuItem(new MenuItem(11, "Salmon Nigiri",    "Fresh salmon on rice (x2)",     6.99, "MAINS"));
        r3.addMenuItem(new MenuItem(12, "Dragon Roll",      "Prawn tempura & avocado",      14.99, "MAINS"));
        r3.addMenuItem(new MenuItem(13, "Miso Soup",        "Traditional broth",             2.99, "DRINKS"));

        restaurants.add(r1);
        restaurants.add(r2);
        restaurants.add(r3);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurants;
    }

    public Restaurant getById(int id) {
        for (Restaurant r : restaurants) {
            if (r.getId() == id) return r;
        }
        return null;
    }
}
