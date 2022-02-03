package training.lepeskin.system_data.calculations.statistics.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import training.lepeskin.system_data.calculations.statistics.representation.ScalarStatistics;
import training.lepeskin.system_data.calculations.statistics.representation.TimeIntervalStatistics;
import training.lepeskin.system_data.repository.ParameterRepository;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScalarStatisticsCalculatorTest {
    private Date date;
    private Date date1;

    @Mock
    private ParameterRepository<Map<Date, ?>> repository;

    private ScalarStatisticsCalculator calculator;

    @Before
    public void setUp() throws InterruptedException {
        repository = mock(ParameterRepository.class);
        calculator = new ScalarStatisticsCalculator();
        calculator.setRepository(repository);

        date = new Date();
        Thread.sleep(5000);
        date1 = new Date();
    }

    @Test
    public void calculateGeneralStatisticsForScalar() {


        try(MockedConstruction<ScalarStatisticsCalculator> mockedConstruction =
                    Mockito.mockConstruction(ScalarStatisticsCalculator.class)){

            when(repository.getData("CPU")).thenAnswer(answer ->
                    Map.of(date, 10.0, date1, 50.0));

            ScalarStatistics statistics = calculator.calculateGeneralStatistics("CPU");

            Assert.assertEquals(50.0, statistics.getMax());
            Assert.assertEquals(10.0, statistics.getMin());
            Assert.assertEquals(30.0, statistics.getAverage());
        }
    }

    @Test
    public void calculateTimeIntervalStatistics() {
        Map<Date, ?> exampleMap = new LinkedHashMap<>(){{put(date, 10.0); put(date1, 15.6);}};
        when(repository.getData("CPU")).thenAnswer(answer -> exampleMap);

        TimeIntervalStatistics expectedInterval = createAndInitTimeIntervalObj(date, date1, 10.0);
        TimeIntervalStatistics expectedInterval1 = createAndInitTimeIntervalObj(date1, date1, 15.6);

        List<TimeIntervalStatistics> actualIntervalStatisticsList =
                calculator.calculateTimeIntervalStatistics(repository.getData("CPU"));

        Assert.assertTrue(actualIntervalStatisticsList.size() >= 2);

        TimeIntervalStatistics actualInterval = actualIntervalStatisticsList.get(0);
        TimeIntervalStatistics actualInterval1 = actualIntervalStatisticsList.get(1);

        assertTwoTimeIntervalObj(expectedInterval, actualInterval);
        assertTwoTimeIntervalObj(expectedInterval1, actualInterval1);
    }

    private TimeIntervalStatistics createAndInitTimeIntervalObj(Date startTime, Date endTime, Object value){
        TimeIntervalStatistics newInterval = new TimeIntervalStatistics();
        newInterval.setStartTime(startTime);
        newInterval.setEndTime(endTime);
        newInterval.setValue(value);
        return newInterval;
    }

    private void assertTwoTimeIntervalObj(TimeIntervalStatistics expectedObj, TimeIntervalStatistics actualObj){
        Assert.assertEquals(expectedObj.getStartTime(),actualObj.getStartTime());
        Assert.assertEquals(expectedObj.getEndTime(),actualObj.getEndTime());
        Assert.assertEquals(expectedObj.getValue(),actualObj.getValue());

    }
}