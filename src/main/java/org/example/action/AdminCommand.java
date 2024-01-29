package org.example.action;

import org.example.Main;
import org.example.Utils;
import org.example.contract.AdminAction;
import org.example.contract.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AdminCommand implements Command {
    private final AdminAction adminAction = new AdminActionImpl();
    Map<Integer, Runnable> actions = new HashMap<>() {{
        put(1, adminAction::setUpShow);
        put(2, adminAction::viewShow);
        put(3, Main::displayMenu);
    }};

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPlease choose your option...");
        System.out.println("[1]. SET UP SHOW");
        System.out.println("[2]. VIEW A SHOW");
        System.out.println("[3]. BACK");
        while (true) {
            int choice = scanner.nextInt();
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
        scanner.close();
    }
}
