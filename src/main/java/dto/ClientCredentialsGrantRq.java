package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ClientCredentialsGrantRq{

	@JsonProperty("grant_type")
	private String granttype;

	@JsonProperty("scope")
	private String scope;

	@JsonProperty("password")
	private String password;

	@JsonProperty("username")
	private String username;
}