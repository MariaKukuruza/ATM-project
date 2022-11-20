package com.company;

import java.util.ArrayList;

public class ToList extends Reader {
    public ArrayList <String> arrayList = arraylistWithInfoFromReadFile();

    public int getCodeFromList(String n){
        int code = 0;
        for (String value : arrayList) {
            if (value.startsWith(n)){
                String [] num = value.split(" ");
                code = Integer.parseInt(num[1]);
            }
        }
        return code;
    }
    public int getBalanceFromList(String n){
        int balance = 0;
        for (String value : arrayList) {
            if (value.startsWith(n)){
                String [] num = value.split(" ");
                balance = Integer.parseInt(num[2]);
            }
        }
        return balance;
    }
    public void changeBalance(String n, String newBalance){
        for (String value : arrayList) {
            if (value.startsWith(n)){
                String [] num = value.split(" ");
                num[2] = newBalance;
                String newData = String.join(" ", num);
                arrayList.set(arrayList.indexOf(value), newData);
            }
        }
    }
    public int getLimitFromList(String n){
        int limit = 0;
        for (String value : arrayList) {
            if (value.startsWith(n)){
                String [] num = value.split(" ");
                limit = Integer.parseInt(num[3]);
            }
        }
        return limit;
    }
}
