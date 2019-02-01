package com.sysc3303.simulator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FileParser {

    private static FileParser instance;
    private static DateFormat df;

    public static synchronized FileParser getInstance(){
        if(instance == null){
            instance = new FileParser();
        }
        return instance;
    }

    public static synchronized void setNull(){
        instance = null;
    }

    private List<String[]> parsed;

    public void parse(String filePath) throws IOException, ParseException{
        parsed.clear();
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line = br.readLine();
        while(line != null){
            String[] parsedLine = parseLine(line);
            parsed.add(parsedLine);
            line = br.readLine();
        }
    }

    private String[] parseLine(String line) throws ParseException{
        String[] elements = line.split(" ");
        return elements;
    }

    public List<String[]> getParsed() {
        return parsed;
    }

    private FileParser(){
        parsed = new ArrayList<>();
    }
}
