package org.example;

import org.example.contract.AdminAction;
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
        Map<Integer, Runnable> actions = Utils.createActionsMap();
        while (true) {
            int choice = scanner.nextInt();
            if (actions.containsKey(choice)) {
                actions.get(choice).run();
                displayMenu();
            } else {
                System.out.println("INVALID INPUT PLEASE TRY AGAIN..");
            }
        }
    }

    static void adminDisplayMenu(){
        System.out.println("\nPlease choose your option...");
        System.out.println("1. SET UP SHOW");
        System.out.println("2. VIEW A SHOW");
        System.out.println("3. BACK");
        Scanner scanner = new Scanner(System.in);
        Map<Integer, Runnable> actions = Utils.createAdminActionsMap();
        while (true) {
            int choice = scanner.nextInt();
            if (actions.containsKey(choice)) {
                actions.get(choice).run();
                displayMenu();
            } else {
                System.out.println("INVALID INPUT PLEASE TRY AGAIN..");
            }
        }
    }

    static void buyerDisplayMenu(){
        System.out.println("\nPlease choose your option...");
        System.out.println("1. AVAILABLE SEATS");
        System.out.println("2. BOOK A SEAT");
        System.out.println("3. CANCEL A SEAT");
        System.out.println("4. BACK");

        Scanner scanner = new Scanner(System.in);
        Map<Integer, Runnable> actions = Utils.createBuyerActionsMap();
        while (true) {
            int choice = scanner.nextInt();
            if (actions.containsKey(choice)) {
                actions.get(choice).run();
                displayMenu();
            } else {
                System.out.println("INVALID INPUT PLEASE TRY AGAIN..");
            }
        }
    }
}