package experiment;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UrlCreatorTest {
  // GithubリポジトリダウンロードURL生成
  @Test public void createRepositoryDownloadUrl() {
    final var creator =  new UrlCreator();
    final var actual = creator.createRepositoryDownloadUrl("torikatsupg/experiment");
    final var expected = "https://github.com/torikatsupg/experiment/archive/master.zip";
    assertEquals("UrlCreator.createRepositoryDownloadUrl", expected, actual);
  }

  // リポジトリ一覧を取得するURL生成
  @Test public void createRepositoriesUrl() {
    final var creator = new UrlCreator();
    final var actual = creator.createRepositoriesUrl(0);
    final var expected =
      "https://api.github.com/search/repositories?q=java&language=java&order=desc&page=1&perpage=30";
    assertEquals("UrlCreator.createRepositoriesUrl", actual, expected);
  }
}
