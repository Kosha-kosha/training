package training.lepeskin.system_data.repository.imp;

import training.lepeskin.system_data.database.Storable;
import training.lepeskin.system_data.database.impl.ScalarStorage;
import training.lepeskin.system_data.database.impl.TableStorage;
import training.lepeskin.system_data.database.impl.VectorStorage;
import training.lepeskin.system_data.enums.ParameterType;
import training.lepeskin.system_data.repository.ParameterRepository;
import training.lepeskin.system_data.service.CollectingService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryParameterRepositoryImpl implements ParameterRepository<Map<Date, ?>> {
    private static InMemoryParameterRepositoryImpl instance;

    private InMemoryParameterRepositoryImpl() {

    }

    public static InMemoryParameterRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new InMemoryParameterRepositoryImpl();
        }
        return instance;
    }

    public void saveData(String parameterName, ParameterType type, Date time, Object value) {
        getStorage(type).saveData(parameterName, time, value);
    }

    public Map<Date, ?> getData(String parameterName) {
        Optional<ParameterType> optionalParameterTypes = CollectingService.getInstance().getParameterType(parameterName);

        return optionalParameterTypes.isPresent() ?
                getStorage(optionalParameterTypes.get()).getDataMap(parameterName) : new HashMap<>();
    }

    private Storable getStorage(ParameterType type) {
        return switch (type) {
            case SCALAR -> ScalarStorage.getInstance();
            case TABLE -> TableStorage.getInstance();
            case VECTOR -> VectorStorage.getInstance();
        };
    }
}
