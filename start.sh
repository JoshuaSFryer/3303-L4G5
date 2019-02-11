mvn package -DskipTests
gnome-terminal -e "java -jar Scheduler/target/Scheduler-1.0.jar"
gnome-terminal -e "java -jar FloorSystem/target/FloorSystem-1.0.jar"
gnome-terminal -e "java -jar ElevatorSystem/target/ElevatorSystem-1.0.jar"
gnome-terminal -e "java -jar Simulator/target/Simulator-1.0.jar '/testEvents.txt'"
