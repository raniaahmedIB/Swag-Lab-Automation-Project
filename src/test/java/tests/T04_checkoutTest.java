package tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.iTestResultListenerClass;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.P01_LoginPage;
import utilities.DataUtils;
import utilities.LogsUtils;
import utilities.Utility;

import java.io.IOException;
import java.time.Duration;

import static DreiverFactory.DriverFactory.*;
import static utilities.DataUtils.getPropertyValue;

@Listeners({IInvokedMethodListenerClass.class, iTestResultListenerClass.class})

public class T04_checkoutTest {
    private final String username = DataUtils.getJsonData("validLogin", "username");
    private final String password = DataUtils.getJsonData("validLogin", "password");
    private final String FIRSTNAME = DataUtils.getJsonData("information", "fName") + "-" + Utility.getTimeSample();
    private final String LASTNAME = DataUtils.getJsonData("information", "lName") + "-" + Utility.getTimeSample();
    private final String ZIPCODE = new Faker().number().digits(5);

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
    public void checkoutOneTC() throws IOException {
        new P01_LoginPage(getDriver())
                .enterUserName(username)
                .enterPassword(password)
                .clickLoginButton()
                .addRandomNumber(3, 6)
                .clickOnCartIcon()
                .clickOnChickoutButton()
                .fillingInformationForm(FIRSTNAME, LASTNAME, ZIPCODE)
                .clickOnContinueButton();
        LogsUtils.info(FIRSTNAME + " " + LASTNAME + " " + ZIPCODE);
        Assert.assertTrue(Utility.verifyURL(getDriver(), getPropertyValue("environment", "Checkout_URl")));

    }

    @AfterMethod
    public void quit() {

        quitDriver();
    }

}
