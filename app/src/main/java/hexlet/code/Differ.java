package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.List;

import static hexlet.code.DifferencesCalculator.calculateDifferences;

public class Differ {
    public static String readFile(String path) throws Exception {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    private static String getFormat(String path) throws Exception {
        String[] parts = path.split("\\.");

        String extension = parts[parts.length - 1];

        switch (extension.toLowerCase()) {
            case "json":
                return ".json";
            case "yaml":
            case "yml":
                return ".yaml";
            default:
                throw new Exception("Unsupported input format: " + extension);
        }
    }

    public static String generate(String filepath1, String filepath2, String outputFormat)
            throws Exception {
        Map<String, Object> map1 = Parser.parse(readFile(filepath1), getFormat(filepath1));
        Map<String, Object> map2 = Parser.parse(readFile(filepath2), getFormat(filepath2));

        List<Map<String, Object>> differences = calculateDifferences(map1, map2);

        return Formatter.format(differences, outputFormat);
    }
    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }
}
