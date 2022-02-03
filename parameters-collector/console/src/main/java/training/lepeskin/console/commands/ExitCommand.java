package training.lepeskin.console.commands;

public class ExitCommand implements Command {
    public ExitCommand() {
    }

    @Override
    public String getName() {
        return "Exit";
    }

    @Override
    public void execute() {
        System.out.println("exit...");
    }
}
