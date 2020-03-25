package com.dadazhishi.zheng.configuration;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class copy from project 'owner'
 */
public class PlaceHolder {

  private static final Pattern PATTERN = Pattern.compile("\\$\\{(.+?)\\}");
  private final Map<String, String> values;

  /**
   * Creates a new instance and initializes it. Uses defaults for variable prefix and suffix and the
   * escaping character.
   *
   * @param values the variables' values, may be null
   */
  PlaceHolder(Map<String, String> values) {
    this.values = values;
  }

  /**
   * Replaces all the occurrences of variables with their matching values from the resolver using
   * the given source string as a template.
   *
   * @param source the string to replace in, null returns null
   * @return the result of the replace operation
   */
  String replace(String source) {
    if (source == null) {
      return null;
    }
    Matcher m = PATTERN.matcher(source);
    StringBuffer sb = new StringBuffer();
    while (m.find()) {
      String var = m.group(1);
      String value = values.get(var);
      String replacement = (value != null) ? replace(value) : "";
      m.appendReplacement(sb, Matcher.quoteReplacement(replacement));
    }
    m.appendTail(sb);
    return sb.toString();
  }

  /**
   * Returns a string modified in according to supplied source and arguments.<br/> If the source
   * string has pattern-replacement content like {@code "a.${var}.b"}, the pattern is replaced
   * property value of "var".<br/> Otherwise the return string is formatted by source and arguments
   * as with {@link String#format(String, Object...)}
   *
   * @param source A source formatting format string. {@code null} returns {@code null}
   * @param args Arguments referenced by the format specifiers in the source string.
   * @return formatted string
   */
  String replace(String source, Object... args) {
    if (source == null) {
      return null;
    }
    Matcher m = PATTERN.matcher(source);
    return m.find() ? replace(source) : String.format(source, args);
  }

}
