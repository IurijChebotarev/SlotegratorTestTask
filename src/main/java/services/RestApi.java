package services;

import dto.ClientCredentialsGrantRq;
import dto.ClientCredentialsGrantRs;
import dto.RegisterANewPlayerRq;
import dto.RegisterANewPlayerRs;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class RestApi {
  public static final String BASE_URL = "http://test-api.d6.dev.devcaz.com/";

  private static RequestSpecification rspec;

  public RestApi() {
    rspec = io.restassured.RestAssured.given()
        .baseUri(BASE_URL);
  }

  public ValidatableResponse grantCredentials(ClientCredentialsGrantRq request) {
    return io.restassured.RestAssured.given(rspec)
        .auth()
        .preemptive()
        .basic("front_2d6b0a8391742f5d789d7d915755e09e", "")
        .contentType(ContentType.JSON)
        .body(request)
        .log().all()
        .when()
        .post("/v2/oauth2/token")
        .then()
        .statusCode(200)
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
        .post("/v2/players")
        .then()
        .statusCode(201)
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
        .get("/v2/players" + "/" + id)
        .then()
        .log().all();
  }

  public ValidatableResponse registerANewPlayer(RegisterANewPlayerRq request) {
    return registerANewPlayer(request, getToken());
  }

  private String getToken(){
    ClientCredentialsGrantRq clientCredentialsGrant = ClientCredentialsGrantRq.builder()
        .grantType("client_credentials")
        .scope("guest:default")
        .build();
    ValidatableResponse response = grantCredentials(clientCredentialsGrant);
    ClientCredentialsGrantRs clientCredentialsGrantRs = response.extract().body().as(ClientCredentialsGrantRs.class);
    return clientCredentialsGrantRs.getAccessToken();
  }

  public String getPlayerToken(Map<String,String> resultMap){
    ClientCredentialsGrantRq clientCredentialsGrant = ClientCredentialsGrantRq.builder()
        .grantType("password")
        .username(resultMap.get("userName"))
        .password(resultMap.get("password"))
        .build();

    ValidatableResponse response = grantCredentials(clientCredentialsGrant);

    ClientCredentialsGrantRs clientCredentialsGrantRs = response.extract().body().as(ClientCredentialsGrantRs.class);
    return clientCredentialsGrantRs.getAccessToken();
  }


  public Map<String,String> getPlayerUserNamePasswordAndId(){
    Map<String,String> usernamePasswordAndId = new HashMap<>();
    TestDataGenerator generator = new TestDataGenerator();
    String name = generator.generateTestName("name");
    String surname = generator.generateTestName("surname");
    String encodedPassword = generator.generateEncodedPassword();

    RegisterANewPlayerRq registerANewPlayer = RegisterANewPlayerRq.builder()
        .username(name + surname)
        .passwordChange(encodedPassword)
        .passwordRepeat(encodedPassword)
        .email(name + surname + "@gmail.com")
        .build();

    ValidatableResponse response = registerANewPlayer(registerANewPlayer);
    RegisterANewPlayerRs registerANewPlayerRs = response.extract().body().as(RegisterANewPlayerRs.class);

    Integer id = registerANewPlayerRs.getId();
    usernamePasswordAndId.put("userName",name + surname);
    usernamePasswordAndId.put("password",encodedPassword);
    usernamePasswordAndId.put("id",id.toString());

    return usernamePasswordAndId;
  }

}



