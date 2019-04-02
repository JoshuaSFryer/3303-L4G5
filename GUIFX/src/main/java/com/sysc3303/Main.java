package com.sysc3303;


import com.sysc3303.commons.ConfigListener;
import com.sysc3303.commons.Direction;
import com.sysc3303.communication.GUIElevatorMoveMessage;
import com.sysc3303.communication.GUIFloorMessage;
import javafx.application.Application;
import javafx.application.Platform;
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
import net.miginfocom.layout.Grid;

import java.util.ArrayList;
import java.util.Collection;
import javafx.concurrent.*;

import static java.lang.Thread.sleep;

public class Main extends Application implements UserInterface {
    public static void main(String[] args) {


        if(args.length > 0){
            if(args[0] .equals("config")){
                new ConfigListener().run();
            }
        }
       // mythread.start();
        launch(args);

    }


    int floorNumber = 9;
    int numberOfElevators = 4;

    GridPane gPane;

    ArrayList<GUIElevator> elevators = new ArrayList<>();
    @Override
    public void start(Stage primaryStage) {



        //TODO change the screen size dynamically
        int windowHeight = 800;
        int windowWidth = 900;


        //Main Container
        BorderPane bPane = new BorderPane();
        bPane.setPadding(new Insets(20,20,20,20));


        //Creating the "elevators", here elevators are represented as rectangles
        //Grid Pane is created to represent both the elevator and floor buttons.

        gPane = new GridPane();
        gPane.setMinSize(700,600);
        gPane.setHgap(5);
        gPane.setVgap(5);
        //gPane.setAlignment(Pos.CENTER);                   //Uncomment this line if you want the grid pane to be in the center
        gPane.setPadding(new Insets(25,25,25,25));


        //Following two for loops are basically creating the rectangles and floor buttons
        //Rectangles are to represent the position of the elevator
        //Floor buttons are to represent the button press by the passenger
        //The loop with n variable deals with creating the floor
        //The loop with m variable creates the rows. Rows are representing the elevator position

        for (int n = 0 ; n < floorNumber; n++){
            for (int m = 1; m <numberOfElevators+1 ; m++) {

                //Create the Rectangle
                Rectangle square = new Rectangle();

                //Set the area properties
                square.setWidth(40);
                square.setHeight(40);
                //Fill in colours
                square.setFill(Color.BLACK);
                //Add to the Pane
                gPane.add(square, m, floorNumber - n - 1);


            }
            //This HBox is to hold the floor buttons
             HBox hBox = new HBox();
             hBox.setSpacing(5);

             //This Label is to Label the Floor numbers
            Label floorLabel = new Label("Floor " + n );
            CustomButton downButton = CustomButton.create(n,  Direction.UP);
            //Thread downThread = new Thread(downButton);
            CustomButton upButton = CustomButton.create(n,  Direction.DOWN);
            //Thread upThread = new Thread (upButton);

           // downThread.start();
            //upThread.start();

            hBox.getChildren().addAll(floorLabel, upButton, downButton);
            gPane.add(hBox, 0, floorNumber-n-1);

        }

        // Create the four squares representing the elevators.
        for (int i = 0 ; i < numberOfElevators ; i++) {
            GUIElevator e = new GUIElevator(i, 0);
            gPane.add(e, i+1, floorNumber-1-e.currentFloor);
            elevators.add(e);
        }

        gPane.setPadding(new Insets(25,25,25,25));



        //Adding Elements to Container: Border Pane
        //TODO
        bPane.setTop(new Text("Top"));
        bPane.setRight(new Text("Right"));
        //bPane.setLeft(vBox);
        bPane.setCenter(gPane);


        //Stuffs for Bottom Panel
        HBox boxBottom = new HBox();
        Button errorButton1 = new Button("Stuck Elevator");
        Button errorButton2 = new Button("Stuck Door");
        boxBottom.setSpacing(10);

        errorButton1.setOnAction((event) -> {
            //TODO use the method to send message to scheduler
            System.out.println("Elevator Stuck");
        });

        errorButton2.setOnAction((event) -> {
            //TODO use the method to send message to scheduler
            System.out.println("Door Stuck");
        });


        boxBottom.getChildren().addAll(errorButton1, errorButton2);


        bPane.setBottom(boxBottom);


        GUIMessageHandler handler = GUIMessageHandler.getInstance(this);



        //Adding the elements to BorderPane
        Scene scene = new Scene(bPane, windowWidth, windowHeight);
        scene.getStylesheets().
                add(Main.class.getResource("styleSheet.css").toExternalForm());
        primaryStage.setTitle("Elevator Control Panel");
        primaryStage.setScene(scene);
        primaryStage.show();







    } //End of start()

    public void moveElevator  (int elevatorID, int newFloor) {

        GUIElevator e = elevators.get(elevatorID);

        gPane.getChildren().remove(e);
        gPane.add(e, elevatorID+1, floorNumber-1-newFloor);

        Rectangle r = new Rectangle(40, 40);
        r.setFill(Color.BLACK);
        gPane.add(r, elevatorID+1, floorNumber-1-e.currentFloor);
        e.currentFloor = newFloor;
    }

    public void moveElevator(GUIElevatorMoveMessage msg) {
        final int target = msg.currentFloor;
        final int elevator = msg.ID;
        final boolean open = msg.doorOpen;

        Task<Void>task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                //sleep(1000);
                //System.out.println("1. Task is working!!");
                //for (int p = 0; p <= 5; p++) {
                //    final int q = p;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            //This line of code will basically take the information for which elevator to move
                            moveElevator(elevator, target);
                            if (open) {
                                openDoor(elevator);
                            } else {
                                closeDoor(elevator);
                            }

                            System.out.println("2. Platform is working!!");
                        }
                        //moveElevator(2, floorNumber - 5);
                    });
                 //   sleep(1000);
               // }
                return null;
          }

        }; // End Task declaration

        Thread taskThread = new Thread (task);
        taskThread.setDaemon(true);
        taskThread.start();



    }

    public void pressFloorButton(GUIFloorMessage msg) {

    }

    public void pressElevatorButton(int floor) {

    }

    public void openDoor(int ID) {
        elevators.get(ID).setFill(Color.MEDIUMTURQUOISE);
    }

    public void closeDoor(int ID){
        elevators.get(ID).setFill(Color.YELLOW);
    }
}
