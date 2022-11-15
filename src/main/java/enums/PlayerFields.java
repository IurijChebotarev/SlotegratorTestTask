package enums;

public enum PlayerFields {
  NAME("name"),
  SURNAME("surname"),
  USERNAME("userName"),
  EMAIL("email"),
  PASSWORD("password"),
  ID("id");

  private String name;

  PlayerFields(String name) {
    this.name = name;

  }

  public String getName() {
    return name;
  }
}