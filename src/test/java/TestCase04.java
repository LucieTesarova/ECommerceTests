import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/*
Test case 04
Steps to reproduce:
1. Go to http://live.techpanda.org/
2. Click on Mobile button
3. Click on "Add To Compare" for 2 mobiles
4. Click on "Compare" button
5.Verify mobiles in pop-up window
6. Close pop-up window
 */
public class TestCase04 {

    private WebDriver driver;

    @BeforeClass
    public static void setUp() {
        System.setProperty(Util.DRIVER_NAME, Util.DRIVER_PATH);
    }

    @Before
    public void init() {
        driver = new FirefoxDriver();
        driver.get(Util.URL);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testCase04() {
        driver.findElement(By.linkText("MOBILE")).click();

        // Click on button "Add to compare"
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[1]/div/div[3]/ul/li[2]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[2]/div/div[3]/ul/li[2]/a")).click();

        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[3]/div[1]/div[2]/div/button")).click();

        // switching to new window
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Verify texts at popup alert
        String popUpTitle = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div[1]/h1")).getText();
        String sonyExperia = driver.findElement(By.xpath("//*[@id=\"product_comparison\"]/tbody[1]/tr[1]/td[1]/h2/a")).getText();
        String samsungGalaxy = driver.findElement(By.xpath("//*[@id=\"product_comparison\"]/tbody[1]/tr[1]/td[2]/h2/a")).getText();
        assertEquals("COMPARE PRODUCTS", popUpTitle);
        assertEquals("SONY XPERIA", sonyExperia);
        assertEquals("SAMSUNG GALAXY", samsungGalaxy);
    }
}
