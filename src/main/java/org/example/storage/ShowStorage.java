package org.example.storage;

import org.example.model.Booking;
import org.example.model.Show;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShowStorage {

    private final static ShowStorage instance = new ShowStorage();
    private final Map<Integer, Show> showMap = new LinkedHashMap<>();

    private ShowStorage(){

    }

    public void addNewShow(Integer showNumber, Show show) {
        showMap.put(showNumber, show);
    }

    public void addBooking(Integer showNumber, Booking booking) {
        Show show = getPersistedShow(showNumber);
        show.addBooking(booking);
    }

    public Show getPersistedShow(Integer showNumber){
        Show show = showMap.get(showNumber);
        if (show==null){
            System.out.println("=======SHOW IS NOT AVAILABLE=======");
        }
        return show;
    }

    public static ShowStorage getInstance(){
        return instance;
    }

}
