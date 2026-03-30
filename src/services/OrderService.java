package services;

import models.Cart;
import models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private List<Order> orders;
    private int nextId;

    public OrderService() {
        orders = new ArrayList<>();
        nextId = 1001;
    }

    public Order placeOrder(Cart cart) {
        Order order = new Order(nextId++, cart.getRestaurant(), cart.getItems(), cart.getTotal());
        orders.add(order);
        return order;
    }

    public List<Order> getAllOrders() {
        return orders;
    }

    public Order getById(int id) {
        for (Order o : orders) {
            if (o.getId() == id) return o;
        }
        return null;
    }
}
