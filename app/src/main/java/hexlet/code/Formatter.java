package hexlet.code;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Stylish;
import hexlet.code.formatters.Plain;

import java.util.List;
import java.util.Map;

public class Formatter {

    public static String format(List<Map<String, Object>> differences, String format) throws Exception {
        return switch (format) {
            case "plain" -> Plain.format(differences);
            case "stylish" -> Stylish.format(differences);
            case "json" -> Json.format(differences);
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
    }
}
