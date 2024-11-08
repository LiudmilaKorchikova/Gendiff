package hexlet.code;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Objects;

public class Differences {
    static List<Map<String, Object>> calculateDifferences(Map<String, Object> map1, Map<String, Object> map2) {
        Set<String> allKeys = Stream.concat(map1.keySet().stream(), map2.keySet().stream())
                .collect(Collectors.toCollection(TreeSet::new));

        List<Map<String, Object>> differences = new ArrayList<>();

        for (String key : allKeys) {
            boolean keyExistsInFile1 = map1.containsKey(key);
            boolean keyExistsInFile2 = map2.containsKey(key);

            Object value1 = keyExistsInFile1 ? map1.get(key) : null;
            Object value2 = keyExistsInFile2 ? map2.get(key) : null;

            Map<String, Object> change = new HashMap<>();
            change.put("key", key);

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

            differences.add(change);
        }

        return differences;
    }
}
