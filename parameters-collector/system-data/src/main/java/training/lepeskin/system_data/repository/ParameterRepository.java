package training.lepeskin.system_data.repository;

import training.lepeskin.system_data.enums.ParameterType;

import java.util.Date;

public interface ParameterRepository<T> {

    void saveData(String parameterName, ParameterType type, Date time, Object value);

    T getData(String parameterName);
}
