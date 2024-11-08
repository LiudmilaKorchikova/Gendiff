import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import hexlet.code.Differ;
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
        stylishExpected = readFile("src/test/resources/stylishExpected.txt").replace("\r\n", "\n");
        plainExpected = readFile("src/test/resources/plainExpected.txt").replace("\r\n", "\n");
        jsonExpected = readFile("src/test/resources/jsonExpected.json").replaceAll("\\s+", "").replace("\r\n", "\n");
    }

    @Test
    public void testGenerate() throws Exception {
        String file1Path = "src/test/resources/file1.json";
        String file2Path = "src/test/resources/file2.json";

        String actual = Differ.generate(file1Path, file2Path).replace("\r\n", "\n");

        assertEquals(stylishExpected, actual);
    }

    @Test
    public void testGenerateStylishFormat() throws Exception {
        String file1Path = "src/test/resources/file1.json";
        String file2Path = "src/test/resources/file2.json";

        String actual = Differ.generate(file1Path, file2Path, "stylish");

        assertEquals(stylishExpected, actual);
    }

    @Test
    public void testGeneratePlainFormat() throws Exception {
        String file1Path = "src/test/resources/file1.json";
        String file2Path = "src/test/resources/file2.json";

        String actual = Differ.generate(file1Path, file2Path, "plain").replace("\r\n", "\n");

        assertEquals(plainExpected, actual);
    }

    @Test
    public void testGenerateJsonFormat() throws Exception {
        String file1Path = "src/test/resources/file1.json";
        String file2Path = "src/test/resources/file2.json";

        String actual = Differ.generate(file1Path, file2Path, "json").replaceAll("\\s+", "")
                .replaceAll(" ", "").replace("\r\n", "\n");

        assertEquals(jsonExpected, actual);
    }

    @Test
    public void testYamlGenerate() throws Exception {
        String file1Path = "src/test/resources/file1.yaml";
        String file2Path = "src/test/resources/file2.yaml";

        String actual = Differ.generate(file1Path, file2Path);

        assertEquals(stylishExpected, actual);
    }

    @Test
    public void testYamlGenerateStylishFormat() throws Exception {
        String file1Path = "src/test/resources/file1.yaml";
        String file2Path = "src/test/resources/file2.yaml";

        String actual = Differ.generate(file1Path, file2Path, "stylish");

        assertEquals(stylishExpected, actual);
    }

    @Test
    public void testYamlGeneratePlainFormat() throws Exception {
        String file1Path = "src/test/resources/file1.yaml";
        String file2Path = "src/test/resources/file2.yaml";

        String actual = Differ.generate(file1Path, file2Path, "plain").replace("\r\n", "\n");

        assertEquals(plainExpected, actual);
    }

    @Test
    public void testYamlGenerateJsonFormat() throws Exception {
        String file1Path = "src/test/resources/file1.yaml";
        String file2Path = "src/test/resources/file2.yaml";

        String actual = Differ.generate(file1Path, file2Path, "json").replaceAll("\\s+", "").replace("\r\n", "\n");

        assertEquals(jsonExpected, actual);
    }
}
