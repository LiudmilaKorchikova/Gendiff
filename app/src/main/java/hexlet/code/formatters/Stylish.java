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
                            .append(change.get("value")).append("\n");
                    break;
                case "updated":
                    result.append("  - ").append(key).append(": ")
                            .append(oldValue).append("\n");
                    result.append("  + ").append(key).append(": ")
                            .append(newValue).append("\n");
                    break;
                case "removed":
                    result.append("  - ").append(key).append(": ")
                            .append(oldValue).append("\n");
                    break;
                case "added":
                    result.append("  + ").append(key).append(": ")
                            .append(newValue).append("\n");
                    break;
                default:
                    throw new IllegalArgumentException("Unknown status: " + status);
            }
        }

        result.append("}");
        return result.toString();
    }
}
