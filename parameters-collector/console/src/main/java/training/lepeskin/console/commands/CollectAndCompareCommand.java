package training.lepeskin.console.commands;

import training.lepeskin.console.service.Printer;
import training.lepeskin.system_data.service.CollectingService;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class CollectAndCompareCommand implements Command {
    private Scanner scanner = new Scanner(System.in);
    private Printer printer = new Printer();
    private CollectingService collectorsService = CollectingService.getInstance();

    public CollectAndCompareCommand() {
    }

    public String getName() {
        return "Print current value of the requested parameter and compare with last collected value";
    }

    @Override
    public void execute() {
        Map<Integer, String> parametersNameMap = collectorsService.getParameterName();

        if (!parametersNameMap.isEmpty()) {

            printer.printMap(parametersNameMap, "%d ----- %s");
            System.out.println("Please, select...");
            int input = scanner.nextInt();

            String parameterName = parametersNameMap.get(input);

            Optional<?> optionalLastValue = collectorsService.getLastCollectedValue(parameterName);

            try {
                Object currentValue = collectorsService.collectValue(parameterName);

                printer.printPair(parameterName, currentValue, "%s:\n%s");
                System.out.println(optionalLastValue.map(lastValue -> compareValues(currentValue, lastValue))
                        .orElse("This is the first collection of the parameter"));

            } catch (Exception e) {
                System.out.println("Data wasn't collected. " + e.getMessage());
            }
        } else {
            System.out.println("You can't collect values because parameters' list is empty");
        }
    }

    private String compareValues(Object currentValue, Object lastCollectedValue) {
        return !currentValue.equals(lastCollectedValue) ? "Value WAS CHANGED" : "Value WASN'T CHANGED";
    }

}
