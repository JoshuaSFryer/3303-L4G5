package com.sysc3303.remote_config;
import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.ConfigUpdater;

import java.util.Scanner;
/**
 * Command line interface used to update the number of floors and elevators in the multinodal system.
 * If you are using this ensure that all nodes are run with the program argument "config"
 * This will ensure that they wait for the updated config to be posted before executing
 *
 * @authorm Mattias Lightstone
 */
public class ConfigCLI {

    /**
     * Run the cli.
     * This will take inputs for floor and elevator numbers, then publish the new configuration including these options
     * to the config exchange for the other nodes to consume.
     * @param sc The scanner that should be used for taking input, left as a parameter to enable testing
     */
    public void run(Scanner sc){
        String configExchangeName = ConfigProperties.getInstance().getProperty("configExchangeName");
        int num_elevators = 0;
        int num_floors = 0;
        System.out.println("This CLI will allow you to choose the configuration of the elevator system that you would like to use");

        while(num_floors <= 0){
            System.out.println("\nHow many floors would you like?: ");
            num_floors = sc.nextInt();
            if (num_floors <= 0){
                System.out.println("\nThat is not a valid number of floors, please enter a positive integer");
            }
        }

        while(num_elevators <= 0){
            System.out.println("\nHow many elevators would you like?: ");
            num_elevators = sc.nextInt();
            if (num_elevators <= 0){
                System.out.println("\nThat is not a valid number of elevators, please enter a positive integer");
            }
        }

        ConfigUpdater updater = ConfigUpdater.getNewInstance();
        String[][] configChanges = {
                {
                    "numberOfFloors",
                    Integer.toString(num_floors)
                },
                {
                    "numberOfElevators",
                    Integer.toString(num_elevators)
                }
        };

        for(int i=0; i<configChanges.length; i++){
            String[] change = configChanges[i];
            ConfigProperties.getInstance().setProperty(change[0], change[1]);
        }

        updater.updateConfigs(ConfigProperties.getInstance().getProperties(), configExchangeName);
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        new ConfigCLI().run(sc);
    }
}
