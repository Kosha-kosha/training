package training.lepeskin.console.commands;

import training.lepeskin.system_data.exceptions.CollectingException;
import training.lepeskin.system_data.service.CollectingService;
import training.lepeskin.system_data.service.StatisticsService;

import java.util.Map;

public class CollectInTimeCommand implements Command {
    private CollectingService collectorsService = CollectingService.getInstance();
    private StatisticsService statisticsService = StatisticsService.getInstance();


    @Override
    public String getName() {
        return "Collect values of supported parameters in 3 minutes";
    }

    @Override
    public void execute() {
        Map<Integer, String> parametersNameMap = collectorsService.getParameterName();

        if (!parametersNameMap.isEmpty()) {

            System.out.println("Data collection has begun. Please wait...");

            parametersNameMap.forEach((key, value) -> {
                try {

                    collectorsService.collectInThreeMinutes(value);
                    System.out.printf("Data by %s was collected.\n", value);

                    System.out.printf("Statistics for %s parameter:\n", value);
                    statisticsService.calculateGeneralStatistics(value).ifPresent(System.out::println);
                    statisticsService.getTimeIntervalStatistics(value).ifPresent(System.out::println);

                } catch (CollectingException e) {
                    System.out.println("Data wasn't collected. " + e.getMessage());
                }
            });
        } else {
            System.out.println("You can't collect values because parameters' list is empty");
        }
    }

}
