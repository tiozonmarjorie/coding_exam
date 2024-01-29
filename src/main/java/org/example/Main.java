package org.example;

import org.example.action.AdminCommand;
import org.example.action.BuyerCommand;
import org.example.action.LoginCommand;
import org.example.contract.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<Integer, Command> userCommandMap = new HashMap<>() {{
        put(1, new AdminCommand());
        put(2, new BuyerCommand());
    }};

    public static void main(String[] args) {
        displayMenu();
    }

    public static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nLOGIN AS. Please choose your option..");
        System.out.println("[1]. ADMIN");
        System.out.println("[2]. BUYER");
        System.out.println("[0]. EXIT");
        while (true) {
            int choice = scanner.nextInt();
            if (userCommandMap.containsKey(choice)) {
                Command userCommand = userCommandMap.get(choice);
                LoginCommand booking = new LoginCommand();
                booking.login(userCommand);
            } else {
                System.out.println("Invalid input. Please enter valid number.");
            }
            if (Utils.shouldExit(choice)) {
                break;
            }
        }
    }
}