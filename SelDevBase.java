package hillel.qaauto;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.List;

public class SeleniumDevBase {

    WebDriver driver;
    WebDriverWait wait;
    final String SELENIUM_DEV_URL = "https://www.selenium.dev/selenium/web/dynamic.html";
    final String EACH_BOX_COLOR = "red";
    String addBoxButtonXpath = "//input[@id='adder']";
    String revealNewInputButtonXpath = "//input[@id='reveal']";
    String inputFieldXpath = "//input[@id='revealed']";
    String allBoxesXpath = "//div[@class='redbox']";


    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(SELENIUM_DEV_URL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    public boolean checkFieldIsDisplayedAfterClickRevealButton() {
        try {
            driver.findElement(By.xpath(revealNewInputButtonXpath)).click();
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(inputFieldXpath))).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public List<WebElement> clickOnAddABoxButton(int qtyClicks) {
        for (int i = 0; i < qtyClicks; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(addBoxButtonXpath))).click();
        }
        return wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(allBoxesXpath), qtyClicks));
    }

    public boolean checkEachBoxColor(String color, int qtyClicks) {
        List<WebElement> allBoxes = clickOnAddABoxButton(qtyClicks);
        for (WebElement i : allBoxes) {
            if (!i.getAttribute("style").contains(String.format("background-color: %s", color))) {
                return false;
            }
        }
        return true;
    }

}
