package training.lepeskin.system_data.database;

import java.util.Date;
import java.util.Map;

public interface Storable {

    Object saveData(String parameterName, Date time, Object value);

    Map<Date, ?> getDataMap(String parameterName);

    void clearStorage();

}
