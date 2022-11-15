package enums;

public enum UrlSuffixes {
  TOKEN("/v2/oauth2/token"),
  PLAYERS("/v2/players");

  private String name;

  UrlSuffixes(String name) {
    this.name = name;

  }

  public String getName() {
    return name;
  }
}
