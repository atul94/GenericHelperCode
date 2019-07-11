import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class CommonStringUtils {

  private static final String DEFAULT_ENCODING = "UTF-8";
  private static final String NON_ALPHA_NUMERIC_CHARACTERS_LEAVING_SPACE_REGEX = "[^a-zA-Z0-9\\s]";
  private SearchStringUtils() {
    /**
     * This is a Utility Class
     */
  }

  /**
   * @param encodedString Encoded String in UTF-8 format
   * @return decoded or empty string(in case of error)
   */
  public static String getDecodedString(String encodedString) {
    try {
      String decodedString = StringEscapeUtils.unescapeHtml4(
          URLDecoder.decode(encodedString, CommonConstants.DEFAULT_ENCODING));
      log.debug("[{}] is decoded to [{}]", encodedString, decodedString);
      return decodedString;
    } catch (UnsupportedEncodingException e) {
      log.error("Error while decoding string", e);
      return "";
    }
  }

  /**
   * @param anyString any string.
   * @return String where diactrictices are converted to normal character.
   */
  public static String getStrippedAccentString(String anyString) {
    return StringUtils.stripAccents(anyString);
  }

  /**
   * @param anyString any string
   * @return get string with alpha numeric characters and space
   */
  public static String getAlphanumericString(String anyString) {
    String stringStrippedOfSpecialCharacter = anyString
        .replaceAll(NON_ALPHA_NUMERIC_CHARACTERS_LEAVING_SPACE_REGEX, "");
    log.debug("The string [{}] became [{}]", anyString, stringStrippedOfSpecialCharacter);
    return stringStrippedOfSpecialCharacter;
  }

  /**
   * @param anyString any string
   * @return converts entire string to lower case.
   */
  public static String getLowerCaseString(String anyString) {
    return anyString.toLowerCase();
  }

  /**
   * @param anyString any string
   * @return removes leading and trailing white spaces and extra white spaces in b/w words.
   */
  public static String getTrimString(String anyString) {
    return anyString.trim().replaceAll(" +", " ");
  }

  public static String processSearchTerm(String searchTerm) {
    String processedSearchTerm = getLowerCaseString(getTrimString(getAlphanumericString(
        getStrippedAccentString(getDecodedString(searchTerm)))));
    log.debug("[{}] is processed to [{}]", searchTerm, processedSearchTerm);
    return processedSearchTerm;
  }

}
