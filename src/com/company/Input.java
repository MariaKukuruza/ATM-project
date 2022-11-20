package com.company;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input extends ToList {

    private static final Pattern PATTERN_FOR_NUMBER = Pattern.compile("[0-9]{16}");

    private static final Pattern PATTERN_FOR_CODE = Pattern.compile("[0-9]{4}");

    public static final String FILE_NAME = "block.txt";

    private int countForPinCheck = 0;

    public String num = validCardNumber();

    public int pin = pinCheckWithList();

    public int getCode() {
        return pin;
    }

    public String getNum() {
        return num;
    }

    public String validCardNumber() {
        System.out.println("Введите номер карты, соответствующий шаблону «ХХХХ-ХХХХ-ХХХХ-ХХХХ»");
        Scanner in = new Scanner(System.in);
        String n = in.next();
        Matcher matcher = PATTERN_FOR_NUMBER.matcher(n);
        while (!matcher.matches() || isCardNumberValid(n)) {
            if (!matcher.matches()) {
                System.out.println("Номер должен содержать 16 цифр, введите заново номер карты");
                n = in.next();
                matcher = PATTERN_FOR_NUMBER.matcher(n);
            }
            if (isCardNumberValid(n)) {
                System.out.println("Номер должен быть валидным, введите заново номер карты");
                n = in.next(); //Пример валидного номера 4561261212345467
                matcher = PATTERN_FOR_NUMBER.matcher(n);
            }
        }
        Map<String, Long> stringLongMap = mapWithInfoFromBlockFile();
        if (stringLongMap.containsKey(n)) {
            long timePassed = System.currentTimeMillis() - stringLongMap.get(n);
            long minutesPassed = timePassed / 60000;
            if (minutesPassed < 1440) {
                int allMinutesLeft = (int) (1440 - minutesPassed);
                byte hoursLeft = (byte) (allMinutesLeft / 60);
                int minutesLeft = allMinutesLeft - hoursLeft * 60;
                String time = hoursLeft + " часов " + minutesLeft + " минут";
                System.out.println("Ваша карта заблокирована еще в течении " + time + ", выполнение операций невозможно");
                System.exit(0);
            }
            else {
                try {
                    deleteBlockedCardFromFile(n);
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return n;
    }

    private static boolean isCardNumberValid(String s) {
        int sum = 0;
        boolean isEven = false;
        for (int i = s.length(); i > 0; i--) {
            int k = Integer.parseInt(s.substring(i - 1, i));
            if (isEven) {
                k = k * 2;
                if (k / 10 != 0)
                    k = k / 10 + k % 10;
            }
            isEven = !isEven;
            sum += k;
        }
        return sum % 10 != 0;
    }
    public int validPin() {
        System.out.println("Введите код карты");
        Scanner in = new Scanner(System.in);
        StringBuilder code = new StringBuilder(in.next());
        Matcher matcher = PATTERN_FOR_CODE.matcher(code);
        while (!matcher.matches()) {
            System.out.println("Код не соответствует шаблону, введите 4 цифры");
            code = new StringBuilder(in.next());
            matcher = PATTERN_FOR_CODE.matcher(code);
        }
        return Integer.parseInt(String.valueOf(code));
    }

    public int pinCheckWithList() {
        int rightCode = getCodeFromList(getNum());
        int code;
        if (!Objects.equals(rightCode, 0)) {
            do {
                if (countForPinCheck >= 1) {
                    System.out.println("Пароль введен неверно");
                }
                if (countForPinCheck >= 3) {
                    addToBlock(getNum());
                }
                code = validPin();
                countForPinCheck++;
            } while (!Objects.equals(rightCode, code));
        } 
        else {
            code = validPin();
        }
        return code;
    }

    public boolean isPinInList() {
        int real = getCodeFromList(getNum());
        return real == getCode();
    }

    public void addToBlock(String s) {
        writeToBlockFile(s);
        try {
            throw new Exception("Ваша карта заблокирована, выполнение операций невозможно");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public void writeToBlockFile(String s) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(s + " " + System.currentTimeMillis() + "\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<String, Long> mapWithInfoFromBlockFile() {
        Map<String, Long> map = new HashMap<>();
        File file = new File(FILE_NAME);
        if(!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new Exception("Не получилось создать файл");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String m;
            while ((m = br.readLine()) != null) {
                String[] forSplit = m.split(" ");
                map.put(forSplit[0], Long.valueOf(forSplit[1]));
            }
        } catch (FileNotFoundException l) {
            System.out.println("Файл с данными не был найден");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

    public void deleteBlockedCardFromFile(String s) throws IOException {
        File file = new File(FILE_NAME);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        ArrayList<String> arrayList = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            if (!line.startsWith(s)) {
                arrayList.add(line);
            }
        }
        reader.close();
        if(file.delete()) {
            File newFile = new File(FILE_NAME);
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile, false));
            for (String st : arrayList) {
                writer.write(st + "\n");
            }
            writer.close();
        }
    }
}

