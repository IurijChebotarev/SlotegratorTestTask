package driver.impl;

import exceptions.DriverTypeNotSupported;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.Config;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;

public interface IDriver {

  WebDriver newDriver();

  default void downloadLocalWebDriver(DriverManagerType driverType) throws DriverTypeNotSupported {
    Config wdmConfig = WebDriverManager.globalConfig();
    wdmConfig.setAvoidBrowserDetection(true);

    String browserVersion = System.getProperty("browser.version", "");
    //при желании можно добавить другие браузеры
    if (!browserVersion.isEmpty()) {
      switch (driverType) {
        case CHROME:
          wdmConfig.setChromeDriverVersion(browserVersion);
          break;
        default:
          throw new DriverTypeNotSupported(driverType);
      }
    }

    WebDriverManager.getInstance(driverType).setup();
  }
}