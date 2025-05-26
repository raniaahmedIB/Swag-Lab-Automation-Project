package tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.iTestResultListenerClass;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.P01_LoginPage;
import pages.P02_landingPage;
import pages.P03_cartPage;
import utilities.DataUtils;
import utilities.LogsUtils;

import java.io.IOException;
import java.time.Duration;

import static DreiverFactory.DriverFactory.*;
import static utilities.DataUtils.getPropertyValue;

@Listeners({IInvokedMethodListenerClass.class, iTestResultListenerClass.class})
public class T03_cartTest {
    private final String username = DataUtils.getJsonData("validLogin", "username");
    private final String password = DataUtils.getJsonData("validLogin", "password");

    @BeforeMethod
    public void setup() throws IOException {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : getPropertyValue("environment", "browser");
        LogsUtils.info(System.getProperty("browser"));
        setupDriver(browser);
        LogsUtils.info("Edge driver is open ");
        getDriver().get(getPropertyValue("environment", "base_URL"));
        LogsUtils.info("Pge redirected to URl");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void comparingPricing() throws IOException {
        String totalPrice = new P01_LoginPage(getDriver())
                .enterUserName(username)
                .enterPassword(password)
                .clickLoginButton()
                .addRandomNumber(3, 6)
                .getTotalPriceOfSelectedProducts();
        new P02_landingPage(getDriver()).clickOnCartIcon();
        Assert.assertTrue(new P03_cartPage(getDriver()).comparingPrice(totalPrice));
        System.out.println(totalPrice);

    }

    @AfterMethod
    public void quit() {

        quitDriver();
    }
}
