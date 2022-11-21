package enums;

public enum BasicAuthenticationUsernames {
  USER1("front_2d6b0a8391742f5d789d7d915755e09e");

  private String userName;

  BasicAuthenticationUsernames(String userName) {
    this.userName = userName;

  }

  public String getUserName() {
    return userName;
  }
}