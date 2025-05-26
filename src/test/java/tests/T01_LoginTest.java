package tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.iTestResultListenerClass;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.P01_LoginPage;
import utilities.DataUtils;
import utilities.LogsUtils;

import java.io.IOException;
import java.time.Duration;

import static DreiverFactory.DriverFactory.*;
import static utilities.DataUtils.getPropertyValue;

@Listeners({IInvokedMethodListenerClass.class, iTestResultListenerClass.class})
public class T01_LoginTest {
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
    public void validloginTest() throws IOException {
        new
                P01_LoginPage(getDriver())
                .enterUserName(username)
                .enterPassword(password)
                .clickLoginButton();
        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLoginTc(getPropertyValue("environment", "Home_URL")));

    }

    @AfterMethod
    public void quit() {

        quitDriver();
    }

}

