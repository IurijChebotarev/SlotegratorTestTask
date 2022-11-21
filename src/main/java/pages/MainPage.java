package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage<MainPage> {

  private String tabLocator = "[data-target='#s-menu-%s']";
  private String playersItemLocator = "[id='s-menu-users'] [href='/user/%s']";

  public MainPage(WebDriver driver) {
    super(driver);
  }

  public String getPageTitle() {
    return driver.getTitle();
  }


  public PlayerManagementPage openPlayersList(String tabName, String usersListItemName) {
    standartWaiter.waitForCondition(ExpectedConditions.elementToBeClickable(By.xpath(String.format(tabLocator, tabName))));
    WebElement usersList = driver.findElement(By.cssSelector(String.format(tabLocator, tabName)));
    usersList.click();
    WebElement playersItem = driver.findElement(By.cssSelector(String.format(playersItemLocator, usersListItemName)));
    playersItem.click();

    return new PlayerManagementPage(driver);
  }
}