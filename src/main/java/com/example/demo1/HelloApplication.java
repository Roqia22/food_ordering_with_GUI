package com.example.demo1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.example.demo1.Restaurant.initializeRestaurants;

public class HelloApplication extends Application{
  Stage window;
    Scene page1,page2;
    @Override
    public void start(Stage stage) throws IOException {
        window=stage;
        window.setOnCloseRequest(e ->{
            e.consume();//to solve the problem as it close even click on No button
            close_window();
        });
//        button
        Button button1=new Button("Admin");
        Button button2=new Button("User");
        Button button3=new Button("DeliveryStaff");
        //css
        button1.getStyleClass().add("button-Background");
        button2.getStyleClass().add("button-Background");
        button3.getStyleClass().add("button-Background");
        button1.setOnAction(e->{
            Person admin=new Admin();
            admin.logIn();
            //admin
        });
        button2.setOnAction(e->{
               // بسم الله الرحمن الرحيم
            Person customer=new Customer();
           customer.logIn();
        });
        button3.setOnAction(e->{
            Person delivery=new DeliveryStaff();
            delivery.logIn();
            //delivery

        });
        Label label=new Label("Welcome ");
        VBox vBox=new VBox(10);
        vBox.getChildren().add(label);
        vBox.setAlignment(Pos.CENTER);

//for buttons
         VBox center = new VBox(30);
         center.getChildren().addAll(button1,button2,button3);

         center.setAlignment(Pos.CENTER);
          HBox bottom=new HBox(12);
        BorderPane all=new BorderPane();
        all.setCenter(center);
        all.setMargin(center, new Insets(0, 0, 10, 0));
        all.setTop(vBox);


        all.getStyleClass().add("background");
        page1=new Scene(all,500,500);
        page1.getStylesheets().add("style.css");
        window.setScene(page1);
        window.setTitle("HEllo Roqia!...");
        window.show();



    }
    private void close_window(){
        Confirm c=new Confirm();
       boolean confirm= c.display("confirm","Are you want to Exist this programme");
        if(confirm)
            window.close();
    }







    public static void main(String[] args) {
        launch();
    }
}