package org.example;

import org.example.contract.AdminAction;
import org.example.contract.BuyerAction;
import java.util.*;

public class Main {

    public static void main(String[] args) {
       displayMenu();
    }

    private static void displayMenu() {
        System.out.println("\nLogin as...");
        System.out.println("1. ADMIN");
        System.out.println("2. BUYER");
        login();
    }

    private static void login(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    adminDisplayMenu();
                    break;
                case 2:
                    buyerDisplayMenu();
                    break;
            }
        }
    }

    private static void adminDisplayMenu(){
        AdminAction adminAction = new AdminActionImpl();
        System.out.println("\nPlease choose your option...");
        System.out.println("1. SET UP SHOW");
        System.out.println("2. VIEW A SHOW");
        System.out.println("3. BACK");

        Scanner s = new Scanner(System.in);
        while (true) {
            int choice = s.nextInt();
            switch (choice) {
                case 1:
                    adminAction.setUpShow();
                    displayMenu();
                    break;
                case 2:
                    adminAction.viewShow();
                    displayMenu();
                    break;
                case 3:
                    displayMenu();
                    break;

            }
        }

    }

    private static void buyerDisplayMenu(){
        BuyerAction buyerAction = new BuyerActionImpl();
        System.out.println("\nPlease choose your option...");
        System.out.println("1. AVAILABLE SEATS");
        System.out.println("2. BOOK A SEAT");
        System.out.println("3. CANCEL A SEAT");
        System.out.println("4. BACK");

        Scanner s = new Scanner(System.in);
        while (true) {
            int choice = s.nextInt();
            switch (choice) {
                case 1:
                    buyerAction.showAvailableSeats();
                    buyerDisplayMenu();
                    break;
                case 2:
                    buyerAction.bookASeat();
                    displayMenu();
                    break;
                case 3:
                    buyerAction.cancelASeat();
                    displayMenu();
                    break;
                case 4:
                    displayMenu();
            }
        }
    }
}