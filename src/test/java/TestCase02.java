import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.*;
/*
Test case 02
Steps to reproduce:
1. Go to http://live.techpanda.org/
2. Click on Mobile button
3. Get the price of mobile Sony Experia from list
4. Click on Sony Experia mobile
5. Get the price from Sony Experia details
6. Verify if prices from step 3 and 5 are same
 */
public class TestCase02 {
    private WebDriver driver;

    @BeforeClass
    public static void setUp() {
        System.setProperty(Util.DRIVER_NAME, Util.DRIVER_PATH);
    }

    @Before
    public void init() {
        driver = new FirefoxDriver();
        driver.get(Util.URL);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testCase02() throws Exception{
        driver.findElement(By.linkText("MOBILE")).click();

        // Price from list of mobiles
        String priceInList = driver.findElement(By.cssSelector("#product-price-1 > span")).getText();

        driver.findElement(By.xpath("//a[@title=\"Sony Xperia\"]")).click();

        // Price from product detail
        String priceInProductDetail = driver.findElement(By.className("price")).getText();

        //Verify if price is same as value $100.00
        assertEquals("$100.00", priceInList);
        // Verify if price in list is same as product detail price
        assertEquals(priceInList, priceInProductDetail);
    }
}
