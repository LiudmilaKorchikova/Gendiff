import org.junit.jupiter.api.BeforeAll;
import hexlet.code.Differ;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class DifferTest {

    private static String stylishExpected;
    private static String plainExpected;
    private static String jsonExpected;

    private static String readFile(String path) throws Exception {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    private static String getExpectedResult(String expected) throws Exception {
        switch (expected) {
            case "stylishExpected":
                return stylishExpected;
            case "plainExpected":
                return plainExpected;
            case "jsonExpected":
                return jsonExpected;
            default:
                throw new IllegalArgumentException("Unknown expected result: " + expected);
        }
    }

    @BeforeAll
    public static void setUp() throws Exception {
        stylishExpected = readFile("src/test/resources/stylishExpected.txt");
        plainExpected = readFile("src/test/resources/plainExpected.txt");
        jsonExpected = readFile("src/test/resources/jsonExpected.json");
    }

    @ParameterizedTest
    @CsvSource({
        "src/test/resources/file1.json, src/test/resources/file2.json, stylishExpected",
        "src/test/resources/file1.yaml, src/test/resources/file2.yaml, stylishExpected",
    })
    void testGenerateWithoutOutputFormat(String file1, String file2, String expected) throws Exception {
        String actual = Differ.generate(file1, file2);
        String expectedResult = getExpectedResult(expected);
        assertEquals(expectedResult, actual);
    }

    @ParameterizedTest
    @CsvSource({
        "src/test/resources/file1.json, src/test/resources/file2.json, stylish, stylishExpected",
        "src/test/resources/file1.json, src/test/resources/file2.json, plain, plainExpected",
        "src/test/resources/file1.json, src/test/resources/file2.json, json, jsonExpected",
        "src/test/resources/file1.yaml, src/test/resources/file2.yaml, stylish, stylishExpected",
        "src/test/resources/file1.yaml, src/test/resources/file2.yaml, plain, plainExpected",
        "src/test/resources/file1.yaml, src/test/resources/file2.yaml, json, jsonExpected"
    })
    void testGenerateWithOutputFormat(String file1, String file2, String format, String expected) throws Exception {
        String actual = Differ.generate(file1, file2, format);
        String expectedResult = getExpectedResult(expected);
        assertEquals(expectedResult, actual);
    }
}
