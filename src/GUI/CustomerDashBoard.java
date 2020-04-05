package GUI;

import Business.*;
import Users.Customer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class CustomerDashBoard implements DashBoard{

    private Restaurant restaurant;
    private  Customer customer;
    private Stage window;
    private Scene loginScene;

    public void display(){

        ListView actions = new ListView();
        actions.setPadding(new Insets(10,0,10,0));
        actions.getItems().addAll("Current Reservations", "Create Reservation", "Log Out");
        actions.setPrefWidth(180); actions.setMaxWidth(180);
        AlertBox alertBox = new AlertBox();
        Order order = new Order();

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20,20,20,20));
        VBox tableLayout = selectTable(order);
        VBox viewLayout = viewReservations();
        layout.getChildren().add(viewLayout);

        actions.setOnMouseClicked(e -> {
            try {
                switch (actions.getSelectionModel().getSelectedItem().toString()) {
                    case "Current Reservations":
                        if(layout.getChildren().contains(tableLayout))
                        if (alertBox.display("If you leave without confirming, your order will be lost\nContinue?")) {
                            layout.getChildren().remove(0);
                            layout.getChildren().addAll(viewLayout);
                        }
                        break;
                    case "Create Reservation":
                        layout.getChildren().remove(0);
                        layout.getChildren().addAll(tableLayout);
                        break;
                    case "Log Out":
                        if (layout.getChildren().contains(viewLayout)) {
                            if (alertBox.display("Are you sure you want to log out?"))
                                window.setScene(this.loginScene);
                        }
                        else if (layout.getChildren().contains(tableLayout)) {
                            if (alertBox.display("If you Log Out without confirming, your order will be lost\nContinue?"))
                                window.setScene(this.loginScene);
                        }
                        break;
                }
            } catch (NullPointerException e1){};
        });
        HBox mainLayout = new HBox(20);
        mainLayout.getChildren().addAll(actions,layout);
        Scene scene = new Scene(mainLayout,1000,750);
        window.setScene(scene);
    }

    public VBox viewReservations() {

        Label welcome = new Label("Welcome back "+ customer.getName());
        welcome.setFont(new Font(24));
        Label reservationsTitle = new Label("Your Current Reservations :");
        reservationsTitle.setFont(new Font(20));
        Button cancelButton = new Button("Cancel Reservation");
        Text cancelError = new Text();
        cancelError.setFill(Color.RED);
        cancelError.setText("Highlight the reservation you want to cancel from the list then press cancel");
        AlertBox alertBox = new AlertBox();

        TreeView<String> orderList = customer.checkReservations(restaurant.getOrder().getOrders(), customer.getName());
        orderList.setMaxWidth(300); orderList.setPrefWidth(300);

        VBox viewLayout = new VBox(20);
        viewLayout.setPadding(new Insets(20,20,20,20));
        viewLayout.getChildren().addAll(welcome, new Separator(), reservationsTitle, orderList, cancelButton);

        cancelButton.setOnAction(e -> {
            viewLayout.getChildren().remove(cancelError);
                if (orderList.getSelectionModel().getSelectedItem() == null)
                    viewLayout.getChildren().add(cancelError);
                else
                    customer.cancelReservation(restaurant, customer, orderList, viewLayout, cancelError);
        });

        return viewLayout;
    }

    private VBox selectDishes(Order order) {
        Label price = new Label("Price: ");
        Label totalPrice = new Label("Total Price (Including taxes) : " + order.getOrderPrice());
        Text alert = new Text();
        alert.setFill(Color.RED);
        Button addDish = new Button("Add Dish");
        Button removeDish = new Button("Remove Dish");
        Button confirmOrder = new Button("Confirm Order");

        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        ListView orderList = new ListView();
        orderList.getItems().add("Selected Dishes : (Prices with taxes) ");
        orderList.setPrefWidth(350);
        orderList.setMaxWidth(350);
        orderList.setMaxHeight(200);


        ToggleGroup menuSelected = new ToggleGroup();
        ComboBox menuMainCourse = new ComboBox();
        ComboBox menuAppetizer = new ComboBox();
        ComboBox menuDessert = new ComboBox();
        menuAppetizer.setPromptText("Select Appetizer");
        menuMainCourse.setPromptText("Select Main Course");
        menuDessert.setPromptText("Select Dessert");
        for (Dish dish : restaurant.getDish().getDishes())
            if (dish instanceof MainCourseDish)
                menuMainCourse.getItems().add(dish.getName());
            else if (dish instanceof  AppetizerDish)
                menuAppetizer.getItems().add(dish.getName());
            else if (dish instanceof  DessertDish)
                menuDessert.getItems().add(dish.getName());


        menuMainCourse.setOnAction(e -> {
            try {
                menuAppetizer.setValue(null); menuDessert.setValue(null);
                restaurant.getDish().findDish(menuMainCourse.getValue().toString(), price, imageView);
            } catch (NullPointerException n1) {}
        });
        menuAppetizer.setOnAction(e -> {
            try {
                menuMainCourse.setValue(null); menuDessert.setValue(null);
                restaurant.getDish().findDish(menuAppetizer.getValue().toString(), price, imageView);
            } catch (NullPointerException n1) {}
        });
        menuDessert.setOnAction(e -> {
            try {
                menuAppetizer.setValue(null); menuMainCourse.setValue(null);
                restaurant.getDish().findDish(menuDessert.getValue().toString(), price, imageView);
            } catch (NullPointerException n1) {}
        });

        List<Dish> dishes = new ArrayList<>();
        addDish.setOnAction(e -> {
            alert.setText("");
            if (menuMainCourse.getValue() == null && menuAppetizer.getValue() == null && menuDessert.getValue() == null)
                alert.setText("Please select a dish from any menu then press add dish");
            else if (menuMainCourse.getValue() != null)
                customer.selectDish(restaurant.getDish(), dishes, order, menuMainCourse.getValue().toString(), orderList, totalPrice);
            else if (menuAppetizer.getValue() != null)
                customer.selectDish(restaurant.getDish(), dishes, order, menuAppetizer.getValue().toString(), orderList, totalPrice);
            else if (menuDessert.getValue() != null)
                customer.selectDish(restaurant.getDish(), dishes, order, menuDessert.getValue().toString(), orderList, totalPrice);
            menuMainCourse.setValue(null); menuAppetizer.setValue(null); menuDessert.setValue(null);
            price.setText("Price: ");
        });
        removeDish.setOnAction(e -> {
            if (orderList.getSelectionModel().isEmpty() || orderList.getSelectionModel().getSelectedIndex() == 0)
                alert.setText("Highlight a dish from the selected dishes list then press remove dish");
            else {
                alert.setText("");
                    order.setOrderPrice(order.getOrderPrice() - dishes.get(orderList.getSelectionModel().getSelectedIndex()-1).calculateTax());
                    orderList.getItems().remove(orderList.getSelectionModel().getSelectedIndex());
                    dishes.remove(orderList.getSelectionModel().getSelectedIndex());
                    for (Dish dish : dishes)
                    totalPrice.setText("Total Price (Including taxes) : LE " + order.getOrderPrice());
            }
        });
        confirmOrder.setOnAction(e -> {
            if (order.getOrderTableNumber() == 0)
                alert.setText("You must select a table by choosing Smoking/Non Smoking and Number of Seats");
            else if (dishes.isEmpty())
                alert.setText("You must select at least one dish and press Add Dish");
            else{
                alert.setText("");
                AlertBox alertBox = new AlertBox();
                if(alertBox.display("Are you sure you want to place your order for LE " + order.getOrderPrice())) {
                    order.setOrderCustomer(customer.getName());
                    order.setOrderedDishes(dishes);
                    customer.createReservation( restaurant, order);
                    display();
                }
            }
        });

        HBox addRemove = new HBox(20);
        addRemove.getChildren().addAll(addDish, removeDish);
        HBox orderInfo = new HBox(20);
        orderInfo.getChildren().addAll(orderList, imageView);
        HBox selection = new HBox(20);
        selection.getChildren().addAll(menuAppetizer, menuMainCourse, menuDessert, price);
        HBox total = new HBox(20);
        total.getChildren().addAll(confirmOrder, totalPrice);

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(new Separator(), orderInfo, selection, addRemove, alert, total);

        return layout;
    }

    private VBox selectTable(Order order) {
        Label title = new Label("Creating new reservation");
        title.setFont(new Font(24));
        Text noTables = new Text("");
        noTables.setFill(Color.RED);
        Label selectTable = new Label("Please select a table for reservation");
        RadioButton nonsmoking = new RadioButton("Non Smoking");
        RadioButton smoking = new RadioButton("Smoking");
        ToggleGroup selectSmoking = new ToggleGroup();
        nonsmoking.setToggleGroup(selectSmoking);
        smoking.setToggleGroup(selectSmoking);

        HBox isSmoking = new HBox(20);
        isSmoking.getChildren().addAll(nonsmoking, smoking);

        VBox tableLayout = new VBox(20);

        ComboBox numOfSeats = new ComboBox();
        numOfSeats.setPromptText("Number of Seats");

        smoking.setOnAction(e -> {
            noTables.setText("");
            for (Table table : restaurant.getTable().getTables())
                if (!table.isSmoking())
                    numOfSeats.getItems().removeAll(table.getNumber_of_seats());
            for (Table table : restaurant.getTable().getTables()) {
                if (table.isSmoking()) {
                    numOfSeats.getItems().add(table.getNumber_of_seats());
                    if (restaurant.getOrder().getOrders() != null)
                        for (Order checkAvailable : restaurant.getOrder().getOrders())
                            if (table.getNumber() == checkAvailable.getOrderTableNumber())
                                numOfSeats.getItems().removeAll(table.getNumber_of_seats());
                }
            }
            if (numOfSeats.getItems().isEmpty())
                noTables.setText("All tables in smoking area are reserved");
        });

        nonsmoking.setOnAction(e -> {
            noTables.setText("");
            for (Table table : restaurant.getTable().getTables())
                if (table.isSmoking())
                    numOfSeats.getItems().removeAll(table.getNumber_of_seats());
            for (Table table : restaurant.getTable().getTables()) {
                if (!table.isSmoking()) {
                    numOfSeats.getItems().addAll(table.getNumber_of_seats());
                    if (restaurant.getOrder().getOrders() != null)
                        for (Order checkAvailable : restaurant.getOrder().getOrders())
                            if (table.getNumber() == checkAvailable.getOrderTableNumber())
                                numOfSeats.getItems().removeAll(table.getNumber_of_seats());
                }
            }
            if (numOfSeats.getItems().isEmpty())
                noTables.setText("All tables in non-smoking area are reserved");
        });

        numOfSeats.setOnAction(e ->{
            if (numOfSeats.getValue() != null) {
                if (smoking.isSelected()) {
                    for (Table table : restaurant.getTable().getTables())
                        if (numOfSeats.getValue().equals(table.getNumber_of_seats()) && table.isSmoking())
                            order.setOrderTableNumber(table.getNumber());
                } else if (nonsmoking.isSelected()) {
                    for (Table table : restaurant.getTable().getTables())
                        if (numOfSeats.getValue().equals(table.getNumber_of_seats()) && !table.isSmoking())
                            order.setOrderTableNumber(table.getNumber());
                }
            }
        });

        VBox reserveLayout = selectDishes(order);


        tableLayout.setPadding(new Insets(20,20,20,20));
        tableLayout.getChildren().addAll(title, new Separator(), selectTable, isSmoking, noTables, numOfSeats, reserveLayout);

        return tableLayout;
    }



    public CustomerDashBoard(Restaurant restaurant, Customer customer, Stage window, Scene loginScene) {
        this.restaurant = restaurant;
        this.customer = customer;
        this.window = window;
        this.loginScene = loginScene;
    }
}
