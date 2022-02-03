package training.lepeskin.system_data.database.impl;

import training.lepeskin.system_data.database.Storable;

import java.util.Date;
import java.util.Map;

public class TableStorage extends Storage implements Storable {
    private static TableStorage instance;

    private TableStorage() {
        super();
    }

    public static Storable getInstance() {
        if (instance == null) {
            instance = new TableStorage();
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
