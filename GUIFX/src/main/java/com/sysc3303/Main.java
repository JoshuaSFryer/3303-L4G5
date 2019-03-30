package com.sysc3303;


import com.sysc3303.commons.Direction;
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

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class Main extends Application {


    public static void main(String[] args) {

        //Thread mythread = new Thread(new TestThread(ma), "TEST");
       // mythread.start();
        launch(args);



    }


    int floorNumber = 10;
    int numberOfElevators = 4;
    int messageAddressee;
    int targetFloor;

    GridPane gPane;

    ArrayList<GUIElevator> elevators = new ArrayList<>();
    @Override
    public void start(Stage primaryStage) {

        //Communication Part
        //GUIMessageHandler msg = GUIMessageHandler.getInstance(this);

        //TODO change the screen size dynamically
        int windowHeight = 800;
        int windowWidth = 900;

        //We might need these variables
        int goToFloor;


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
            CustomButton downButton = CustomButton.create(n,  Direction.UP, this);
            CustomButton upButton = CustomButton.create(n,  Direction.DOWN, this);

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


        bPane.setBottom(new Text("Bottom"));


        //Adding the elements to BorderPane
        Scene scene = new Scene(bPane, windowWidth, windowHeight);
        scene.getStylesheets().
                add(Main.class.getResource("styleSheet.css").toExternalForm());
        primaryStage.setTitle("Elevator Control Panel");
        primaryStage.setScene(scene);
        primaryStage.show();


        //Task 1
        //in this block, the parameters are

        for( int ID = 0; ID < numberOfElevators; ID++) {
            final int d = ID;
            Task<Void> task = new Task<Void>() {
                int taskID = d;
                @Override
                protected synchronized Void call() throws Exception {
                    sleep(1000);
                    System.out.println("Elevator Move command received. ");
                    //TODO define int currentFloor number Here

                    Platform.runLater(new Runnable() {
                        @Override
                        public synchronized void run() {
//                            while(true) { // While
//                                try {
//                                    wait(); // Wait until a message is received.
//                                    // Check to see if the message is meant for this thread.
//                                    if (getMessageAddressee() == d) {
//                                        // This message is meant for this thread.
//                                        System.out.println("Message processed by task " + taskID);
//                                    }
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
                            //while(true) {
                                if(messageAddressee >= 0) {
                                    if (messageAddressee == taskID) {
                                        // Message was meant for this thread.
                                        System.out.println("Message handled by thread" + taskID);
                                        moveElevator(taskID, targetFloor);
                                        messageAddressee = -1; // ints cannot be null, so neg values instead.
                                    } else {
                                        System.out.println("Ignoring message");
                                    }
                                }
                                try {
                                    sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            //}
                        }

                    }); // End of runLater
                        sleep(1000);

                    return null;
                } // End of call()

            }; //End of Task

            Thread taskThread = new Thread (task);
            taskThread.setDaemon(true);
            taskThread.start();

        }

        messageAddressee = 2;

    } //End of start()

    //This method moves The rectangle from one place to another
    //This simulates that the elevator is moving

    public void moveElevator  (int elevatorID, int newFloor) {

        GUIElevator e = elevators.get(elevatorID);

        gPane.getChildren().remove(e);
        gPane.add(e, elevatorID+1, floorNumber-1-newFloor);

        Rectangle r = new Rectangle(40, 40);
        r.setFill(Color.BLACK);
        gPane.add(r, elevatorID+1, floorNumber-1-e.currentFloor);
        e.currentFloor = newFloor;
    }

    private int getMessageAddressee() {
        return this.messageAddressee;
    }
}
