package enums;

public enum ClientCredentials {
  GUEST("client_credentials","guest:default","",""),
  PLAYER("password","","userName","password");


  private String grantType;
  private String scope;
  private String userName;
  private String password;

  ClientCredentials(String grantType, String scope, String userName, String password) {
    this.grantType = grantType;
    this.scope = scope;
    this.userName = userName;
    this.password = password;

  }

  public String getGrantType() {
    return grantType;
  }
  public String getscope() {
    return scope;
  }
  public String getUserName() { return userName; }
  public String getPassword() { return password; }
}
