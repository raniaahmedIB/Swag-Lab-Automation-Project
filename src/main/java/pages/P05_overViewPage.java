package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.LogsUtils;
import utilities.Utility;

public class P05_overViewPage {
    private final By subTotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.xpath("//div[contains(@class,'summary_total_label')]");
    private final By finishButton = By.id("finish");
    private final WebDriver driver;

    public P05_overViewPage(WebDriver driver) {

        this.driver = driver;
    }

    public float getSubTotal() {
        return Float.parseFloat(Utility.getText(driver, subTotal).replace("Item total: $", ""));
    }

    public float getTax() {
        return Float.parseFloat(Utility.getText(driver, tax).replace("Tax: $", ""));
    }

    public float getTotal() {
        LogsUtils.info("actual total price :" + Utility.getText(driver, total).replace("Total: $", ""));

        return Float.parseFloat(Utility.getText(driver, total).replace("Total: $", ""));
    }

    public String calculatedTotalPrice() {
        LogsUtils.info("calculated total price :" + (getSubTotal() + getTax()));
        return String.valueOf(getSubTotal() + getTax());
    }

    public boolean comparingPrice() {
        return calculatedTotalPrice().equals(String.valueOf(getTotal()));
    }

    public P06_finishingOrderPage clickfinshButton() {
        Utility.clickingOnElement(driver, finishButton);
        return new P06_finishingOrderPage(driver);
    }
}
