package services;

import org.apache.commons.lang3.RandomStringUtils;
import java.util.Base64;

public class TestDataGenerator {

  private String generateLettersString() {
    return RandomStringUtils.randomAlphabetic(6);
  }

  private String generateAlphanumericString() {
    return RandomStringUtils.randomAlphanumeric(6);
  }

  public String generateTestName(String prefix){
    return prefix + generateLettersString();
  }

  public String generateEncodedPassword(){
    return Base64.getEncoder().encodeToString(generateAlphanumericString().getBytes());
  }
}
