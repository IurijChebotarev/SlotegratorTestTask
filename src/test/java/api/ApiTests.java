package api;

import dto.ClientCredentialsGrantRs;
import dto.RegisterANewPlayerRs;
import enums.ClientCredentials;
import enums.PlayerFields;
import enums.TestData;
import glue.ApiBaseStep;
import glue.ApiSteps;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("API")
public class ApiTests extends ApiBaseStep {

  static ValidatableResponse response;

  @Test
  @DisplayName("Проверка получения токена гостя")
  public void postClientCredentialsGrantTest() {
    response = apiSteps.postClientCredentialsGrantRq(ClientCredentials.GUEST.getGrantType(), ClientCredentials.GUEST.getscope());

    ClientCredentialsGrantRs clientCredentialsGrantRs = response.extract().body().as(ClientCredentialsGrantRs.class);
    response.statusCode(200);
    Assertions.assertNotNull(clientCredentialsGrantRs.getAccessToken());
  }

  @Test
  @DisplayName("Проверка регистрации игрока с опциональными полями")
  public void postRegisterANewPlayerWithOptionalFieldsTest() {
    response = apiSteps.postRegisterANewPlayerWithOptionalFields(TestData.NAMEPREFIX.getName(), TestData.SURNAMEPREFIX.getName(), TestData.EMAILSUFFIX.getName());

    RegisterANewPlayerRs registerANewPlayerRs = response.extract().body().as(RegisterANewPlayerRs.class);
    response.statusCode(201);
    Assertions.assertAll(() -> {
      Assertions.assertNotNull(registerANewPlayerRs.getId());
      Assertions.assertNull(registerANewPlayerRs.getCountryId());
      Assertions.assertNull(registerANewPlayerRs.getTimezoneId());
      Assertions.assertEquals(registerANewPlayerRs.getUsername(), ApiSteps.testData.get(PlayerFields.USERNAME.getName()));
      Assertions.assertEquals(registerANewPlayerRs.getEmail(), ApiSteps.testData.get(PlayerFields.EMAIL.getName()));
      Assertions.assertEquals(registerANewPlayerRs.getName(), ApiSteps.testData.get(PlayerFields.NAME.getName()));
      Assertions.assertEquals(registerANewPlayerRs.getSurname(), ApiSteps.testData.get(PlayerFields.SURNAME.getName()));
      Assertions.assertNull(registerANewPlayerRs.getGender());
      Assertions.assertNull(registerANewPlayerRs.getPhoneNumber());
      Assertions.assertNull(registerANewPlayerRs.getBirthdate());
      Assertions.assertTrue(registerANewPlayerRs.getBonusesAllowed());
      Assertions.assertFalse(registerANewPlayerRs.getIsVerified());
    });
  }

  @Test
  @DisplayName("Проверка регистрации игрока без опциональных полей")
  public void postRegisterANewPlayerWithMandatoryTest() {
    response = apiSteps.postRegisterANewPlayerWithMandatoryFields(TestData.NAMEPREFIX.getName(), TestData.SURNAMEPREFIX.getName(), TestData.EMAILSUFFIX.getName());

    RegisterANewPlayerRs registerANewPlayerRs = response.extract().body().as(RegisterANewPlayerRs.class);
    response.statusCode(201);
    Assertions.assertAll(() -> {
      Assertions.assertNotNull(registerANewPlayerRs.getId());
      Assertions.assertNull(registerANewPlayerRs.getCountryId());
      Assertions.assertNull(registerANewPlayerRs.getTimezoneId());
      Assertions.assertEquals(registerANewPlayerRs.getUsername(), ApiSteps.testData.get(PlayerFields.USERNAME.getName()));
      Assertions.assertEquals(registerANewPlayerRs.getEmail(), ApiSteps.testData.get(PlayerFields.EMAIL.getName()));
      Assertions.assertNull(registerANewPlayerRs.getName());
      Assertions.assertNull(registerANewPlayerRs.getSurname());
      Assertions.assertNull(registerANewPlayerRs.getGender());
      Assertions.assertNull(registerANewPlayerRs.getPhoneNumber());
      Assertions.assertNull(registerANewPlayerRs.getBirthdate());
      Assertions.assertTrue(registerANewPlayerRs.getBonusesAllowed());
      Assertions.assertFalse(registerANewPlayerRs.getIsVerified());
    });
  }

  @Test
  @DisplayName("Проверка авторизации под созданым игроком")
  public void postResourceOwnerPasswordCredentialsGrantTest() {
    response = apiSteps.PostResourceOwnerPasswordCredentialsGrant(TestData.NAMEPREFIX.getName(), TestData.SURNAMEPREFIX.getName(), TestData.EMAILSUFFIX.getName());

    ClientCredentialsGrantRs clientCredentialsGrantRs = response.extract().body().as(ClientCredentialsGrantRs.class);
    response.statusCode(200);
    Assertions.assertNotNull(clientCredentialsGrantRs.getAccessToken());
  }

  @Test
  @DisplayName("Проверка запроса данных профиля существующего игрока")
  public void getASinglePlayerProfileTest() {
    response = apiSteps.getASinglePlayerProfile(TestData.NAMEPREFIX.getName(), TestData.SURNAMEPREFIX.getName(), TestData.EMAILSUFFIX.getName());

    RegisterANewPlayerRs getASinglePlayerProfileRs = response.extract().body().as(RegisterANewPlayerRs.class);
    response.statusCode(200);
    Assertions.assertAll(() -> {
      Assertions.assertNotEquals(getASinglePlayerProfileRs.getId(), ApiSteps.testData.get(PlayerFields.ID.getName()));
      Assertions.assertNull(getASinglePlayerProfileRs.getCountryId());
      Assertions.assertNull(getASinglePlayerProfileRs.getTimezoneId());
      Assertions.assertEquals(getASinglePlayerProfileRs.getUsername(), ApiSteps.testData.get(PlayerFields.USERNAME.getName()));
      Assertions.assertEquals(getASinglePlayerProfileRs.getEmail(), ApiSteps.testData.get(PlayerFields.EMAIL.getName()));
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
  @DisplayName("Проверка запроса данных профиля не существующего игрока")
  public void getAnAnotherSinglePlayerProfileTest() {
    response = apiSteps.getAnAnotherSinglePlayerProfile(TestData.NAMEPREFIX.getName(), TestData.SURNAMEPREFIX.getName(), TestData.EMAILSUFFIX.getName());

    response.statusCode(404);
  }
}