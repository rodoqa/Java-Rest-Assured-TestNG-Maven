package listeners;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {

    public static final Logger logger = LogManager.getLogger(Listener.class.getName());
    @Override
    public void onTestStart(ITestResult result) {  }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info(result.getName() + " PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.fatal(result.getName() + " FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn(result.getName() + " SKIPPED");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.error(result.getName() + " FAILED %");
    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
