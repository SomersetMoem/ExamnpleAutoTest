package test.org.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JsonUtils {
    private static final Logger LOG = LogManager.getLogger(FileUtils.class);
    private static final Gson gson = new Gson();

    public JsonUtils() {
    }

    public static List<String> extractValuesByKeyFromString(String jsonString, String key) {
        List<String> values = new ArrayList();
        String patternString = "\"\\b" + key + "\":\\s*\"(.*?)\"";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(jsonString);

        while(matcher.find()) {
            String value = matcher.group(1);
            values.add(value);
        }

        return values;
    }

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

    public static JSONObject getJsonObjectFromFile(String path) throws IOException {
        try {
            String dataString = Fil.readFile(path);
            return new JSONObject(dataString);
        } catch (IOException var2) {
            LOG.error("Не удалось создать JSON-объект из файла " + path + ": " + var2.getMessage());
            throw var2;
        }
    }

    public static String toJsonString(Object obj) {
        return (new Gson()).toJson(obj);
    }

    public static boolean hasKeyAndNotNullValue(JSONObject jsonObject, String key) {
        return jsonObject.has(key) && !jsonObject.isNull(key);
    }

    public static boolean hasKeyAndNotNullValue(JSONObject jsonObject, Collection<String> keys) {
        return keys.stream().allMatch((key) -> {
            return hasKeyAndNotNullValue(jsonObject, key);
        });
    }
}
