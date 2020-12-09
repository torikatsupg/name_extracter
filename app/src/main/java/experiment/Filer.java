package experiment;

import java.io.File;
import java.util.ArrayList;

public class Filer {
  //ファイルを全て削除
  public void delete(String rootUri) {
    final var root = new File(rootUri);
    final var files = this.listFiles(root);
    for(File f: files) {
      f.delete();
    }
  }

  // 特定のディレクトリに含まれるjavaファイルを抽出
  public ArrayList<File> extractjavaFileFromRootDirectory(String rootUri) {
    final var root = new File(rootUri);
    final var files = listFiles(root);
    return extractJavaFile(files);
  }

  // 特定のディレクトリに含まれるファイル一覧を取得
  public ArrayList<File> listFiles(File root) {
    var fileList = root.listFiles();
    var files = new ArrayList<File>();
    for(File f: fileList) {
      if(f.isDirectory()) {
        files.addAll(listFiles(f));
      } else {
        files.add(f);
      }
    }
    return files;
  }

  //javaファイルのみを抽出
  public ArrayList<File> extractJavaFile(ArrayList<File> files) {
    final var javaFiles = new ArrayList<File>();
    for(File f: files) {
      final var fileName = f.getName();
      try {
        final var extension = fileName.substring(fileName.lastIndexOf("."));
        if(extension.equals(".java")) {
          javaFiles.add(f);
        }
      } catch (Exception e) {
        continue;
      }
    }
    return javaFiles;
  }
}
