package experiment;

public class UrlCreator {
  public String createRepositoryDownloadUrl(String fullName) {
    return "https://github.com/" + fullName + "/archive/master.zip";
  }

  //URLを作成
  public String createRepositoriesUrl(int page) {
    final var PER_PAGE = 30;
    final var url = new StringBuilder("https://api.github.com/search/repositories?")
      .append("q=java&")
      .append("language=java&")
      .append("order=desc&")
      .append("page=" + (page + 1) + "&")
      .append("perpage=" + PER_PAGE)
      .toString();
    return url;
  }
}
