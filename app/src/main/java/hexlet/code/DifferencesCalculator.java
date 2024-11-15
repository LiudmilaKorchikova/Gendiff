package hexlet.code;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Objects;

public class DifferencesCalculator {
    static List<Map<String, Object>> calculateDifferences(Map<String, Object> map1, Map<String, Object> map2) {
        Set<String> allKeys = new TreeSet<>(map1.keySet());
        allKeys.addAll(map2.keySet());

        List<Map<String, Object>> differences = new ArrayList<>();

        for (String key : allKeys) {
            Map<String, Object> change = new HashMap<>();
            change.put(DiffConstants.KEY, key);

            Object value1 = map1.get(key);
            Object value2 = map2.get(key);

            if (!map1.containsKey(key)) {
                change.put(DiffConstants.STATUS, DiffConstants.STATUS_ADDED);
                change.put(DiffConstants.NEW_VALUE, value2);
            } else if (!map2.containsKey(key)) {
                change.put(DiffConstants.STATUS, DiffConstants.STATUS_REMOVED);
                change.put(DiffConstants.OLD_VALUE, value1);
            } else if (Objects.equals(value1, value2)) {
                change.put(DiffConstants.STATUS, DiffConstants.STATUS_UNCHANGED);
                change.put(DiffConstants.VALUE, value1);
            } else {
                change.put(DiffConstants.STATUS, DiffConstants.STATUS_UPDATED);
                change.put(DiffConstants.OLD_VALUE, value1);
                change.put(DiffConstants.NEW_VALUE, value2);
            }

            differences.add(change);
        }

        return differences;
    }
}
