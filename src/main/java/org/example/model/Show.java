package org.example.model;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Show {

    private final int showNumber;
    private final int numberOfRows;
    private final int numberOfSeatsPerRow;
    private final int cancellationTimeLimit;

    private final List<Booking> bookingList = new ArrayList<>();

    private Map<String, Boolean> seatMap = new LinkedHashMap<>();

    public Show(int showNumber, int numberOfRows, int numberOfSeatsPerRow, int cancellationTimeLimit) {
        this.showNumber = showNumber;
        this.numberOfRows = numberOfRows;
        this.numberOfSeatsPerRow = numberOfSeatsPerRow;
        this.cancellationTimeLimit = cancellationTimeLimit;
    }

    public void markSeatMap(List<String> seatsList) {
        seatsList.forEach(s -> {
            if (this.seatMap.containsKey(s)) {
                this.seatMap.replace(s, false);
            }
        });
    }

    public void unMarkSeatMap(List<String> seatsList) {
        seatsList.forEach(s -> {
            if (this.seatMap.containsKey(s)) {
                this.seatMap.replace(s, true);
            }
        });
    }

    public Map<String, Boolean> getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(Map<String, Boolean> seatMap) {
        this.seatMap = seatMap;
    }

    public void addBooking(Booking booking) {
        this.bookingList.add(booking);
    }

    public void removeBooking(Booking booking) {
        this.bookingList.remove(booking);
    }

    public List<Booking> getBookingsList() {
        return bookingList;
    }

    @Override
    public String toString() {
        return "Show{" +
                "showNumber=" + showNumber +
                ", numberOfRows=" + numberOfRows +
                ", numberOfSeatsPerRow=" + numberOfSeatsPerRow +
                ", cancellationTimeLimit=" + cancellationTimeLimit +
                ", seatMap=" + seatMap +
                '}';
    }
}
