package com.sysc3303.analyzer;

import java.util.List;

public class TelemetryMath {


    public static TelemetryMath getInstance(){
        return new TelemetryMath();
    }

    public long getMean(List<Long> list){
        long sum = 0;
        for (Long num: list){
            sum += (num / list.size());
        }
        return sum;
    }

    public long getVariance(List<Long> list){
        long mean = getMean(list);
        return getVariance(list, mean);
    }

    public long getVariance(List<Long> list, long mean){
        long var = 0;
        long size = list.size();
        for (Long num: list){
            var += (num-mean)*(num-mean);
        }
        return var/size;
    }


    private TelemetryMath(){
    }
}
