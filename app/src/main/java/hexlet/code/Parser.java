package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.Map;

public class Parser {

    public static Map<String, Object> parse(String[] file) throws Exception {
        ObjectMapper objectMapper;
        String fileContent = file[0];
        String format = file[1];

        switch (format) {
            case ".json":
                objectMapper = new ObjectMapper();
                break;
            case ".yaml":
                objectMapper = new ObjectMapper(new YAMLFactory());
                break;
            default:
                throw new IllegalArgumentException("Unsupported format");
        }

        return objectMapper.readValue(fileContent, Map.class);
    }
}
