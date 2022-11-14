package order;

import dto.RegisterANewPlayerRq;
import dto.RegisterANewPlayerRs;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.TestDataGenerator;

public class PostRegisterANewPlayerTest extends RestBaseTest {

  @Test
  @DisplayName("Send request with optional fields")
  public void checkRegisterANewPlayerWithOptionalFields() {

    TestDataGenerator generator = new TestDataGenerator();
    String name = generator.generateTestName("name");
    String surname = generator.generateTestName("surname");
    String encodedPassword = generator.generateEncodedPassword();


    RegisterANewPlayerRq registerANewPlayer = RegisterANewPlayerRq.builder()
        .username(name+surname)
        .passwordChange(encodedPassword)
        .passwordRepeat(encodedPassword)
        .email(name+surname+"@gmail.com")
        .name(name)
        .surname(surname)
        .build();

    ValidatableResponse response = restApi.registerANewPlayer(registerANewPlayer);

    RegisterANewPlayerRs registerANewPlayerRs = response.extract().body().as(RegisterANewPlayerRs.class);
    Assertions.assertAll(() -> {
      Assertions.assertNotNull(registerANewPlayerRs.getId());
      Assertions.assertNull(registerANewPlayerRs.getCountryId());
      Assertions.assertNull(registerANewPlayerRs.getTimezoneId());
      Assertions.assertEquals(registerANewPlayerRs.getUsername(),registerANewPlayer.getUsername());
      Assertions.assertEquals(registerANewPlayerRs.getEmail(),registerANewPlayer.getEmail());
      Assertions.assertEquals(registerANewPlayerRs.getName(),registerANewPlayer.getName());
      Assertions.assertEquals(registerANewPlayerRs.getSurname(),registerANewPlayer.getSurname());
      Assertions.assertNull(registerANewPlayerRs.getGender());
      Assertions.assertNull(registerANewPlayerRs.getPhoneNumber());
      Assertions.assertNull(registerANewPlayerRs.getBirthdate());
      Assertions.assertTrue(registerANewPlayerRs.getBonusesAllowed());
      Assertions.assertFalse(registerANewPlayerRs.getIsVerified());
    });
  }

  @Test
  @DisplayName("Send request with mandatory fields")
  public void checkRegisterANewPlayerWithMandatoryFields() {

    TestDataGenerator generator = new TestDataGenerator();
    String name = generator.generateTestName("name");
    String surname = generator.generateTestName("surname");
    String encodedPassword = generator.generateEncodedPassword();


    RegisterANewPlayerRq registerANewPlayer = RegisterANewPlayerRq.builder()
        .username(name+surname)
        .passwordChange(encodedPassword)
        .passwordRepeat(encodedPassword)
        .email(name+surname+"@gmail.com")
        .build();

    ValidatableResponse response = restApi.registerANewPlayer(registerANewPlayer);

    RegisterANewPlayerRs registerANewPlayerRs = response.extract().body().as(RegisterANewPlayerRs.class);
    Assertions.assertAll(() -> {
      Assertions.assertNotNull(registerANewPlayerRs.getId());
      Assertions.assertNull(registerANewPlayerRs.getCountryId());
      Assertions.assertNull(registerANewPlayerRs.getTimezoneId());
      Assertions.assertEquals(registerANewPlayerRs.getUsername(),registerANewPlayer.getUsername());
      Assertions.assertEquals(registerANewPlayerRs.getEmail(),registerANewPlayer.getEmail());
      Assertions.assertNull(registerANewPlayerRs.getName());
      Assertions.assertNull(registerANewPlayerRs.getSurname());
      Assertions.assertNull(registerANewPlayerRs.getGender());
      Assertions.assertNull(registerANewPlayerRs.getPhoneNumber());
      Assertions.assertNull(registerANewPlayerRs.getBirthdate());
      Assertions.assertTrue(registerANewPlayerRs.getBonusesAllowed());
      Assertions.assertFalse(registerANewPlayerRs.getIsVerified());
    });
  }
}