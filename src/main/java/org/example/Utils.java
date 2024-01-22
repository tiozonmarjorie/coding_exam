package org.example;

import org.example.contract.AdminAction;
import org.example.contract.BuyerAction;
import org.example.model.Booking;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class Utils {

    public static Consumer<Booking> printBookingInfo = System.out::println;
    public static BiPredicate<Integer, Integer> testIfRowsAndSeatsAreValid = (n1, n2) -> n1 > 10 || n2 > 26;

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

    public static Map<Integer, Runnable> createActionsMap() {
        Map<Integer, Runnable> actions = new HashMap<>();
        actions.put(1, Main::adminDisplayMenu);
        actions.put(2, Main::buyerDisplayMenu);
        actions.put(3, () -> {});
        return actions;
    }

    public static Map<Integer, Runnable> createAdminActionsMap() {
        AdminAction adminAction = new AdminActionImpl();
        Map<Integer, Runnable> actions = new HashMap<>();
        actions.put(1, adminAction::setUpShow);
        actions.put(2, adminAction::viewShow);
        actions.put(3, () -> {});
        return actions;
    }

    public static Map<Integer, Runnable> createBuyerActionsMap() {
        BuyerAction buyerAction = new BuyerActionImpl();
        Map<Integer, Runnable> actions = new HashMap<>();
        actions.put(1, buyerAction::showAvailableSeats);
        actions.put(2, buyerAction::bookASeat);
        actions.put(3, buyerAction::cancelBooking);
        actions.put(4, () -> {});
        return actions;
    }
}
