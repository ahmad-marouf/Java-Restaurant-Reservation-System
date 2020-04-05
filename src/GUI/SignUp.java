package GUI;

import Business.Restaurant;
import Users.Customer;
import Users.Manager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;

public class SignUp {

    private Restaurant restaurant;
    private Stage window;
    private Scene loginScene;

    public void display() {

        Label title = new Label("Creating new Customer account");
        title.setFont(new Font(24));
        Text customerOnly = new Text("If you are a new employee please contact your manager to create your account");
        Text error = new Text();
        error.setFill(Color.RED);
        Separator separator = new Separator();
        Label nameLabel = new Label("Name :");
        Label usernameLabel = new Label("Username :");
        Label passwordLabel = new Label("Password :");
        Label confirmPassLabel = new Label("Enter Password Again :");
        AlertBox alertBox = new AlertBox();

        TextField nameField = new TextField(null);
        TextField usernameField = new TextField(null);
        PasswordField passwordField = new PasswordField();
        passwordField.setText(null);
        PasswordField confirmPassField = new PasswordField();
        confirmPassField.setText(null);
        Button create = new Button("Create Account");
        Button cancel = new Button("Go Back");
        GridPane gridPane = new GridPane();

        create.setOnAction(e1 ->{
            error.setText("");
                try {
                    if (passwordField.getText().equals(confirmPassField.getText())) {
                    Customer customer = new Customer(nameField.getText(), "Client", usernameField.getText(), passwordField.getText());
                    switch (customer.newCustomer(restaurant)) {
                        case -1:
                            error.setText("Username is already taken");
                            break;
                        case -2:
                            error.setText("Account already exits for " + customer.getName());
                            break;
                        case 0:
                            if (alertBox.display("A new account will be created for " + nameField.getText() + "\nContinue?")) {
                                restaurant.getUser().createUser(customer);
                                window.setScene(loginScene);
                            }
                            break;
                    }
                } else
                error.setText("Please make sure you entered the same password in both password fields");
                } catch (NullPointerException | JAXBException n1) {
                    error.setText("Please fill out all fields then press the Create Account button");
                }


        });

        cancel.setOnAction(e2 ->{
            if(alertBox.display("Are you sure you want to cancel and go back to Log In screen?"))
            window.setScene(loginScene);
        });

        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(20); gridPane.setVgap(20);
        gridPane.add(title, 0,0);
        GridPane.setColumnSpan(title,2);
        gridPane.add(customerOnly, 0,1);
        GridPane.setColumnSpan(customerOnly,2);
        gridPane.add(separator, 0,2);
        GridPane.setColumnSpan(separator,2);
        gridPane.add(nameLabel, 0,3);
        gridPane.add(nameField, 1,3);
        gridPane.add(usernameLabel, 0,4);
        gridPane.add(usernameField, 1,4);
        gridPane.add(passwordLabel, 0,5);
        gridPane.add(passwordField, 1,5);
        gridPane.add(confirmPassLabel, 0,6);
        gridPane.add(confirmPassField, 1,6);
        gridPane.add(error,0,7);
        GridPane.setColumnSpan(error,2);
        gridPane.add(create, 0,8);
        gridPane.add(cancel, 1,8);

        Scene scene = new Scene(gridPane,800,500);
        window.setScene(scene);

    }

    public SignUp(Restaurant restaurant, Stage window, Scene loginScene) {
        this.restaurant = restaurant;
        this.window = window;
        this.loginScene = loginScene;
    }
}
