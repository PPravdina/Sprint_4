import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import practicum.scooter.MainPage;
import practicum.scooter.OrderPage;

public class BaseTest {
    protected WebDriver webDriver;
    protected MainPage mainPage;
    protected OrderPage orderPage;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(3, java.util.concurrent.TimeUnit.SECONDS);
        mainPage = new MainPage(webDriver);
        orderPage = new OrderPage(webDriver);
        mainPage.open();
    }

    @After
    public void tearDown() {
        webDriver.quit();
    }
}
