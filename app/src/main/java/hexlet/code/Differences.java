package hexlet.code;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Objects;

public class Differences {
    static List<Map<String, Object>> calculateDifferences(Map<String, Object> map1, Map<String, Object> map2) {
        Set<String> allKeys = new TreeSet<>(map1.keySet());
        allKeys.addAll(map2.keySet());

        List<Map<String, Object>> differences = new ArrayList<>();

        for (String key : allKeys) {
            Map<String, Object> change = new HashMap<>();
            change.put("key", key);

            boolean keyExistsInFile1 = map1.containsKey(key);
            boolean keyExistsInFile2 = map2.containsKey(key);

            if (keyExistsInFile1 && !keyExistsInFile2) {
                change.put("status", "removed");
                change.put("oldValue", map1.get(key));
                differences.add(change);
                continue;
            }

            if (!keyExistsInFile1 && keyExistsInFile2) {
                change.put("status", "added");
                change.put("newValue", map2.get(key));
                differences.add(change);
                continue;
            }

            Object value1 = map1.get(key);
            Object value2 = map2.get(key);

            if (Objects.equals(value1, value2)) {
                change.put("status", "unchanged");
                change.put("value", value1);
            } else {
                change.put("status", "updated");
                change.put("oldValue", value1);
                change.put("newValue", value2);
            }

            differences.add(change);
        }

        return differences;
    }
}
