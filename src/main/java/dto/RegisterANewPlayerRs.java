package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterANewPlayerRs {

    @JsonProperty("bonuses_allowed")
    private Boolean bonusesAllowed;

    @JsonProperty("birthdate")
    private Object birthdate;

    @JsonProperty("gender")
    private Object gender;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("timezone_id")
    private Object timezoneId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone_number")
    private Object phoneNumber;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("is_verified")
    private Boolean isVerified;

    @JsonProperty("country_id")
    private Object countryId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;
}