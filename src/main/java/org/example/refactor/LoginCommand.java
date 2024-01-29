package org.example.refactor;

public class LoginCommand {
    public void login(Command userCommand) {
        userCommand.execute();
    }
}
