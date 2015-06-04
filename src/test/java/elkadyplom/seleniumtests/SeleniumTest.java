package elkadyplom.seleniumtests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {

  private WebDriver driver;

  @Before
  public void setUp() throws Exception {
    driver = new ChromeDriver();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
  }

  @Test
  public void loginTest() throws Exception {
    driver.get("http://localhost:8084/elkadyplom/login");
    By usernameBy = By.id("j_username");
    waitFor(driver, usernameBy);
    WebElement username = driver.findElement(usernameBy);
    username.sendKeys("admin");
    WebElement password = driver.findElement(By.id("j_password"));
    password.sendKeys("admin");

    WebElement helloMessage = driver.findElement(By.cssSelector(".jumbotron h1"));
    Assert.assertEquals("elkadyplom", helloMessage.getText().toLowerCase());
  }

  private void waitFor(WebDriver driver, By by) {
    long end = System.currentTimeMillis() + 10000;
    while (System.currentTimeMillis() < end) {
      WebElement waitElement = null;
      try {
        waitElement = driver.findElement(by);
      } catch (NoSuchElementException e) {
        System.out.println("...");
      }

      if (waitElement != null && waitElement.isDisplayed()) {
        break;
      }
      try {
        Thread.sleep(250);
      } catch (InterruptedException e) {
        System.out.println("Interrupted sleep");
      }
    }
  }
}
