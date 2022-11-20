package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu extends ToList {
    private final Scanner in = new Scanner(System.in);

    public String getNewBalancePlus(String s) {
        return String.valueOf(replenishBalance(s));
    }

    public String getNewBalanceMinus(String s) {
        return String.valueOf(withdrawMoney(s));
    }

    public void message() {
        System.out.println("""
                \nВыберите действие:
                1. проверить баланс карты
                2. снять средства со счета
                3. пополнить баланc
                0. Выход
                """);
    }

    public void menu(String s) {
        byte answer;
        do {
            message();
            answer = in.nextByte();
            switch (answer) {
                case 1 -> checkBalance(s);
                case 2 -> changeBalance(s, getNewBalanceMinus(s));
                case 3 -> changeBalance(s, getNewBalancePlus(s));
                case 0 -> {writeDataAboutAllCards(arrayList);
                        System.out.println("До свидания!");}
                default -> System.out.println("Такого пункта в меню нет");
            }
        } while (answer != 0);
    }

    public void checkBalance(String s) {
        System.out.format("Ваш баланс составляет %d руб", getBalanceFromList(s));
    }

    public int withdrawMoney(String s) {
        System.out.println("Какую сумму желаете снять");
        int sum = in.nextInt();
        while (sum > getBalanceFromList(s) | sum > getLimitFromList(s) | sum <= 0) {
            if(sum > getBalanceFromList(s)){System.out.format("Введенную сумму невозможно снять так как она либо больше суммы на балансе %d%n",getBalanceFromList(s));
                System.out.println("Какую отличную сумму желаете снять");
                sum = in.nextInt();}
            if (sum > getLimitFromList(s)) {System.out.format("Введенную сумму невозможно снять так как она превышает лимит средств в банкомате %d%n",getLimitFromList(s));
                System.out.println("Какую отличную сумму желаете снять");
                sum = in.nextInt();}
            if(sum<=0){System.out.format("Введенную сумму невозможно снять так как она меньше 1%n");
                System.out.println("Какую отличную сумму желаете снять");
                sum = in.nextInt();}
        }
        return getBalanceFromList(s) - sum;
    }

    public int replenishBalance(String s) {
        System.out.println("Какую сумму желаете положить");
        int sum = in.nextInt();
        while (sum > 1000000 | sum <= 0) {
            if (sum>1000000){
                System.out.println("Введенную сумму невозможно положить так как она не должна превышать 1 000 000");
                System.out.println("Какую отличную сумму желаете положить");
                sum = in.nextInt();
            }
            if(sum<=0) {
                System.out.println("Введенную сумму невозможно положить так как она не должна быть меньше 1");
                System.out.println("Какую отличную сумму желаете положить");
                sum = in.nextInt();
            }
        }
        return getBalanceFromList(s) + sum;
    }
    public void writeDataAboutAllCards(ArrayList<String> strings) {
        try {
            FileWriter stream1 = new FileWriter("read.txt");
            BufferedWriter out1 = new BufferedWriter(stream1);
            out1.write("");
            out1.close();
            FileWriter writer = new FileWriter("read.txt", false) ;
            for(String string :strings)writer.write(string +"\n");
            writer.flush();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}


