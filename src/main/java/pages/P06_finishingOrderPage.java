package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static utilities.Utility.findWebElement;

public class P06_finishingOrderPage {
    private final WebDriver driver;
    private final By thanksMessageOrder = By.tagName("h2");

    public P06_finishingOrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean checkVisibilityOfThanksMessage() {
        return findWebElement(driver, thanksMessageOrder).isDisplayed();
    }
}
