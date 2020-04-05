package GUI;

import Business.Restaurant;
import Users.Manager;
import Users.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

public class ManagerDashboard implements  DashBoard {

    private Restaurant restaurant;
    private Manager manager;
    private Stage window;
    private Scene loginScene;

    @Override
    public void display() {

        ListView actions = new ListView<>();
        actions.setPadding(new Insets(10,0,10,0));
        actions.getItems().addAll("Today's Reservations", "Check Statistics", "Create New Employee Account", "Log Out");
        AlertBox alertBox = new AlertBox();

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        VBox stats = showStats();
        GridPane createEmployee = createEmployee();
        VBox viewLayout = viewReservations();
        layout.getChildren().add(viewLayout);


        actions.setOnMouseClicked(e -> {
            try {
                switch (actions.getSelectionModel().getSelectedItem().toString()) {
                    case "Today's Reservations":
                        if (layout.getChildren().contains(createEmployee)) {
                            if (alertBox.display("if you leave without confirming, the account will not be created\nContinue?")) {
                                layout.getChildren().removeAll(viewLayout, createEmployee, stats);
                                layout.getChildren().addAll(viewLayout);
                            }}
                        else {
                            layout.getChildren().removeAll(viewLayout, createEmployee, stats);
                            layout.getChildren().addAll(viewLayout);
                        }
                        break;
                    case "Create New Employee Account":
                        layout.getChildren().removeAll(viewLayout,createEmployee,stats);
                        layout.getChildren().addAll(createEmployee);
                        break;
                    case "Check Statistics":
                        if (layout.getChildren().contains(createEmployee)) {
                            if (alertBox.display("if you leave without confirming, the account will not be created\nContinue?")) {
                                layout.getChildren().removeAll(viewLayout, createEmployee, stats);
                                layout.getChildren().addAll(stats);
                            }}
                        else {
                            layout.getChildren().removeAll(viewLayout, createEmployee, stats);
                            layout.getChildren().addAll(stats);
                        }
                        break;
                    case "Log Out":
                        if (alertBox.display("Are you sure you want to log out?"))
                            window.setScene(this.loginScene);
                        break;
                }
            } catch (NullPointerException e1){};

        });
        HBox mainLayout = new HBox(20);
        mainLayout.getChildren().addAll(actions, layout);
        Scene scene = new Scene(mainLayout, 1000, 750);
        window.setScene(scene);
    }

    public VBox viewReservations(){

        Label welcome = new Label("Welcome back " + manager.getName());
        welcome.setFont(new Font(24));
        Label reservationsTitle = new Label("Today's Reservations:");
        reservationsTitle.setFont(new Font(20));
        Label totalRevenue = new Label();
        totalRevenue.setFont(new Font(20));

        TreeView<String> orderList = manager.checkReservations(restaurant.getOrder().getOrders());

        totalRevenue.setText("Total Revenue today : LE " + manager.calculateRevenue(restaurant.getOrder().getOrders()));

        VBox information = new VBox(30);
        information.setPadding(new Insets(20));
        information.getChildren().addAll(welcome, reservationsTitle, orderList, totalRevenue);

        return information;
    }

    private VBox showStats() {

        Label title = new Label("Restaurant Statistics");
        title.setFont(new Font(24));
        Label customerLabel = new Label("Most Frequent Customer(s) :");
        customerLabel.setFont(new Font(20));
        Label dishLabel = new Label("Most Ordered Dish(es) :");
        dishLabel.setFont(new Font(20));
        Label smokerLabel = new Label("Majority of customers are :");
        smokerLabel.setFont(new Font(20));

        Text customerNames = new Text();
        customerNames.setFont(new Font(18));
        Text dishName = new Text();
        dishName.setFont(new Font(18));
        Text isSmoking = new Text();
        isSmoking.setFont(new Font(18));

        List<String> customerModes = manager.checkStats(restaurant, "Customer");
        for (String customer : customerModes)
            customerNames.setText(customerNames.getText() + '\'' + customer + "\'\n ");
        List<String> dishModes = manager.checkStats(restaurant, "Dish");
        for (String dish : dishModes)
            dishName.setText(dishName.getText() + '\'' + dish + "\'\n ");
        List<String> smokingModes = manager.checkStats(restaurant, "Smoking");
        if (smokingModes.size() > 1)
            isSmoking.setText("Equal number of smoker and non-smoker customers");
        else
            isSmoking.setText(smokingModes.get(0));

        HBox name = new HBox(20);
        name.getChildren().addAll(customerLabel, customerNames);
        HBox dish = new HBox(20);
        dish.getChildren().addAll(dishLabel, dishName);
        HBox smoking = new HBox(20);
        smoking.getChildren().addAll(smokerLabel, isSmoking);

        VBox stats = new VBox(20);
        stats.getChildren().addAll(title, new Separator(), name, dish, smoking);

        return stats;
    }

    private GridPane createEmployee() {

        Label title = new Label("Enter Employee Information");
        title.setFont(new Font(24));
        Separator separator = new Separator();
        Label nameLabel = new Label("Name :");
        Label usernameLabel = new Label("Username :");
        Label passwordLabel = new Label("Password :");
        Label roleLabel = new Label("Role :");
        Button confirm = new Button("Confirm");
        Text error = new Text();
        error.setFill(Color.RED);
        AlertBox alertBox = new AlertBox();

        TextField employeeName = new TextField();
        TextField employeeUsername = new TextField();
        TextField employeePassword = new TextField();
        ChoiceBox selectRole = new ChoiceBox();
        selectRole.getItems().addAll("Manager", "Waiter", "Cooker");
        GridPane gridPane = new GridPane();
        GridPane.setColumnSpan(error, 2);

        confirm.setOnAction(e -> {
            error.setText("");
                try {
                    User user = new User(employeeName.getText(), selectRole.getValue().toString(), employeeUsername.getText(), employeePassword.getText());
                    switch(manager.newEmployee(restaurant, user)) {
                        case -1:
                            error.setText("Username is already taken");
                            break;
                        case -2:
                            error.setText("Account already exits for " + user.getName());
                            break;
                        case 0:
                            if (alertBox.display("A new account will be created for " + selectRole.getValue() + " " + employeeName.getText() + "\nContinue?")) {
                                restaurant.getUser().createUser(user);
                                display();
                            }
                            break;
                    }
                } catch (JAXBException e1) {
                    e1.printStackTrace();
                } catch (NullPointerException n1) {
                    error.setText("Please fill out all fields");
                }
        });

        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(20); gridPane.setHgap(20);
        gridPane.add(title, 0,0);
        GridPane.setColumnSpan(title, 2);
        gridPane.add(separator, 0,1);
        GridPane.setColumnSpan(separator,2);
        gridPane.add(nameLabel, 0,2);
        gridPane.add(employeeName, 1,2);
        gridPane.add(usernameLabel, 0,3);
        gridPane.add(employeeUsername, 1,3);
        gridPane.add(passwordLabel, 0,4);
        gridPane.add(employeePassword, 1,4);
        gridPane.add(roleLabel, 0,5);
        gridPane.add(selectRole, 1,5);
        gridPane.add(confirm, 0, 6);
        gridPane.add(error, 0, 7);

        return gridPane;
    }

    public ManagerDashboard(Restaurant restaurant, Manager manager, Stage window, Scene loginScene) {
        this.restaurant = restaurant;
        this.manager = manager;
        this.window = window;
        this.loginScene = loginScene;
    }
}
