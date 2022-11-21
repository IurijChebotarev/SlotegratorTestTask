package enums;

public enum Browsers {
  CHROME("chrome");

  private String name;

  Browsers(String name) {
    this.name = name;

  }

  public String getName() {
    return name;
  }
}