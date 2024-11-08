package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.List;

import static hexlet.code.Differences.calculateDifferences;

public class Differ {

    static String readFile(String path) throws Exception {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        Map<String, Object> map1 = Parser.parse(readFile(filepath1));
        Map<String, Object> map2 = Parser.parse(readFile(filepath2));

        List<Map<String, Object>> differences = calculateDifferences(map1, map2);

        return Formatter.format(differences, format);
    }
    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "Stylish");
    }
}
