package Listeners;

import io.qameta.allure.Allure;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import utilities.LogsUtils;
import utilities.Utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static DreiverFactory.DriverFactory.getDriver;

public class IInvokedMethodListenerClass implements IInvokedMethodListener {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {

    }


    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        //  Utility.takeFullScreenshot(getDriver(), new P02_landingPage(getDriver()).getNumberOfProductsOnCartIcon());
        File logFile = Utility.getLastFile(LogsUtils.Logs_Path);
        try {
            assert logFile != null;
            Allure.addAttachment("logs.log", Files.readString(Path.of(logFile.getPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (testResult.getStatus() == ITestResult.FAILURE) {
            LogsUtils.info("Test Case" + testResult.getName() + "failed");
            Utility.takeScreenshot(getDriver(), testResult.getName());
        }
    }
}
