package com.company;

//Добавить в программу обработки файла с продуктами из предыдущего задания журналирование событий, при этом стоит указать
// различные уровни логирования, как информационного уровня, так и предупреждения и ошибки.
//
//Смоделировать как нормальную работу программы, так и случаи, ведущие к возникновению ошибок.
//
//Настроить сбор логов в файл.
//
//Фреймворк логирования - на ваш выбор.
import java.io.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream("log.config")){ //полный путь до файла с конфигами
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Main.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            LOGGER.log(Level.INFO,"Начало Main, создание объектов для чтения файла products.txt");
            File file = new File("C://SomeDir//products.txt");
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            System.out.println("Наименование        Кол-во   Цена     Стоимость\n" +
                    "===============================================");
            double AllAmount = 0.0;//общая сумма
            double Amount = 0.0;//для вычесления суммы
            int Flag = 1;
            LOGGER.log(Level.INFO,"Старт считывания файла products.txt");
            String line = reader.readLine();
            String lineOut = line;//формируемая строка для вывода
            while (line != null) {
                if (Flag == 1){
                    lineOut = String.format("%-20s", lineOut);
                    line = reader.readLine();
                    Flag++;
                }
                if (Flag == 2){
                    Amount = Double.valueOf(line);
                    //lineOut = lineOut + line + " X ";
                    lineOut = lineOut + String.format("%-6s", line) + " X ";
                    line = reader.readLine();
                    Flag++;

                }
                if (Flag == 3){
                    Amount = Amount * Double.valueOf(line);//стоимость конкретного продукта
                    AllAmount = AllAmount + Amount;
                    //lineOut = lineOut + line + " = " + String.format("%.2f", Amount);
                    lineOut = lineOut + String.format("%6s", line) + " = " + String.format("%.2f", Amount);
                    System.out.println(lineOut);
                    line = reader.readLine();//считывание строки с названием продукта
                    Flag = 1;
                    lineOut = line;
                }
            }
            reader.close();//закрываем считывание
            LOGGER.log(Level.INFO,"Окончание считывания файла products.txt");
            System.out.println("===============================================");
            System.out.printf("Итого:                              =%8.2f\n", AllAmount);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.log(Level.WARNING,"Ошибка при поиске файла" , e);
                    } catch (IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.WARNING,"Ошибка при открытии или чтении файла" , e);
        }

        LOGGER.log(Level.INFO,"Завершение программы Main");
    }
}
