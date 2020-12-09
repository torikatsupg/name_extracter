package experiment;

import static org.junit.Assert.assertTrue;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


import org.junit.Test;

public class MyApiClientTest {

  // GithubのAPIを叩くテスト
  @Test public void fetchRepoList ()
      throws IOException , InterruptedException, JsonParseException, JsonMappingException {
    final var apiClient = new MyApiClient();
    final var fullNames = apiClient.fetchRepoList(1);
    for(String s:fullNames) {
      System.out.println(s);
    }
    assertTrue("GithubAPIClientテスト",  fullNames.size() == (1 * 30));
  }
}
