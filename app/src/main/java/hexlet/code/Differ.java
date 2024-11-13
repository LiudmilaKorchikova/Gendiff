package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.List;

import static hexlet.code.Differences.calculateDifferences;

public class Differ {
    public static String[] readFile(String path) throws Exception {
        String[] file = new String[2];
        file[0] = new String(Files.readAllBytes(Paths.get(path)));
        file[1] = new String(getFormat(path));
        return file;
    }

    private static String getFormat(String path) throws Exception {
        if (path.endsWith(".json")) {
            return ".json";
        } else if (path.endsWith(".yaml") || path.endsWith(".yml")) {
            return ".yaml";
        }
        throw new Exception("Unsupported input format");
    }

    public static String generate(String filepath1, String filepath2, String outputFormat)
            throws Exception {
        Map<String, Object> map1 = Parser.parse(readFile(filepath1));
        Map<String, Object> map2 = Parser.parse(readFile(filepath2));

        List<Map<String, Object>> differences = calculateDifferences(map1, map2);

        return Formatter.format(differences, outputFormat);
    }
    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }
}
