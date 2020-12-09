package experiment.analyzer;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class VariableInfoTest {
  final private String fileName = "file name";
  final private String repository = "repository";

  @Test
  public void equals() {
    final var expected = new VariableInfo("variable name", true, "String", true, repository, fileName);
    // fail
    assertNotEquals(expected, null);
    assertNotEquals(expected, new String("variable info"));
    assertNotEquals(expected, new VariableInfo("variable info", true, "String", true, repository, fileName));
    assertNotEquals(expected, new VariableInfo("variable name", false, "String", true, repository, fileName));
    assertNotEquals(expected, new VariableInfo("variable name", true, "Int", true, repository, fileName));
    assertNotEquals(expected, new VariableInfo("variable name", true, "String", false, repository, fileName));
    assertNotEquals(expected, new VariableInfo("variable name", true, "String", true, "repositories", fileName));
    assertNotEquals(expected, new VariableInfo("variable name", true, "String", true, repository, "fileName"));
    // pass
    assertEquals(expected, new VariableInfo("variable name", true, "String", true, repository, fileName));
    assertEquals(expected, expected);
  }

  @Test
  public void toMap() {
    var map = new VariableInfo("variable name", true, "type", true, repository, fileName).toMap();
    assertThat(map, hasEntry("name", "variable name"));
    assertThat(map, hasEntry("type", "type"));
    assertThat(map, hasEntry("isPublic", "true"));
    assertThat(map, hasEntry("isFieldValue", "true"));
    assertThat(map, hasEntry("repository", repository));
    assertThat(map, hasEntry("fileName", fileName));
  }
}
