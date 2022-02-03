package training.lepeskin.system_data.service;

import training.lepeskin.system_data.calculations.statistics.Calculable;
import training.lepeskin.system_data.calculations.statistics.representation.TimeIntervalStatistics;
import training.lepeskin.system_data.enums.ParameterType;
import training.lepeskin.utility.logger.FilesLogger;

import java.util.List;
import java.util.Optional;

public class StatisticsService {
    private static final FilesLogger filesLogger =
            new FilesLogger(StatisticsService.class.getName(), "statistics-service-log.txt");
    private static StatisticsService instance;

    private StatisticsService() {
    }

    public static StatisticsService getInstance() {
        if (instance == null) {
            instance = new StatisticsService();
        }
        return instance;
    }

    public Optional<?> calculateGeneralStatistics(String parameterName) {

        return getCalculator(parameterName).map(calculator -> calculator.calculateGeneralStatistics(parameterName));
    }

    public Optional<List<TimeIntervalStatistics>> getTimeIntervalStatistics(String parameterName) {

        return getCalculator(parameterName).map(calculator -> calculator.getTimeIntervalsStatistics(parameterName));
    }

    private Optional<Calculable<?>> getCalculator(String parameterName) {

        Calculable<?> calculator = null;
        ParameterType parameterType;

        Optional<ParameterType> optionalParameterType = CollectingService.getInstance().getParameterType(parameterName);

        if (optionalParameterType.isPresent()) {
            parameterType = optionalParameterType.get();
            try {
                calculator = (Calculable<?>) Class.forName(
                        String.format("training.lepeskin.system_data.calculations.statistics.impl.%sStatisticsCalculator",
                                parameterType.getText())).newInstance();

                return Optional.of(calculator);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                String logString = String.format("Loadable candidate class object %s could not be instantiated.", calculator);
                filesLogger.log(logString, e);
            }
        }
        return Optional.empty();
    }
}
