package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterANewPlayerRq {

  @JsonProperty("surname")
  private String surname;

  @JsonProperty("name")
  private String name;

  @JsonProperty("password_change")
  private String passwordChange;

  @JsonProperty("password_repeat")
  private String passwordRepeat;

  @JsonProperty("email")
  private String email;

  @JsonProperty("currency_code")
  private String currencyCode;

  @JsonProperty("username")
  private String username;
}