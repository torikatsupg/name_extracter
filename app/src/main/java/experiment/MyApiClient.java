package experiment;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

public class MyApiClient {
  //
  /**
   *
   * @param page 1ページあたり30件
   * */
  public ArrayList<String> fetchRepoList(int page) throws IOException, InterruptedException, JsonParseException, JsonMappingException {
    final var fullNames = new ArrayList<String>();
    for(int i = 0; i < page; i++) {
      final var body = hitGithubApi(i);
      final var map = json2Map(body);
      fullNames.addAll(extractFullNames(map));
    }
    return fullNames;
  };

  //GithubのAPIを叩く
  /**
  * @param page 0からカウント
  */
  private String hitGithubApi(int page) throws IOException, InterruptedException {
    //URLを作成
    final var uri = URI.create(new UrlCreator().createRepositoriesUrl(page));

    //リクエスト
    final var request = HttpRequest
      .newBuilder(uri)
      .GET()
      .build();

    //ボディの処理
    final var bodyHandler = HttpResponse
      .BodyHandlers
      .ofString(StandardCharsets.UTF_8);

    //レスポンス
    final var response = HttpClient
      .newBuilder()
      .version(Version.HTTP_2)
      .build()
      .send(request, bodyHandler);

    return response.body();
  }

  //JSONをMapに変換
  private Map<String, Object> json2Map(String json) 
      throws IOException, JsonParseException, JsonMappingException {
    final var mapper = new ObjectMapper();
    final Map<String, Object> map = mapper.readValue(json, new TypeReference<Map<String, Object>>(){});
    return map;
  }

  //レスポンスからFullName一覧を抽出
  @SuppressWarnings("unchecked")
  private ArrayList<String> extractFullNames(Map<String, Object> responseBody) {
    //リポジトリ一覧を取得
    final var items = (ArrayList<Map<String, Object>>) responseBody.get("items");

    //リポジトリ一覧からFullName一覧を抽出
    final var fullNames = new ArrayList<String>();
    for(Map<String, Object> item: items) {
      fullNames.add((String) item.get("full_name"));
    }
    return fullNames;
  }
}
