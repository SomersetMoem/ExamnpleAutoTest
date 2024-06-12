package test.org;


import test.org.utils.JsonUtils;

import java.io.IOException;
import java.util.List;

public class DataGeneration {
    public <T> T generateTestData(String filePath, Class<T> type) throws IOException {
        return JsonUtils.getObjectFromFile(filePath, type);
    }

    public <T> List<T> generateListTestData(String filePath, Class<T> type) {
        return JsonUtils.getListObjectsFromString(filePath, type);
    }
}
