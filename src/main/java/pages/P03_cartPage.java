package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.LogsUtils;
import utilities.Utility;

import java.util.List;

public class P03_cartPage {
    static float totalPrice = 0;
    private final By priceOfSlectedProductslocator = By.xpath("//button[.=\"Remove\"] //preceding-sibling::div[@class='inventory_item_price']");
    private final By checkoutButton = By.id("checkout");
    private final WebDriver driver;

    public P03_cartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTotalPrice() {
        try {
            List<WebElement> priceOFSelectedProducts = driver.findElements(priceOfSlectedProductslocator);
            for (int i = 1; i <= priceOFSelectedProducts.size(); i++) {
                By element = By.xpath("(//button[.=\"Remove\"] //preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");
                String fullText = Utility.getText(driver, element);
                totalPrice += Float.parseFloat(fullText.replace("$", ""));
            }
            LogsUtils.info("total price:" + totalPrice);
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

    public boolean comparingPrice(String price) {
        return getTotalPrice().equals(price);
    }

    public P04_checkoutPage clickOnChickoutButton() {
        Utility.clickingOnElement(driver, checkoutButton);
        return new P04_checkoutPage(driver);
    }
}
