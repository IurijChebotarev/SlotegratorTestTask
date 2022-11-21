package enums;

public enum TestData {
  NAMEPREFIX("name"),
  SURNAMEPREFIX("surname"),
  EMAILSUFFIX("@gmail.com");

  private String name;

  TestData(String name) {
    this.name = name;

  }

  public String getName() {
    return name;
  }
}