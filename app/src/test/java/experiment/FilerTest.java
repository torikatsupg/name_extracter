package experiment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FilerTest {
  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  //ディレクトリ一括削除テスト
  @Test public void delete() throws IOException {
    prepareTempFolder();
    final var filer = new Filer();
    final var beforeFiles = filer.listFiles(tempFolder.getRoot());
    assertTrue("ファイル削除テスト", beforeFiles.size() != 0);
    filer.delete(tempFolder.getRoot().getPath());
    final var files = filer.listFiles(tempFolder.getRoot());
    assertTrue("ファイル削除テスト", files.size() == 0);
  }

  // ファイル一覧抽出テスト
  @Test public void extractFileList() throws IOException {
    // 期待されるファイル一覧
    var expectFiles = new ArrayList<String>();
    expectFiles.add("a.txt");
    expectFiles.add("b.txt");
    expectFiles.add("c.txt");
    expectFiles.add("d.txt");

    prepareTempFolder();
    var result = new Filer().listFiles(tempFolder.getRoot());

    var containAllFile = true;
    for(File f: result) {
      var fileName = f.getName();
      if(!expectFiles.contains(fileName)) {
        containAllFile = false;
        break;
      }
    }
    assertTrue("ファイル抽出テスト", containAllFile);
  }

  @Test
  public void extractjavaFileFromRootDirectory() throws IOException {
    prepareTempFolderForJavaFile();

    final var filer = new Filer();
    final var rootUri = tempFolder.getRoot().getPath();
    final var javaFiles = filer.extractjavaFileFromRootDirectory(rootUri);
    //ファイル一覧に.java以外が含まれていないかテスト
    
    var hasOnlyJavaFile = true;
    for(File f: javaFiles) {
      final var fileName = f.getName();
      final var extension = fileName.substring(fileName.lastIndexOf("."));
      if(!extension.equals(".java")) {
        hasOnlyJavaFile = false;
      }
    }
    assertTrue("Filer.extractjavaFile", hasOnlyJavaFile);
  }

  //Javaファイル抽出テスト
  @Test public void extractJavaFile() throws IOException {
    prepareTempFolderForJavaFile();

    final var filer = new Filer();
    final var files = filer.listFiles(tempFolder.getRoot());
    final var javaFiles = filer.extractJavaFile(files);
    //ファイル一覧に.java以外が含まれていないかテスト
    
    var hasOnlyJavaFile = true;
    for(File f: javaFiles) {
      final var fileName = f.getName();
      final var extension = fileName.substring(fileName.lastIndexOf("."));
      if(!extension.equals(".java")) {
        hasOnlyJavaFile = false;
      }
    }
    assertTrue("Filer.extractjavaFile", hasOnlyJavaFile);
  }

  //テスト用ディレクトリ作成
  private void prepareTempFolder() throws IOException {
    final var root = tempFolder.getRoot();
    final var SAMPLE_DIRECTORY = "sample";
    new File(root, "a.txt").createNewFile();
    new File(root, "b.txt").createNewFile();
    new File(root, SAMPLE_DIRECTORY).mkdir();
    new File(root, SAMPLE_DIRECTORY + File.separator + "c.txt").createNewFile();
    new File(root, SAMPLE_DIRECTORY + File.separator + "d.txt").createNewFile();
  }

  //.javaを含むテスト用ディレクトリ作成
  private void prepareTempFolderForJavaFile() throws IOException {
    final var root = tempFolder.getRoot();
    final var SAMPLE_DIRECTORY = "sample";
    new File(root, "a.txt").createNewFile();
    new File(root, "b.txt").createNewFile();
    new File(root, "a.java").createNewFile();
    new File(root, "b.java").createNewFile();
    new File(root, SAMPLE_DIRECTORY).mkdir();
    new File(root, SAMPLE_DIRECTORY + File.separator + "c.txt").createNewFile();
    new File(root, SAMPLE_DIRECTORY + File.separator + "c.java").createNewFile();
    new File(root, SAMPLE_DIRECTORY + File.separator + "d.txt").createNewFile();
  }
}
