package order;

import dto.ClientCredentialsGrantRq;
import dto.ClientCredentialsGrantRs;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PostClientCredentialsGrantTest extends RestBaseTest {

  @Test
  public void checkClientCredentialsGrant() {
    ClientCredentialsGrantRq clientCredentialsGrant = ClientCredentialsGrantRq.builder()
        .grantType("client_credentials")
        .scope("guest:default")
        .build();

    ValidatableResponse response = restApi.grantCredentials(clientCredentialsGrant);

    ClientCredentialsGrantRs clientCredentialsGrantRs = response.extract().body().as(ClientCredentialsGrantRs.class);
    Assertions.assertNotNull(clientCredentialsGrantRs.getAccessToken());
  }
}