package enums;

public enum Urls {
  UI("http://test-app.d6.dev.devcaz.com/admin/login");

  private String name;

  Urls(String name) {
    this.name = name;

  }

  public String getName() {
    return name;
  }
}