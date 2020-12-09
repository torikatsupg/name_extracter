package experiment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReportManagerTest {
  final String REPORT_DIRECTORY = "report";
  final String CLASS_REPORT = "class.csv";
  final String ENUM_REPORT = "enum.csv";
  final String VARIABLE_REPORT = "variable.csv";
  final String METHOD_REPORT = "method.csv";
  final String FAILED_FILES = "failed.csv";
  final String FAILED_REPOSITORY_FILE = "failed_repository.csv";

  @Rule
  public final TemporaryFolder tempFolder = new TemporaryFolder();

  //レポート出力先ファイル作成テスト
  @Test public void constructor() throws IOException {
    final var exceptFiles = new ArrayList<String>();
    exceptFiles.add(CLASS_REPORT);
    exceptFiles.add(ENUM_REPORT);
    exceptFiles.add(VARIABLE_REPORT);
    exceptFiles.add(METHOD_REPORT);
    exceptFiles.add(FAILED_FILES);
    exceptFiles.add(FAILED_REPOSITORY_FILE);

    new ReportManager(tempFolder.getRoot().getPath());

    //レポート用ファイルが作成されているかテスト
    var hasAllFile = true;
    final var files = new Filer().listFiles(tempFolder.getRoot());
    for(File f: files) {
      if(!exceptFiles.contains(f.getName())){
        hasAllFile = false;
      };
    }
    assertTrue("ReportManager.constructor", hasAllFile);
  }

  //レポート書き出しテスト
  @Test public void export() throws IOException {
    //テストの準備
    final var manager = new ReportManager(tempFolder.getRoot().getPath());
    final var data = prepareData();
    final var order = prepareOrder();
    final var except ="a,b,c,d,\ne,f,g,h,\n";

    //ファイル書き込み実行
    manager.write(order, data, ReportManager.CLASS_REPORT);

    //ファイルの内容を検証
    var actual = "";
    final var reportFile = new File(createClassReportPath());
    final var reader = new BufferedReader(new FileReader(reportFile));
    String line;
    while((line = reader.readLine()) != null) {
      actual += line;
      actual += "\n";
    }
    reader.close();
    assertEquals("ReportManager.write()", except, actual);
  }

  // 書き込みデータ作成
  private ArrayList<Map<String, String>> prepareData() throws IOException {
    //1行目のデータを作成
    final var row1 = new HashMap<String, String>();
    row1.put("key4", "d");
    row1.put("key3", "c");
    row1.put("key1", "a");
    row1.put("key2", "b");

    //2行目のデータを作成
    final var row2 = new HashMap<String, String>();
    row2.put("key3", "g");
    row2.put("key1", "e");
    row2.put("key2", "f");
    row2.put("key4", "h");

    //データを作成
    final var data = new ArrayList<Map<String, String>>();
    data.add(row1);
    data.add(row2);

    return data;
  }

  //並び順データ作成
  private ArrayList<String> prepareOrder() {
    //並び順を指定
    final var order = new ArrayList<String>();
    order.add("key1");
    order.add("key2");
    order.add("key3");
    order.add("key4");
    return order;
  }

  //ファイルの参照作成
  private String createClassReportPath() {
    final var builder = new StringBuilder()
      .append(tempFolder.getRoot().getAbsolutePath())
      .append(File.separator)
      .append(REPORT_DIRECTORY)
      .append(File.separator)
      .append(CLASS_REPORT);
    return builder.toString();
  }
}
