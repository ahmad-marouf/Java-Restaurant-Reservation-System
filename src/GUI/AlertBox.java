package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;

public class AlertBox {

    private boolean selection = false;

    public boolean display(String message){

        Stage window = new Stage();
        Scene scene;

        window.initModality(Modality.APPLICATION_MODAL);

        Label alert = new Label(message);
        alert.setAlignment(Pos.CENTER);
        alert.setTextAlignment(TextAlignment.CENTER);

        Button confirm = new Button("Yes");
        Button cancel = new Button("Cancel");

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(confirm, cancel);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(alert, buttons);

        scene = new Scene(layout, 500,150);


        confirm.setOnAction(e ->{
            selection = true;
            window.close();
        });

        cancel.setOnAction(e ->{
            selection = false;
            window.close();
        });

        window.setScene(scene);
        window.showAndWait();

        return selection;
    }
}
