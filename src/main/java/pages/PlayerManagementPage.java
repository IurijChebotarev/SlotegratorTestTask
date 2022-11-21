package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerManagementPage extends BasePage<PlayerManagementPage> {

  static List<String> actualValuesList = new ArrayList<>();
  private String playersTableLocator = "[id='payment-system-transaction-grid']";

  public PlayerManagementPage(WebDriver driver) {
    super(driver);
  }

  public String getPageTitle() {
    return driver.getTitle();
  }

  public boolean checkPlayerManagementIsEmpty() {
    return driver.findElements(By.cssSelector(playersTableLocator + " tbody  tr")).isEmpty();
  }

  public boolean retryingFindClick(By by) {
    boolean result = false;
    int attempts = 0;
    while (attempts < 2) {
      try {
        driver.findElement(by).click();
        result = true;
        break;
      } catch (StaleElementReferenceException e) {
        System.out.println("Был пойман StaleElementReferenceException");
      }
      attempts++;
    }
    return result;
  }

  public PlayerManagementPage collectValuesFromColumn(String columnNumber) {
    for (int t = 0; true; ) {
      standartWaiter.waitForCondition(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(By.cssSelector(playersTableLocator))));
      List<WebElement> columnElementsList = driver.findElements(By.cssSelector(String.format(playersTableLocator + " tbody tr td:nth-child(%s)", columnNumber)));
      for (int i = 0; i <= columnElementsList.size() - 1; i++) {
        actualValuesList.add(columnElementsList.get(i).getText());
      }
      WebElement nextButton = driver.findElement(By.cssSelector(".next a"));
      if (nextButton.isDisplayed()) {
        retryingFindClick(By.cssSelector(".next a"));
      } else break;
    }
    WebElement firstButton = driver.findElement(By.cssSelector(".first a"));
    if (firstButton.isDisplayed()) {
      retryingFindClick(By.cssSelector(".first a"));
      standartWaiter.waitForCondition(ExpectedConditions.elementToBeClickable(By.cssSelector(".next a")));
    }
    return this;
  }

  public PlayerManagementPage filterByValue(String filterName, String filterValue) {
    driver.findElement(By.cssSelector(String.format("[name='PlayerSearch[%s]']", filterName))).sendKeys(filterValue + "\n");
    return this;
  }

  public PlayerManagementPage clickOnSorting(String columnNumber) {
    driver.findElement(By.cssSelector(String.format(playersTableLocator + " th:nth-child(%s)", columnNumber))).click();
    return this;
  }

  public void checkSorting(String columnNumber) {
    List<String> valuesAfterSorting = actualValuesList.stream()
        .sorted().collect(Collectors.toList());

    actualValuesList.clear();
    collectValuesFromColumn(columnNumber);
    Assertions.assertEquals(valuesAfterSorting, actualValuesList);
  }
}