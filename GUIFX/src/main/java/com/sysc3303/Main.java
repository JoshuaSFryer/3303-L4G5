package com.sysc3303;


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
import net.miginfocom.layout.Grid;

import java.util.ArrayList;
import java.util.Collection;
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
    	//new ConfigListener().run();
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
            CustomButton upButton = CustomButton.create(n, Direction.DOWN);
            floorClicks[n][0] = upButton;
            floorClicks[n][1] = downButton;

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
        //bPane.setTop(new Text("Top"));
        //bPane.setRight(new Text("Right"));
        bPane.setRight(t);
        //bPane.setLeft(vBox);
        // Add the scroll pane to the left border.
        bPane.setCenter(sPane);


        //Stuffs for Bottom Panel
        HBox boxBottom = new HBox();

        boxBottom.setSpacing(10);

        // Create a pair of buttons for each elevator which can be used to trigger faults.
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


    /**
     * Move the representation of an elevator in the GUI to a new position.
     * @param elevatorID    The ID of the elevator to move.
     * @param newFloor      The floor number to reposition the elevator to.
     */
    public void moveElevator(int elevatorID, int newFloor) {

        GUIElevator e = elevators.get(elevatorID);

        gPane.getChildren().remove(e);
        gPane.add(e, elevatorID + 1, floorNumber - 1 - newFloor);

        Rectangle r = new Rectangle(40, 40);
        r.setFill(Color.BLACK);
        gPane.add(r, elevatorID + 1, floorNumber - 1 - e.currentFloor);
        e.currentFloor = newFloor;
    }


    /**
     * Remove a Node located at a specified coordinate from a GridPane.
     * @param row   The row the Node is located at.
     * @param col   The column the Node is located at.
     * @param pane  The GridPane the Node is a child of.
     */
    public void removeNodeByCoordinates(int row, int col, GridPane pane) {
    	ObservableList<Node> list = pane.getChildren();
    	for(Node n : list) {
    		if (pane.getRowIndex(n) == row && pane.getColumnIndex(n) == col) {
    			// We have found the target node.
    			pane.getChildren().remove(n);
    		}
    	}
    }


    /**
     * Respond to a message instructing an elevator to move, opening its doors
     * if required.
     * This method has this name due to being an implementation of a method in
     * UserInterface.
     * @param msg   The message passed on from the MessageHandler.
     */
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
        // Required code to launch the task thread.
        taskThread.setDaemon(true);
        taskThread.start();
    }


    /**
     * Empty function, only present because this class needs to implement
     * UserInterface.
     * @param msg   The message passed on from the MessageHandler.
     */
    public void pressFloorButton(GUIFloorMessage msg) {
        System.out.println("This function should never be called!");
    }


    /**
     * Empty function, only present because this class needs to implement
     * UserInterface.
     * @param floor The floor number of the pressed button.
     */
    public void pressElevatorButton(int floor) {
        System.out.println("This function should never be called!");
    }


    /**
     * Display the opening the specified elevator's doors.
     * Additionally, create a new window that simulates the button panel on the
     * elevator's interior, allowing the user to click a button and request a
     * floor.
     * @param ID    The ID of the elevator.
     */
    public void openDoor(int ID) {
        // Change the elevator's colour to blue
        elevators.get(ID).setFill(Color.MEDIUMTURQUOISE);
        // Create a prompt to simulate a user pressing a button inside the elevator.
        createNewScene(ID);
    }


    /**
     * Display the closing the elevator's doors, returning its colour back to
     * normal.
     * @param ID    The ID of the elevator.
     */
    public void closeDoor(int ID) {
        elevators.get(ID).setFill(Color.YELLOW);
    }

    /**
     * Display that an elevator is stuck, changing its colour and logging the
     * fault.
     * @param ID    The ID of the elevator.
     */
    public void stickElevator(int ID) {
    	elevators.get(ID).setFill(Color.RED);
    	t.appendText("\nElevator " + ID + " fault! Stuck!");
    }

    /**
     * Display that an elevator is no longer stuck, changing its colour back to
     * normal.
     * @param ID    The ID of the elevator.
     */
    public void unstickElevator(int ID) {
    	elevators.get(ID).setFill(Color.YELLOW);
    	t.appendText("\nElevator " + ID + " unstuck!");
    }


    /**
     * Create a new GUI window that simulates the button panel on the inside of
     * an elevator.
     * @param elevatorID    The ID of the elevator.
     */
    public void createNewScene(int elevatorID) {
        // Create a new Stage, i.e. window.
        Stage stage = new Stage();
        stage.setTitle("Choose a floor!");
        // Create a ScrollPane for the buttons.
        ScrollPane sPane = new ScrollPane();
        VBox v = new VBox();
        Label title = new Label("Elevator " + elevatorID + " Control");
        Label currentFloor = new Label("You are at floor: " + elevators.get(elevatorID).currentFloor);
        v.getChildren().add(title);
        v.getChildren().add(currentFloor);
        // Create buttons and add them to the VPane.
        for (int i=0; i<floorNumber; i++) {
        	ElevatorButton b = new ElevatorButton(Integer.toString(i), i, elevatorID, stage);
        	v.getChildren().add(b);
        }
        sPane.setContent(v);
        Scene scene = new Scene(sPane, 400, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void printToLog(String message) {
        t.appendText("\n" + message);
    }
}
