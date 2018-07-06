package wm.wmcanada.testscripts;

import static org.testng.Assert.assertTrue;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import config.FrameworkException;
import config.TestSetup;
import reusablecomponents.BusinessComponents;
import reusablecomponents.Utilities;

/**
 * Test Class to validate My Services section.
 * 
 * @author jkhanuja
 *
 */
public class TestCaseFile extends BusinessComponents {

	/**
	 * JavaDoc
	 */
	@Test(dataProvider = "DataProviderName", dataProviderClass = data.TestData.class)
	public void testCase1(testDesc, complexity, parameter1, parameter2, errorMessage) {
	setParametersPerTestCase(testDesc, complexity);
		if (toBeTested) {
			try {

				//Put your test functions here.
				
				if (errorMessage.equals("")) {
					logger.log(LogStatus.PASS, "Test Case: " + testDesc + " passed.");
					assertTrue(true, "Test Case: " + testDesc + " Passed");
				} else {
					logger.log(LogStatus.FAIL, "Expected Error Message but No error message encountered.");
					logger.log(LogStatus.FAIL, "Test Case: " + testDesc + " failed.");
					assertTrue(false, "Test Case: " + testDesc + " failed.");
				}
			} catch (FrameworkException e) {
				if (errorMessage.equals("")) {
					logger.log(LogStatus.FAIL, e.getMessage() + logger.addScreenCapture(screenshot(driver)));
					logger.log(LogStatus.FAIL, "Test Case: " + testDesc + " failed.");
					assertTrue(false, "Test Case: " + testDesc + " failed.");
				} else {
					try {
						logger.log(LogStatus.INFO, e.getMessage() + logger.addScreenCapture(screenshot(driver)));
						verifyErrorMessage(errorMessage);
						logger.log(LogStatus.PASS, "Test Case: " + testDesc + " passed.");
					} catch (FrameworkException ex) {
						logger.log(LogStatus.FAIL, ex.getMessage() + logger.addScreenCapture(screenshot(driver)));
						logger.log(LogStatus.FAIL, "Test Case: " + testDesc + " failed.");
						assertTrue(false, "Test Case: " + testDesc + " failed.");
					}

				}
			}
		} else {
			logger.log(LogStatus.SKIP,
					"Test Case: " + testDesc+"  skipped.");
			throw new SkipException(
					"Test Case: " + testDesc+"  skipped.");
		}
	}

}
