package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Stylish {

    public static String format(List<Map<String, Object>> differences) {
        StringBuilder result = new StringBuilder();
        result.append("{\n");

        for (Map<String, Object> change : differences) {
            String key = (String) change.get("key");
            String status = (String) change.get("status");
            Object oldValue = change.get("oldValue");
            Object newValue = change.get("newValue");

            switch (status) {
                case "unchanged":
                    result.append("    ").append(key).append(": ")
                            .append(formatValue(change.get("value"))).append("\n");
                    break;
                case "updated":
                    result.append("  - ").append(key).append(": ")
                            .append(formatValue(oldValue)).append("\n");
                    result.append("  + ").append(key).append(": ")
                            .append(formatValue(newValue)).append("\n");
                    break;
                case "removed":
                    result.append("  - ").append(key).append(": ")
                            .append(formatValue(oldValue)).append("\n");
                    break;
                case "added":
                    result.append("  + ").append(key).append(": ")
                            .append(formatValue(newValue)).append("\n");
                    break;
                default:
                    throw new IllegalArgumentException("Unknown status: " + status);
            }
        }

        result.append("}");
        return result.toString();
    }

    private static String formatValue(Object value) {
        if (value instanceof Map) {
            return formatMap((Map<String, Object>) value);
        } else if (value instanceof String) {
            return "'" + value + "'";
        } else if (value == null) {
            return "null";
        }
        return value.toString();
    }

    private static String formatMap(Map<String, Object> map) {
        StringBuilder result = new StringBuilder("{");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            result.append(entry.getKey()).append(": ").append(formatValue(entry.getValue())).append(", ");
        }
        if (map.size() > 0) {
            result.setLength(result.length() - 2);
        }
        result.append("}");
        return result.toString();
    }
}
