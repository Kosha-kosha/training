package training.lepeskin.system_data.service;

import training.lepeskin.system_data.collectors.ParameterCollector;
import training.lepeskin.system_data.enums.ParameterType;
import training.lepeskin.system_data.exceptions.CollectingException;
import training.lepeskin.system_data.repository.ParameterRepository;
import training.lepeskin.system_data.repository.imp.InMemoryParameterRepositoryImpl;
import training.lepeskin.utility.loader.Loader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectingService {
    private static CollectingService instance;

    private Map<String, ? extends ParameterCollector<?>> collectorsMap;
    private ParameterRepository<Map<Date, ?>> repository = InMemoryParameterRepositoryImpl.getInstance();

    private CollectingService() {
        Loader<ParameterCollector<?>> collectorLoader = new Loader<>();
        List<ParameterCollector<?>> collectorsList =
                collectorLoader.load("training.lepeskin.system_data.collectors.impl", ParameterCollector.class);
        collectorsMap = collectorsList.stream()
                .collect(Collectors.toMap(ParameterCollector::getParameterName, collector -> collector));
    }

    public static CollectingService getInstance() {
        if (instance == null) {
            instance = new CollectingService();
        }
        return instance;
    }

    public Map<Integer, String> getParameterName() {
        return IntStream.range(0, collectorsMap.size()).boxed()
                .collect(Collectors.toMap(i -> i,
                        collectorsMap.values().stream().map(ParameterCollector::getParameterName).toList()
                                ::get));
    }

    public Optional<ParameterType> getParameterType(String parameterName) {
        return getCollector(parameterName).map(ParameterCollector::getValueType);
    }

    public Object collectValue(String parameterName) throws CollectingException {
        Optional<ParameterCollector<?>> optionalCollector = getCollector(parameterName);

        if (optionalCollector.isPresent()) {
            ParameterCollector<?> collector = optionalCollector.get();
            Object value = collector.collect();
            repository.saveData(parameterName, collector.getValueType(), new Date(), value);
            return value;
        } else {
            return Optional.empty();
        }
    }

    public Optional<?> getLastCollectedValue(String parameterName) {
        Optional<ParameterCollector<?>> optionalCollector = getCollector(parameterName);
        return optionalCollector.isPresent() ? optionalCollector.get().getLastCollectedValue() : Optional.empty();
    }

    public List<Object> collectInThreeMinutes(String parameterName) throws CollectingException {
        int iteration = (3 * 60) / 5;
        List<Object> objectList = new ArrayList<>();

        for (int i = 0; i < iteration; i++) {

            objectList.add(collectValue(parameterName));

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return objectList;
    }

    private Optional<ParameterCollector<?>> getCollector(String parameterName) {
        return Optional.ofNullable(collectorsMap.get(parameterName));
    }
}
