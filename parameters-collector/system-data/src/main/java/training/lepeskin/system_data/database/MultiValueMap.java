package training.lepeskin.system_data.database;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MultiValueMap {
    private final Map<String, Map<Date, Object>> mappings = new HashMap<>();

    public Map<Date, Object> getValues(String key) {
        return mappings.get(key);
    }

    public Object addValue(String key, Date time, Object value) {
        Map<Date, Object> target = mappings.computeIfAbsent(key, k -> new LinkedHashMap<>());

        return target.put(time, value);
    }

    public void clearMultiMap(){
        mappings.clear();
    }
}
