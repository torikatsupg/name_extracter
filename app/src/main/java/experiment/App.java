package experiment;

import java.util.ArrayList;
import java.util.Map;

import experiment.analyzer.*;

public class App {
  final static private String DOWNLOAD_DIRECTORY =
      "/Users/toriikatsuya/Desktop/experiment/experiment/nameExtracter/app/src/download/";
  final static private String ROOT_DIRECTORY =
      "/Users/toriikatsuya/Desktop/experiment/experiment/nameExtracter/app/src";

  public static void main(String[] args) throws Exception {
    System.out.println("Process is started");
    var repositories = new MyApiClient().fetchRepoList(10);
    // 各リポジトリを処理
    var manager = new ReportManager(ROOT_DIRECTORY);
    for(var repository: repositories) {
      try {
        analyzeRepository(manager, repository);
      } catch (Exception e) {
        manager.writeDownloadError(repository, e);
        continue;
      }
    }
    System.out.println("Process is finished");
  }

  // リポジトリを解析
  private static void analyzeRepository(ReportManager manager, String repository) throws Exception {
    var filer = new Filer();
    new Downloader(DOWNLOAD_DIRECTORY).download("https://github.com/" + repository + "/archive/master.zip");
    System.out.println("download: " + repository);
    var javaFiles = filer.extractjavaFileFromRootDirectory(DOWNLOAD_DIRECTORY);
    System.out.println("extract java files: " + repository);
    // 全てのjavaファイルを処理
    for(var javaFile: javaFiles) {
      System.out.println("repository: " + repository + ", excecute: " + javaFile.getName());
      try {
        var analyzer = new Analyzer(repository, javaFile.getName());
        var classes = analyzer.extractClass(javaFile);
        var methods = analyzer.extractMethod(javaFile);
        var variables = analyzer.extractVariable(javaFile);
        // レポート書き出し
        writeClassReport(manager, classes);
        writeMethodReport(manager, methods);
        writeVariableReport(manager, variables);
      } catch (Exception e) {
        manager.writeError(javaFile.getName(), repository, e);
        continue;
      }
      // 解析

    }
    filer.delete(DOWNLOAD_DIRECTORY);
    System.out.println("delete: " + repository);
  }

  // クラスのレポート書き出し
  private static void writeClassReport(ReportManager manager, ArrayList<ClassInfo> classes) throws Exception {
    final var order = baseOrder();
    final var maps = toMaps(classes);
    manager.write(order, maps, ReportManager.CLASS_REPORT);
  }

  // メソッドのレポート書き出し
  private static void writeMethodReport(ReportManager manager, ArrayList<MethodInfo> methods) throws Exception {
    final var order = baseOrder();
    order.add("returnType");
    final var maps = toMaps(methods);
    manager.write(order, maps, ReportManager.METHOD_REPORT);
  }

  // 変数のレポート書き出し
  private static void writeVariableReport(ReportManager manager, ArrayList<VariableInfo> variables) throws Exception {
    final var order = baseOrder();
    order.add("type");
    order.add("isFieldValue");
    final var maps = toMaps(variables);
    manager.write(order, maps, ReportManager.VARIABLE_REPORT);
  }

  // mapのリストに変換
  private static ArrayList<Map<String, String>> toMaps(ArrayList<? extends SyntaxInfo> list) {
    var maps = new ArrayList<Map<String, String>>();
    for(var info: list) {
      maps.add(info.toMap());
    }
    return maps;
  }

  // ベースのオーダー作成
  private static ArrayList<String> baseOrder() {
    final var order = new ArrayList<String>();
    order.add("repository");
    order.add("fileName");
    order.add("name");
    order.add("isPublic");
    return order;
  }
}
