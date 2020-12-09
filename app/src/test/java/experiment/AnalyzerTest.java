package experiment;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;
import experiment.analyzer.*;

public class AnalyzerTest {
  private final File file;
  private final String repository = "repository";
  private final String fileName = "file name";

  public AnalyzerTest() {
   this.file = new File("/Users/toriikatsuya/Desktop/experiment/experiment/nameExtracter/app/src/test/java/experiment/analyzer/Sample.java");
  }

  @Test // クラス名抽出テスト
  public void extractClass() throws FileNotFoundException {
    // 期待される戻り値の生成
    final var analyzer = new ClassInfo("Sample", true, repository, fileName);
    final var info = new ClassInfo("Test", false, repository, fileName);
    // 実際の結果
    final var result = new Analyzer(repository, fileName).extractClass(this.file);
    // assertion
    assertThat(result, is(containsInAnyOrder(info, analyzer)));
  }

  // クラス名抽出テスト
  @Test
  public void extractMethod() throws FileNotFoundException {
    // 期待される値の生成
    final var visit = new MethodInfo("visit", true, "void", repository, fileName);
    final var createText = new MethodInfo("createText", false, "String", repository, fileName);
    final var isTestDone = new MethodInfo("isTestDone", false, "String", repository, fileName);
    final var execute = new MethodInfo("excecute", true, "void", repository, fileName);
    // assertion
    final var result = new Analyzer(repository, fileName).extractMethod(this.file);
    assertThat(result, is(containsInAnyOrder(visit, createText, isTestDone, execute)));
  }


  // 変数名抽出テスト
  @Test
  public void extractValuable() throws FileNotFoundException {
    final var title = new VariableInfo("title", false, "String", false, repository, fileName);
    final var a = new VariableInfo("a", false, "var", false, repository, fileName);
    final var b = new VariableInfo("b", false, "double", false, repository, fileName);
    final var text = new VariableInfo("text", false, "String", false, repository, fileName);
    final var fieldText = new VariableInfo("fieldText", false, "String", true, repository, fileName);
    final var isTest = new VariableInfo("isTest", true, "boolean", true, repository, fileName);
    final var result = new Analyzer(repository, fileName).extractVariable(this.file);
    assertThat(result, is(containsInAnyOrder(title, text, a, b, fieldText, isTest)));
  }
}
