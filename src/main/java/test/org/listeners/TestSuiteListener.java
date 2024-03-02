package test.org.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import test.org.utils.AllureUtils;

public class TestSuiteListener implements ITestListener {
    protected final static Logger LOG = LogManager.getLogger(TestSuiteListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        LOG.info("Starting test: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info("Test passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOG.info("Test failed: " + result.getName());
        AllureUtils.screenshotAs("Failure screenshot");
        AllureUtils.pageSource();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.info("Test skipped: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        LOG.info("Starting suite: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        LOG.info("Finishing suite: " + context.getName());
    }
}
