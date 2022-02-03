package training.lepeskin.system_data.calculations.statistics.impl;

import training.lepeskin.system_data.calculations.statistics.representation.ScalarStatistics;
import training.lepeskin.system_data.calculations.statistics.representation.TimeIntervalStatistics;
import training.lepeskin.system_data.collectors.impl.TimeCollector;
import training.lepeskin.system_data.repository.ParameterRepository;
import training.lepeskin.system_data.repository.imp.InMemoryParameterRepositoryImpl;
import training.lepeskin.utility.loader.Loader;
import training.lepeskin.utility.logger.FilesLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

public class ScalarStatisticsCalculator extends Calculator<ScalarStatistics> {
    private static final FilesLogger filesLogger = new FilesLogger(Loader.class.getName(), "scalar-statistics-log.txt");

    private ParameterRepository<Map<Date, ?>> repository = InMemoryParameterRepositoryImpl.getInstance();

    protected void setRepository(ParameterRepository<Map<Date, ?>> repository) {
        this.repository = repository;
    }

    public ScalarStatistics calculateGeneralStatistics(String parameterName) {

        ScalarStatistics statistics = new ScalarStatistics();
        TimeCollector timeCollector = null;

        List<?> valuesList = repository.getData(parameterName).values().stream().toList();

        try {
            timeCollector = (TimeCollector) Class.forName("training.lepeskin.system_data.collectors.impl.TimeCollector").newInstance();
            Method method = TimeCollector.class.getDeclaredMethod("getParameterName");
            method.setAccessible(true);

            statistics.setMax(findMax((Collection<? extends Comparable>) valuesList));
            statistics.setMin(findMin((Collection<? extends Comparable>) valuesList));

            if (!parameterName.equals(method.invoke(timeCollector))) {

                OptionalDouble average = findAverage(valuesList);

                statistics.setAverage(average.isPresent() ? average.getAsDouble() : null);

            } else {
                statistics.setAverage(null);
            }

        } catch (NoSuchMethodException e) {
            filesLogger.log(String.format("'getParameterName' method in %s class not found. ", ScalarStatistics.class.getName()), e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            filesLogger.log(String.format("The base method throws an %s exception", e.getCause()), e);
        } catch (ClassNotFoundException | InstantiationException e) {
            String logString = String.format("Loadable candidate class object %s could not be instantiated.", timeCollector);
            filesLogger.log(logString, e);
        }
        return statistics;
    }

    public List<TimeIntervalStatistics> getTimeIntervalsStatistics(String parameterName) {
        return calculateTimeIntervalStatistics(repository.getData(parameterName));
    }

    private Comparable<?> findMax(Collection<? extends Comparable> valuesList) {
        return Collections.max(valuesList);
    }

    private Comparable<?> findMin(Collection<? extends Comparable> valuesList) {
        return Collections.min(valuesList);
    }

    private OptionalDouble findAverage(List<?> valuesList) {
        return valuesList.stream().mapToDouble(elem -> (double) elem).average();
    }

}
