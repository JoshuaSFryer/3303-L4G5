package com.sysc3303.communication;

import com.sysc3303.commons.ConfigCLI;
import org.junit.Test;

import java.util.Scanner;

public class ConfigCLITest {

    @Test
    public void cliTest(){
        Scanner scanner = new Scanner(System.in);
        new ConfigCLI().run(scanner);
    }
}
