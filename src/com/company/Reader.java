package com.company;

import java.io.*;
import java.util.ArrayList;

public class Reader implements ICheck
{
    ArrayList<String> list = new ArrayList<>();
    private final String fileName = "read.txt";

    public ArrayList <String> arraylistWithInfoFromReadFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            checking();
            String m;
            while ((m = br.readLine()) != null) {
                list.add(m);
            }
            br.close();
        }
        catch (FileNotFoundException l){
            System.out.println("Файл с данными карты не был найден");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    private File getFile(){
        return new File(fileName);
    }
    @Override
    public void checking(){
        File file1 = getFile();
        if(file1.length()!=0){
            System.out.println("Файл с данными карт успешно прочитан");
        }
        else {
            try {
                throw new Exception("Файл не имеет данных");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
