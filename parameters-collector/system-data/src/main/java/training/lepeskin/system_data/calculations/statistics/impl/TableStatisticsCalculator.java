package training.lepeskin.system_data.calculations.statistics.impl;

import training.lepeskin.system_data.calculations.statistics.representation.TimeIntervalStatistics;
import training.lepeskin.system_data.repository.ParameterRepository;
import training.lepeskin.system_data.repository.imp.InMemoryParameterRepositoryImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TableStatisticsCalculator extends Calculator<Optional<?>> {
    private ParameterRepository<Map<Date, ?>> repository = InMemoryParameterRepositoryImpl.getInstance();

    public Optional<?> calculateGeneralStatistics(String parameterName) {
        return Optional.empty();
    }

    public List<TimeIntervalStatistics> getTimeIntervalsStatistics(String parameterName) {
        return calculateTimeIntervalStatistics(repository.getData(parameterName));
    }
}
