package experiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Downloader {
  final private byte[] buffer = new byte[1024];
  private String outPutDirectory;

  public Downloader(String outPutDirectory) {
    this.outPutDirectory = outPutDirectory;
  }

  public void download(String urlStr) throws IOException, MalformedURLException, FileNotFoundException {
    final var url = new URL(urlStr);
    final var is = url.openStream();
    unzipFile(is);
  }

  //zipファイルの解凍
  private void unzipFile(InputStream is) throws IOException, FileNotFoundException {
    final var zis = new ZipInputStream(is);
    //イテレータの初期化
    var entry = zis.getNextEntry();
    //各Zipエントリをファイルに書き出していく
    while(entry != null) {
      writeFile(entry, zis);
      entry = zis.getNextEntry();
    }
  }


  //各Zipエントリをファイルに書き出していく
  private void writeFile(ZipEntry entry, ZipInputStream zis) throws IOException, FileNotFoundException {
    final var newUnzipFile = new File(outPutDirectory + File.separator + entry.getName());
    if(entry.isDirectory()) {
      //エントリがディレクトリならディレクトリ作成
      newUnzipFile.mkdir();
    } else {
      //ファイル作成
      var fos = new FileOutputStream(newUnzipFile);
      //1024バイトごとに読み込んで書き出し
      for(int length_byte; (length_byte = zis.read(buffer)) > 0;) {
        fos.write(buffer, 0, length_byte);
      }
      //ストリームを閉じる
      fos.close();
    }
  }   
}
