package ui.steps;

import driver.DriverFactory;
import enums.Browsers;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.*;

public class UiTests extends Pages {

  private WebDriver driver;

  @Before()
  public void setup() {
    DriverFactory.browserType = Browsers.CHROME.getName();
    driver = new DriverFactory().getDriver();
    basePage = new BasePage(driver);
    authorizationPage = new AuthorizationPage(driver);
    mainPage = new MainPage(driver);
    playerManagementPage = new PlayerManagementPage(driver);
  }

  @After()
  public void after() {
    if (driver != null) {
      driver.close();
      driver.quit();
    }
  }

  @Given("Открываем сайт")
  public void openSite() {
    basePage.openSite();
  }

  @When("Авторизовываемся в админке под юзером {string} и паролем {string}")
  public void authorizationWithCreds(String loginName, String password) {
    authorizationPage
        .openSite()
        .loginWithCreds(loginName, password);
  }

  @Then("Dashboard - Casino страница открыта")
  public void checkMainPageIsOpened() {
    Assertions.assertEquals("Dashboard - Casino", mainPage.getPageTitle());
  }

  @When("Нажимаем на таб {string} и выбираем {string} из списка")
  public void selectValueFromList(String tabName, String item) {
    mainPage.openPlayersList(tabName, item);
  }

  @Then("Dashboard - Player management страница открыта")
  public void checkPlayerManagementPageIsOpened() {
    Assertions.assertEquals("Dashboard - Player management", playerManagementPage.getPageTitle());
  }

  @Then("Таблица Player management загрузилась")
  public void checkPlayerManagementTableIsLoaded() {
    Assertions.assertFalse(playerManagementPage.checkPlayerManagementIsEmpty());
  }

  @When("Фильтруем таблицу Player management по колонке {string} с значением {string}")
  public void filterPlayerManagementTable(String columnName, String value) {
    playerManagementPage.filterByValue(columnName, value);
  }

  @And("Сортируем таблицу Player management по колонке с номером {string}")
  public void sortPlayerManagementTable(String columnNumber) {
    playerManagementPage
        .collectValuesFromColumn(columnNumber)
        .clickOnSorting(columnNumber);
  }

  @Then("Проверяем что сортировка по колонке {string} корректна")
  public void checkSortPlayerManagementTable(String columnNumber) {
    playerManagementPage.checkSorting(columnNumber);
  }

}