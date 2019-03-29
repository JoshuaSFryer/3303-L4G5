package com.sysc3303.commons;

import java.util.Scanner;

public class RunConfigCLI {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        new ConfigCLI().run(scanner);
    }
}
