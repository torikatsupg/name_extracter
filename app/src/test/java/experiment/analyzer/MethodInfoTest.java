package experiment.analyzer;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class MethodInfoTest {
  final private String repository = "repository";
  final private String fileName = "file name";

  @Test
  public void equals() {
    final var expected = new MethodInfo("method name", true, "String", repository, fileName);
    // fail
    assertNotEquals(expected, null);
    assertNotEquals(expected, new String("classInfo"));
    assertNotEquals(expected, new MethodInfo("method info", true, "String", repository, fileName));
    assertNotEquals(expected, new MethodInfo("method name", false, "String", repository, fileName));
    assertNotEquals(expected, new MethodInfo("method name", true, "Int", repository, fileName));
    assertNotEquals(expected, new MethodInfo("method name", true, "String", "repositories", fileName));
    assertNotEquals(expected, new MethodInfo("method name", true, "String", repository, "fileName"));
    // pass
    assertEquals(expected, new MethodInfo("method name", true, "String", repository, fileName));
    assertEquals(expected, expected);
  }

  @Test
  public void toMap() {
    var map = new MethodInfo("method name", true, "return type", repository, fileName).toMap();
    assertThat(map, hasEntry("name", "method name"));
    assertThat(map, hasEntry("isPublic", "true"));
    assertThat(map, hasEntry("returnType", "return type"));
    assertThat(map, hasEntry("repository", repository));
    assertThat(map, hasEntry("fileName", fileName));
  }
}
