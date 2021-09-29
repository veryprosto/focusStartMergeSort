package ru.veryprosto;

import java.io.*;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) {
        boolean sortUp = true;
        boolean intString = true;

        int countArgs = 0;
        if (args[0].equals("-s") || args[1].equals("-s")) {
            System.out.println("тип данных Стринг");
            intString = false;
            countArgs++;
        } else if (args[0].equals("-i") || args[1].equals("-i")) {
            System.out.println("тип данных Integer");
            intString = true;
            countArgs++;
        } else {
            System.out.println("Не верные аргументы!");
        }

        if (args[0].equals("-d") || args[1].equals("-d")) {
            System.out.println("сортировка по убыванию");
            sortUp = false;
            countArgs++;
        } else if (args[0].equals("-a") || args[1].equals("-a")) {
            sortUp = true;
            countArgs++;
            System.out.println("сортировка по возрастанию");
        } else {
            sortUp = true;
            System.out.println("сортировка по возрастанию");
        }

        File outputfile = new File(args[countArgs]);

        File tempFile = null;
        try {
            tempFile = File.createTempFile("temp", "tmp");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (countArgs < args.length - 1) {
            File inputFile = new File(args[countArgs + 1]);
            tempFile = sort(sortUp, intString, inputFile, tempFile);
            countArgs++;
        }

        try {
            Files.copy(tempFile.toPath(), outputfile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        tempFile.deleteOnExit();
    }

    public static File sort(boolean sortUp, boolean intString, File file1, File file2) {
        File tempFile = null;
        int multiplier = sortUp ? 1 : -1;

        try (FileReader fileReader1 = new FileReader(file1);
             FileReader fileReader2 = new FileReader(file2);
             BufferedReader reader1 = new BufferedReader(fileReader1);
             BufferedReader reader2 = new BufferedReader(fileReader2)) {

            tempFile = File.createTempFile("temp", "tmp");

            FileWriter fileWriter = new FileWriter(tempFile);

            String line1 = reader1.readLine();
            String line2 = reader2.readLine();

            while (true) {
                if (line1 == null && line2 == null) {
                    break;
                } else {
                    if (line1 == null) {
                        if (intString) {
                            int num2 = Integer.parseInt(line2);
                            fileWriter.write(num2 + System.getProperty("line.separator"));
                            line2 = reader2.readLine();
                        } else {
                            fileWriter.write(line2 + System.getProperty("line.separator"));
                            line2 = reader2.readLine();
                        }
                    } else if (line2 == null) {
                        if (intString) {
                            int num1 = Integer.parseInt(line1);
                            fileWriter.write(num1 + System.getProperty("line.separator"));
                            line1 = reader1.readLine();
                        } else {
                            fileWriter.write(line1 + System.getProperty("line.separator"));
                            line1 = reader1.readLine();
                        }
                    } else {
                        if (intString) {
                            int num1 = Integer.parseInt(line1);
                            int num2 = Integer.parseInt(line2);
                            if (num1 * multiplier <= num2 * multiplier) {
                                fileWriter.write(num1 + System.getProperty("line.separator"));
                                line1 = reader1.readLine();
                            } else {
                                fileWriter.write(num2 + System.getProperty("line.separator"));
                                line2 = reader2.readLine();
                            }
                        } else {
                            if (line1.compareTo(line2)*multiplier<=0) {
                                fileWriter.write(line1 + System.getProperty("line.separator"));
                                line1 = reader1.readLine();
                            } else {
                                fileWriter.write(line2 + System.getProperty("line.separator"));
                                line2 = reader2.readLine();
                            }
                        }
                    }
                }
            }
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }
}
