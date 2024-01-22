package org.example.contract;

import org.example.model.Booking;
import org.example.model.Show;


public interface BuyerAction {

    void showAvailableSeats();

    void bookASeat();

    void cancelBooking();

    void removeBooking(Show show, String ticketNumber, int phoneNumber);

}
