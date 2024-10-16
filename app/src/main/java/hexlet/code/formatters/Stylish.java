package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Stylish {

    public static String format(Map<String, Object> differences) {
        StringBuilder result = new StringBuilder("{\n");
        formatNode(differences, result, 1);
        result.append("}");
        return result.toString();
    }

    private static void formatNode(Map<String, Object> node, StringBuilder result, int depth) {
        for (Map.Entry<String, Object> entry : node.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                result.append(getIndent(depth)).append(key).append(": {\n");
                formatNode((Map<String, Object>) value, result, depth + 1);
                result.append(getIndent(depth)).append("}\n");
            } else {
                if (value instanceof String) {
                    result.append(getIndent(depth)).append(key).append(": ").append(value).append("\n");
                } else if (value instanceof List) {
                    result.append(getIndent(depth)).append(key).append(": ").append(value).append("\n");
                } else {
                    result.append(getIndent(depth)).append(key).append(": ").append(value).append("\n");
                }
            }
        }
    }

    private static String getIndent(int depth) {
        return "  ".repeat(depth);
    }
}
