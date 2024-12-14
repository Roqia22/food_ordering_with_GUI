package com.example.demo1;

import com.example.demo1.Food;
import com.example.demo1.Restaurant;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Order  {

    private ArrayList<Restaurant> restaurants = Restaurant.initializeRestaurants(); // All restaurants
    private ComboBox<String> categoryComboBox = new ComboBox<>();
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ComboBox<String> menuComboBox = new ComboBox<>();
    private ListView<String> orderListView = new ListView<>();
    private Label totalPriceLabel = new Label("Total Price: 0 EGP");

    private List<Food> selectedItems = new ArrayList<>();
    private double totalPrice = 0.0;


    public void start() {
        Stage primaryStage=new Stage();
        ObservableList<String> categories = FXCollections.observableArrayList(
                "Pizza", "Fried Chicken", "Sea Food", "Dessert", "Burgers", "Café");

        categoryComboBox.setItems(categories);


        categoryComboBox.setOnAction(e -> updateRestaurantsByCategory());


        restaurantComboBox.setOnAction(e -> updateMenuByRestaurant());


        menuComboBox.setOnAction(e -> addToOrder());


        Button createOrderButton = new Button("Create Order");
        createOrderButton.setOnAction(e -> createOrder());


        Button viewOrderButton = new Button("View Order");
        viewOrderButton.setOnAction(e -> viewOrder());


        Button getTotalPriceButton = new Button("Get Total Price");
        getTotalPriceButton.setOnAction(e -> getTotalPrice());


        VBox categoryBox = new VBox(new Label("Select Category:"), categoryComboBox);
        categoryBox.setSpacing(10);
        categoryBox.setPadding(new Insets(10));

        VBox restaurantBox = new VBox(new Label("Select Restaurant:"), restaurantComboBox);
        restaurantBox.setSpacing(10);
        restaurantBox.setPadding(new Insets(10));

        VBox menuBox = new VBox(new Label("Select Menu Item:"), menuComboBox);
        menuBox.setSpacing(10);
        menuBox.setPadding(new Insets(10));

        VBox orderBox = new VBox(new Label("Your Order:"), orderListView, totalPriceLabel);
        orderBox.setSpacing(10);
        orderBox.setPadding(new Insets(10));
        orderBox.setVisible(false);

        HBox buttonBox = new HBox(createOrderButton, viewOrderButton, getTotalPriceButton);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10));


        buttonBox.setAlignment(Pos.CENTER);

        VBox mainLayout = new VBox(categoryBox, restaurantBox, menuBox, orderBox, buttonBox);
        mainLayout.setSpacing(20);
        mainLayout.setPadding(new Insets(20));

        Scene scene = new Scene(mainLayout, 500, 500);
//        mainLayout.getStyleClass().add("background_emptyImage");
        scene.getStylesheets().add("style.css");
        primaryStage.setTitle("Order Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateRestaurantsByCategory() {
        String selectedCategory = categoryComboBox.getValue();
        if (selectedCategory != null) {
            ObservableList<String> filteredRestaurants = FXCollections.observableArrayList(
                    restaurants.stream()
                            .filter(r -> r.getCategory().toLowerCase().contains(selectedCategory.toLowerCase()))
                            .map(Restaurant::getName)
                            .collect(Collectors.toList())
            );
            restaurantComboBox.setItems(filteredRestaurants);
            menuComboBox.getItems().clear();
        }
    }

    private void updateMenuByRestaurant() {
        String selectedRestaurantName = restaurantComboBox.getValue();
        if (selectedRestaurantName != null) {
            // Find the selected restaurant and get its menu
            Restaurant selectedRestaurant = restaurants.stream()
                    .filter(r -> r.getName().equals(selectedRestaurantName))
                    .findFirst()
                    .orElse(null);

            if (selectedRestaurant != null) {

                ObservableList<String> menuItems = FXCollections.observableArrayList(
                        selectedRestaurant.getMenu().stream()
                                .map(food -> food.getName() + " - " + food.getType() + " - " + food.getPrice() + " EGP")
                                .collect(Collectors.toList())
                );
                menuComboBox.setItems(menuItems);
            }
        }
    }

    private void addToOrder() {
        String selectedMenuItem = menuComboBox.getValue();
        if (selectedMenuItem != null) {
            String[] parts = selectedMenuItem.split(" - ");
            String itemName = parts[0];
            String itemType = parts[1];
            double itemPrice = Double.parseDouble(parts[2].replace(" EGP", ""));

            Food selectedFood = new Food(itemName, itemType, itemPrice);
            selectedItems.add(selectedFood);

            orderListView.getItems().add(selectedFood.getName() + " - " + selectedFood.getPrice() + " EGP");

            totalPrice += itemPrice;
        }
    }

    private void createOrder() {
        // Display order creation confirmation
        if (selectedItems.isEmpty()) {
            showAlert("No items selected", "Please select items to create an order.");
        } else {
            showAlert("Order Created", "Your order has been created successfully!");
        }
    }

    private void viewOrder() {

        StringBuilder orderDetails = new StringBuilder("Order Details:\n");
        for (Food item : selectedItems) {
            orderDetails.append(item.getName()).append(" - ").append(item.getPrice()).append(" EGP\n");
        }
        orderDetails.append("\nTotal Price: ").append(totalPrice).append(" EGP");

        // Show the order ListView
        orderListView.setVisible(true);
        totalPriceLabel.setText("Total Price: " + totalPrice + " EGP");

        showAlert("View Order", orderDetails.toString());
    }

    private void getTotalPrice() {

        showAlert("Total Price", "The total price of your order is: " + totalPrice + " EGP");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
