package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.Map;

public class Parser {

    public static Map<String, Object> parse(String file) throws Exception {

        ObjectMapper objectMapper;
        if (file.startsWith("{") || file.startsWith("[")) {
            objectMapper = new ObjectMapper();
        } else {
            objectMapper = new ObjectMapper(new YAMLFactory());
        }

        return objectMapper.readValue(file, Map.class);
    }
}
