package org.example;

import org.example.action.BookingProcessor;
import org.example.contract.BuyerAction;
import org.example.model.Booking;
import org.example.model.Show;
import org.example.storage.ShowStorage;

import java.util.*;
import java.util.concurrent.TimeUnit;
public class BuyerActionImpl implements BuyerAction {
    private final ShowStorage showStorage = ShowStorage.getInstance();
    private static boolean userInput = false;

    @Override
    public void showAvailableSeats() {

        System.out.println("ENTER SHOW NUMBER:");
        Scanner s = new Scanner(System.in);
        Show show = showStorage.getPersistedShow(s.nextInt());
        if (show !=null){
            List<String> availableSeats = new ArrayList<>();
            show.getSeatMap().forEach((key, value) -> {
                if (value) availableSeats.add(key);
            });
            System.out.println("AVAILABLE SEATS : " + String.join(",", availableSeats));
        }
    }

    @Override
    public void bookASeat() {
        System.out.println("\nPlease fill up the following details:");
        Scanner s = new Scanner(System.in);

        System.out.println("SHOW NUMBER:");
        int showNumber = s.nextInt();

        System.out.println("PHONE NUMBER:");
        int phoneNumber = s.nextInt();

        System.out.println("ENTER DESIRED SEATS, separated by a comma:\nexample: A1,A2");
        String seats = s.next();
        List<String> seatsList = Arrays.asList(seats.split(","));

        BookingProcessor bookingProcessor = new BookingProcessor(showStorage);
        bookingProcessor.processBooking(showNumber, phoneNumber, seatsList);
    }
    @Override
    public void removeBooking(Show show, String ticketNumber, int phoneNumber) {
        Optional<Booking> bookingsOptional = show.getBookingsList()
                .stream()
                .filter(b ->  b.getTicketNumber().toString().equals(ticketNumber)
                        && b.getMobileNumber() == phoneNumber)
                .findFirst();

        if (bookingsOptional.isPresent()) {
            Booking booking = bookingsOptional.get();
            List<String> seats = booking.getSeatNumbers();
            show.unMarkSeatMap(seats);
            show.removeBooking(booking);
            System.out.println("======SUCCESSFULLY CANCELLED========");
        }
    }

    @Override
    public void cancelBooking() {

        Scanner scanner = new Scanner(System.in);
        int limit = Utils.getTimeConfigLimit();
        System.out.println("SYSTEM NOTICE - YOU ARE ONLY ALLOWED TO CANCEL SEAT IN "
                + limit + "minutes..");

        Thread timerThread = new Thread(() -> {
            try {
                Thread.sleep(TimeUnit.MINUTES.toMillis(limit)); // 2 minutes in milliseconds//120000
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            if (!userInput) {
                System.out.println("\n=====UNABLE TO CANCEL SEATS. TIME IS UP!!=====");
                System.exit(0);
            }
        });
        timerThread.start();
        scanner.useDelimiter("[\\s,]+");
        System.out.println("\nPlease fill up the following details separated by a SPACE or a COMMA:");
        System.out.println("[Show Number] [Ticket Number] [Phone Number]");
        System.out.println("ex: 1111, ea335-adsffd, 123456789");

        int showNumber = scanner.nextInt();
        String ticketNumber = scanner.next();
        int phoneNumber = scanner.nextInt();

        userInput = true;
        timerThread.interrupt();

        Show show = showStorage.getPersistedShow(showNumber);
        removeBooking(show, ticketNumber, phoneNumber);
    }
}
