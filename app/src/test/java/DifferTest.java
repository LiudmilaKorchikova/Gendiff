import org.junit.jupiter.api.Test;
import hexlet.code.Differ;
import java.nio.file.Files;
import java.nio.file.Path;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class DifferTest {

    @Test
    public void testGenerateStylishFormat() throws Exception {
        String file1Path = "src/test/resources/file1.json";
        String file2Path = "src/test/resources/file2.json";
        String expectedFilePath = "src/test/resources/stylishExpected.txt";

        String result = Differ.generate(file1Path, file2Path, "stylish");

        String expected = Files.readString(Path.of(expectedFilePath)).trim();

        String normalizedExpected = expected.replaceAll("\\s+", " ");
        String normalizedResult = result.replaceAll("\\s+", " ");

        assertEquals(normalizedExpected, normalizedResult);
    }

    @Test
    public void testGeneratePlainFormat() throws Exception {
        String file1Path = "src/test/resources/file1.json";
        String file2Path = "src/test/resources/file2.json";
        String expectedFilePath = "src/test/resources/plainExpected.txt";

        String result = Differ.generate(file1Path, file2Path, "plain");

        String expected = Files.readString(Path.of(expectedFilePath)).trim();

        String normalizedExpected = expected.replaceAll("\\s+", " ");
        String normalizedResult = result.replaceAll("\\s+", " ");

        assertEquals(normalizedExpected, normalizedResult);
    }

    public void testGenerateJsonFormat() throws Exception {
        String file1Path = "src/test/resources/file1.json";
        String file2Path = "src/test/resources/file2.json";
        String expectedFilePath = "src/test/resources/jsonExpected.json";
        ObjectMapper mapper = new ObjectMapper();

        JsonNode result;
        result = mapper.readTree(Differ.generate(file1Path, file2Path, "json").trim());

        JsonNode expected;
        expected = mapper.readTree(Files.readString(Path.of((expectedFilePath))).trim());
        assertEquals(result, expected);
    }

    public void testYamlGenerateStylishFormat() throws Exception {
        String file1Path = "src/test/resources/file1.yaml";
        String file2Path = "src/test/resources/file2.yaml";
        String expectedFilePath = "src/test/resources/stylishExpected.txt";

        String result = Differ.generate(file1Path, file2Path, "stylish");

        String expected = Files.readString(Path.of(expectedFilePath)).trim();

        String normalizedExpected = expected.replaceAll("\\s+", " ");
        String normalizedResult = result.replaceAll("\\s+", " ");

        assertEquals(normalizedExpected, normalizedResult);
    }

    @Test
    public void testYamlGeneratePlainFormat() throws Exception {
        String file1Path = "src/test/resources/file1.yaml";
        String file2Path = "src/test/resources/file2.yaml";
        String expectedFilePath = "src/test/resources/plainExpected.txt";

        String result = Differ.generate(file1Path, file2Path, "plain");

        String expected = Files.readString(Path.of(expectedFilePath)).trim();

        String normalizedExpected = expected.replaceAll("\\s+", " ");
        String normalizedResult = result.replaceAll("\\s+", " ");

        assertEquals(normalizedExpected, normalizedResult);
    }

    public void testYamlGenerateJsonFormat() throws Exception {
        String file1Path = "src/test/resources/file1.yaml";
        String file2Path = "src/test/resources/file2.yaml";
        String expectedFilePath = "src/test/resources/jsonExpected.json";
        ObjectMapper mapper = new ObjectMapper();

        JsonNode result;
        result = mapper.readTree(Differ.generate(file1Path, file2Path, "json").trim());

        JsonNode expected;
        expected = mapper.readTree(Files.readString(Path.of((expectedFilePath))).trim());
        assertEquals(result, expected);
    }
}
