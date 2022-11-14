package order;

import dto.ClientCredentialsGrantRq;
import dto.ClientCredentialsGrantRs;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;


public class PostResourceOwnerPasswordCredentialsGrantTest extends RestBaseTest {

  @Test
  public void checkClientCredentialsGrant() {

      Map<String,String> result = restApi.getPlayerUserNamePasswordAndId();
      ClientCredentialsGrantRq clientCredentialsGrant = ClientCredentialsGrantRq.builder()
          .grantType("password")
          .username(result.get("userName"))
          .password(result.get("password"))
          .build();

    ValidatableResponse response = restApi.grantCredentials(clientCredentialsGrant);

    ClientCredentialsGrantRs clientCredentialsGrantRs = response.extract().body().as(ClientCredentialsGrantRs.class);
    Assertions.assertNotNull(clientCredentialsGrantRs.getAccessToken());
    }
}