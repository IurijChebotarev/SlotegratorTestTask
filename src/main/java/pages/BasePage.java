package pages;

import actions.CommonActions;
import enums.Urls;
import org.openqa.selenium.WebDriver;

public class BasePage<T> extends CommonActions<T> {

  private final String BASE_URL = Urls.UI.getName();

  public BasePage(WebDriver driver) {
    super(driver);
  }

  public WebDriver getDriver() {
    return driver;
  }

  public AuthorizationPage openSite() {
    driver.get(BASE_URL);
    driver.manage().window().maximize();
    return new AuthorizationPage(driver);
  }
}