package org.example;

import org.example.model.Booking;
import org.example.model.Show;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

    public static Consumer<Booking> printBookingInfo = System.out::println;
    public static Consumer<Show> printShow = System.out::println;
    public static BiPredicate<Integer, Integer> testIfRowsAndSeatsAreValid = (n1, n2) -> n1 > 10 || n2 > 26;

    public static boolean isSeatInvalid(Map<String, Boolean> seatMap, List<String> seatsList) {
        Optional<String> seatOptional = seatsList
                .stream()
                .filter(s -> !(seatMap.containsKey(s)) || !(seatMap.get(s)))
                .findAny();
        return seatOptional.isPresent();
    }

    public static int getTimeConfigLimit() {
        Properties prop = new Properties();
        String fileName = "config.properties";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
        } catch (IOException ex) {
            //logger here
        }
        return Integer.parseInt(prop.getProperty("timeLimit"));
    }

    public static Map<String, Boolean> initializeSeat(int rows, int seats) {
        return IntStream.rangeClosed(1, rows)
                .boxed()
                .flatMap(row ->
                        IntStream.rangeClosed(1, seats)
                                .mapToObj(seat ->
                                        String.format("%c%d", 'A' + row - 1, seat))
                )
                .collect(Collectors.toMap(seatKey -> seatKey, seatKey -> true, (a, b) -> a, LinkedHashMap::new));
    }

    public static boolean shouldExit(int choice) {
        return choice == 0;
    }

    public static int getUserChoice(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        return scanner.nextInt();
    }

    public static int readInt(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextInt();
    }

    public static String readString(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.next();
    }
}
