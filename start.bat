mvn package -DskipTests
start cmd /k java -jar Scheduler\target\Scheduler-1.0.jar
start cmd /k java -jar FloorSystem\target\FloorSystem-1.0.jar
start cmd /k java -jar ElevatorSystem\target\ElevatorSystem-1.0.jar
start cmd /k java -jar Simulator\target\Simulator-1.0.jar
