package com.example.demo1;

import java.io.Serializable;

public class Food implements Serializable {
    String name;
    String type; // e.g., Main Meal, Side Dish, Drink, etc.
    double price;

    Food(String name, String type, double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
}

