package org.example.action;

import org.example.contract.Command;

public class LoginCommand {
    public void login(Command userCommand) {
        userCommand.execute();
    }
}
