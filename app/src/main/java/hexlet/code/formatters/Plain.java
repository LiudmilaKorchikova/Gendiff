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
                    result.append(String.format("Property '%s' was added with value: %s%n",
                            property, formatValue(newValue)));
                    break;
                case "removed":
                    result.append(String.format("Property '%s' was removed%n", property));
                    break;
                case "updated":
                    result.append(String.format("Property '%s' was updated. From %s to %s%n",
                            property, formatValue(oldValue), formatValue(newValue)));
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
