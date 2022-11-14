package order;

import dto.RegisterANewPlayerRs;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;


public class GetASinglePlayerProfile extends RestBaseTest {

  @Test
  public void checkGetASinglePlayerProfile() {
    Map<String, String> result = restApi.getPlayerUserNamePasswordAndId();
    String playerId = result.get("id");
    String playerToken = restApi.getPlayerToken(result);

    ValidatableResponse response = restApi.getASinglePlayerProfile(playerToken, playerId);
    RegisterANewPlayerRs getASinglePlayerProfileRs = response.extract().body().as(RegisterANewPlayerRs.class);
    response.statusCode(200);
    Assertions.assertAll(() -> {
      Assertions.assertNotEquals(getASinglePlayerProfileRs.getId(), result.get("id"));
      Assertions.assertNull(getASinglePlayerProfileRs.getCountryId());
      Assertions.assertNull(getASinglePlayerProfileRs.getTimezoneId());
      Assertions.assertEquals(getASinglePlayerProfileRs.getUsername(), result.get("userName"));
      Assertions.assertEquals(getASinglePlayerProfileRs.getEmail(), result.get("userName") + "@gmail.com");
      Assertions.assertNull(getASinglePlayerProfileRs.getName());
      Assertions.assertNull(getASinglePlayerProfileRs.getSurname());
      Assertions.assertNull(getASinglePlayerProfileRs.getGender());
      Assertions.assertNull(getASinglePlayerProfileRs.getPhoneNumber());
      Assertions.assertNull(getASinglePlayerProfileRs.getBirthdate());
      Assertions.assertTrue(getASinglePlayerProfileRs.getBonusesAllowed());
      Assertions.assertFalse(getASinglePlayerProfileRs.getIsVerified());
    });
  }

  @Test
  public void checkGetAnAnotherSinglePlayerProfile() {
    Map<String, String> result = restApi.getPlayerUserNamePasswordAndId();
    Map<String, String> result2 = restApi.getPlayerUserNamePasswordAndId();
    String playerId = result.get("id");
    String playerToken = restApi.getPlayerToken(result2);

    ValidatableResponse response = restApi.getASinglePlayerProfile(playerToken, playerId);
    response.statusCode(404);
  }
}