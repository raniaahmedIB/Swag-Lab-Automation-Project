package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.LogsUtils;
import utilities.Utility;

import java.util.List;
import java.util.Set;

public class P02_landingPage {
    static float totalPrice = 0;
    private static List<WebElement> allProducts;
    private static List<WebElement> selectProducts;
    private final By addToCardButtonForAllProduct = By.xpath("//button[@class]");
    private final By numberOfProductsSelected = By.xpath("//button[.='Remove']");
    private final By numberOfProductsOnCartIcon = By.className("shopping_cart_badge");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By priceOfSlectedProductslocator = By.xpath("//button[.=\"Remove\"] //preceding-sibling::div[@class='inventory_item_price']");
    private final WebDriver driver;

    public P02_landingPage(WebDriver driver) {
        this.driver = driver;
    }

    public By getNumberOfProductsOnCartIcon() {
        return numberOfProductsOnCartIcon;
    }

    public P02_landingPage addAllProductToCart() {
        allProducts = driver.findElements(addToCardButtonForAllProduct);
        LogsUtils.info("number of all products:" + allProducts.size());
        for (int i = 1; i <= allProducts.size(); i++) {

            By addToCardButtonForAllProduct = By.xpath("(//button[@class])[" + i + "]");
            Utility.clickingOnElement(driver, addToCardButtonForAllProduct);
        }
        return this;
    }

    public String getNumberOFProductOnCartItem() {
        try {
            LogsUtils.info("get number on cart:" + Utility.getText(driver, numberOfProductsOnCartIcon));
            return Utility.getText(driver, numberOfProductsOnCartIcon);

        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }

    }

    public String getNumberOfSelectProducts() {
        try {
            selectProducts = driver.findElements(numberOfProductsSelected);
            LogsUtils.info("selected products:" + ((selectProducts.size())));
            return String.valueOf((selectProducts.size()));
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
        }
        return "0";
    }

    public P02_landingPage addRandomNumber(int numberOfProductsNeeds, int totalOfProducts) {
        Set<Integer> randomNumbers = Utility.generateUniqueNumber(numberOfProductsNeeds, totalOfProducts);
        for (int random : randomNumbers) {
            LogsUtils.info("random Number :" + random);
            By addToCardButtonForAllProduct = By.xpath("(//button[@class])[" + random + "]");
            Utility.clickingOnElement(driver, addToCardButtonForAllProduct);
        }
        return this;
    }

    public boolean comparingNumberOfSelectedProductWithCart() {
        return getNumberOFProductOnCartItem().equals(getNumberOfSelectProducts());
    }

    public P03_cartPage clickOnCartIcon() {
        Utility.clickingOnElement(driver, cartIcon);
        return new P03_cartPage(driver);
    }

    public String getTotalPriceOfSelectedProducts() {
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
}
