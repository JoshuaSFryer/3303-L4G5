package com.sysc3303.analyzer;

import java.util.List;

/**
 * Used to do mathematical operations on lists of longs
 * Can calculate mean and variance
 */
public class TelemetryMath {

    /**
     * Constructor replacement
     * @return a new TelemetryMath instance
     */
    public static TelemetryMath getInstance(){
        return new TelemetryMath();
    }

    /**
     * calculates the mean of the elements in the list
     * @param list the list with the elements in it
     * @return the mean of the elements in the list
     */
    public long getMean(List<Long> list){
        long sum = 0;
        for (Long num: list){
            sum += (num / list.size());
        }
        return sum;
    }

    /**
     * calculates the variance of the elements in the list
     * @param list the list with the elements in it
     * @return the variance of the elements in the list
     */
    public long getVariance(List<Long> list){
        long mean = getMean(list);
        return getVariance(list, mean);
    }

    /**
     * calculates the variance of the elements in the list given the mean of the list
     * Saves one iteration of the list if the mean has already been calculated
     * @param list the list with the elements in it
     * @param mean the mean of the elements in the list
     * @return the variance of the elements in the list
     */
    public long getVariance(List<Long> list, long mean){
        long var = 0;
        long size = list.size();
        for (Long num: list){
            var += Math.pow((num-mean), 2);
        }
        return var/size;
    }


    /**
     * Private constructor prevents newing of the object
     */
    private TelemetryMath(){
    }
}
