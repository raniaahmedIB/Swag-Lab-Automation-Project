package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.Utility;

public class P01_LoginPage {
    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By LoginButton = By.id("login-button");
    private final WebDriver driver;

    public P01_LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public P01_LoginPage enterUserName(String usernameText) {
        Utility.sendData(driver, username, usernameText);
        return this;
    }

    public P01_LoginPage enterPassword(String PasswordText) {
        Utility.sendData(driver, password, PasswordText);
        return this;
    }

    public P02_landingPage clickLoginButton() {
        Utility.clickingOnElement(driver, LoginButton);
        return new P02_landingPage(driver);

    }

    public boolean assertLoginTc(String expectedValue) {
        return driver.getCurrentUrl().equals(expectedValue);
    }
}

