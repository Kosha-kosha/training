package training.lepeskin.system_data.calculations.statistics;

import training.lepeskin.system_data.calculations.statistics.representation.TimeIntervalStatistics;

import java.util.List;

public interface Calculable<T> {

    T calculateGeneralStatistics(String parameterName);

    List<TimeIntervalStatistics> getTimeIntervalsStatistics(String parameterName);

}
