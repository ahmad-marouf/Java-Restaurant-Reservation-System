package GUI;

import Business.Dish;
import Business.Restaurant;
import Users.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import javax.xml.bind.JAXBException;


public class Login extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws JAXBException {

        Stage window = primaryStage;

        Label nameLabel = new Label("Username:");
        Label passLabel = new Label("Password");
        Text error = new Text();
        TextField nameField = new TextField();
        PasswordField passField = new PasswordField();
        nameField.setPromptText("username"); passField.setPromptText("password");
        Text newUser = new Text("If this is your first time please sign up to create a new account");


        Button login = new Button("Log In");
        Button signup = new Button("Sign Up");

        Restaurant restaurant = new Restaurant();


        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10,10,10,10));
        GridPane.setConstraints(nameLabel, 0,0);
        GridPane.setConstraints(nameField,1,0);
        GridPane.setConstraints(passLabel,0,1);
        GridPane.setConstraints(passField,1,1);
        GridPane.setConstraints(login, 0,3);
        GridPane.setConstraints(signup, 0,5);
        GridPane.setConstraints(error, 0, 2);
        GridPane.setColumnSpan(error, 3);
        GridPane.setConstraints(newUser, 0,4);
        GridPane.setColumnSpan(newUser,2);
        grid.getChildren().addAll(nameLabel,passLabel, nameField, passField, newUser, login, signup);
        grid.setAlignment(Pos.CENTER);

        Scene scene = new Scene(grid, 600,350);

        login.setOnAction(e ->{
            try {
                restaurant.loadRestaurant();
            } catch (JAXBException ex) {
                ex.printStackTrace();
            }
            restaurant.assignUsers();
            restaurant.assignDishes();
            grid.getChildren().removeAll(error);
            try {
            User user = restaurant.getUser().authenticate(nameField.getText(), passField.getText());
                switch (user.getRole()) {
                    case "Manager":
                        ManagerDashboard managerDash = new ManagerDashboard(restaurant, (Manager) user, window, scene);
                        managerDash.display();
                        break;
                    case "Cooker":
                        CookerDashBoard cookerDash = new CookerDashBoard(restaurant, (Cooker) user, window, scene);
                        cookerDash.display();
                        break;
                    case "Waiter":
                        WaiterDashBoard waiterDash = new WaiterDashBoard(restaurant, (Waiter) user, window, scene);
                        waiterDash.display();
                        break;
                    case "Client":
                        CustomerDashBoard customerDash = new CustomerDashBoard(restaurant, (Customer) user, window, scene);
                        customerDash.display();
                        break;
                }
            } catch (NullPointerException n1) {
                error.setText("Incorrect username/password entered");
                error.setFill(Color.RED);
                grid.getChildren().add(error);
            }
            nameField.setText(""); passField.setText("");
        });

        signup.setOnAction(e ->{
            try {
                restaurant.loadRestaurant();
            } catch (JAXBException ex) {
                ex.printStackTrace();
            }
            SignUp signUp = new SignUp(restaurant, primaryStage, scene);
            signUp.display();
        });

        window.setScene(scene);
        window.setTitle("Restaurant");
        window.show();
    }
}
