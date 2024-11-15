import org.junit.jupiter.api.BeforeAll;
import hexlet.code.Differ;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    @BeforeAll
    public static void setUp() throws Exception {
        stylishExpected = readFile("src/test/resources/stylishExpected.txt");
        plainExpected = readFile("src/test/resources/plainExpected.txt");
        jsonExpected = readFile("src/test/resources/jsonExpected.json");
    }


    @ParameterizedTest
    @ValueSource(strings = {"json", "yaml"})
    void testGenerateWithDefaultFormat(String extension) throws Exception {
        String file1 = "src/test/resources/file1." + extension;
        String file2 = "src/test/resources/file2." + extension;

        String actual = Differ.generate(file1, file2);
        assertEquals(stylishExpected, actual);
    }

    @ParameterizedTest
    @CsvSource({
        "json, stylish, stylishExpected",
        "json, plain, plainExpected",
        "json, json, jsonExpected",
        "yaml, stylish, stylishExpected",
        "yaml, plain, plainExpected",
        "yaml, json, jsonExpected"
    })
    void testGenerateWithSpecificFormat(String extension, String format, String expected) throws Exception {
        String file1 = "src/test/resources/file1." + extension;
        String file2 = "src/test/resources/file2." + extension;

        String actual = Differ.generate(file1, file2, format);
        String expectedResult = switch (expected) {
            case "stylishExpected" -> stylishExpected;
            case "plainExpected" -> plainExpected;
            case "jsonExpected" -> jsonExpected;
            default -> throw new IllegalArgumentException("Unknown expected result: " + expected);
        };

        assertEquals(expectedResult, actual);
    }
}
