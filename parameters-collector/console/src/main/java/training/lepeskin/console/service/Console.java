package training.lepeskin.console.service;

import training.lepeskin.console.commands.Command;
import training.lepeskin.console.commands.ExitCommand;
import training.lepeskin.utility.loader.Loader;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Console {

    public static void main(String[] args) {
        Console console = new Console();
        console.run();
    }

    public void run() {
        System.out.println("It's Console!");

        Loader<Command> commandLoader = new Loader<>();
        List<Command> commandList = commandLoader.load("training.lepeskin.console.commands", Command.class);

        Map<Integer, Command> commandMap = IntStream.range(0, commandList.size()).boxed()
                .collect(Collectors.toMap(i -> i, commandList::get));

        Menu commandsMenu = new Menu();
        boolean exitFlag = false;
        do {
            Optional<Command> executableCommand = commandsMenu.showCommandsMenu(commandMap);
            if (executableCommand.isPresent()) {
                exitFlag = executableCommand.get() instanceof ExitCommand;
                executableCommand.get().execute();
            } else {
                System.out.println("Invalid value! Try again.");
            }
        } while (!exitFlag);
    }
}
