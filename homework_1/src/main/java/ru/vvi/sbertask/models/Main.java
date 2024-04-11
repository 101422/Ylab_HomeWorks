package ru.vvi.sbertask.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("d:/Prog/Java/Сбер стажировка/Задача ВС Java Сбер.csv"));

        List<List<String>> citiesList = new ArrayList<>();

        List<City> citiesObjectList = new ArrayList<>();

        while (scanner.hasNextLine()) {
            citiesList.add(getRecordFromLine(scanner.nextLine()));
        }

        citiesList.forEach( (cityRow) -> {
            // Проверка размера массива, т.к. кое-где пропущен год основания и возникает ошибка.

            if (5 >= cityRow.size())
                cityRow.add(5,"");

            City city = new City(cityRow.get(1), cityRow.get(2), cityRow.get(3), Integer.parseInt(cityRow.get(4)) , cityRow.get(5));

            citiesObjectList.add(city);
        });

        citiesObjectList.forEach(System.out::println);
    }

    private static List<String> getRecordFromLine(String line) {

        List<String> values = new ArrayList<String>();

        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(";");

            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }

        return values;
    }

}
