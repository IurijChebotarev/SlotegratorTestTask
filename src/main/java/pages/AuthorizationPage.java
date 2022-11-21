package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthorizationPage extends BasePage<AuthorizationPage> {

  @FindBy(css = "#UserLogin_username")
  private WebElement loginField;
  @FindBy(css = "#UserLogin_password")
  private WebElement passwordField;
  @FindBy(css = "[type='submit']")
  private WebElement submitButton;

  public AuthorizationPage(WebDriver driver) {
    super(driver);
  }

  public MainPage loginWithCreds(String loginName, String password) {
    loginField.sendKeys(loginName);
    passwordField.sendKeys(password);
    submitButton.click();
    return new MainPage(driver);
  }
}