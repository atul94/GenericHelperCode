
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 * @author atul94
 */
public class RestTemplateUtils<T, U> {

  private RestTemplateUtils() {
    /**
     * This is a utility class
     */
  }

  private static <U> String getResponseAsString(RestTemplate restTemplate, HttpEntity<U> entity,
      URI uri) {
    return restTemplate.exchange(uri, HttpMethod.GET, entity, String.class).getBody();

  }

  public static <T> T mapJsonResponseToObject(RestTemplate restTemplate, URI uri, Class<T> type) {
    return restTemplate.getForObject(uri, type);
  }

  public static <T, U> T mapSmallJsonResponseToObject(RestTemplate restTemplate, Gson gson, URI uri,
      HttpEntity<U> entity, Class<T> type) {
    return gson.fromJson(getResponseAsString(restTemplate, entity, uri), type);
  }

  public static <T, U> T mapBigJsonResponseToObject(RestTemplate restTemplate,
      ObjectMapper objectMapper, URI uri,
      HttpEntity<U> entity, Class<T> type) throws IOException {
    return objectMapper.readValue(getResponseAsString(restTemplate, entity, uri), type);
  }

}
