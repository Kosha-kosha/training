package training.lepeskin.system_data.collectors.impl;

import training.lepeskin.system_data.collectors.ParameterCollector;
import training.lepeskin.system_data.enums.ParameterType;

import java.util.Date;
import java.util.Optional;

public class TimeCollector implements ParameterCollector<Date> {
    private Date timeValue;

    public TimeCollector() {
    }

    @Override
    public Date collect() {
        timeValue = new Date();
        return timeValue;
    }

    @Override
    public Optional<Date> getLastCollectedValue() {
        return Optional.ofNullable(timeValue);
    }

    @Override
    public String getParameterName() {
        return "Time";
    }

    @Override
    public ParameterType getValueType() {
        return ParameterType.SCALAR;
    }
}
