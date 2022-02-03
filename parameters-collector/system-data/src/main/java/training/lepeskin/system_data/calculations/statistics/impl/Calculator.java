package training.lepeskin.system_data.calculations.statistics.impl;

import training.lepeskin.system_data.calculations.statistics.Calculable;
import training.lepeskin.system_data.calculations.statistics.representation.TimeIntervalStatistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class Calculator<T> implements Calculable<T> {

    public abstract T calculateGeneralStatistics(String parameterName);

    protected List<TimeIntervalStatistics> calculateTimeIntervalStatistics(Map<Date, ?> dataMap) {

        List<TimeIntervalStatistics> timeIntervalStatisticsList = new ArrayList<>();

        dataMap.forEach((key, value) -> {
            if (timeIntervalStatisticsList.isEmpty()) {
                timeIntervalStatisticsList.add(createAndInitNewInterval(key, key, value));
            } else {
                TimeIntervalStatistics lastInterval = getLastIntervalFromIntervalList(timeIntervalStatisticsList);
                lastInterval.setEndTime(key);
                if (!value.equals(lastInterval.getValue())) {
                    timeIntervalStatisticsList.add(createAndInitNewInterval(key, key, value));
                }
            }
        });
        return timeIntervalStatisticsList;
    }

    private TimeIntervalStatistics createAndInitNewInterval(Date startTime, Date endTime, Object value) {
        TimeIntervalStatistics newInterval = new TimeIntervalStatistics();
        newInterval.setStartTime(startTime);
        newInterval.setEndTime(endTime);
        newInterval.setValue(value);
        return newInterval;
    }

    private TimeIntervalStatistics getLastIntervalFromIntervalList(List<TimeIntervalStatistics> intervalList) {
        return intervalList.get(intervalList.size() - 1);
    }
}
