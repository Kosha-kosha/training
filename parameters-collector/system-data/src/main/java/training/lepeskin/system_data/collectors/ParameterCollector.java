package training.lepeskin.system_data.collectors;


import training.lepeskin.system_data.enums.ParameterType;
import training.lepeskin.system_data.exceptions.CollectingException;

import java.util.Optional;

public interface ParameterCollector<T> {

    T collect() throws CollectingException;

    Optional<T> getLastCollectedValue();

    String getParameterName();

    ParameterType getValueType();
}

