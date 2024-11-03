package hexlet.code.formatters;

import java.util.Map;
import java.util.TreeMap;

public class Stylish {

    public static String format(Map<String, Object> differences) {
        StringBuilder result = new StringBuilder();
        result.append("{\n");

        Map<String, Object> sortedDifferences = new TreeMap<>(differences);

        for (Map.Entry<String, Object> entry : sortedDifferences.entrySet()) {
            String key = entry.getKey();
            Map<String, Object> change = (Map<String, Object>) entry.getValue();

            switch ((String) change.get("status")) {
                case "unchanged":
                    result.append("    ").append(key).append(": ")
                            .append(formatValue(change.get("value"))).append("\n");
                    break;
                case "updated":
                    result.append("  - ").append(key).append(": ")
                            .append(formatValue(change.get("oldValue"))).append("\n");
                    result.append("  + ").append(key).append(": ")
                            .append(formatValue(change.get("newValue"))).append("\n");
                    break;
                case "removed":
                    result.append("  - ").append(key).append(": ")
                            .append(formatValue(change.get("oldValue"))).append("\n");
                    break;
                case "added":
                    result.append("  + ").append(key).append(": ")
                            .append(formatValue(change.get("newValue"))).append("\n");
                    break;
                default:
                    throw new IllegalArgumentException("Unknown status: " + change.get("status"));
            }
        }

        result.append("}");
        return result.toString();
    }

    private static String formatValue(Object value) {
        if (value instanceof Map) {
            return formatMap((Map<String, Object>) value);
        } else if (value instanceof String) {
            return String.valueOf(value);
        } else {
            return String.valueOf(value);
        }
    }

    private static String formatMap(Map<String, Object> map) {
        StringBuilder result = new StringBuilder("{");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.append(entry.getKey()).append("=").append(formatValue(entry.getValue())).append(", ");
        }
        if (map.size() > 0) {
            result.setLength(result.length() - 2);
        }
        result.append("}");
        return result.toString();
    }
}
