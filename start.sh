mvn package -DskipTests
#gnome-terminal -x bash -c "java -jar Scheduler/target/Scheduler-1.0.jar; $SHELL"
#gnome-terminal -x bash -c "java -jar FloorSystem/target/FloorSystem-1.0.jar; $SHELL"
#gnome-terminal -x bash -c "java -jar ElevatorSystem/target/ElevatorSystem-1.0.jar; $SHELL"
#gnome-terminal -x bash -c "java -jar Simulator/target/Simulator-1.0.jar '/testEvents.txt'; $SHELL"
gnome-terminal -e "java -jar Scheduler/target/Scheduler-1.0.jar"
gnome-terminal -e "java -jar FloorSystem/target/FloorSystem-1.0.jar"
gnome-terminal -e "java -jar ElevatorSystem/target/ElevatorSystem-1.0.jar"
gnome-terminal -e "java -jar Simulator/target/Simulator-1.0.jar '/testEventsErrors.txt'"
