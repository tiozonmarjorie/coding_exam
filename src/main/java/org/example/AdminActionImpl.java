package org.example;

import org.example.contract.AdminAction;
import org.example.model.Show;
import org.example.storage.ShowStorage;

import java.util.Scanner;

public class AdminActionImpl implements AdminAction {
    private final ShowStorage showStorage = ShowStorage.getInstance();

    @Override
    public void setUpShow() {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("[\\s,]+");

        System.out.println("\nPlease fill up the following details separated by a space or a comma:");
        System.out.println("[Show Number] [Number of Rows] [Number of seats per row] [Cancellation window in minutes]");
        System.out.println("ex: 54361, 10, 26, 2");

        int showNumber = Utils.readInt(scanner, "");
        int rows = Utils.readInt(scanner, "");
        int columns = Utils.readInt(scanner, "");
        int timeLimit = Utils.readInt(scanner, "");

        if (Utils.testIfRowsAndSeatsAreValid.test(columns, rows)) {
            System.out.println("UNABLE TO ADD A SHOW. MAXIMUM SEATS PER ROW IS 10. MAXIMUM ROWS IS 26!");
        } else {
            Show show = createShow(showNumber, rows, columns, timeLimit);
            showStorage.addNewShow(showNumber, show);
            System.out.println("========SUCCESSFULLY ADDED NEW SHOW=======");
            Utils.printShow.accept(showStorage.getPersistedShow(showNumber));
        }

    }

    @Override
    public void viewShow() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ENTER SHOW NUMBER:");
        int showNumber = scanner.nextInt();
        Show show = showStorage.getPersistedShow(showNumber);
        if (show != null) show.getBookingsList().forEach(Utils.printBookingInfo);
    }

    private Show createShow(int showNumber, int rows, int columns, int timeLimit) {
        Show show = new Show(showNumber, rows, columns, timeLimit);
        show.setSeatMap(Utils.initializeSeat(rows, columns));
        return show;
    }
}
