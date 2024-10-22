
import org.junit.jupiter.api.Test;
import hexlet.code.Differ;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {

    @Test
    public void testGenerateNestedStructuresSameValues() throws Exception {
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        String json1 = "{ \"key1\": { \"nestedKey1\": \"value1\" }, \"key2\": [1, 2, 3] }";
        String json2 = "{ \"key1\": { \"nestedKey1\": \"value1\" }, \"key2\": [1, 2, 3] }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        String expected = "{\n"
                + "    key1: {nestedKey1=value1}\n"
                + "    key2: [1, 2, 3]\n"
                + "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }

    @Test
    public void testGenerateNestedStructuresDifferentValues() throws Exception {
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        String json1 = "{ \"key1\": { \"nestedKey1\": \"value1\" }, \"key2\": [1, 2, 3] }";
        String json2 = "{ \"key1\": { \"nestedKey1\": \"value2\" }, \"key2\": [1, 2, 3, 4] }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        String expected = "{\n"
                + "    key1: {nestedKey1=value1}\n"
                + "  - key2: [1, 2, 3]\n"
                + "  + key2: [1, 2, 3, 4]\n"
                + "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }

    @Test
    public void testGenerateFile1HasExtraKeyNested() throws Exception {
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        String json1 = "{ \"key1\": { \"nestedKey1\": \"value1\" }, \"key2\": [1, 2] }";
        String json2 = "{ \"key1\": { \"nestedKey1\": \"value1\" }, \"key2\": [1, 2, 3] }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        String expected = "{\n"
                + "    key1: {nestedKey1=value1}\n"
                + "  - key2: [1, 2]\n"
                + "  + key2: [1, 2, 3]\n"
                + "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }

    @Test
    public void testGenerateFile2HasExtraKeyNested() throws Exception {
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        String json1 = "{ \"key1\": { \"nestedKey1\": \"value1\" }, \"key2\": [1, 2] }";
        String json2 = "{ \"key1\": { \"nestedKey1\": \"value1\" }, \"key2\": [1, 2], \"key3\": \"newKey\" }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        String expected = "{\n"
                + "    key1: {nestedKey1=value1}\n"
                + "  - key2: [1, 2]\n"
                + "  + key2: [1, 2]\n"
                + "  + key3: newKey\n"
                + "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }

    @Test
    public void testGenerateNestedArray() throws Exception {
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        String json1 = "{ \"key1\": [1, 2, 3], \"key2\": {\"nestedKey\": \"value\"} }";
        String json2 = "{ \"key1\": [1, 2, 4], \"key2\": {\"nestedKey\": \"value\"} }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        String expected = "{\n"
                + "  - key1: [1, 2, 3]\n"
                + "  + key1: [1, 2, 4]\n"
                + "    key2: {nestedKey=value}\n"
                + "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }
}
