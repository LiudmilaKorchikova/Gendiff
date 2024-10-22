package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Parser {

    public static Map<String, Object> parse(String filepath) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(filepath)));

        ObjectMapper objectMapper;
        if (filepath.endsWith(".yml") || filepath.endsWith(".yaml")) {
            objectMapper = new ObjectMapper(new YAMLFactory());
        } else if (filepath.endsWith(".json")) {
            objectMapper = new ObjectMapper();
        } else {
            throw new Exception("Unsupported file format: " + filepath);
        }

        return objectMapper.readValue(content, Map.class);
    }
}
