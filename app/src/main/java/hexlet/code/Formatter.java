package hexlet.code;

import hexlet.code.formatters.Stylish;
import hexlet.code.formatters.Plain;

import java.util.Map;

public class Formatter {

    public static String format(Map<String, Object> differences, String format) {
        return switch (format) {
            case "plain" -> Plain.format(differences);
            case "stylish" -> Stylish.format(differences);
            default -> throw new IllegalArgumentException("Unknown format: " + format);
        };
    }
}
