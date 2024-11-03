package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.formatters.Json;
import hexlet.code.formatters.Stylish;
import hexlet.code.formatters.Plain;

import java.util.Map;

public class Formatter {

    public static String format(Map<String, Object> differences, String format) throws JsonProcessingException {
        return switch (format) {
            case "plain" -> Plain.format(differences);
            case "stylish" -> Stylish.format(differences);
            case "json" -> Json.format(differences);
            default -> Stylish.format(differences);
        };
    }
}
