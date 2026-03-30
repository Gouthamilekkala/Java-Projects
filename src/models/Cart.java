package models;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private Restaurant restaurant;
    private Map<MenuItem, Integer> items;

    public Cart() {
        this.items = new LinkedHashMap<>();
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void addItem(MenuItem item) {
        items.put(item, items.getOrDefault(item, 0) + 1);
    }

    public void removeItem(MenuItem item) {
        if (items.containsKey(item)) {
            int qty = items.get(item);
            if (qty <= 1) {
                items.remove(item);
            } else {
                items.put(item, qty - 1);
            }
        }
        if (items.isEmpty()) {
            restaurant = null;
        }
    }

    public Map<MenuItem, Integer> getItems() {
        return items;
    }

    public double getTotal() {
        double total = 0;
        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
        restaurant = null;
    }
}
