package test.org.listeners;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import java.io.IOException;

public class TestSuiteListener implements ITestListener {
    private static final String FAILED_TESTS_SUITE_FILE_NAME = "TestSuiteFailed.xml";
    private static final XmlSuite failSuite = new XmlSuite();
    private static final Logger LOG = LogManager.getLogger(TestSuiteListener.class);

    public TestSuiteListener() {
    }

}
