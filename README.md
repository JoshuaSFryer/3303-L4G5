# Elevator Simulation

## Branch Naming Policy
Name the branch "iteration#-the feature you are working on"  
After code review, it can be pushed to the branch "iteration#"

## Authors
- Joshua Fryer
- Tanvir Hossain
- Mattias Lightstone
- Yu Yamanaka
- Xinrui Zhang

This Elevator System Consists of four subsystems:
## Floor System
FloorSystem is responsible for simulating passenger actions on the floors of
a building, and the operation of lamps. It contains an array of Floor objects,
each representing an individual floor. It takes button inputs from each floor
(implemented in Iteration 1 as messages from the Simulator) and sends messages
to the Scheduler using a class called MessageHandler.
It also controls the lamps on each floor, which indicate the direction in which
elevators are moving, and which turn off when an elevator arrives. 
## Scheduler System
The Scheduler system acts as an intermediary between the Floor and Elevator
systems, taking input from both systems and coordinating the elevator.
Messages from the Floor system represent passengers requesting an elevator from
a given floor.
Messages from the Elevator system represent either passengers pressing a
button from within the elevator to request a destination, or a notification
that the elevator has arrived at a floor. This notification occurs upon
arrival at _any_ floor, and its purpose is to update the scheduler's
representation of the system's state.
## Elevator System


## Simulator System
