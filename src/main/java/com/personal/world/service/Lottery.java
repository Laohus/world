package com.personal.world.service;

import java.util.*;

public class Lottery {

    public static List<Integer> RedNumber(Integer num){
        int n = 6;
        int MaxNum = 33;
        Random r = new Random();
        Set<Integer> number = new HashSet<>();
        if(num!=null){
            number.add(num);
        }
        while (number.size()<n){
            number.add(r.nextInt(MaxNum)+1);
        }
        List<Integer> RedNumber = new ArrayList<>(number);
        Collections.sort(RedNumber);
        return RedNumber;
    }

    public static Integer BlueNumber(Integer num){
        if(num==null){
            Random r = new Random();
            int BlueNum = 16;
            return r.nextInt(BlueNum)+1;
        }else {
            return num;
        }

    }

}
