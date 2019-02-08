mvn package -DskipTests
gnome-terminal -e "mvn exec:java -Dexec.mainClass='com.sysc3303.scheduler.Scheduler'"
gnome-terminal -e "mvn exec:java -Dexec.mainClass='com.sysc3303.floor.FloorSystem'"
gnome-terminal -e "mvn exec:java -Dexec.mainClass='com.sysc3303.elevator.Elevator'"
gnome-terminal -e "mvn exec:java -Dexec.mainClass='com.sysc3303.simulator.Runner'"
