package org.example.action;

import org.example.Utils;
import org.example.model.Booking;
import org.example.model.Show;
import org.example.storage.ShowStorage;

import java.util.*;

public abstract class BookingProcessor {
    private ShowStorage showStorage = ShowStorage.getInstance();
    public void processBooking(int showNumber, int phoneNumber, List<String> seatsList) {
        Show show = showStorage.getPersistedShow(showNumber);
        Optional<Booking> bookingsOptional = show.getBookingsList()
                .stream()
                .filter(booking1 -> booking1.getMobileNumber() == phoneNumber)
                .findAny();

        if (bookingsOptional.isPresent()) {
            System.out.println("====UNABLE TO BOOK A SHOW, PHONE NUMBER EXISTS!=====");
        } else if (Utils.isSeatInvalid(show.getSeatMap(), seatsList)) {
            System.out.println("====UNABLE TO BOOK A SHOW, SEATS ARE ALREADY TAKEN!====");
        } else {
            Booking booking = new Booking(showNumber, UUID.randomUUID(), phoneNumber, seatsList);
            saveBooking(show, booking);
        }
    }

    private void saveBooking(Show show, Booking booking) {
        show.markSeatMap(booking.getSeatNumbers());
        showStorage.addBooking(booking.getShowNumber(), booking);
        System.out.println("======SUCCESSFULLY RESERVED A SEAT==========");
        Utils.printBookingInfo.accept(booking);
    }

    public void removeBooking(Show show, String ticketNumber, int phoneNumber) {
        Optional<Booking> bookingsOptional = show.getBookingsList()
                .stream()
                .filter(b -> b.getTicketNumber().toString().equals(ticketNumber)
                        && b.getMobileNumber() == phoneNumber)
                .findFirst();

        if (bookingsOptional.isPresent()) {
            Booking booking = bookingsOptional.get();
            List<String> seats = booking.getSeatNumbers();
            show.unMarkSeatMap(seats);
            booking.setSeatNumbers(new ArrayList<>());
            System.out.println("======SEATS HAS BEEN SUCCESSFULLY CANCELLED========");
            rebook(show, booking);
        }
    }

    private void rebook(Show show, Booking booking) {
        Scanner scanner = new Scanner(System.in);
        int choice = Utils.readInt(scanner, "DO YOU STILL WANT TO RESERVE SEATS?\n[1]. RESERVE SEAT\n[2]. CANCEL BOOKING");
        if (choice == 1) {
            String input = Utils.readString(scanner, "Enter desired seats, separated by a comma:\nexample: A1,A2:");
            List<String> seatsList = Arrays.asList(input.split(","));
            show.markSeatMap(seatsList);
            booking.setSeatNumbers(seatsList);
            System.out.println("======SUCCESSFULLY RESERVED SEATS========");
        } else {
            show.removeBooking(booking);
            System.out.println("======YOUR BOOKING HAS BEEN CANCELLED========");
        }
    }


//////for unit test only
    public void setShowStorage(ShowStorage showStorage) {
        this.showStorage = showStorage;
    }
}
