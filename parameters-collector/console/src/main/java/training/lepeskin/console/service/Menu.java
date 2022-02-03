package training.lepeskin.console.service;

import training.lepeskin.console.commands.Command;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);

    public Optional<Command> showCommandsMenu(Map<Integer, Command> commandMap) {
        System.out.println("____________MENU____________");
        commandMap.entrySet().stream().map(command -> command.getKey() + " --- " + command.getValue().getName())
                .forEach(System.out::println);

        Integer choice = scanner.nextInt();
        return Optional.ofNullable(commandMap.get(choice));
    }
}
