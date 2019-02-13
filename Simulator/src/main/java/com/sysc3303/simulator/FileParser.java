package com.sysc3303.simulator;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads the file
 * Parses each file into an array of strings
 * Packages the lines into a List
 *
 * @author Mattias Lightstone
 */
public class FileParser {

    private static FileParser instance;

    /**
     * Gets an instance of FileParser
     *
     * If there is an instance return it
     * If there isn't one construct it and return it
     *
     * @return an instance of FileParser
     *
     */
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

    /**
     * Reads the file
     * Parses each file into an array of strings
     * Packages the lines into a List
     *
     * @param filePath the path to the input file
     * @throws IOException thrown by the filereader and buffered reader
     */
    public void parse(String filePath) throws IOException, ParseException{
        parsed.clear();
        //InputStreamReader isr = new InputStreamReader((getClass().getResourceAsStream(filePath)));
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line = br.readLine();
        while(line != null){
            String[] parsedLine = parseLine(line);
            parsed.add(parsedLine);
            line = br.readLine();
        }
    }

    /**
     *
     * Splits a line into an array of strings split by spaces
     * @param line The line to be parsed
     * @return the array of strings
     */
    private String[] parseLine(String line){
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
