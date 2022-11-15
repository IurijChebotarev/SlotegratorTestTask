package glue;

import dto.ClientCredentialsGrantRq;
import dto.ClientCredentialsGrantRs;
import dto.RegisterANewPlayerRq;
import dto.RegisterANewPlayerRs;
import enums.ClientCredentials;
import enums.PlayerFields;
import io.restassured.response.ValidatableResponse;
import services.TestDataGenerator;

import java.util.HashMap;
import java.util.Map;

public class ApiSteps extends ApiBase {

  public static Map<String, String> testData;

  private Map<String, String> getPlayerNameSurnamePassword(String namePrefix, String surnamePrefix, String emailSuffix) {
    Map<String, String> nameSurnamePassword = new HashMap<>();
    TestDataGenerator generator = new TestDataGenerator();
    String name = generator.generateTestName(namePrefix);
    String surname = generator.generateTestName(surnamePrefix);
    String encodedPassword = generator.generateEncodedPassword();

    nameSurnamePassword.put(PlayerFields.NAME.getName(), name);
    nameSurnamePassword.put(PlayerFields.SURNAME.getName(), surname);
    nameSurnamePassword.put(PlayerFields.USERNAME.getName(), name + surname);
    nameSurnamePassword.put(PlayerFields.EMAIL.getName(), name + surname + emailSuffix);
    nameSurnamePassword.put(PlayerFields.PASSWORD.getName(), encodedPassword);

    return nameSurnamePassword;
  }

  private Map<String, String> getPlayerNameSurnamePasswordAndId(String namePrefix, String surnamePrefix, String emailSuffix) {
    Map<String, String> playerNameSurnamePasswordAndId = getPlayerNameSurnamePassword(namePrefix, surnamePrefix, emailSuffix);

    RegisterANewPlayerRq registerANewPlayer = RegisterANewPlayerRq.builder()
        .username(playerNameSurnamePasswordAndId.get(PlayerFields.USERNAME.getName()))
        .passwordChange(playerNameSurnamePasswordAndId.get(PlayerFields.PASSWORD.getName()))
        .passwordRepeat(playerNameSurnamePasswordAndId.get(PlayerFields.PASSWORD.getName()))
        .email(playerNameSurnamePasswordAndId.get(PlayerFields.EMAIL.getName()))
        .build();

    ValidatableResponse response = restApi.registerANewPlayer(registerANewPlayer);
    RegisterANewPlayerRs registerANewPlayerRs = response.extract().body().as(RegisterANewPlayerRs.class);

    Integer id = registerANewPlayerRs.getId();
    playerNameSurnamePasswordAndId.put(PlayerFields.ID.getName(), id.toString());

    return playerNameSurnamePasswordAndId;
  }

  private String getPlayerToken(Map<String, String> resultMap) {
    ClientCredentialsGrantRq clientCredentialsGrant = ClientCredentialsGrantRq.builder()
        .granttype(ClientCredentials.PLAYER.getGrantType())
        .username(resultMap.get(ClientCredentials.PLAYER.getUserName()))
        .password(resultMap.get(ClientCredentials.PLAYER.getPassword()))
        .build();

    ValidatableResponse response = restApi.grantCredentials(clientCredentialsGrant);

    ClientCredentialsGrantRs clientCredentialsGrantRs = response.extract().body().as(ClientCredentialsGrantRs.class);
    return clientCredentialsGrantRs.getAccessToken();
  }

  public ValidatableResponse postClientCredentialsGrantRq(String grantType, String scope) {
    ClientCredentialsGrantRq clientCredentialsGrant = ClientCredentialsGrantRq.builder()
        .granttype(grantType)
        .scope(scope)
        .build();

    return restApi.grantCredentials(clientCredentialsGrant);
  }

  public ValidatableResponse postRegisterANewPlayerWithOptionalFields(String namePrefix, String surnamePrefix, String emailSuffix) {

    testData = getPlayerNameSurnamePassword(namePrefix, surnamePrefix, emailSuffix);
    RegisterANewPlayerRq registerANewPlayer = RegisterANewPlayerRq.builder()
        .username(testData.get(PlayerFields.USERNAME.getName()))
        .passwordChange(testData.get(PlayerFields.PASSWORD.getName()))
        .passwordRepeat(testData.get(PlayerFields.PASSWORD.getName()))
        .email(testData.get(PlayerFields.EMAIL.getName()))
        .name(testData.get(PlayerFields.NAME.getName()))
        .surname(testData.get(PlayerFields.SURNAME.getName()))
        .build();

    return restApi.registerANewPlayer(registerANewPlayer);
  }

  public ValidatableResponse postRegisterANewPlayerWithMandatoryFields(String namePrefix, String surnamePrefix, String emailSuffix) {

    testData = getPlayerNameSurnamePassword(namePrefix, surnamePrefix, emailSuffix);
    RegisterANewPlayerRq registerANewPlayer = RegisterANewPlayerRq.builder()
        .username(testData.get(PlayerFields.USERNAME.getName()))
        .passwordChange(testData.get(PlayerFields.PASSWORD.getName()))
        .passwordRepeat(testData.get(PlayerFields.PASSWORD.getName()))
        .email(testData.get(PlayerFields.EMAIL.getName()))
        .build();

    return restApi.registerANewPlayer(registerANewPlayer);
  }

  public ValidatableResponse PostResourceOwnerPasswordCredentialsGrant(String namePrefix, String surnamePrefix, String emailSuffix) {

    testData = getPlayerNameSurnamePasswordAndId(namePrefix, surnamePrefix, emailSuffix);
    ClientCredentialsGrantRq clientCredentialsGrant = ClientCredentialsGrantRq.builder()
        .granttype(ClientCredentials.PLAYER.getGrantType())
        .username(testData.get(PlayerFields.USERNAME.getName()))
        .password(testData.get(PlayerFields.PASSWORD.getName()))
        .build();

    return restApi.grantCredentials(clientCredentialsGrant);
  }

  public ValidatableResponse getASinglePlayerProfile(String namePrefix, String surnamePrefix, String emailSuffix) {
    testData = getPlayerNameSurnamePasswordAndId(namePrefix, surnamePrefix, emailSuffix);
    String playerId = testData.get(PlayerFields.ID.getName());
    String playerToken = getPlayerToken(testData);

    return restApi.getASinglePlayerProfile(playerToken, playerId);
  }

  public ValidatableResponse getAnAnotherSinglePlayerProfile(String namePrefix, String surnamePrefix, String emailSuffix) {
    testData = getPlayerNameSurnamePasswordAndId(namePrefix, surnamePrefix, emailSuffix);
    Map<String, String> data2 = getPlayerNameSurnamePasswordAndId(namePrefix, surnamePrefix, emailSuffix);
    String playerId = testData.get(PlayerFields.ID.getName());
    String playerToken = getPlayerToken(data2);

    return restApi.getASinglePlayerProfile(playerToken, playerId);
  }
}