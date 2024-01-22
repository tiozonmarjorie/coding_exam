package org.example.contract;

import java.util.Map;

public interface AdminAction {

    void setUpShow();
    void viewShow();

    Map<String, Boolean> initializeSeat(int rows, int seats);

}
