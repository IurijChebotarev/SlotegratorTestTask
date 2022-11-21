package services;

import dto.ClientCredentialsGrantRq;
import dto.ClientCredentialsGrantRs;
import dto.RegisterANewPlayerRq;
import enums.BasicAuthenticationUsernames;
import enums.ClientCredentials;
import enums.UrlSuffixes;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

public class RestApi {
  private static RequestSpecification rspec;
  private final String BASE_URL = System.getProperty("api.url").toLowerCase(Locale.ROOT);

  public RestApi() {
    rspec = io.restassured.RestAssured.given()
        .baseUri(BASE_URL);
  }

  public ValidatableResponse grantCredentials(ClientCredentialsGrantRq request) {
    return io.restassured.RestAssured.given(rspec)
        .auth()
        .preemptive()
        .basic(BasicAuthenticationUsernames.USER1.getUserName(), "")
        .contentType(ContentType.JSON)
        .body(request)
        .log().all()
        .when()
        .post(UrlSuffixes.TOKEN.getName())
        .then()
        .log().all();
  }

  public ValidatableResponse registerANewPlayer(RegisterANewPlayerRq request, String token) {
    return io.restassured.RestAssured.given(rspec)
        .auth()
        .preemptive()
        .oauth2(token)
        .contentType(ContentType.JSON)
        .body(request)
        .log().all()
        .when()
        .post(UrlSuffixes.PLAYERS.getName())
        .then()
        .log().all();
  }

  public ValidatableResponse getASinglePlayerProfile(String token, String id) {
    return io.restassured.RestAssured.given(rspec)
        .auth()
        .preemptive()
        .oauth2(token)
        .contentType(ContentType.JSON)
        .log().all()
        .when()
        .get(UrlSuffixes.PLAYERS.getName() + "/" + id)
        .then()
        .log().all();
  }

  public ValidatableResponse registerANewPlayer(RegisterANewPlayerRq request) {
    return registerANewPlayer(request, getToken());
  }

  private String getToken() {
    ClientCredentialsGrantRq clientCredentialsGrant = ClientCredentialsGrantRq.builder()
        .granttype(ClientCredentials.GUEST.getGrantType())
        .scope(ClientCredentials.GUEST.getscope())
        .build();
    ValidatableResponse response = grantCredentials(clientCredentialsGrant);
    ClientCredentialsGrantRs clientCredentialsGrantRs = response.extract().body().as(ClientCredentialsGrantRs.class);
    return clientCredentialsGrantRs.getAccessToken();
  }
}