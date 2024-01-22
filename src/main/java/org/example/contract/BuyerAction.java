package org.example.contract;

import org.example.model.Booking;
import org.example.model.Show;


public interface BuyerAction {

    void showAvailableSeats();

    void bookASeat();

    void cancelASeat();

    void saveBooking(Show show, Booking booking);

    void removeBooking(Show show, String ticketNumber, int phoneNumber);

}
