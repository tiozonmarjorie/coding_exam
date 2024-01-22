package org.example;

import org.example.model.Booking;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

public class Utils {

    static Consumer<Booking> printBookingInfo = System.out::println;

    public static boolean isSeatInvalid(Map<String, Boolean> seatMap, List<String> seatsList){
        Optional<String> seatOptional = seatsList
                .stream()
                .filter(s -> !(seatMap.containsKey(s)) || !(seatMap.get(s)))
                .findAny();
        return seatOptional.isPresent();
    }

    public static int getTimeConfigLimit () {
        Properties prop = new Properties();
        String fileName = "config.properties";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
        } catch (IOException ex) {
            //logger here
        }
        return Integer.parseInt(prop.getProperty("timeLimit"));
    }
}
