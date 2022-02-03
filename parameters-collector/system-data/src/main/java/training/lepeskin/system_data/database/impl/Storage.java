package training.lepeskin.system_data.database.impl;

import training.lepeskin.system_data.database.MultiValueMap;

import java.util.Date;
import java.util.Map;

public class Storage {
    private MultiValueMap storage;

    protected Storage() {
        this.storage = new MultiValueMap();
    }

    protected Map<Date, ?> read(String parameterName) {
        return storage.getValues(parameterName);
    }

    protected Object write(String parameterName, Date time, Object value) {
        return storage.addValue(parameterName, time, value);
    }

    protected void clear(){
        storage.clearMultiMap();
    }
}
