package com.learntocode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class OnlineStore {
    private static ArrayList<Product> inventory = new ArrayList<>();
    private static ArrayList<Product> cart = new ArrayList<>();
    private static double total = 0.0;

    public static void main(String[] args) {
        getInventory();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    showProducts(scanner);
                    break;
                case 2:
                    showCart(scanner);
                    break;
                case 3:
                    System.out.println("Thank you for shopping with us!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void getInventory() {
        File inventoryFile = new File("inventory.csv");
        try {
            Scanner scanner = new Scanner(inventoryFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                String id = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                inventory.add(new Product(id, name, price));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Inventory file not found.");
        }
    }

    private static void showProducts(Scanner scanner) {
        System.out.println("Products:");
        for (Product product : inventory) {
            System.out.println(product.getId() + "|" + product.getName() + "|$" + product.getPrice());
        }
        System.out.println("Press X. to Go back to home screen");
        System.out.print("Enter product id to add to cart: ");

        String choice = scanner.next();
        if (choice.equalsIgnoreCase("X")) {
            return;
        }

        //int productId = Integer.parseInt(choice);

        for (Product product : inventory) {
            if (product.getId().equals(choice)) {
                cart.add(product);
                System.out.println(product.getName() + " added to cart.");
                break;
            }
        }

        showProducts(scanner);
    }

    private static void showCart(Scanner scanner) {
        int size = cart.size();

        if (cart.size() > 0) {

            System.out.println("Your cart:");
            for (Product product : cart) {
                int quantity = 0;
                for (Product cartProduct : cart) {
                    if (cartProduct.getId() == product.getId()) {
                        quantity++;
                    }
         else {
                    System.out.println("Your cart is empty.");
                }
                System.out.println(quantity + "x " + product.getName() + " - $" + product.getPrice() * quantity);
            }
            System.out.println("Total: $" + getTotal());

            System.out.println("1. Check out");
            System.out.println("2. Remove item(s) from cart");
            System.out.println("3. Go back to home screen");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkout(scanner);
                    break;
                case 2:
                    showCart(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        showCart(scanner);
    } }

    private static void checkout(Scanner scanner) {
        System.out.println("Total: $" + getTotal());
        System.out.print("Enter payment amount: ");

        double payment = scanner.nextDouble();

        if (payment < getTotal()) {
            System.out.println("Insufficient payment. Please try again.");
        } else {
            double change = payment - getTotal();
            System.out.println("Change: $" + change);

            System.out.println("Items sold:");

        }

    }

    private static double getTotal() {
        double total = 0.0;
        for (Product product : cart) {
            total += product.getPrice();
        }
        return total;
    }
}