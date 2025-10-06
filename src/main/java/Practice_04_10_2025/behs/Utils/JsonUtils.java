package Practice_04_10_2025.behs.Utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> String toJson(T object) {
        try {
            return mapper.writeValueAsString(object);
        }catch (Exception e) {
            log.error("Error converting object to json string", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> Optional<T> fromJson(String json, Class<T> clazz) {
        try {
            return Optional.of(mapper.readValue(json, clazz));
        }catch (Exception e) {
            log.error("Error converting json string to object", e);
            return Optional.empty();
        }
    }

}
