package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Plain {

    public static String format(List<Map<String, Object>> differences) {
        StringBuilder result = new StringBuilder();

        for (Map<String, Object> change : differences) {
            String property = (String) change.get("key");
            String status = (String) change.get("status");
            Object oldValue = change.get("oldValue");
            Object newValue = change.get("newValue");

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
                            .append(formatValue(oldValue)).append(" to ")
                            .append(formatValue(newValue)).append("\n");
                    break;
                default:
                    break;
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
