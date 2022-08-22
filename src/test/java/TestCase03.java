import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

/*
Test case 03
Steps to reproduce:
1. Go to http://live.techpanda.org/
2. Click on Mobile button
3. Click on "Add to cart" for Sony Experia
4. Change value to "1000" and click "Update" button
5. Verify error message
6. Click on "Empty card" link
7. Verify that card is empty
 */
public class TestCase03 {
    private WebDriver driver;

    @BeforeClass
    public static void setUp() {
        System.setProperty(Util.DRIVER_NAME, Util.DRIVER_PATH);
    }

    @Before
    public void init() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(Util.URL);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testCaw03() {
        driver.findElement(By.linkText("MOBILE")).click();

        //Click on button Add to cart
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[2]/div/div[3]/button/span/span")).click();

        // Enter value 1000
        WebElement qty = driver.findElement(By.xpath("//input[@title=\"Qty\"]"));
        qty.clear();
        qty.sendKeys("1000");

        // Click on button Update
        driver.findElement(By.cssSelector(".product-cart-actions span span")).click();

        // Verify if cannot add more products than is available
        try {
            String errorMessage = driver.findElement(By.xpath("//*[@id=\"shopping-cart-table\"]/tbody/tr/td[2]/p")).getText();
            assertEquals(Util.ERROR_MESSAGE, errorMessage);
        }catch (NoSuchElementException e){
            System.out.println("Error message is not shown");
        }

        // Click button Empty cart
        driver.findElement(By.xpath("//*[@id=\"empty_cart_button\"]/span/span")).click();

        //Verify that shopping cart is empty
        String cartEmptyMessage = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div/div[1]/h1")).getText();
        assertEquals(Util.EMPTY_MESSAGE, cartEmptyMessage);

    }
}


