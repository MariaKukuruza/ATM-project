package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile extends Input {

    private static final int LIMIT = 50000;

    public void writeDataAboutCard() {
        if (!isPinInList()) {
            File file = new File("read.txt");
            if(!file.exists()){
                try {
                    if(!file.createNewFile()){
                        throw new Exception("Не получилось создать файл");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            try (FileWriter writer = new FileWriter(file, true)) {
                int balance = (int) (1000 + Math.random() * 100000);
                writer.write(getNum() + " " + getCode() + " " + balance + " " + LIMIT + "\n");
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void processOfMenu(){
        Menu m = new Menu();
        m.menu(getNum());
    }
}
