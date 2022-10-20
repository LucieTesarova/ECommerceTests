import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.assertEquals;

/*
Test Case 6 - using chrome browser
Steps to reproduce:
1. Go to http://live.techpanda.org/
2. Click on my account link
3. Login in application
4. Click on "My wishlist"
5. Click "Add to cart"
6. Enter shipping information
7. Click "Estomate"
8. Verify Shipping cost generated
9. Select Shipping Cost, Update Total
10. Verify shipping cost is added to total
11. Click "Proceed to checkout"
12. Enter Billing information
13. Click "Continue" in shipping method
14. Select "Check/Money Order" radio button in payment method, Click "Continue"
15. Click "Place order"
17. Verify order is generated. Note the order number.
 */
public class TestCase06 {
    private WebDriver driver;

    @BeforeClass
    public static void setUp() {
        System.setProperty(Util.CHROME_DRIVER_NAME, Util.CHROME_DRIVER_PATH);
    }

    @Before
    public void init() {
        driver = new ChromeDriver();
        driver.get(Util.URL);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testCase06() throws InterruptedException {
        // login in application
        driver.findElement(By.cssSelector(".account-cart-wrapper span.label")).click();
        driver.findElement(By.linkText("My Account")).click();
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys(Util.EMAIL);
        driver.findElement(By.id("pass")).clear();
        driver.findElement(By.id("pass")).sendKeys(Util.PASSWORD);
        driver.findElement(By.id("send2")).click();

        Thread.sleep(2000);

        // go to wishlist
        driver.findElement(By.linkText("MY WISHLIST")).click();
        driver.findElement(By.cssSelector("[title~='Add'] span > span")).click();

        Thread.sleep(2000);

        // enter shipping information
        Select selectCountry = new Select(driver.findElement(By.id("country")));
        selectCountry.selectByVisibleText(Util.COUNTRY);
        Select selectState = new Select(driver.findElement(By.id("region_id")));
        selectState.selectByVisibleText(Util.CITY);
        driver.findElement(By.id("postcode")).sendKeys(Util.ZIP);
        driver.findElement(By.cssSelector("[title=Estimate]")).click();

        // verify Shipping cost generated
        String expectedShippingPrice = "$5.00";
        String actualShippingPrice = driver.findElement(By.cssSelector("#co-shipping-method-form label")).getText();
        assertEquals("Fixed - " + expectedShippingPrice, actualShippingPrice);

        //select Shipping cost, Update Total
        driver.findElement(By.id("s_method_flatrate_flatrate")).click();
        driver.findElement(By.cssSelector("button[title~=Total] ")).click();

        // verify shipping cost is added to total
        actualShippingPrice = driver.findElement(By.xpath("//*[@id=\"shopping-cart-totals-table\"]/tbody/tr[2]/td[2]/span")).getText();
        assertEquals(expectedShippingPrice, actualShippingPrice);

        // click "Proceed to checkout"
        driver.findElement(By.cssSelector("button[title~=\"Proceed\"]")).click();

        Thread.sleep(2000);

        // check radio button to "Ship to different address" and "Continue"
        driver.findElement(By.id("billing:use_for_shipping_no")).click();
        driver.findElement(By.xpath("//*[@id=\"billing-buttons-container\"]/button")).click();

        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Thread.sleep(2000);

        //select new address
        Select select = new Select(driver.findElement(By.id("shipping-address-select")));

        Thread.sleep(2000);

        select.selectByIndex(2);

        // enter shipping information
        driver.findElement(By.id("shipping:firstname")).clear();
        driver.findElement(By.id("shipping:firstname")).sendKeys(Util.FIRSTNAME);
        driver.findElement(By.id("shipping:lastname")).clear();
        driver.findElement(By.id("shipping:lastname")).sendKeys(Util.LASTNAME);
        driver.findElement(By.id("shipping:street1")).clear();
        driver.findElement(By.id("shipping:street1")).sendKeys(Util.STREET);
        driver.findElement(By.id("shipping:street2")).clear();
        driver.findElement(By.id("shipping:city")).clear();
        driver.findElement(By.id("shipping:city")).sendKeys(Util.CITY);
        new Select(driver.findElement(By.xpath("//select[@id='shipping:country_id']"))).selectByVisibleText("United States");
        driver.findElement(By.id("shipping:postcode")).clear();
        driver.findElement(By.id("shipping:postcode")).sendKeys(Util.ZIP);
        driver.findElement(By.id("shipping:telephone")).clear();
        driver.findElement(By.id("shipping:telephone")).sendKeys(Util.PHONE_NO);

        // click "Continue" in shipping information
        driver.findElement(By.xpath("//*[@id=\"shipping-buttons-container\"]/button")).click();

        Thread.sleep(2000);

        // click "Continue" in shipping method
        driver.findElement(By.xpath("//*[@id=\"shipping-method-buttons-container\"]/button")).click();

        // click "Check/Money order" in payment information and "Continue"
        Thread.sleep(2000);

        driver.findElement(By.id("p_method_checkmo")).click();
        driver.findElement(By.xpath("//*[@id=\"payment-buttons-container\"]/button")).click();

        Thread.sleep(2000);

        // click "Place order"
        driver.findElement(By.cssSelector("button[title~=\"Place\"]")).click();

        Thread.sleep(2000);

        // verify if order is generated
        String expectedOrder = "YOUR ORDER HAS BEEN RECEIVED.";
        assertEquals(expectedOrder, driver.findElement(By.cssSelector(".page-title h1")).getText());
        String orderNumber = driver.findElement(By.cssSelector(".col-main p")).getText();
        System.out.println(orderNumber);

    }
}
