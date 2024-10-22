package hexlet.code;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Differ {

    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        Map<String, Object> map1 = Parser.parse(filepath1);
        Map<String, Object> map2 = Parser.parse(filepath2);

        Map<String, Object> differences = calculateDifferences(map1, map2);

        return Formatter.format(differences, format);
    }
    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "Stylish");
    }

    private static Map<String, Object> calculateDifferences(Map<String, Object> map1, Map<String, Object> map2) {
        Set<String> allKeys = Stream.concat(map1.keySet().stream(), map2.keySet().stream())
                .collect(Collectors.toCollection(TreeSet::new));

        Map<String, Object> differences = new LinkedHashMap<>();

        for (String key : allKeys) {
            boolean keyExistsInFile1 = map1.containsKey(key);
            boolean keyExistsInFile2 = map2.containsKey(key);

            Object value1 = keyExistsInFile1 ? map1.get(key) : null;
            Object value2 = keyExistsInFile2 ? map2.get(key) : null;

            Map<String, Object> change = new HashMap<>();

            if (keyExistsInFile1 && keyExistsInFile2) {
                if (Objects.equals(value1, value2)) {
                    change.put("status", "unchanged");
                    change.put("value", value1);
                } else {
                    change.put("status", "updated");
                    change.put("oldValue", value1);
                    change.put("newValue", value2);
                }
            } else if (keyExistsInFile1) {
                change.put("status", "removed");
                change.put("oldValue", value1);
            } else {
                change.put("status", "added");
                change.put("newValue", value2);
            }

            differences.put(key, change);
        }

        return differences;
    }
}
