package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.Utility;

public class P04_checkoutPage {
    private final WebDriver driver;
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By zipCode = By.id("postal-code");
    private final By continueButton = By.id("continue");

    public P04_checkoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public P04_checkoutPage fillingInformationForm(String fName, String lName, String zip) {
        Utility.sendData(driver, firstName, fName);
        Utility.sendData(driver, lastName, lName);
        Utility.sendData(driver, zipCode, zip);
        return this;
    }

    public P05_overViewPage clickOnContinueButton() {
        Utility.clickingOnElement(driver, continueButton);
        return new P05_overViewPage(driver);
    }
}
