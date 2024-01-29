package org.example.model;

import java.util.List;
import java.util.UUID;

public class Booking {

    private final int showNumber;
    private final UUID ticketNumber;
    private final int mobileNumber;
    private List<String> seatNumbers;

    public Booking(int showNumber, UUID ticketNumber, int mobileNumber, List<String> seatNumbers) {
        this.showNumber = showNumber;
        this.ticketNumber = ticketNumber;
        this.mobileNumber = mobileNumber;
        this.seatNumbers = seatNumbers;
    }

    public int getShowNumber() {
        return showNumber;
    }

    public UUID getTicketNumber() {
        return ticketNumber;
    }

    public int getMobileNumber() {
        return mobileNumber;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<String> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    @Override
    public String toString() {
        return "Bookings{" +
                "showNumber=" + showNumber +
                ", ticketNumber=" + ticketNumber +
                ", mobileNumber=" + mobileNumber +
                ", seatNumbers='" + seatNumbers + '\'' +
                '}';
    }
}
