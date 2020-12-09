package experiment.analyzer;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ClassInfoTest {
  @Test
  public void testEquals() {
    var expected = new ClassInfo("class info", true, "repository", "file name");
    // fails
    assertNotEquals(expected, null);
    assertNotEquals(expected, new String("classInfo"));
    assertNotEquals(expected, new ClassInfo("klass info", true, "repository", "file name"));
    assertNotEquals(expected, new ClassInfo("class info", false, "repository", "file name"));
    assertNotEquals(expected, new ClassInfo("class info", true, "repositorys", "file name"));
    assertNotEquals(expected, new ClassInfo("class info", true, "repository", "fileName"));
    // pass
    assertEquals(expected, new ClassInfo("class info", true, "repository", "file name"));
    assertEquals(expected, expected);
  }

  @Test
  public void toMap() {
    var map = new ClassInfo("class name", true, "repository", "file name").toMap();
    assertThat(map, hasEntry("name", "class name"));
    assertThat(map, hasEntry("isPublic", "true"));
    assertThat(map, hasEntry("repository", "repository"));
    assertThat(map, hasEntry("fileName", "file name"));
  }
}
