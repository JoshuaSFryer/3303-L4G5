package com.sysc3303.remote_config;

import org.junit.Test;

import java.util.Scanner;

public class ConfigCLITest {

    @Test
    public void cliTest(){
        Scanner scanner = new Scanner(System.in);
        new ConfigCLI().run(scanner);
    }
}
