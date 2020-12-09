package experiment;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class DownloaderTest {
  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  @Test public void download() {
    final var urlStr = "https://github.com/torikatsupg/experiment/archive/master.zip";
    final var root = tempFolder.getRoot();
    try {
      new Downloader(root.getPath()).download(urlStr);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      final var EXCEPT_DELIRECTORY = "experiment-master";
      final var exceptFile = new File(root.getPath() + File.separator + EXCEPT_DELIRECTORY);
      assertTrue("ファイルのダウンロード＆解凍テスト", exceptFile.exists());
    }
  }
}
