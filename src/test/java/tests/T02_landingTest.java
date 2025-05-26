package tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.iTestResultListenerClass;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.P01_LoginPage;
import pages.P02_landingPage;
import utilities.DataUtils;
import utilities.LogsUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import static DreiverFactory.DriverFactory.*;
import static utilities.DataUtils.getPropertyValue;
import static utilities.Utility.*;

@Listeners({IInvokedMethodListenerClass.class, iTestResultListenerClass.class})
public class T02_landingTest {
    private final String username = DataUtils.getJsonData("validLogin", "username");
    private final String password = DataUtils.getJsonData("validLogin", "password");
    private Set<Cookie> cookies;

    @BeforeClass
    public void Login() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : getPropertyValue("environment", "browser");
        LogsUtils.info(System.getProperty("browser"));
        setupDriver(browser);
        LogsUtils.info("Edge driver is open ");
        getDriver().get(getPropertyValue("environment", "base_URL"));
        LogsUtils.info("Pge redirected to URl");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        new P01_LoginPage(getDriver())
                .enterUserName(username)
                .enterPassword(password)
                .clickLoginButton();
        cookies = getAllCookies(getDriver());
        quitDriver();

    }

    @BeforeMethod
    public void setup() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : getPropertyValue("environment", "browser");
        LogsUtils.info(System.getProperty("browser"));
        setupDriver(browser);
        LogsUtils.info("Edge driver is open ");
        getDriver().get(getPropertyValue("environment", "base_URL"));
        LogsUtils.info("Pge redirected to URl");
        restoreSession(getDriver(), cookies);
        getDriver().get(getPropertyValue("environment", "Home_URL"));
        getDriver().navigate().refresh();
    }

    @Test
    public void checkingNumberOfSelectProducts() {
        new P02_landingPage(getDriver())
                .addAllProductToCart();
        Assert.assertTrue(new P02_landingPage(getDriver()).comparingNumberOfSelectedProductWithCart());

    }

    @Test
    public void addingRandomNumbers() {
        new P02_landingPage(getDriver())
                .addRandomNumber(3, 6);
        Assert.assertTrue(new P02_landingPage(getDriver()).comparingNumberOfSelectedProductWithCart());

    }

    @Test
    public void clickOnCartIcon() throws IOException {
        new P02_landingPage(getDriver())
                .clickOnCartIcon();
        Assert.assertTrue(verifyURL(getDriver(), DataUtils.getPropertyValue("environment", "CARt_URL")));

    }

    @AfterMethod
    public void quit() {

        quitDriver();
    }

    @AfterClass
    public void deleteSession() {
        cookies.clear();
    }

}
