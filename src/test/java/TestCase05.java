import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/*
Test case 05
Steps to reproduce:
1. Go to http://live.techpanda.org/
2. Click on my account link
3. Click Create Account link and fill anformation except email ID
4. Click Register
5. Verify Registration is done
6. Go to "TV menu"
7. Add product in your wish list
8. Click "Share Wishlist"
9. Enter email and message and click "Share Wishlist"
10. Check if wishlist is share
 */
public class TestCase05 {

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
    public void testCase05() {
        //click on Account
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a/span[2]")).click();
        driver.findElement(By.linkText("MY ACCOUNT")).click();
        //Click on Registration button
        driver.findElement(By.linkText("CREATE AN ACCOUNT")).click();

        //Add information to create account
        String firstName = "A";
        String lastName = "B";
        driver.findElement(By.id("firstname")).clear();
        driver.findElement(By.id("firstname")).sendKeys(firstName);
        driver.findElement(By.id("lastname")).clear();
        driver.findElement(By.id("lastname")).sendKeys(lastName);
        driver.findElement(By.id("email_address")).clear();
        driver.findElement(By.id("email_address")).sendKeys("lucka1212+6@atlas.cz");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("qqqqqq");
        driver.findElement(By.id("confirmation")).clear();
        driver.findElement(By.id("confirmation")).sendKeys("qqqqqq");
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div/form/div[2]/button")).click();
        //verify is registration is done
        String welcomeText = ("WELCOME, " + firstName + " " + lastName + "!");
        assertEquals(welcomeText, driver.findElement(By.xpath(".//*[@id='top']/body/div[1]/div/div[1]/div/p")).getText());

        //click on TV menu
        driver.findElement(By.linkText("TV")).click();
        // add TV to wishlist
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[2]/ul/li[1]/div/div[3]/ul/li[1]/a")).click();
        //click "Share wishlist"
        driver.findElement(By.xpath("//*[@id=\"wishlist-view-form\"]/div/div/button[1]/span/span")).click();
        // Add email address and message that you want to share
        String message = "I want this TV";
        driver.findElement(By.id("email_address")).clear();
        driver.findElement(By.id("email_address")).sendKeys(Util.EMAIL);
        driver.findElement(By.id("message")).clear();
        driver.findElement(By.id("message")).sendKeys(message);
        driver.findElement(By.xpath("//*[@id=\"form-validate\"]/div[2]/button/span/span")).click();

        //Verify if email was sent
        String whishListText = "Your Wishlist has been shared.";
        assertEquals(whishListText, driver.findElement(By.xpath(".//*[@id='top']/body/div[1]/div/div[2]/div/div[2]/div/div[1]/ul/li/ul/li/span")).getText());
    }
}
