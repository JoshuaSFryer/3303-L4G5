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
import javafx.scene.shape.Rectangle;
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
        int floorNumber = 22;
        int numberOfElevators = 4;

        //Main Container
        BorderPane bPane = new BorderPane();
        bPane.setPadding(new Insets(20,20,20,20));


        //VBOX under the main container
//        VBox vBox = new VBox();
//        vBox.setSpacing(10);
//        vBox.setBackground(Background.EMPTY);
//        String style = "-fx-background-color: rgba(140, 255, 140, 0.5);";
//        vBox.setStyle(style);

//        for (int i = floorNumber; i > 0 ; i--) {
//
//            Label floorLabel = new Label("Floor " + i );
//            CustomButton downButton = CustomButton.create(i,  Direction.UP);
//            CustomButton upButton = CustomButton.create(i,  Direction.DOWN);
//            vBox.getChildren().addAll(floorLabel, downButton, upButton);
//
//
//        }

        //Creating the "elevators"
        GridPane gPane = new GridPane();
        gPane.setMinSize(700,600);
        gPane.setHgap(5);
        gPane.setVgap(5);
        //gPane.setAlignment(Pos.CENTER);
        gPane.setPadding(new Insets(25,25,25,25));
        //gPane.setRowSpan(vBox, floorNumber);
        //gPane.add(vBox, 0,0 );



        Text test = new Text ("Testing gPane");

        for (int n = 0 ; n < floorNumber; n++){
            for (int m = 1; m <numberOfElevators+1 ; m++){

                    //Create the Rectangle
                    Rectangle square = new Rectangle();

                    //Set the area properties
                    square.setWidth(40);
                    square.setHeight(40);
                    //Fill in colours
                    square.setFill(Color.BLACK);
                    //Add to the Pane
                    gPane.add(square, m, floorNumber-n-1);


                }
             HBox hBox = new HBox();
             hBox.setSpacing(5);

            Label floorLabel = new Label("Floor " + n );
            CustomButton downButton = CustomButton.create(n,  Direction.UP);
            CustomButton upButton = CustomButton.create(n,  Direction.DOWN);

            hBox.getChildren().addAll(floorLabel, upButton, downButton);
            gPane.add(hBox, 0, floorNumber-n-1);

//            gPane.add(upButton, 0, n);
//            gPane.add(downButton,0, n);

        }













        gPane.setPadding(new Insets(25,25,25,25));

        //gPane.getChildren().addAll(elevator);



        //Adding Elements to Container: Border Pane
        //TODO
        bPane.setTop(new Text("Top"));
        bPane.setRight(new Text("Right"));
        //bPane.setLeft(vBox);
        bPane.setCenter(gPane);


        bPane.setBottom(new Text("Bottom"));






        Scene scene = new Scene(bPane, windowWidth, windowHeight);
        scene.getStylesheets().
                add(Main.class.getResource("styleSheet.css").toExternalForm());
        primaryStage.setTitle("Elevator Control Panel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}