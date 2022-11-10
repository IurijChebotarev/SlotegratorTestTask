package order;

import dto.ClientCredentialsGrantRq;
import dto.ClientCredentialsGrantRs;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//import static services.RestApi.getToken;
//import static services.RestApi.parseForAccessToken;

public class PostClientCredentialsGrantTest extends RestBaseTest {

  @Test
  public void checkCompleteOrder() {
    ClientCredentialsGrantRq clientCredentialsGrantRq = ClientCredentialsGrantRq.builder()
        .grantType("client_credentials")
        .scope("guest:default")
        .build();

    ValidatableResponse response = restApi.grantCredentials(clientCredentialsGrantRq);

    ClientCredentialsGrantRs clientCredentialsGrantRs = response.extract().body().as(ClientCredentialsGrantRs.class);
    Assertions.assertAll(() -> {
      Assertions.assertEquals(clientCredentialsGrantRs.getTokenType(), "Bearer");
    });
  }
}
