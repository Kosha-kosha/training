package training.lepeskin.system_data.collectors.impl;

import training.lepeskin.system_data.collectors.ParameterCollector;
import training.lepeskin.system_data.enums.ParameterType;

import java.lang.management.ManagementFactory;
import java.util.Optional;

public class MemoryCollector implements ParameterCollector<Double> {
    private final static int GBToBytes = 1024 * 1024 * 1024;
    private Double memoryUsed;

    public MemoryCollector() {
    }

    @Override
    public Double collect() {
        memoryUsed = (double) ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax() / GBToBytes;
        return memoryUsed;
    }

    @Override
    public Optional<Double> getLastCollectedValue() {
        return Optional.ofNullable(memoryUsed);
    }

    @Override
    public String getParameterName() {
        return "Memory";
    }

    @Override
    public ParameterType getValueType() {
        return ParameterType.SCALAR;
    }
}
