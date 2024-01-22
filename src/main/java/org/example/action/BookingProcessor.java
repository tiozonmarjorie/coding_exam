package org.example.action;

import org.example.Utils;
import org.example.model.Booking;
import org.example.model.Show;
import org.example.storage.ShowStorage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookingProcessor {

    private final ShowStorage showStorage;

    public BookingProcessor(ShowStorage showStorage) {
        this.showStorage = showStorage;
    }

    public String processBooking(int showNumber, int phoneNumber, List<String> seatsList) {
        Show show = showStorage.getPersistedShow(showNumber);
        Optional<Booking> bookingsOptional = show.getBookingsList()
                .stream()
                .filter(booking1 -> booking1.getMobileNumber() == phoneNumber)
                .findAny();

        if (bookingsOptional.isPresent()) {
            return "====UNABLE TO BOOK A SHOW, PHONE NUMBER EXISTS!=====";
        } else if (Utils.isSeatInvalid(show.getSeatMap(), seatsList)) {
            return "====UNABLE TO BOOK A SHOW, SEATS ARE ALREADY TAKEN!====";
        } else {
            Booking booking = new Booking(showNumber, UUID.randomUUID(), phoneNumber, seatsList);
            saveBooking(show, booking);
            return "Booking successful";
        }
    }

    private void saveBooking(Show show, Booking booking) {
        show.markSeatMap(booking.getSeatNumbers());
        showStorage.addBooking(booking.getShowNumber(), booking);
        System.out.println("======SUCCESSFULLY RESERVED A SEAT==========");
        Utils.printBookingInfo.accept(booking);
    }
}
