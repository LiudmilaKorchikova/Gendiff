package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Differ {

    public static String generate(String filepath1, String filepath2) throws Exception {
        String content1 = new String(Files.readAllBytes(Paths.get(filepath1)));
        String content2 = new String(Files.readAllBytes(Paths.get(filepath2)));

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map1 = objectMapper.readValue(content1, Map.class);
        Map<String, Object> map2 = objectMapper.readValue(content2, Map.class);

        Set<String> allKeys = Stream.concat(map1.keySet().stream(), map2.keySet().stream())
                .collect(Collectors.toCollection(TreeSet::new));

        StringBuilder result = new StringBuilder("{\n");

        for (String key : allKeys) {
            boolean keyExistsInFile1 = map1.containsKey(key);
            boolean keyExistsInFile2 = map2.containsKey(key);

            if (keyExistsInFile1 && keyExistsInFile2) {
                Object value1 = map1.get(key);
                Object value2 = map2.get(key);

                if (value1.equals(value2)) {
                    result.append("    ").append(key).append(": ").append(value1).append("\n");
                } else {
                    result.append("  - ").append(key).append(": ").append(value1).append("\n");
                    result.append("  + ").append(key).append(": ").append(value2).append("\n");
                }
            } else if (keyExistsInFile1) {
                result.append("  - ").append(key).append(": ").append(map1.get(key)).append("\n");
            } else {
                result.append("  + ").append(key).append(": ").append(map2.get(key)).append("\n");
            }
        }

        result.append("}");
        return result.toString();
    }
}
