package hillel.qaauto;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;


public class HerokuappBase {

    String HEROKU_APP_URL = "https://the-internet.herokuapp.com/dynamic_loading";
    String textAfterLoadingInExamples = "Hello World!";
    String exampleLinkXpath = "//a[contains(text(),'Example %d')]";
    String exampleStartButtonXpath = "//button[text()='Start']";
    String showedTextAfterLoadingXpath = "//div[@id='finish']//h4";

    WebDriver driver;
    WebDriverWait wait;


    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(HEROKU_APP_URL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    public String checkLoadedTextInExamplesByNumber(int numberOfExample) {
        driver.findElement(By.xpath(String.format(exampleLinkXpath, numberOfExample))).click();
        driver.findElement(By.xpath(exampleStartButtonXpath)).click();
        if (numberOfExample == 1) {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(showedTextAfterLoadingXpath))).getText();
        } else {
            return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(showedTextAfterLoadingXpath))).getText();
        }
    }
}
