package elkadyplom.seleniumtests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SeleniumTest {

  private WebDriver driver;
  private Logger logger = LoggerFactory.getLogger(this.getClass());

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
    login("admin", "admin");
    WebElement helloMessage = driver.findElement(By.cssSelector(".jumbotron h1"));
    assertEquals("elkadyplom", helloMessage.getText().toLowerCase());
  }

  @Test
  public void addAndDeleteTopicTest() throws Exception {
    login("admin", "admin");

    By topicsTabBy = By.linkText("Tematy prac");
    waitFor(topicsTabBy);
    driver.findElement(topicsTabBy).click();
    By topicNameBy = By.linkText("Dodaj nowy temat");
    waitFor(topicNameBy);
    driver.findElement(topicNameBy).click();
    driver.findElement(By.name("name")).clear();
    driver.findElement(By.name("name")).sendKeys("test topic - selenium");
    driver.findElement(By.name("description")).clear();
    driver.findElement(By.name("description")).sendKeys("test description - selenium");
    new Select(driver.findElement(By.name("supervisor"))).selectByVisibleText("supervisor1");
    new Select(driver.findElement(By.name("student"))).selectByVisibleText("student2");
    driver.findElement(By.name("thesisType")).click();
    driver.findElement(By.cssSelector("input.btn.btn-inverse")).click();
    assertEquals("test topic - selenium",
        driver.findElement(By.cssSelector("td.tdTopicsCentered.ng-binding")).getText());
    driver.findElement(By.xpath("//div[@id='gridContainer']/table/tbody/tr/td[9]/div/a[2]"))
        .click();
    driver.findElement(By.cssSelector("form[name=\"deleteTopicForm\"] > input.btn.btn-inverse"))
        .click();
    assertNotEquals("test topic - selenium",
        driver.findElement(By.cssSelector("td.tdTopicsCentered.ng-binding")).getText());
  }

  private void login(String username, String password) {
    driver.get("http://localhost:8084/elkadyplom/login");
    By usernameBy = By.id("j_username");
    waitFor(usernameBy);
    WebElement usernameElement = driver.findElement(usernameBy);
    usernameElement.sendKeys(username);
    WebElement passwordElement = driver.findElement(By.id("j_password"));
    passwordElement.sendKeys(password);
    passwordElement.submit();
  }

  private void waitFor(By by) {
    long end = System.currentTimeMillis() + 10000;
    while (System.currentTimeMillis() < end) {
      WebElement waitElement = null;
      try {
        waitElement = driver.findElement(by);
      } catch (NoSuchElementException e) {
        logger.info("waiting...");
      }

      if (waitElement != null && waitElement.isDisplayed()) {
        break;
      }
      try {
        Thread.sleep(250);
      } catch (InterruptedException e) {
        logger.warn("Sleep interrupted");
      }
    }
  }
}
