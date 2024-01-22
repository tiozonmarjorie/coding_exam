package org.example;

import org.example.contract.AdminAction;
import org.example.model.Show;
import org.example.storage.ShowStorage;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AdminActionImpl implements AdminAction {

    static Consumer<Show> printShow = System.out::println;

    public ShowStorage showStorage = ShowStorage.getInstance();

    @Override
    public void setUpShow() {

        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("[\\s,]+");
        System.out.println("\nPlease fill up the following details separated by a space or a comma:");
        System.out.println("[Show Number] [Number of Rows] [Number of seats per row]  [Cancellation window in minutes]");
        System.out.println("ex: 54361, 10, 26, 2");

        int showNumber = scanner.nextInt();
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        int timeLimit = scanner.nextInt();

        if (Utils.testIfRowsAndSeatsAreValid.test(columns, rows)) {
            System.out.println("UNABLE TO ADD A SHOW. MAXIMUM SEATS PER ROW IS 10. MAXIMUM ROWS IS 26!");
        } else {
            Show show = new Show(showNumber, rows, columns, timeLimit);
            show.setSeatMap(initializeSeat(rows, columns));
            showStorage.addNewShow(showNumber, show);
            System.out.println("========SUCCESSFULLY ADDED NEW SHOW=======");
            printShow.accept(showStorage.getPersistedShow(showNumber));
        }
    }

    @Override
    public Map<String, Boolean> initializeSeat(int rows, int seats) {
        return IntStream.rangeClosed(1, rows)
                .boxed()
                .flatMap(row ->
                        IntStream.rangeClosed(1, seats)
                                .mapToObj(seat ->
                                        String.format("%c%d", 'A' + row - 1, seat))
                )
                .collect(Collectors.toMap(seatKey -> seatKey, seatKey -> true, (a, b) -> a, LinkedHashMap::new));
    }

    @Override
    public void viewShow() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER SHOW NUMBER:...");
        int showNumber = scanner.nextInt();
        Show show = showStorage.getPersistedShow(showNumber);
        if (show != null) show.getBookingsList().forEach(Utils.printBookingInfo);
    }
}
