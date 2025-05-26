package utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Utility {
    private static final String SCREENSHOT_PATH = "test-outputs/screenshots/";

    public static void clickingOnElement(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }

    public static void sendData(WebDriver driver, By locator, String data) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(data);
    }

    public static String getText(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).getText();
        return driver.findElement(locator).getText();
    }

    public static WebDriverWait generalWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public static void scrolling(WebDriver driver, By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", findWebElement(driver, locator));
    }

    public static WebElement findWebElement(WebDriver driver, By locator) {

        return driver.findElement(locator);

    }

    public static void selectingDropDown(WebDriver driver, By locator, String option) {
        new Select(findWebElement(driver, locator)).selectByVisibleText(option);

    }

    public static String getTimeSample() {
        return new SimpleDateFormat("yyyy-MM-dd-h-m-ss-a").format(new Date());
    }

    public static void takeScreenshot(WebDriver driver, String screenshotName) {
        try {
            //capture screenshot using TakesScreenshot
            File screensc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // save the screenshot to file if u need
            File screendest = new File(SCREENSHOT_PATH + screenshotName + "-" + getTimeSample() + ".png");
            FileUtils.copyFile(screensc, screendest);

            //attach the screenshot to allure
            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screendest.getPath())));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void takeFullScreenshot(WebDriver driver, By locator) {
        try {
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                    .highlight(findWebElement(driver, locator))
                    .save(SCREENSHOT_PATH);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
        }
    }

    public static int generateRandomNumber(int upperBound) {
        return new Random().nextInt(upperBound) + 1;
    }

    public static Set<Integer> generateUniqueNumber(int numberOfProductsNeeds, int totalOfProducts) {
        Set<Integer> generateNumbers = new HashSet<>();
        while (generateNumbers.size() < (numberOfProductsNeeds)) {
            int randomNumber = generateRandomNumber(totalOfProducts);
            generateNumbers.add(randomNumber);

        }
        return generateNumbers;
    }

    public static boolean verifyURL(WebDriver driver, String expctedURL) {
        try {
            generalWait(driver).until(ExpectedConditions.urlToBe(expctedURL));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static File getLastFile(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        assert files != null;
        if (files.length == 0)
            return null;
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        return files[0];
    }

    public static Set<Cookie> getAllCookies(WebDriver driver) {
        return driver.manage().getCookies();
    }

    public static void restoreSession(WebDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies)
            driver.manage().addCookie(cookie);
    }
}
