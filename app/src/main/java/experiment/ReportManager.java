package experiment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class ReportManager {
  final public static String REPORT_DIRECTORY = "report";
  final public static String CLASS_REPORT = "class.csv";
  final public static String ENUM_REPORT = "enum.csv";
  final public static String VARIABLE_REPORT = "variable.csv";
  final public static String METHOD_REPORT = "method.csv";
  final public static String FAILED_FILES = "failed.csv";
  final public static String FAILED_REPOSITORY_REPORT = "failed_repository.csv";

  String rootPath;

  //レポート書き出し先ファイル作成
  public ReportManager(String rootDirectory) throws IOException {
    final var root = new File(rootDirectory);
    this.rootPath = root.getAbsolutePath();
    new File(root, REPORT_DIRECTORY).mkdir();
    new File(root, REPORT_DIRECTORY + File.separator + CLASS_REPORT).createNewFile();
    new File(root, REPORT_DIRECTORY + File.separator + METHOD_REPORT).createNewFile();
    new File(root, REPORT_DIRECTORY + File.separator + ENUM_REPORT).createNewFile();
    new File(root, REPORT_DIRECTORY + File.separator + VARIABLE_REPORT).createNewFile();
    new File(root, REPORT_DIRECTORY + File.separator + FAILED_FILES).createNewFile();
    new File(root, REPORT_DIRECTORY + File.separator + FAILED_REPOSITORY_REPORT).createNewFile();
  }

  public void writeDownloadError(String repository, Exception e) throws IOException {
    final var file = new File(this.createFilePath(FAILED_REPOSITORY_REPORT));
    final var fw = new FileWriter(file, true);
    final var pw = new PrintWriter(fw);
    final var sb = new StringBuilder();
    sb.append(repository);
    sb.append(",");
    sb.append(e.getStackTrace());
    sb.append(",");
    pw.println(sb.toString());
    fw.close();
    pw.close();
  }

  public void writeError(String fileName, String repository, Exception e) throws IOException {
    final var file = new File(this.createFilePath(FAILED_FILES));
    final var fw = new FileWriter(file, true);
    final var pw = new PrintWriter(fw);
    final var sb = new StringBuilder();
    sb.append(repository);
    sb.append(",");
    sb.append(fileName);
    sb.append(",");
    sb.append(e.getStackTrace());
    sb.append(",");
    pw.println(sb.toString());
    fw.close();
    pw.close();
  }

  //レポート書き出し
  public void write(ArrayList<String> order, ArrayList<Map<String, String>> data, String path) throws IOException {
    //クラスレポートファイルの参照を作成
    final var reportFile = new File(this.createFilePath(path));
    for(Map<String, String> row: data) {
      final var builder = new StringBuilder();
      //データをキー順に一つの文字列にする
      for(String key: order) {
        builder.append(row.get(key));
        builder.append(",");
      }
      //ファイルの書き込み
      final var lineData = builder.toString();
      final var fw = new FileWriter(reportFile, true);
      final var pw = new PrintWriter(fw);
      pw.println(lineData);
      fw.close();
      pw.close();
    }
  }

  //ファイルパス生成
  private String createFilePath(String path) {
    final var builder = new StringBuilder()
      .append(rootPath)
      .append(File.separator)
      .append(REPORT_DIRECTORY)
      .append(File.separator)
      .append(path);
    return builder.toString();
  }
}
