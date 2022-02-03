package training.lepeskin.system_data.repository.imp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import training.lepeskin.system_data.database.impl.ScalarStorage;
import training.lepeskin.system_data.database.impl.VectorStorage;
import training.lepeskin.system_data.enums.ParameterType;
import training.lepeskin.system_data.repository.ParameterRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class InMemoryParameterRepositoryImplTest {
    private static final Date date = new Date();
    private static final List<String> stringList = Arrays.asList("A", "B");

    @Before
    public void setUp(){
        ScalarStorage.getInstance().saveData("CPU", date, 10.0);
        VectorStorage.getInstance().saveData("Process list", date, stringList);
    }

    @After
    public void clearInstances(){
        ScalarStorage.getInstance().clearStorage();
    }

    @Test
    public void saveData(){
        ParameterRepository<?> repository = InMemoryParameterRepositoryImpl.getInstance();
        Assert.assertEquals(Map.of(date, 10.0), ScalarStorage.getInstance().getDataMap("CPU"));

        Date date1 = new Date();
        repository.saveData("CPU", ParameterType.SCALAR, date1, 15.6);
        Assert.assertEquals(Map.of(date, 10.0, date1, 15.6), ScalarStorage.getInstance().getDataMap("CPU"));
    }

    @Test
    public void getData(){
        ParameterRepository<?> repository = InMemoryParameterRepositoryImpl.getInstance();
        Assert.assertEquals(Map.of(date, 10.0), repository.getData("CPU"));
        Assert.assertEquals(Map.of(date, stringList), repository.getData("Process list"));
    }
}