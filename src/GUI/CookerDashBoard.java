package GUI;

import Business.Restaurant;
import Users.Cooker;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CookerDashBoard implements DashBoard {

    private Restaurant restaurant;
    private Cooker cooker;
    private Stage window;
    private Scene loginScene;

    @Override
    public void display() {

        ListView actions = new ListView<>();
        actions.setPadding(new Insets(10,0,10,0));
        actions.getItems().addAll("Today's Reservations", "Log Out");
        AlertBox alertBox = new AlertBox();

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        VBox viewLayout = viewReservations();
        layout.getChildren().add(viewLayout);

        actions.setOnMouseClicked(e -> {
            try {
                switch (actions.getSelectionModel().getSelectedItem().toString()) {
                    case "Today's Reservations":
                        layout.getChildren().removeAll(viewLayout);
                        layout.getChildren().addAll(viewLayout);
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

        Label welcome = new Label("Welcome back " + cooker.getName());
        welcome.setFont(new Font(24));
        Label reservationsTitle = new Label("Today's Reservations:");
        reservationsTitle.setFont(new Font(20));


        TreeView<String> orderList = cooker.checkReservations(restaurant.getOrder().getOrders());


        VBox information = new VBox(30);
        information.setPadding(new Insets(20));
        information.getChildren().addAll(welcome, reservationsTitle, orderList);

        return information;
    }

    public CookerDashBoard(Restaurant restaurant, Cooker cooker, Stage window, Scene loginScene) {
        this.restaurant = restaurant;
        this.cooker = cooker;
        this.window = window;
        this.loginScene = loginScene;
    }
}
