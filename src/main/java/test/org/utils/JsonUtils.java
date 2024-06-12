package test.org.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonUtils {
    private static final Logger LOG = LogManager.getLogger(FileUtils.class);
    private static final Gson gson = new Gson();

    public static <T> T getObjectFromString(String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }

    public static <T> List<T> getListObjectsFromString(String jsonArrayString, Class<T> clazz) {
        TypeToken<ArrayList<T>> token = new TypeToken<ArrayList<T>>() {
        };
        List<T> listObjects = (List)gson.fromJson(jsonArrayString, token.getType());
        return (List)listObjects.stream().map((obj) -> {
            return gson.fromJson(gson.toJson(obj), clazz);
        }).collect(Collectors.toList());
    }

    public static <T> T getObjectFromFile(String path, Class<T> type) throws IOException {
        try {
            String dataString = FileUtils.readFile(path);
            return getObjectFromString(dataString, type);
        } catch (IOException var3) {
            LOG.error("Не удалось создать объект из файла " + path + " типа " + type.getSimpleName() + ": " + var3.getMessage());
            throw var3;
        }
    }
}
