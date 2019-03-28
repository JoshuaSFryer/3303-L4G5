package com.sysc3303;

import com.sysc3303.commons.Direction;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        //TODO change the screen size dynamically
        int windowHeight = 800;
        int windowWidth = 900;
        int floorNumber = 20;
        int numberOfElevators = 5;

        //Main Container
        BorderPane bPane = new BorderPane();
        bPane.setPadding(new Insets(20,20,20,20));


        //VBOX under the main container
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        for (int i = floorNumber; i > 0 ; i--) {

            Label floorLabel = new Label("Floor " + i );
            CustomButton downButton = CustomButton.create(i,  Direction.UP);
            CustomButton upButton = CustomButton.create(i,  Direction.DOWN);
            vBox.getChildren().addAll(floorLabel, downButton, upButton);


        }

        //Adding Elements to Container: Border Pane
        //TODO
        bPane.setTop(new Text("Top"));
        bPane.setRight(new Text("Right"));
        bPane.setLeft(vBox);
        bPane.setCenter(new Text("Center"));
        bPane.setBottom(new Text("Bottom"));






        Scene scene = new Scene(bPane, windowWidth, windowHeight);
        scene.getStylesheets().
                add(Main.class.getResource("styleSheet.css").toExternalForm());
        primaryStage.setTitle("Elevator Control Panel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}