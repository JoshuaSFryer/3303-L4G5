package com.sysc3303.gui;


import com.sysc3303.commons.ConfigListener;
import com.sysc3303.commons.Direction;
import com.sysc3303.communication.GUIElevatorMoveMessage;
import com.sysc3303.communication.GUIFloorMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

import javafx.concurrent.*;
import javafx.scene.control.ScrollPane;

import static java.lang.Thread.sleep;

import com.sysc3303.commons.ConfigProperties;

// TODO: Fix bug that results in elevators disappearing.
// TODO: Fix bug that results in doors not holding open when selecting.
public class Main extends Application implements UserInterface {
    public static void main(String[] args) {
//        if(args.length > 0){
//            if(args[0] .equals("config")){
//                new ConfigListener().run();
//            }
//        }
       // mythread.start();
    	new ConfigListener().run();
        launch(args);
    }

    // Number of floors in the system.
    int floorNumber = Integer.parseInt(ConfigProperties.getInstance().getProperty("numberOfFloors"));
    // Number of elevators in the system.
    int numberOfElevators = Integer.parseInt(ConfigProperties.getInstance().getProperty("numberOfElevators"));
    //boolean elevatorArrived = false;

    // A grid pane to hold/organize all the UI elements in.
    GridPane gPane;
    
    // A text area to print debug information to.
    TextArea t;

    // Contains references to all the elevators.
    ArrayList<GUIElevator> elevators = new ArrayList<>();
    CustomButton[][] floorClicks = new CustomButton[floorNumber][2];

    @Override
    public void start(Stage primaryStage) {


        //TODO change the screen size dynamically
        int windowHeight = 800;
        int windowWidth = 900;

        // Scrollable Container
        ScrollPane sPane = new ScrollPane();
        sPane.setPrefSize(windowWidth/1.5, windowHeight);
       
        
        //Main Container
        BorderPane bPane = new BorderPane();
        bPane.setPadding(new Insets(20, 20, 20, 20));
        

        //Creating the "elevators", here elevators are represented as rectangles
        //Grid Pane is created to represent both the elevator and floor buttons.

        gPane = new GridPane();
        gPane.setMinSize(500, 600);
        gPane.setHgap(5);
        gPane.setVgap(5);
        //gPane.setAlignment(Pos.CENTER);                   //Uncomment this line if you want the grid pane to be in the center
        gPane.setPadding(new Insets(25, 25, 25, 25));

        sPane.setContent(gPane);

        //Following two for loops are basically creating the rectangles and floor buttons
        //Rectangles are to represent the position of the elevator
        //Floor buttons are to represent the button press by the passenger
        //The loop with n variable deals with creating the floor
        //The loop with m variable creates the rows. Rows are representing the elevator position

        for (int n = 0; n < floorNumber; n++) {
            for (int m = 1; m < numberOfElevators + 1; m++) {

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
            Label floorLabel = new Label("Floor " + n);
            CustomButton downButton = CustomButton.create(n, Direction.UP);


            //Thread downThread = new Thread(downButton);
            CustomButton upButton = CustomButton.create(n, Direction.DOWN);
            //Thread upThread = new Thread (upButton);
            floorClicks[n][0] = upButton;
            floorClicks[n][1] = downButton;


            // downThread.start();
            //upThread.start();

            hBox.getChildren().addAll(floorLabel, upButton, downButton);
            gPane.add(hBox, 0, floorNumber - n - 1);

        }

        // Create the four squares representing the elevators.
        // Remove squares that are there first
        for (int i = 0; i < numberOfElevators; i++) {
        	// Remove the squares where the elevators will go.
        	removeNodeByCoordinates(i, floorNumber-1, gPane);
        	// Create the elevator.
            GUIElevator e = new GUIElevator(i, 0);
            gPane.add(e, i + 1, floorNumber - 1 - e.currentFloor);
            elevators.add(e);
        }

        gPane.setPadding(new Insets(25, 25, 25, 25));

        //Adding stuffs for right pane

        t = new TextArea();
        t.setText("Test Message");
        //Adding Elements to Container: Border Pane
        //TODO
        bPane.setTop(new Text("Top"));
        //bPane.setRight(new Text("Right"));
        bPane.setRight(t);
        //bPane.setLeft(vBox);
        // Add the scroll pane to the left border.
        bPane.setCenter(sPane);


        //Stuffs for Bottom Panel
        HBox boxBottom = new HBox();

        boxBottom.setSpacing(10);

        for (int d = 0; d < numberOfElevators; d++) {
            ErrorButtons errorButton1 = new ErrorButtons(d, "StickDoor " + d, "StickDoor");
            ErrorButtons errorButton2 = new ErrorButtons(d, "StickElevator " + d, "StickElevator");
            boxBottom.getChildren().addAll(errorButton1, errorButton2);
        }


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

    public void moveElevator(int elevatorID, int newFloor) {

        GUIElevator e = elevators.get(elevatorID);

        gPane.getChildren().remove(e);
        gPane.add(e, elevatorID + 1, floorNumber - 1 - newFloor);

        Rectangle r = new Rectangle(40, 40);
        r.setFill(Color.BLACK);
        gPane.add(r, elevatorID + 1, floorNumber - 1 - e.currentFloor);
        e.currentFloor = newFloor;
    }
    		
    public void removeNodeByCoordinates(int row, int col, GridPane pane) {
    	ObservableList<Node> list = pane.getChildren();
    	for(Node n : list) {
    		if (pane.getRowIndex(n) == row && pane.getColumnIndex(n) == col) {
    			// We have found the target node.
    			pane.getChildren().remove(n);
    		}
    	}
    }

    public void moveElevator(GUIElevatorMoveMessage msg) {
        final int target = msg.currentFloor;
        final int elevator = msg.ID;
        final boolean open = msg.doorOpen;
        final Direction dir = msg.currentDirection;



        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {


                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //This line of code will basically take the information for which elevator to move
                        moveElevator(elevator, target);
                        if (open) {
                            System.out.println("Doors are open");
                            openDoor(elevator);

                        } else {
                            closeDoor(elevator);
                        }
                        System.out.println("2. Platform is working!!");
                    }
                });

                return null;
            }

        }; // End Task declaration

        // Create a thread to launch the task.
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
        // Create a prompt to simulate a user pressing a button inside the elevator.
        createNewScene(ID);
    }

    public void closeDoor(int ID) {
        elevators.get(ID).setFill(Color.YELLOW);
    }
    
    public void stickElevator(int ID) {
    	elevators.get(ID).setFill(Color.RED);
    	t.appendText("\nElevator " + ID + " fault! Stuck!");
    }
    
    public void unstickElevator(int ID) {
    	elevators.get(ID).setFill(Color.YELLOW);
    	t.appendText("\nElevator " + ID + " unstuck!");
    }
    


    public void createNewScene(int elevatorID) {
        Stage stage = new Stage();
        stage.setTitle("Choose a floor!");
        ScrollPane sPane = new ScrollPane();
        VBox v = new VBox();
        Label title = new Label("Elevator " + elevatorID + " Control");
        Label currentFloor = new Label("You are at floor: " + elevators.get(elevatorID).currentFloor);
        v.getChildren().add(title);
        v.getChildren().add(currentFloor);
        //Fill stage with content
        for (int i=0; i<floorNumber; i++) {
        	ElevatorButton b = new ElevatorButton(Integer.toString(i), i, elevatorID, stage);
        	v.getChildren().add(b);
        }
        sPane.setContent(v);
        Scene scene = new Scene(sPane, 400, 600);
        stage.setScene(scene);
        stage.show();
    }

}
