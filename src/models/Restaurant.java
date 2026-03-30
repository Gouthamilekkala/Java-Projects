package models;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private int id;
    private String name;
    private String cuisine;
    private double rating;
    private int deliveryTime;
    private List<MenuItem> menu;

    public Restaurant(int id, String name, String cuisine, double rating, int deliveryTime) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.menu = new ArrayList<>();
    }

    public void addMenuItem(MenuItem item) {
        menu.add(item);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCuisine() { return cuisine; }
    public double getRating() { return rating; }
    public int getDeliveryTime() { return deliveryTime; }
    public List<MenuItem> getMenu() { return menu; }
}
