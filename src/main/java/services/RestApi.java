package services;

import dto.ClientCredentialsGrantRq;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import sun.security.krb5.internal.AuthorizationData;

import java.util.Base64;

import static io.restassured.RestAssured.given;


public class RestApi {
  public static final String BASE_URL = "http://test-api.d6.dev.devcaz.com/";
  public static final String ORDER = "/store/order";

  public static String username = "front_2d6b0a8391742f5d789d7d915755e09e";

  private RequestSpecification rspec;
  public static String encode(String str1) {
    return new String(Base64.getEncoder().encode((str1).getBytes()));
  }
  private String authorization = encode(username);

//  public static Response getToken() {
//    String username = "front_2d6b0a8391742f5d789d7d915755e09e";
//    String authorization = encode(username);
//
//    return
//        given()
//            .baseUri(BASE_URL)
//            .header("authorization", "Basic " + authorization)
//            .contentType(ContentType.JSON)
////            .queryParam("grant_type", "client_credentials")
////            .queryParam("scope","guest:default")
//            .log().all()
//            .post("/oauth2/token")
//
//            .then()
////            .statusCode(200)
//            .log().all()
//            .extract()
//            .response();
//  }
//
//  public static String parseForAccessToken(Response loginResponse) {
//    return loginResponse.jsonPath().getString("access_token");
//  }

  public RestApi() {
    rspec = io.restassured.RestAssured.given()
        .baseUri(BASE_URL)
//        .header("Authorization", "Basic " + authorization)
        .auth()
        .preemptive()
        .basic("front_2d6b0a8391742f5d789d7d915755e09e", "")
        .contentType(ContentType.JSON);

  }

  public ValidatableResponse grantCredentials(ClientCredentialsGrantRq request) {
    return io.restassured.RestAssured.given(rspec)
        .body(request)
        .log().all()
        .when()
        .post("/v2/oauth2/token")
        .then()
        .statusCode(200)
        .log().all();
  }

//  public ValidatableResponse getOrder(String order) {
//    return io.restassured.RestAssured.given(rspec)
//        .basePath(ORDER)
//        .get(order)
//        .then()
//        .statusCode(200);
//
//  }
//
//  public ValidatableResponse getOrderWithError(String order) {
//    return io.restassured.RestAssured.given(rspec)
//        .basePath(ORDER)
//        .get(order)
//        .then()
//        .statusCode(404);
//
//  }
}

