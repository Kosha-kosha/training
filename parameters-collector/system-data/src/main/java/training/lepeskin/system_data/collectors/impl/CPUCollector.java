package training.lepeskin.system_data.collectors.impl;

import training.lepeskin.system_data.collectors.ParameterCollector;
import training.lepeskin.system_data.enums.ParameterType;

import java.util.Optional;

public class CPUCollector implements ParameterCollector<Double> {
    private Double loadCPU;

    public CPUCollector() {
    }

    @Override
    public Double collect() {
        double maxExclusively = 100.0;
        double min = 1.0 / Integer.MAX_VALUE;
        loadCPU = Math.random() * maxExclusively + min;
        return loadCPU;
    }


    @Override
    public Optional<Double> getLastCollectedValue() {
        return Optional.ofNullable(loadCPU);
    }

    @Override
    public String getParameterName() {
        return "CPU";
    }

    @Override
    public ParameterType getValueType() {
        return ParameterType.SCALAR;
    }
}
