package test.org.utils;

import com.sun.istack.internal.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class FileUtils {
    private static final Logger LOG = LogManager.getLogger(FileUtils.class);

    private static File initFile(String path) {
        return new File(String.valueOf(Paths.get(path)));
    }

    public static String readFile(@NotNull String path, Charset charset) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToString(initFile(path), charset);
    }

    /**
     * Прочитать файл с диска (UTF-8)
     *
     * @param path путь
     * @return строковое представление данных
     * @throws IOException исключение, возникшее в ходе чтения
     */
    public static String readFile(@NotNull String path) throws IOException {
        return readFile(path, StandardCharsets.UTF_8);
    }
}
