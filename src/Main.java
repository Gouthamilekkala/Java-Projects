import models.*;
import services.*;

import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static RestaurantService restaurantService = new RestaurantService();
    static CartService cartService = new CartService();
    static OrderService orderService = new OrderService();

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║       WELCOME TO QUICKBITE   ║");
        System.out.println("╚══════════════════════════════╝");

        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": browseRestaurants(); break;
                case "2": viewCart();          break;
                case "3": viewOrders();        break;
                case "4":
                    System.out.println("\nThank you for using QuickBite! Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    static void printMainMenu() {
        System.out.println("\n==============================");
        System.out.println("          MAIN MENU");
        System.out.println("==============================");
        System.out.println("  1. Browse Restaurants");
        System.out.println("  2. View Cart");
        System.out.println("  3. My Orders");
        System.out.println("  4. Exit");
        System.out.println("==============================");
        System.out.print("Choose: ");
    }

    static void browseRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        System.out.println("\n-------- Restaurants --------");
        for (int i = 0; i < restaurants.size(); i++) {
            Restaurant r = restaurants.get(i);
            System.out.printf("  %d. %-18s  * %.1f  %d min  [%s]%n",
                i + 1, r.getName(), r.getRating(), r.getDeliveryTime(), r.getCuisine());
        }
        System.out.println("  0. Back");
        System.out.print("Select restaurant: ");

        String input = scanner.nextLine().trim();
        if (input.equals("0")) return;

        try {
            int idx = Integer.parseInt(input) - 1;
            if (idx >= 0 && idx < restaurants.size()) {
                viewMenu(restaurants.get(idx));
            } else {
                System.out.println("Invalid selection.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    static void viewMenu(Restaurant restaurant) {
        Cart cart = cartService.getCart();
        if (!cart.isEmpty() && cart.getRestaurant() != restaurant) {
            System.out.println("\nYour cart has items from " + cart.getRestaurant().getName() + ".");
            System.out.print("Clear cart and switch to " + restaurant.getName() + "? (y/n): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("y")) return;
            cartService.clearCart();
        }

        while (true) {
            System.out.println("\n=== " + restaurant.getName().toUpperCase() + " MENU ===");
            List<MenuItem> menu = restaurant.getMenu();
            String currentCategory = "";
            for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.get(i);
                if (!item.getCategory().equals(currentCategory)) {
                    currentCategory = item.getCategory();
                    System.out.println("\n  -- " + currentCategory + " --");
                }
                System.out.printf("  %d. %-26s $%.2f%n", i + 1, item.getName(), item.getPrice());
            }
            System.out.println("\n  [A] Add item   [V] View cart   [B] Back");
            System.out.print("  Choice: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            if (choice.equals("B")) {
                break;
            } else if (choice.equals("V")) {
                viewCart();
            } else if (choice.equals("A")) {
                System.out.print("  Enter item number: ");
                String input = scanner.nextLine().trim();
                try {
                    int idx = Integer.parseInt(input) - 1;
                    if (idx >= 0 && idx < menu.size()) {
                        MenuItem selected = menu.get(idx);
                        cartService.addItem(restaurant, selected);
                        System.out.println("  >> " + selected.getName() + " added to cart!");
                    } else {
                        System.out.println("  Invalid item number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("  Invalid input.");
                }
            } else {
                System.out.println("  Invalid option.");
            }
        }
    }

    static void viewCart() {
        Cart cart = cartService.getCart();
        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty.");
            return;
        }

        System.out.println("\n=== YOUR CART === [" + cart.getRestaurant().getName() + "]");
        List<MenuItem> keys = new ArrayList<>(cart.getItems().keySet());
        for (int i = 0; i < keys.size(); i++) {
            MenuItem item = keys.get(i);
            int qty = cart.getItems().get(item);
            System.out.printf("  %d. %-26s x%d   $%.2f%n",
                i + 1, item.getName(), qty, item.getPrice() * qty);
        }
        System.out.println("  ----------------------------------------");
        System.out.printf("  Total:                             $%.2f%n", cart.getTotal());
        System.out.println("\n  [C] Checkout   [R] Remove item   [B] Back");
        System.out.print("  Choice: ");
        String choice = scanner.nextLine().trim().toUpperCase();

        if (choice.equals("C")) {
            checkout();
        } else if (choice.equals("R")) {
            System.out.print("  Enter item number to remove: ");
            String input = scanner.nextLine().trim();
            try {
                int idx = Integer.parseInt(input) - 1;
                if (idx >= 0 && idx < keys.size()) {
                    MenuItem item = keys.get(idx);
                    cartService.removeItem(item);
                    System.out.println("  >> " + item.getName() + " removed from cart.");
                } else {
                    System.out.println("  Invalid item number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input.");
            }
        }
    }

    static void checkout() {
        Cart cart = cartService.getCart();
        System.out.println("\n-------- Order Summary --------");
        for (Map.Entry<MenuItem, Integer> entry : cart.getItems().entrySet()) {
            System.out.printf("  %-26s x%d   $%.2f%n",
                entry.getKey().getName(), entry.getValue(),
                entry.getKey().getPrice() * entry.getValue());
        }
        System.out.println("  ----------------------------------------");
        System.out.printf("  Total:                             $%.2f%n", cart.getTotal());
        System.out.print("\nConfirm order? (y/n): ");

        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            Order order = orderService.placeOrder(cart);
            cartService.clearCart();
            System.out.println("\n  Order #" + order.getId() + " placed successfully!");
            System.out.println("  Estimated delivery: " + order.getRestaurant().getDeliveryTime() + " min");
            System.out.println("\n  Status: PLACED --> PREPARING --> OUT FOR DELIVERY --> DELIVERED");
            System.out.println("            ^");
            System.out.println("          (current)");
            System.out.println("\n  Track your order anytime from 'My Orders'.");
        } else {
            System.out.println("Order cancelled.");
        }
    }

    static void viewOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("\nNo orders yet.");
            return;
        }

        System.out.println("\n=== MY ORDERS ===");
        for (Order o : orders) {
            System.out.printf("  #%d  %-18s  $%.2f   [%s]%n",
                o.getId(), o.getRestaurant().getName(), o.getTotal(), o.getStatus());
        }
        System.out.println("\n  [T] Track/advance an order   [B] Back");
        System.out.print("  Choice: ");
        String choice = scanner.nextLine().trim().toUpperCase();

        if (choice.equals("T")) {
            System.out.print("  Enter order ID: ");
            String input = scanner.nextLine().trim();
            try {
                int id = Integer.parseInt(input);
                Order order = orderService.getById(id);
                if (order != null) {
                    order.advanceStatus();
                    System.out.println("\n  Order #" + order.getId() + " - " + order.getRestaurant().getName());
                    System.out.println("  Status: " + order.getStatus());
                } else {
                    System.out.println("  Order not found.");
                }
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input.");
            }
        }
    }
}
