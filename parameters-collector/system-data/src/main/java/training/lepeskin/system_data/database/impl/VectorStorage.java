package training.lepeskin.system_data.database.impl;

import training.lepeskin.system_data.database.Storable;

import java.util.Date;
import java.util.Map;

public class VectorStorage extends Storage implements Storable {
    private static VectorStorage instance;

    private VectorStorage() {
        super();
    }

    public static VectorStorage getInstance() {
        if (instance == null) {
            instance = new VectorStorage();
        }
        return instance;
    }

    @Override
    public Object saveData(String parameterName, Date time, Object value) {
        return write(parameterName, time, value);
    }

    @Override
    public Map<Date, ?> getDataMap(String parameterName) {
        return read(parameterName);
    }

    @Override
    public void clearStorage() {
        instance.clear();
    }
}
