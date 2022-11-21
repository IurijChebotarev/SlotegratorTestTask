package driver;

import driver.impl.ChromeWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class DriverFactory implements IDriverFactory {

  public static String browserType;

  //при желании можно добавить другие браузеры
  @Override
  public EventFiringWebDriver getDriver() {
    switch (browserType) {
      case "chrome": {
        return new EventFiringWebDriver(new ChromeWebDriver().newDriver());
      }
      default:
        new ChromeWebDriver().newDriver();
    }
    return null;
  }
}