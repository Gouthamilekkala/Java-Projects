package models;

import java.util.LinkedHashMap;
import java.util.Map;

public class Order {
    public enum Status {
        PLACED, PREPARING, OUT_FOR_DELIVERY, DELIVERED
    }

    private int id;
    private Restaurant restaurant;
    private Map<MenuItem, Integer> items;
    private double total;
    private Status status;

    public Order(int id, Restaurant restaurant, Map<MenuItem, Integer> items, double total) {
        this.id = id;
        this.restaurant = restaurant;
        this.items = new LinkedHashMap<>(items);
        this.total = total;
        this.status = Status.PLACED;
    }

    public void advanceStatus() {
        switch (status) {
            case PLACED:           status = Status.PREPARING;        break;
            case PREPARING:        status = Status.OUT_FOR_DELIVERY;  break;
            case OUT_FOR_DELIVERY: status = Status.DELIVERED;         break;
            default: break;
        }
    }

    public int getId() { return id; }
    public Restaurant getRestaurant() { return restaurant; }
    public Map<MenuItem, Integer> getItems() { return items; }
    public double getTotal() { return total; }
    public Status getStatus() { return status; }
}
