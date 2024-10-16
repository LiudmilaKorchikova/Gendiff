package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Differ {

    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        Map<String, Object> map1 = Parser.parse(filepath1);
        Map<String, Object> map2 = Parser.parse(filepath2);

        Set<String> allKeys = Stream.concat(map1.keySet().stream(), map2.keySet().stream())
                .collect(Collectors.toCollection(TreeSet::new));

        StringBuilder result = new StringBuilder("{\n");

        for (String key : allKeys) {
            boolean keyExistsInFile1 = map1.containsKey(key);
            boolean keyExistsInFile2 = map2.containsKey(key);

            Object value1 = keyExistsInFile1 ? map1.get(key) : null;
            Object value2 = keyExistsInFile2 ? map2.get(key) : null;

            if (keyExistsInFile1 && keyExistsInFile2) {
                if (value1 instanceof Map && value2 instanceof Map) {
                    String nestedDiff = generateNestedDiff(key, (Map<String, Object>) value1, (Map<String, Object>) value2);
                    if (!nestedDiff.isEmpty()) {
                        result.append(nestedDiff);
                    }
                } else if (Objects.equals(value1, value2)) {
                    result.append("    ").append(key).append(": ").append(value1).append("\n");
                } else {
                    result.append("  - ").append(key).append(": ").append(value1).append("\n");
                    result.append("  + ").append(key).append(": ").append(value2).append("\n");
                }
            } else if (keyExistsInFile1) {
                result.append("  - ").append(key).append(": ").append(value1).append("\n");
            } else {
                result.append("  + ").append(key).append(": ").append(value2).append("\n");
            }
        }

        result.append("}");
        return result.toString();
    }

    private static String generateNestedDiff(String key, Map<String, Object> map1, Map<String, Object> map2) {
        StringBuilder nestedResult = new StringBuilder();
        Set<String> allNestedKeys = Stream.concat(map1.keySet().stream(), map2.keySet().stream())
                .collect(Collectors.toCollection(TreeSet::new));

        for (String nestedKey : allNestedKeys) {
            boolean keyExistsInNestedMap1 = map1.containsKey(nestedKey);
            boolean keyExistsInNestedMap2 = map2.containsKey(nestedKey);

            Object value1 = keyExistsInNestedMap1 ? map1.get(nestedKey) : null;
            Object value2 = keyExistsInNestedMap2 ? map2.get(nestedKey) : null;

            if (keyExistsInNestedMap1 && keyExistsInNestedMap2) {
                if (value1 instanceof Map && value2 instanceof Map) {
                    String innerDiff = generateNestedDiff(key + "." + nestedKey, (Map<String, Object>) value1, (Map<String, Object>) value2);
                    if (!innerDiff.isEmpty()) {
                        nestedResult.append(innerDiff);
                    }
                } else if (Objects.equals(value1, value2)) {
                    nestedResult.append("    ").append(key).append(".").append(nestedKey).append(": ").append(value1).append("\n");
                } else {
                    nestedResult.append("  - ").append(key).append(".").append(nestedKey).append(": ").append(value1).append("\n");
                    nestedResult.append("  + ").append(key).append(".").append(nestedKey).append(": ").append(value2).append("\n");
                }
            } else if (keyExistsInNestedMap1) {
                nestedResult.append("  - ").append(key).append(".").append(nestedKey).append(": ").append(value1).append("\n");
            } else {
                nestedResult.append("  + ").append(key).append(".").append(nestedKey).append(": ").append(value2).append("\n");
            }
        }

        return nestedResult.toString();
    }
}
