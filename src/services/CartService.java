package services;

import models.Cart;
import models.MenuItem;
import models.Restaurant;

public class CartService {
    private Cart cart;

    public CartService() {
        cart = new Cart();
    }

    public void addItem(Restaurant restaurant, MenuItem item) {
        if (cart.getRestaurant() == null) {
            cart.setRestaurant(restaurant);
        }
        cart.addItem(item);
    }

    public void removeItem(MenuItem item) {
        cart.removeItem(item);
    }

    public Cart getCart() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }
}
