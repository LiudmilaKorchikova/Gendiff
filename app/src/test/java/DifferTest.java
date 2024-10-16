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

        String expected = "{\n" +
                "    key1: {nestedKey1=value1}\n" +
                "    key2: [1, 2, 3]\n" +
                "}";

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

        String expected = "{\n" +
                "    key1: {nestedKey1=value1}\n" +
                "  - key2: [1, 2, 3]\n" +
                "  + key2: [1, 2, 3, 4]\n" +
                "}";

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

        String expected = "{\n" +
                "    key1: {nestedKey1=value1}\n" +
                "  - key2: [1, 2]\n" +
                "  + key2: [1, 2, 3]\n" +
                "}";

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

        String expected = "{\n" +
                "    key1: {nestedKey1=value1}\n" +
                "  - key2: [1, 2]\n" +
                "  + key2: [1, 2]\n" +
                "  + key3: newKey\n" +
                "}";

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

        String expected = "{\n" +
                "  - key1: [1, 2, 3]\n" +
                "  + key1: [1, 2, 4]\n" +
                "    key2: {nestedKey=value}\n" +
                "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }
}

/*
import org.junit.jupiter.api.Test;

import hexlet.code.Differ;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {

    @Test
    public void testGenerateSameValues() throws Exception {
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        String json1 = "{ \"1\" : \"json\", \"3\" : \"1\" }";
        String json2 = "{ \"1\" : \"json\", \"3\" : \"1\" }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        String expected = "{\n" +
                "    1: json\n" +
                "    3: 1\n" +
                "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }

    @Test
    public void testGenerateDifferentValues() throws Exception {
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        String json1 = "{ \"1\" : \"json\", \"3\" : \"1\" }";
        String json2 = "{ \"1\" : \"json\", \"3\" : \"2\" }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        String expected = "{\n" +
                "    1: json\n" +
                "  - 3: 1\n" +
                "  + 3: 2\n" +
                "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }

    @Test
    public void testGenerateFile1HasExtraKey() throws Exception {
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        String json1 = "{ \"1\" : \"json\", \"3\" : \"1\", \"4\" : \"extra\" }";
        String json2 = "{ \"1\" : \"json\", \"3\" : \"2\" }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        String expected = "{\n" +
                "    1: json\n" +
                "  - 3: 1\n" +
                "  + 3: 2\n" +
                "  - 4: extra\n" +
                "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }

    @Test
    public void testGenerateFile2HasExtraKey() throws Exception {
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        String json1 = "{ \"1\" : \"json\", \"3\" : \"1\" }";
        String json2 = "{ \"1\" : \"json\", \"3\" : \"2\", \"5\" : \"new\" }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        String expected = "{\n" +
                "    1: json\n" +
                "  - 3: 1\n" +
                "  + 3: 2\n" +
                "  + 5: new\n" +
                "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }

    @Test
    public void testGenerateNestedStructures() throws Exception {
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        String json1 = "{ \"key1\": \"value1\", \"nested\": { \"innerKey1\": \"innerValue1\" } }";
        String json2 = "{ \"key1\": \"value1\", \"nested\": { \"innerKey1\": \"innerValue2\" } }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        String expected = "{\n" +
                "    key1: value1\n" +
                "  - nested: {innerKey1=innerValue1}\n" +
                "  + nested: {innerKey1=innerValue2}\n" +
                "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }

    @Test
    public void testGenerateArrayDifferences() throws Exception {
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        String json1 = "{ \"arrayKey\": [1, 2, 3] }";
        String json2 = "{ \"arrayKey\": [1, 2, 4] }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        String expected = "{\n" +
                "  - arrayKey: [1, 2, 3]\n" +
                "  + arrayKey: [1, 2, 4]\n" +
                "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }

    @Test
    public void testGenerateMixedDifferences() throws Exception {
        Path tempFile1 = Files.createTempFile("temp1", ".json");
        Path tempFile2 = Files.createTempFile("temp2", ".json");

        String json1 = "{ \"setting1\": \"Some value\", \"chars\": [\"a\", \"b\", \"c\"] }";
        String json2 = "{ \"setting1\": \"Another value\", \"chars\": false }";

        Files.write(tempFile1, json1.getBytes(), StandardOpenOption.WRITE);
        Files.write(tempFile2, json2.getBytes(), StandardOpenOption.WRITE);

        String result = Differ.generate(tempFile1.toString(), tempFile2.toString());

        String expected = "{\n" +
                "  - setting1: Some value\n" +
                "  + setting1: Another value\n" +
                "  - chars: [a, b, c]\n" +
                "  + chars: false\n" +
                "}";

        assertEquals(expected, result);

        Files.delete(tempFile1);
        Files.delete(tempFile2);
    }
}*/
