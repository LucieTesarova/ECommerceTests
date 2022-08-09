import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
Test for ecommerce project
Test Case 1
Steps to reproduce:
1. Go to http://live.techpanda.org/
2. Verify title of home page
3. Click to Mobile button
4. Verify title of page
5. Sort products by name
6. Verify if products are sorted alphabetically
 */
public class TestCase01 {
    private WebDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public static void setUp() {
        System.setProperty(Util.DRIVER_NAME, Util.DRIVER_PATH);
    }

    @Before
    public void init() {
        driver = new FirefoxDriver();
        driver.get(Util.URL);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void testCase01() {

        assertEquals("THIS IS DEMO SITE FOR   ", driver.findElement(By.cssSelector("h2")).getText());

        driver.findElement(By.linkText("MOBILE")).click();

        assertEquals("Mobile", driver.getTitle());

        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/div[1]/div[1]/div/select")));
        select.selectByVisibleText("Name");

        // Verify if products are sorted alphabetically
        List<WebElement> products = driver.findElements(By.className("product-name"));
        List<String> verifyText = new ArrayList<>();

        // adding product texts to string list
        for (WebElement element : products) {
            verifyText.add(element.getText());
        }

        Collections.sort(verifyText);

        // Verify if list of product is same as expected list
        for (int i = 0; i < products.size(); i++) {
            assertEquals(products.get(i).getText(), verifyText.get(i));
        }
    }
}
