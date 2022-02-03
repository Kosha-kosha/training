package training.lepeskin.system_data.collectors.impl;

import training.lepeskin.system_data.collectors.ParameterCollector;
import training.lepeskin.system_data.enums.ParameterType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class DiskCollector implements ParameterCollector<Set<List<String>>> {
    private HashSet<List<String>> diskSet;

    public DiskCollector() {
    }

    @Override
    public Set<List<String>> collect() {
        diskSet = new HashSet<>();
        diskSet.add(Arrays.asList("Disk 0", "C", "В сети", "512"));
        diskSet.add(Arrays.asList("Disk 1", "D", "В сети", "512"));
        diskSet.add(Arrays.asList("Disk 2", "E", "В сети", "256"));
        return diskSet;
    }

    @Override
    public Optional<Set<List<String>>> getLastCollectedValue() {
        return Optional.ofNullable(diskSet);
    }

    @Override
    public String getParameterName() {
        return "Disk list";
    }

    @Override
    public ParameterType getValueType() {
        return ParameterType.TABLE;
    }
}
