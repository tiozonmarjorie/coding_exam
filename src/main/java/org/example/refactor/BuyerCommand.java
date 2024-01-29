package org.example.refactor;

import org.example.BuyerActionImpl;
import org.example.Main;
import org.example.Utils;
import org.example.contract.BuyerAction;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BuyerCommand implements Command {
    private final BuyerAction buyerAction = new BuyerActionImpl();

    Map<Integer, Runnable> actions = new HashMap<>() {{
        put(1, buyerAction::showAvailableSeats);
        put(2, buyerAction::reserveSeat);
        put(3, buyerAction::cancelSeat);
        put(4, Main::displayMenu);
    }};

    @Override
    public void execute() {
        System.out.println("\nPlease choose your option...");
        System.out.println("[1]. AVAILABLE SEATS");
        System.out.println("[2]. BOOK A SEAT");
        System.out.println("[3]. CANCEL A SEAT");
        System.out.println("[4]. BACK");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            int choice = Utils.getUserChoice(scanner);
            if (actions.containsKey(choice)) {
                actions.get(choice).run();
                execute();
            } else {
                System.out.println("Invalid input. Please enter valid number.");
            }

            if (Utils.shouldExit(choice)) {
                break;
            }
        }
    }
}
