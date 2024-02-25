package test.org.utils;

import com.sun.istack.internal.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
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

    /**
     * Прочитать файл с диска
     *
     * @param path    путь
     * @param charset кодировка
     * @return строковое представление данных
     * @throws IOException исключение, возникшее в ходе чтения
     */
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

    /**
     * Прочитать файл с диска (UTF-8) с обработкой исключения
     *
     * @param path путь
     * @return строковое представление данных. Null, если возникло исключение
     */
    public static @Nullable String readFileQuietly(@NotNull String path) {
        try {
            return readFile(path, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            LOG.warn("Не удалось прочитать файл {}: {}", path, exception.getMessage());
            return null;
        }
    }

    /**
     * Записать файл на диск
     *
     * @param data    строковое представление данных
     * @param path    путь
     * @param charset кодировка
     * @throws IOException исключение, возникшее в ходе записи
     */
    public static void writeFile(String data, @NotNull String path, Charset charset) throws IOException {
        org.apache.commons.io.FileUtils.write(initFile(path), data, charset);
    }

    /**
     * Записать файл на диск (UTF-8)
     *
     * @param data строковое представление данных
     * @param path путь
     * @throws IOException исключение, возникшее в ходе записи
     */
    public static void writeFile(String data, @NotNull String path) throws IOException {
        writeFile(data, path, StandardCharsets.UTF_8);
    }

    /**
     * Записать файл на диск (UTF-8) с обработкой исключения
     *
     * @param data строковое представление данных
     * @param path путь
     */
    public static void writeFileQuietly(String data, @NotNull String path) {
        try {
            writeFile(data, path, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            LOG.warn("Не удалось записать в файл {}: {}", path, exception.getMessage());
        }
    }

    /**
     * Удалить файл с диска
     *
     * @param path путь
     * @return флаг об успехе операции
     */
    public static boolean deleteFile(@NotNull String path) {
        return org.apache.commons.io.FileUtils.deleteQuietly(initFile(path));
    }

}
