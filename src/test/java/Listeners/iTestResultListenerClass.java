package Listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.LogsUtils;

public class iTestResultListenerClass implements ITestListener {

    public void onTestStart(ITestResult result) {
        LogsUtils.info("Test Case"+result.getName()+"Started");
    }

    public void onTestSuccess(ITestResult result) {
        LogsUtils.info("Test Case"+result.getName()+"passed");

    }


    public void onTestSkipped(ITestResult result) {
        LogsUtils.info("Test Case"+result.getName()+"skipped");


    }
}
