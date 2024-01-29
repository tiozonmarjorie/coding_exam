package org.example.action;

import org.example.Utils;
import org.example.contract.BuyerAction;
import org.example.model.Show;
import org.example.storage.ShowStorage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class BuyerActionImpl extends BookingProcessor implements BuyerAction {
    private final ShowStorage showStorage = ShowStorage.getInstance();
    private static volatile boolean userInput = false;
    private static int showNumber; // for cancellation of seats;

    @Override
    public void showAvailableSeats() {
        System.out.println("Enter Show Number:");
        Scanner scanner = new Scanner(System.in);
        int showNumber = scanner.nextInt();
        Show show = showStorage.getPersistedShow(showNumber);
        if (show != null) {
            List<String> availableSeats = show.getSeatMap()
                    .entrySet()
                    .stream()
                    .filter(Map.Entry::getValue)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            if (!availableSeats.isEmpty()) {
                System.out.println("Available Seats: " + String.join(",", availableSeats));
            } else {
                System.out.println("No available seats.");
            }
        } else {
            System.out.println("Show not found.");
        }
    }

    @Override
    public void reserveSeat() {
        System.out.println("\nPlease fill up the following details:");
        Scanner scanner = new Scanner(System.in);
        showNumber = Utils.readInt(scanner, "Enter Show Number:");
        int phoneNumber = Utils.readInt(scanner, "Enter Phone Number:");
        String seats = Utils.readString(scanner, "Enter desired seats, separated by a comma:\nexample: A1,A2");
        List<String> seatsList = Arrays.asList(seats.split(","));
        processBooking(showNumber, phoneNumber, seatsList);
    }

    @Override
    public void cancelSeat() {
        Scanner scanner = new Scanner(System.in);
        int limit = Utils.getTimeConfigLimit();
        System.out.println("SYSTEM NOTICE - YOU ARE ONLY ALLOWED TO CANCEL SEAT IN "
                + limit + "minutes..");
        Thread timerThread = startTimerThread(limit);
        userInput(scanner);

        String ticketNumber = Utils.readString(scanner, "Enter Ticket Number:");
        int phoneNumber = Utils.readInt(scanner, "Enter Phone Number:");

        userInput = true;
        timerThread.interrupt();

        Show show = showStorage.getPersistedShow(showNumber);
        removeBooking(show, ticketNumber, phoneNumber);
    }

    private Thread startTimerThread(int limit) {
        Thread timerThread = new Thread(() -> {
            try {
                Thread.sleep(TimeUnit.MINUTES.toMillis(limit));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            if (!userInput) {
                System.out.println("\n=====UNABLE TO CANCEL SEATS. TIME IS UP!!=====");
                System.exit(0);
            }
        });
        timerThread.start();
        return timerThread;
    }

    private void userInput(Scanner scanner) {
        scanner.useDelimiter("[\\s,]+");
        System.out.println("\nPlease fill up the following details separated by a SPACE or a COMMA:");
        System.out.println("[Show Number] [Ticket Number] [Phone Number]");
        System.out.println("ex: 1111, ea335-adsffd, 123456789");
    }
}
