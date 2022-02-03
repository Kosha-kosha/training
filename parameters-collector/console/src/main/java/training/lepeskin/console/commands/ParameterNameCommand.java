package training.lepeskin.console.commands;

import training.lepeskin.console.service.Printer;
import training.lepeskin.system_data.service.CollectingService;

import java.util.Map;

public class ParameterNameCommand implements Command {
    private Printer printer = new Printer();
    private CollectingService collectorsService = CollectingService.getInstance();

    public ParameterNameCommand() {
    }

    @Override
    public String getName() {
        return "Print a list of supported parameters";
    }

    @Override
    public void execute() {
        Map<Integer, String> parametersNameMap = collectorsService.getParameterName();
        if (!parametersNameMap.isEmpty()) {
            printer.printMap(parametersNameMap, "%d ----- %s");
        } else {
            System.out.println("List is empty");
        }
    }
}
