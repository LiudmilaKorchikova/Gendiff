package hexlet.code.formatters;

import java.util.Map;

public class Plain {

    public static String format(Map<String, Object> differences) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, Object> entry : differences.entrySet()) {
            String property = entry.getKey();
            Object change = entry.getValue();

            if (change instanceof Map) {
                Map<String, String> diffDetails = (Map<String, String>) change;
                String status = diffDetails.get("status");
                Object oldValue = diffDetails.get("oldValue");
                Object newValue = diffDetails.get("newValue");

                switch (status) {
                    case "added":
                        result.append("Property '").append(property).append("' was added with value: ")
                                .append(formatValue(newValue)).append("\n");
                        break;
                    case "removed":
                        result.append("Property '").append(property).append("' was removed").append("\n");
                        break;
                    case "updated":
                        result.append("Property '").append(property).append("' was updated. From ")
                                .append(formatValue(oldValue)).append(" to ").append(formatValue(newValue)).append("\n");
                        break;
                    default:
                        break;
                }
            }
        }

        return result.toString().trim();
    }

    private static String formatValue(Object value) {
        if (value instanceof Map || value instanceof Iterable) {
            return "[complex value]";
        } else if (value instanceof String) {
            return "'" + value + "'";
        } else if (value == null) {
            return "null";
        }
        return value.toString();
    }
}
