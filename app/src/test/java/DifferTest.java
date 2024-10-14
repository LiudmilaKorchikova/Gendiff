import org.junit.jupiter.api.Test;

import hexlet.code.Differ;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {

    @Test
    public void testGenerateSameValues() throws Exception {
        // Создаем временные файлы
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        // Записываем одинаковые значения в оба файла
        String json1 = "{ \"key1\": \"value1\", \"key2\": \"value2\" }";
        String json2 = "{ \"key1\": \"value1\", \"key2\": \"value2\" }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        // Вызываем метод generate
        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        // Ожидаем, что результат будет
        String expected = "{\n" +
                "    key1: value1\n" +
                "    key2: value2\n" +
                "}";

        assertEquals(expected, result);

        // Удаляем временные файлы
        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }

    @Test
    public void testGenerateDifferentValues() throws Exception {
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        String json1 = "{ \"key1\": \"value1\", \"key2\": \"value2\" }";
        String json2 = "{ \"key1\": \"value1\", \"key2\": \"value3\" }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        String expected = "{\n" +
                "  - key2: value2\n" +
                "  + key2: value3\n" +
                "    key1: value1\n" +
                "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }

    @Test
    public void testGenerateOnlyInFirstFile() throws Exception {
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        String json1 = "{ \"key1\": \"value1\", \"key2\": \"value2\" }";
        String json2 = "{ \"key3\": \"value3\" }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        String expected = "{\n" +
                "  - key1: value1\n" +
                "  - key2: value2\n" +
                "  + key3: value3\n" +
                "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }

    @Test
    public void testGenerateOnlyInSecondFile() throws Exception {
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        String json1 = "{ \"key1\": \"value1\" }";
        String json2 = "{ \"key2\": \"value2\", \"key3\": \"value3\" }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        String expected = "{\n" +
                "  - key1: value1\n" +
                "  + key2: value2\n" +
                "  + key3: value3\n" +
                "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }
}