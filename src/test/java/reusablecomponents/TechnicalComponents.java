package reusablecomponents;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Flags.Flag;
import javax.mail.search.FlagTerm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import config.ElementIndex;
import config.FrameworkException;
import config.Mappings;
import config.TestSetup;

/**
 * Class for technical components, this class is utilized to perform actions on
 * application.
 * 
 * @author jkhanuja
 */
public class TechnicalComponents extends TestSetup {
	private static String linkFromEmail;
	private static HashMap<String, String> links = new HashMap<String, String>();

	/**
	 * Navigate to URL.
	 * 
	 * @param url
	 * @throws FrameworkException
	 */
	public static void navigatetoUrl(String url) throws FrameworkException {
		try {
			if (TestSetup.isFrench) {
				url = Mappings.getFrenchMapping(url);
			}
			driver.get(url);
			logger.log(LogStatus.PASS, "Navigated to URL : " + url);
			loggerForLogs.log(LogStatus.INFO, "Navigated to URL : " + url);
		} catch (Exception e) {
			throw new FrameworkException(
					"Unable to navigate to URL--- " + url + "---" + e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Function to type using keyboard keys. Mostly used to mimic shortcuts.
	 * 
	 * @param element
	 *            - Element on which action needs to be performed.
	 * @param text
	 *            - Keys that needs to be entered.
	 * @param desc
	 *            - Free text used to identify field.
	 * @throws FrameworkException
	 *             in case of Error.
	 */
	public static void type(WebElement element, Keys text, String desc) throws FrameworkException {
		try {
			if (element.isDisplayed()) {
				if (element.isEnabled()) {
					// clear(element, desc);
					element.sendKeys(text);
				} else {
					throw new FrameworkException("Field " + desc + " is disabled.");
				}
			} else {
				throw new FrameworkException("Field " + desc + " is not displayed.");
			}
			driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
			loggerForLogs.log(LogStatus.INFO, "Typed '" + text + "' to " + desc);
		} catch (NoSuchElementException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				type(element, text, desc);
			} else {
				throw new FrameworkException("Field " + desc + " not found.");
			}
		} catch (ElementNotVisibleException e) {
			scroll(element);
			type(element, text, desc);
		} catch (FrameworkException e) {
			throw new FrameworkException(e.getMessage());
		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown exception occured while typing on: " + desc + e.getClass() + "---" + e.getMessage());
		}

	}

	/**
	 * Function to type the text on field. This function will first clear the text
	 * on screen and will then enter text.
	 * 
	 * @param element
	 *            - Element on which action needs to be performed.
	 * @param text
	 *            - Text that needs to be entered.
	 * @param desc
	 *            - Free text used to identify field.
	 * @throws FrameworkException
	 *             in case of Error.
	 */
	public static void type(WebElement element, String text, String desc) throws FrameworkException {
		try {
			if (element.isDisplayed()) {
				if (element.isEnabled()) {
					element.clear();
					clear(element, desc);
					element.sendKeys(text);
				} else {
					throw new FrameworkException("Field " + desc + " is disabled.");
				}
			} else {
				throw new FrameworkException("Field " + desc + " is not displayed.");
			}
			driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
			loggerForLogs.log(LogStatus.INFO, "Typed '" + text + "' to " + desc);
		} catch (NoSuchElementException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				type(element, text, desc);
			} else {
				throw new FrameworkException("Field " + desc + " not found.");
			}
		} catch (ElementNotVisibleException e) {
			scroll(element);
			type(element, text, desc);
		} catch (FrameworkException e) {
			throw new FrameworkException(e.getMessage());
		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown exception occured while typing on: " + desc + e.getClass() + "---" + e.getMessage());
		}

	}

	/**
	 * Function to click on particular element.
	 * 
	 * @param element
	 *            - Element to be clicked.
	 * @param desc
	 *            - Free Text to identify field.
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static void click(WebElement element, String desc) throws FrameworkException {
		try {
			if (element.isDisplayed()) {
				if (element.isEnabled()) {
					// element.click();
					js.executeScript("arguments[0].click();", element);
				} else {
					throw new FrameworkException("Field: " + desc + " is disabled.");
				}
			} else {
				try {
					js.executeScript("arguments[0].click();", element);
				} catch (Exception ee) {
					throw new FrameworkException("Field: " + desc + " is not displayed on UI." + "---" + ee.getClass()
							+ "---" + ee.getMessage());
				}
			}
			driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
			loggerForLogs.log(LogStatus.INFO, desc + " clicked");
		} catch (NoSuchElementException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				click(element, desc);
			} else {
				throw new FrameworkException("Field " + desc + " not found.");
			}
		} catch (FrameworkException e) {
			throw new FrameworkException(e.getMessage());
		} catch (ElementNotVisibleException e) {
			scroll(element);
			click(element, desc);
		} catch (Exception e) {
			try {
				element.click();
			} catch (Exception ee) {
				throw new FrameworkException("Unknown exception occured while clicking: " + desc + "---" + ee.getClass()
						+ "---" + ee.getMessage());
			}
		}
	}

	/**
	 * Function to click on particular element.
	 * 
	 * @param element
	 *            - Element to be clicked.
	 * @param desc
	 *            - Free Text to identify field.
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static void click_exceptional(WebElement element, String desc) throws FrameworkException {
		try {
			if (element.isDisplayed()) {
				if (element.isEnabled()) {
					element.click();
					// js.executeScript("arguments[0].click();", element);
				} else {
					throw new FrameworkException("Field: " + desc + " is disabled.");
				}
			} else {
				try {
					js.executeScript("arguments[0].click();", element);
				} catch (Exception ee) {
					throw new FrameworkException("Field: " + desc + " is not displayed on UI." + "---" + ee.getClass()
							+ "---" + ee.getMessage());
				}
			}
			driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
			loggerForLogs.log(LogStatus.INFO, desc + " clicked");
		} catch (NoSuchElementException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				click_exceptional(element, desc);

			} else {
				throw new FrameworkException("Field " + desc + " not found.");
			}
		} catch (FrameworkException e) {
			throw new FrameworkException(e.getMessage());
		} catch (ElementNotVisibleException e) {
			scroll(element);
			click(element, desc);
		} catch (Exception e) {
			try {
				js.executeScript("arguments[0].click();", element);
			} catch (Exception ee) {
				throw new FrameworkException("Unknown exception occured while clicking: " + desc + "---" + ee.getClass()
						+ "---" + ee.getMessage());
			}
		}
	}

	/**
	 * Function to hover on particular element.
	 * 
	 * @param element
	 *            - Element to be hovered.
	 * @param desc
	 *            - Free Text to identify field.
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static void mouseHover(WebElement element, String desc) throws FrameworkException {
		try {
			if (element.isDisplayed()) {
				Actions a = new Actions(driver);
				a.moveToElement(element).perform();
				waitTill(1);
			} else {
				throw new FrameworkException("Field: " + desc + " is not displayed.");
			}
			driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
			loggerForLogs.log(LogStatus.INFO, "Hovered to " + desc);
		} catch (NoSuchElementException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				mouseHover(element, desc);
			} else {
				throw new FrameworkException("Field " + desc + " not found.");
			}
		} catch (FrameworkException e) {
			throw new FrameworkException(e.getMessage());
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while hovering to: " + desc + "---" + e.getClass()
					+ "---" + e.getMessage());
		}
	}

	/**
	 * Verify Attribute of particular field.
	 * 
	 * @param element
	 *            - Element on which validation needs to be performed.
	 * @param attributeDesc
	 *            - Attribute to be validated, performs both equals and contains
	 *            validation. Expected format 'AttributeName'-'validation to be
	 *            performed' Expected validation equals or contains, if not passed
	 *            then will perform exact match.
	 * @param value
	 *            - Expected value of Attribute.
	 * @param desc
	 *            - Free text to identify field.
	 * @throws FrameworkException
	 *             in case of failure.
	 */
	public static void verifyAttribute(WebElement element, String attributeDesc, String value, String desc)
			throws FrameworkException {
		try {
			if (value != null) {
				boolean result = false;
				String attribute;
				String match;
				isDisplayed(element, desc);
				if (TestSetup.isFrench) {
					value = Mappings.getFrenchMapping(value);
				}
				if (attributeDesc.split("-").length > 1) {
					attribute = attributeDesc.split("-")[0];
					match = attributeDesc.split("-")[1];
					switch (match.toLowerCase()) {
					case "equals":
						if (attribute.equals("text")) {
							result = element.getText().toLowerCase().trim().equals(value.toLowerCase().trim());
						} else {
							result = element.getAttribute(attribute).toLowerCase().trim()
									.equals(value.toLowerCase().trim());
						}
						break;
					case "contains":
						if (attribute.equals("text")) {
							result = element.getText().toLowerCase().contains(value.toLowerCase());
						} else {
							result = element.getAttribute(attribute).toLowerCase().contains(value.toLowerCase());
						}
						break;
					default:
						throw new FrameworkException("Attribute matching criteria not configured.");
					}
				} else {
					attribute = attributeDesc;
					if (attribute.equals("text")) {
						result = element.getText().toLowerCase().trim().equalsIgnoreCase(value.toLowerCase().trim());
					} else {
						result = element.getAttribute(attribute).toLowerCase().trim()
								.equalsIgnoreCase(value.toLowerCase().trim());
					}
				}
				if (result) {
					logger.log(LogStatus.PASS, attribute.toUpperCase() + " matched for " + desc.toUpperCase());
				} else {
					throw new FrameworkException("Expected " + attribute.toUpperCase() + " for " + desc + " is: '"
							+ value + "' Actual " + attribute.toUpperCase() + " is: --- text = '" + element.getText()
							+ "' ---- attribute ='" + element.getAttribute(attribute) + "'");
				}
				driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
				loggerForLogs.log(LogStatus.INFO, "Attribute " + attributeDesc + " matched for " + desc);
			}
		} catch (NoSuchElementException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				verifyAttribute(element, attributeDesc, value, desc);
			} else {
				throw new FrameworkException("Field " + desc + " not found.");
			}
		}
	}

	/**
	 * Get the attribute value of particular element.
	 * 
	 * @param element
	 *            - Element for which attribute value needs to be retrieved.
	 * @param attribute
	 *            - Attribute to be retrieved.
	 * @param desc
	 *            - Free text to identify field.
	 * @return - Returns attribute value for the element. Returns "" in case value
	 *         is null.
	 * @throws FrameworkException
	 *             in case of failure.
	 */
	public static String getAttribute(WebElement element, String attribute, String desc) throws FrameworkException {
		// isDisplayed(element,desc);
		try {
			String value;
			if (attribute.equals("text")) {
				if (element.getText() != null) {
					value = element.getText();
				} else {
					value = "";
				}
			} else {
				if (element.getAttribute(attribute) != null) {
					value = element.getAttribute(attribute);
				} else {
					value = "";
				}
			}
			driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
			loggerForLogs.log(LogStatus.INFO, "Attribute " + attribute + " for " + desc + " returned " + value);
			return value;
		} catch (NoSuchElementException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				return getAttribute(element, attribute, desc);
			} else {
				throw new FrameworkException("Field " + desc + " not found.");
			}
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while retrieving Attribute: "
					+ attribute.toUpperCase() + " value for " + desc + "---" + e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Function to wait for any element to be visible, invisible or enable.
	 * 
	 * @param element
	 *            - Element to be looked for.
	 * @param state
	 *            - Expected state of Element. Expected values: "visible", "enable",
	 *            "invisible"
	 * @throws FrameworkException
	 *             - in case of error.
	 */
	public static void waitTill(WebElement element, String state) throws FrameworkException {

		try {
			switch (state.toLowerCase()) {
			case "visible":
				wait.until(ExpectedConditions.visibilityOf(element));
				break;
			case "enable":
				wait.until(ExpectedConditions.elementToBeClickable(element));
				break;
			case "invisible":
				wait.until(ExpectedConditions.invisibilityOf(element));
				break;
			default:
				wait.until(ExpectedConditions.visibilityOf(element));
			}
			loggerForLogs.log(LogStatus.INFO, "Element " + element.toString() + " " + state);
		} catch (StaleElementReferenceException e) {
			if (timeOut > 0) {
				timeOut--;
				waitTill(element, state);
			} else {
				throw new FrameworkException(
						"Page refreshed while waiting for element : *  '" + element.toString() + "'");
			}
		} catch (TimeoutException e) {
			throw new FrameworkException(
					"Element : *  '" + element.toString() + "' not found within defined time limit.");
		} catch (NoSuchElementException e) {
			throw new FrameworkException("Element : *  '" + element.toString() + "' not found in DOM.");
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while waiting for element: *  '"
					+ element.toString() + "'---" + e.getClass() + "---" + e.getMessage());
		}

	}

	/**
	 * Function to wait for element to contains expected text.
	 * 
	 * @param element
	 *            - Element where text is expected.
	 * @param state
	 *            - should be text in order to validate presence of text. Expected
	 *            state can be: visible, invisible, text or enable.
	 * @param text
	 *            - Expected text.
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static void waitTill(WebElement element, String state, String text) throws FrameworkException {
		try {
			if (TestSetup.isFrench) {
				text = Mappings.getFrenchMapping(text);
			}
			if (state != "text") {
				waitTill(element, state);
			} else {
				wait.until(ExpectedConditions.textToBePresentInElement(element, text));
			}
			loggerForLogs.log(LogStatus.INFO, "Element(with text) " + element.toString() + " " + state);
		} catch (TimeoutException e) {
			throw new FrameworkException("Element : " + element.toString() + " not found within defined time limit.");
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception while selecting: " + element.toString() + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Function to save screenshot.
	 * 
	 * @param driver
	 *            - driver instance to which screenshot needs to be taken.
	 * @return file name of the file.
	 * @throws FrameworkException
	 *             in case of error
	 */
	public static String screenshot(WebDriver driver) throws FrameworkException {

		// try {
		// String src_path = Utilities.getProperty("SCREENSHOTS_LOCATION_FOR_RUN");
		// Robot robot = new Robot();
		// String format = ".jpeg";
		// String fileName = src_path + Utilities.gettimestamp("local").replace("-",
		// "").replace(":", "") + format;
		//
		// Rectangle screenRect = new
		// Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		// BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
		// ImageIO.write(screenFullImage, format, new File(fileName));
		// return fileName;
		// } catch (Exception e) {
		// throw new FrameworkException("Not able to take screenshot.---" + e.getClass()
		// + "---" + e.getMessage());
		// }

		try {
			String src_path = Utilities.getProperty("SCREENSHOTS_LOCATION_FOR_RUN");

			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(scrFile,
					new File(src_path + Utilities.getTimeStamp("local").replace("-", "").replace(":", "") + ".jpeg"));

			return src_path + Utilities.getTimeStamp("local").replace("-", "").replace(":", "") + ".jpeg";
		} catch (Exception e) {
			throw new FrameworkException("Not able to take screenshot.---" + e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Select value from drop down values where list is dynamic.
	 * 
	 * @param dd_Options
	 *            - List with drop down values.
	 * @param value
	 *            - value to be selected.
	 * @param isDynamic
	 *            - true if list is dynamic else false. Will wait for 3 secs for
	 *            list to be updated in case value is not found.
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static void selectValuefromDropdown(List<WebElement> dd_Options, String value, boolean isDynamic)
			throws FrameworkException {
		WebElement option;
		boolean found = false;
		String listItems = "";
		if (TestSetup.isFrench) {
			value = Mappings.getFrenchMapping(value);
		}
		// logger.log(LogStatus.INFO, Utilities.gettimestamp("local") +
		// logger.addScreenCapture(screenshot(driver)));
		try {
			if (value != "") {
				waitTill(dd_Options.get(0), "enable");
				for (int i = 0; i < dd_Options.size(); i++) {
					option = dd_Options.get(i);
					listItems += getAttribute(option, "text", "List Item") + " : ";
					if (option.getText().equalsIgnoreCase(value)) {
						click(option, value);
						found = true;
						break;
					}
				}
			}
			if (!found && isDynamic) {
				waitTill(5);
				// refreshPage();
				selectValuefromDropdown(dd_Options, value, false);
			}
			if (!found && !isDynamic) {
				throw new FrameworkException("Value '" + value + "' not found. Values from UI are " + listItems);
			}
			driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
			loggerForLogs.log(LogStatus.INFO, "Selected value '" + value + "' from list. ");
		} catch (IndexOutOfBoundsException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				selectValuefromDropdown(dd_Options, value, isDynamic);

			} else {
				throw new FrameworkException(
						"Drop down options not loaded in specified time while looking for " + value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new FrameworkException(
					"Unknown exception while selecting: " + value + "---" + e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Select value from drop down values.
	 * 
	 * @param dd_Options
	 *            - List with drop down values.
	 * @param value
	 *            - value to be selected.
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static void selectValuefromDropdown(List<WebElement> dd_Options, String value) throws FrameworkException {

		selectValuefromDropdown(dd_Options, value, false);
	}

	/**
	 * Get element at run time using locator type and locator value.
	 * 
	 * @param locatorType
	 *            - locator Type to be used to identify element, Expected: xpath,
	 *            id, link text
	 * @param locator
	 *            - locator value to be utilized to identify object.
	 * @return element retrieved.
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static WebElement getElement(String locatorType, String locator) throws FrameworkException {
		try {
			if (locatorType.equalsIgnoreCase("xpath")) {
				return driver.findElement(By.xpath(locator));
			} else if (locatorType.equalsIgnoreCase("id")) {
				return driver.findElement(By.id(locator));
			} else if (locatorType.equalsIgnoreCase("link text")) {
				return driver.findElement(By.linkText(locator));
			} else {
				throw new FrameworkException("Locator Type: " + locatorType + " not configured.");
			}
		} catch (NoSuchElementException e) {
			throw new FrameworkException("Not able to find element from '" + locatorType + "' and locator :" + locator);
		}
	}

	/**
	 * Get element and index from the start of list based on attribute. Works for
	 * both exact attribute match and partial attribute match.
	 * 
	 * @param elements
	 *            - list of elements from which particular element needs to be
	 *            fetched.
	 * @param attributeDesc
	 *            - Attribute to be matched, performs both equals and contains
	 *            match. Expected format 'AttributeName'-'validation to be
	 *            performed' Expected validation equals or contains, if not passed
	 *            then will perform exact match.
	 * @param value
	 *            - value of attribute to be matched.
	 * @param listName
	 *            - Free text to identify list name.
	 * @return element and index of element. Index will be based on consideration
	 *         that first element will start from 0.
	 * @throws FrameworkException
	 *             in case of Failure.
	 */
	public static ElementIndex getElementFromList(List<WebElement> elements, String attributeDesc, String value,
			String listName) throws FrameworkException {
		WebElement element = null;
		ElementIndex elementIndex = null;
		boolean found = false;
		String attribute, match, listItems = "";
		if (TestSetup.isFrench) {
			value = Mappings.getFrenchMapping(value);
		}
		try {
			if (attributeDesc.split("-").length > 1) {
				attribute = attributeDesc.split("-")[0];
				match = attributeDesc.split("-")[1];
			} else {
				attribute = attributeDesc;
				match = "equals";
			}
			if (elements.size() > 0) {
				for (int i = 0; i < elements.size(); i++) {
					element = elements.get(i);
					listItems += getAttribute(element, attribute, "List Item") + " : ";
					switch (match.toLowerCase()) {
					case "equals":

						if (attribute.equals("text")) {
							if (element.getText().trim().equalsIgnoreCase(value.trim())) {
								elementIndex = new ElementIndex(element, i);
								found = true;
							}
						} else {
							if (element.getAttribute(attribute).trim().equalsIgnoreCase(value.trim())) {
								elementIndex = new ElementIndex(element, i);
								found = true;
							}
						}
						break;
					case "contains":
						if (attribute.equals("text")) {
							if (element.getText().trim().contains(value.trim())) {
								elementIndex = new ElementIndex(element, i);
								found = true;
							}
						} else {
							if (element.getAttribute(attribute).trim().contains(value.trim())) {
								elementIndex = new ElementIndex(element, i);
								found = true;
							}
						}
						break;
					default:
						throw new FrameworkException("Attribute matching criteria not configured.");
					}
					if (found) {
						break;
					}
				}

			} else {
				if (driverWait > 0) {
					driverWait--;
					waitTill(1);
					return getElementFromList(elements, attributeDesc, value, listName);
				} else {
					throw new FrameworkException("List: " + listName + " not found.");
				}
			}

		} catch (FrameworkException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				return getElementFromList(elements, attributeDesc, value, listName);
			} else {
				throw new FrameworkException(e.getMessage());
			}

		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while getting list value from: " + listName + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
		if (elementIndex != null) {
			driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
			loggerForLogs.log(LogStatus.INFO, "Element returned based on attribute " + attributeDesc + " and value "
					+ value + "List Items are " + listItems);
			return elementIndex;
		} else {
			throw new FrameworkException(
					"Not able to locate: " + value + " in " + listName + "  Values from UI are " + listItems);
		}
	}

	/**
	 * Get element and index from the end of list based on attribute. Works for both
	 * exact attribute match and partial attribute match.
	 * 
	 * @param elements
	 *            - list of elements from which particular element needs to be
	 *            fetched.
	 * @param attributeDesc
	 *            - Attribute to be matched, performs both equals and contains
	 *            match. Expected format 'AttributeName'-'validation to be
	 *            performed' Expected validation equals or contains, if not passed
	 *            then will perform exact match.
	 * @param value
	 *            - value of attribute to be matched.
	 * @param listName
	 *            - Free text to identify list name.
	 * @return element and index of element. Index will be based on consideration
	 *         that first element will start from 0.
	 * @throws FrameworkException
	 *             in case of Failure.
	 */
	public static ElementIndex getElementFromListInReverseOrder(List<WebElement> elements, String attributeDesc,
			String value, String listName) throws FrameworkException {
		WebElement element = null;
		ElementIndex elementIndex = null;
		boolean found = false;
		String attribute, match, listItems = "";
		if (TestSetup.isFrench) {
			value = Mappings.getFrenchMapping(value);
		}
		try {
			if (attributeDesc.split("-").length > 1) {
				attribute = attributeDesc.split("-")[0];
				match = attributeDesc.split("-")[1];
			} else {
				attribute = attributeDesc;
				match = "equals";
			}
			if (elements.size() > 0) {
				for (int i = elements.size() - 1; i >= 0; i--) {
					element = elements.get(i);
					listItems += getAttribute(element, attribute, "List Item") + " : ";
					switch (match.toLowerCase()) {
					case "equals":

						if (attribute.equals("text")) {
							if (element.getText().trim().equalsIgnoreCase(value.trim())) {
								elementIndex = new ElementIndex(element, i);
								found = true;
							}
						} else {
							if (element.getAttribute(attribute).trim().equalsIgnoreCase(value.trim())) {
								elementIndex = new ElementIndex(element, i);
								found = true;
							}
						}
						break;
					case "contains":
						if (attribute.equals("text")) {
							if (element.getText().trim().contains(value.trim())) {
								elementIndex = new ElementIndex(element, i);
								found = true;
							}
						} else {
							if (element.getAttribute(attribute).trim().contains(value.trim())) {
								elementIndex = new ElementIndex(element, i);
								found = true;
							}
						}
						break;
					default:
						throw new FrameworkException("Attribute matching criteria not configured.");
					}
					if (found) {
						break;
					}
				}
			} else {
				if (driverWait > 0) {
					driverWait--;
					waitTill(1);
					return getElementFromListInReverseOrder(elements, attributeDesc, value, listName);
				} else {
					throw new FrameworkException("List: " + listName + " not found.");
				}
			}

		} catch (FrameworkException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				return getElementFromListInReverseOrder(elements, attributeDesc, value, listName);
			} else {
				throw new FrameworkException(e.getMessage());
			}
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while getting list value from: " + listName + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
		if (elementIndex != null) {
			driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
			loggerForLogs.log(LogStatus.INFO, "Element returned based on attribute " + attributeDesc + " and value "
					+ value + "List Items are " + listItems);
			return elementIndex;
		} else {
			throw new FrameworkException(
					"Not able to locate: " + value + " in " + listName + "  Values from UI are " + listItems);
		}
	}

	/**
	 * Get element from the start of list based on index.
	 * 
	 * @param elements
	 *            - list of elements from which particular element needs to be
	 *            fetched.
	 * @param index
	 *            - index from which element needs to be retrieved.
	 * @param listName
	 *            - Free text to identify list name.
	 * @return element at index
	 * @throws FrameworkException
	 *             in case of Failure.
	 */
	public static WebElement getElementFromList(List<WebElement> elements, int index, String listName)
			throws FrameworkException {
		try {
			if (elements.size() > 0) {
				// scroll(elements.get(index));
				driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
				return elements.get(index);
			} else {
				if (driverWait > 0) {
					driverWait--;
					waitTill(1);
					return getElementFromList(elements, index, listName);
				} else {
					throw new FrameworkException("List: " + listName + " not found.");
				}

			}
		} catch (FrameworkException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				return getElementFromList(elements, index, listName);
			} else {
				throw new FrameworkException(e.getMessage());
			}
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while getting list value from: " + listName + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Verify option values from a list. This list can be anything with common
	 * linkage viz dropdown options, header values, portlets etc.
	 * 
	 * @param optionValues
	 *            - List of elements that needs to be validated.
	 * @param optionValuesFromData
	 *            - expected option values separated by ':'
	 * @param optionName
	 *            - Free text to identify option list.
	 * @throws FrameworkException
	 *             in case of failure. This will also fail in case option values
	 *             count do not match or if any of the expected option is missing.
	 */
	public static void verifyOptionValues(List<WebElement> optionValues, String optionValuesFromData, String optionName)
			throws FrameworkException {
		String[] optionValuefromdata = optionValuesFromData.split(":");
		WebElement optionValueOnUI;
		String result = "";
		boolean found = false;
		if (optionValues.size() > 0) {
			if (optionValuefromdata.length == optionValues.size()) {
				try {
					waitTill(optionValues.get(optionValuefromdata.length - 1), "visible");
					for (int i = 0; i < optionValuefromdata.length; i++) {
						if (TestSetup.isFrench) {
							optionValuefromdata[i] = Mappings.getFrenchMapping(optionValuefromdata[i]);
						}
						found = false;
						for (int j = 0; j < optionValues.size(); j++) {
							optionValueOnUI = optionValues.get(j);
							// System.out.println(unescapeJava(TechnicalComponents.getAttribute(optionValueOnUI,
							// "text", "")));
							if (optionValuefromdata[i].toLowerCase().trim()
									.equalsIgnoreCase(optionValueOnUI.getText().toLowerCase().trim())) {
								found = true;
								break;
							}
						}
						if (!found) {
							result += optionValuefromdata[i] + " : ";
						}
					}
					driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
				} catch (IndexOutOfBoundsException e) {
					if (driverWait > 0) {
						driverWait--;
						waitTill(1);
						verifyOptionValues(optionValues, optionValuesFromData, optionName);
					} else {
						throw new FrameworkException("Options not loaded in specified time.");
					}
				} catch (Exception e) {
					throw new FrameworkException("Unknown exception occured while verifying option values for: "
							+ optionName + "---" + e.getClass() + "---" + e.getMessage());
				}
			} else {
				throw new FrameworkException("Values for " + optionName + "mismatch. Expected "
						+ optionValuefromdata.length + " values but found " + optionValues.size());
			}
		} else {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				verifyOptionValues(optionValues, optionValuesFromData, optionName);
			} else {
				throw new FrameworkException("Options not loaded in specified time.");
			}
		}

		if (result != "") {
			throw new FrameworkException(optionName + " Values: " + result + " not found");
		} else {
			logger.log(LogStatus.PASS, "Values matched for: " + optionName);
		}
	}

	/**
	 * Brings element to the top of screen and then scroll down a bit to get element
	 * on screen.
	 * 
	 * @param element
	 *            - element to which control should be passed.
	 * @throws FrameworkException
	 *             in case of failure.
	 */
	public static void scroll(WebElement element) throws FrameworkException {
		try {
			js.executeScript("arguments[0].scrollIntoView(true);", element);
			js.executeScript("window.scrollBy(0,-250)", "");
			driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
			loggerForLogs.log(LogStatus.INFO, "Scrolled to element " + element.toString());
		} catch (NoSuchElementException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				scroll(element);
			} else {
				throw new FrameworkException(element.toString() + " not found while scrolling to element.");
			}

		} catch (Exception e) {
			throw new FrameworkException("Unknown exception encountered while scrolling to " + element.toString()
					+ "---" + e.getClass() + "---" + e.getMessage());
		}

		/*
		 * do { js.executeScript("window.scrollBy(0,-100)",""); height = height/10;
		 * }while(element.isDisplayed() && height > 0);
		 */
	}

	/**
	 * Verify if element is displayed or not. Will wait for element till it gets
	 * displayed or till timeout is reached whichever is lower.
	 * 
	 * @param element
	 *            - expected element.
	 * @param desc
	 *            - Free text to identify element.
	 * @throws FrameworkException
	 *             in case of failure.
	 */
	public static void isDisplayed(WebElement element, String desc) throws FrameworkException {
		try {
			waitTill(element, "visible");
			loggerForLogs.log(LogStatus.INFO, "Element " + desc + " displayed.");
		} catch (FrameworkException e) {
			throw new FrameworkException(
					"Field: '" + desc + "' not displayed in defined Time Limit.---" + e.getMessage());
		}
	}

	/**
	 * Verify if element is selected or not.
	 * 
	 * @param element
	 *            - expected element.
	 * @param desc
	 *            - Free text to identify element.
	 * @return true if element is selected else false
	 * @throws FrameworkException
	 *             in case of failure.
	 */
	public static boolean isSelected(WebElement element, String desc) throws FrameworkException {
		try {
			if (element.isDisplayed()) {
				driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
				return element.isSelected();
			} else {
				throw new FrameworkException(desc + "is not displayed.");
			}
		} catch (NoSuchElementException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				return isSelected(element, desc);
			} else {
				throw new FrameworkException("Field: '" + desc + "' not available in DOM.");
			}
		} catch (Exception e) {
			throw new FrameworkException("Unknown error encountered while validating selection of " + desc + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Verify the state of element as enabled or disabled.
	 * 
	 * @param element
	 *            - Element for which state needs to be validated.
	 * @param expectedState
	 *            - Expected state of element. enable or disable
	 * @param fieldDesc
	 *            - Free text to identify field.
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static void verifyElementState(WebElement element, String expectedState, String fieldDesc)
			throws FrameworkException {
		try {
			waitTill(element, "visible");
			if (expectedState.equalsIgnoreCase("enable")) {
				if (element.isEnabled()) {
					logger.log(LogStatus.PASS,
							"Field '" + fieldDesc + "' verified successfully and is " + expectedState + ".");
				} else {
					throw new FrameworkException("Field '" + fieldDesc + "' is not present in desired state.");
				}
			} else {
				if (element.isEnabled()) {
					throw new FrameworkException("Field '" + fieldDesc + "' is not present in desired state.");
				} else {
					logger.log(LogStatus.PASS,
							"Field '" + fieldDesc + "' verified successfully and is " + expectedState + ".");
				}
			}
		} catch(FrameworkException e) {
			throw new FrameworkException(e.getMessage());
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while verifying states for: " + fieldDesc + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Verify the state of element as displayed or not displayed.
	 * 
	 * @param element
	 *            - Element for which state needs to be validated.
	 * @param expectedState
	 *            - Expected state of element. displayed or not displayed
	 * @param fieldDesc
	 *            - Free text to identify field.
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static void verifyElementPresence(WebElement element, String expectedState, String fieldDesc)
			throws FrameworkException {
		try {

			if (expectedState.equalsIgnoreCase("displayed")) {
				isDisplayed(element, fieldDesc);
			} else {
				try {
					if (element.isDisplayed()) {
						throw new FrameworkException("Element " + fieldDesc + " is being displayed.");
					}
				} catch (NoSuchElementException e) {

				}
			}
		} catch (Exception e) {
			if (e.getMessage().equals("Element " + fieldDesc + " is being displayed.") || e.getMessage()
					.contains("Field: '" + fieldDesc + "' not displayed in defined Time Limit.")) {
				throw new FrameworkException(e.getMessage());
			}
			throw new FrameworkException("Unknown exception occured while verifying states for: " + fieldDesc + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Refreshes the page.
	 * 
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static void refreshPage() throws FrameworkException {
		try {
			driver.navigate().refresh();
			loggerForLogs.log(LogStatus.INFO, "Page Refreshed");
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception occured while refreshing the page." + "---" + e.getClass()
					+ "---" + e.getMessage());
		}
	}
	/*
	 * public static void openNewTab() throws FrameworkException{ Robot rb; try { rb
	 * = new Robot(); rb.keyPress(KeyEvent.VK_CONTROL); rb.keyPress(KeyEvent.VK_T);
	 * 
	 * } catch (AWTException e1) { throw new
	 * FrameworkException("Not able to Open New Tab."); }
	 * //js.executeScript("window.open('about:blank','_blank');","")â€Œâ€‹;
	 * //getElement("xpath", "//body").sendKeys(Keys.CONTROL + "\t"); for(String
	 * handle : driver.getWindowHandles()) {
	 * if(!handle.equals(Utilities.getProperty("PARENT_HANDLE"))) {
	 * driver.switchTo().window(handle); } } }
	 */

	public static void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	public static void closeCurrentwindow() {
		driver.close();
	}

	// Code From Deepak 10/16/2017

	public static void explicitWait(WebElement element, int a) {
		WebDriverWait ww = new WebDriverWait(driver, a);
		ww.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void navigateToTab() {
		driver.navigate().back();
	}

	public static void switchToTab(String tabName) {
		ArrayList<String> tab = new ArrayList<>(driver.getWindowHandles());
		ArrayList<String> tabList = new ArrayList<>();
		for (int i = 0; i < tab.size(); i++) {
			tabList.add(i, driver.switchTo().window(tab.get(i)).getTitle());
			driver.switchTo().window(tab.get(0));

			if (tabList.get(i).contains(tabName)) {
				driver.switchTo().window(tab.get(i));

			} else {

				logger.log(LogStatus.FAIL, "No such title present for  " + tabName);

			}
		}
	}

	/**
	 * Wait till defined time limit. Time unit is in seconds.
	 * 
	 * @param time
	 *            - Expected wait time.
	 */
	public static void waitTill(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {

		}
	}

	public static void implicitwait(int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public static void maximizescreen() {
		driver.manage().window().maximize();
	}

	public static void switchtoiframe(String name) throws FrameworkException {
		driver.switchTo().frame(name);
	}

	public static void Actionevent(WebElement element) throws FrameworkException {

		try {
			Actions action = new Actions(driver).doubleClick(element);
			action.build().perform();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * Clears the field.
	 * 
	 * @param element
	 *            - Field to be cleared.
	 * @param desc
	 *            - Free text to identify field.
	 * @throws FrameworkException
	 *             in case of failure.
	 */
	public static void clear(WebElement element, String desc) throws FrameworkException {
		try {
			click(element, desc);
			type(element, Keys.END, desc);
			do {
				type(element, Keys.BACK_SPACE, desc);
				type(element, Keys.DELETE, desc);
			} while (!element.getAttribute("value").equals("") || !element.getText().equals(""));
			driverWait = Long.parseLong(Utilities.getProperty("IMPLICIT_WAIT"));
			loggerForLogs.log(LogStatus.INFO, desc + " cleared.");
		} catch (NoSuchElementException e) {
			if (driverWait > 0) {
				driverWait--;
				waitTill(1);
				clear(element, desc);
			} else {
				throw new FrameworkException("Element " + desc + " not found while clearing element.");
			}
		} catch (Exception e) {
			throw new FrameworkException("Unknown exception encountered while clearing " + desc + "---" + e.getClass()
					+ "---" + e.getMessage());
		}
	}

	/**
	 * Sets the logger test description to the one from sheet and customize waits
	 * per test case complexity
	 * 
	 * @param testDesc
	 *            - Test Description to be entered.
	 * @param complexity
	 *            - Complexity of test case i.e. high, medium or low. high will have
	 *            maximum wait and will be reduced from high till low.
	 * @throws FrameworkException
	 *             in case of error.
	 */
	public static void setParametersPerTestCase(String testDesc, String complexity) throws FrameworkException {

		logger.getTest().setName(logger.getTest().getName() + "---" + testDesc);
		loggerForLogs.getTest().setName(loggerForLogs.getTest().getName() + "---" + testDesc);
		if (driver != null) {
			if (complexity.toLowerCase().equals("high")) {
				timeOut = Long.parseLong(Utilities.getProperty("TIME_OUT"));
			} else if (complexity.toLowerCase().equals("medium")) {
				timeOut = Long.parseLong(Utilities.getProperty("TIME_OUT_MEDIUM"));
			} else if (complexity.toLowerCase().equals("low")) {
				timeOut = Long.parseLong(Utilities.getProperty("TIME_OUT_LOW"));
			}

			wait = new WebDriverWait(driver, timeOut);
		}
		logger.setDescription(testDesc + "---" + complexity + "---" + timeOut);
		loggerForLogs.setDescription(testDesc + "---" + complexity + "---" + timeOut);
	}

	/**
	 * Function to switch to new window by using windows title name.
	 * 
	 * @param title
	 *            - title of the window to be switched.
	 * @exception FrameworkException
	 *                in case window is not found.
	 */
	public static void switchToNewWindow(String title) throws FrameworkException {
		boolean windowFound = false;
		String parentWindow = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		for (String windowHandle : handles) {
			if (!windowHandle.equals(parentWindow)) {
				if (driver.switchTo().window(windowHandle).getTitle().contains(title)) {
					driver.switchTo().window(windowHandle);
					windowFound = true;
					break;
				}
			}
		}
		if (!windowFound) {
			throw new FrameworkException("Window " + title + " not found.");
		}
	}

	/**
	 * Verify email being sent to User.
	 * 
	 * @param emailTo
	 *            - Email Id to which email has been sent
	 * @param from
	 *            - Email from which email has been sent
	 * @param subject
	 *            - Subject of the email
	 * @param content
	 *            - Content to be verified
	 * @param toBeVerified
	 *            - true in case email needs to be verified else false
	 * @throws FrameworkException
	 *             - in case of error
	 */
	public static void verifyEmail(String emailTo, String from, String subject, String content[], boolean toBeVerified)
			throws FrameworkException {
		boolean emailFound = false;

		if (!TestSetup.emailVerification && !toBeVerified) {
			throw new FrameworkException("As Configured, Email Not verified.");
		}
		try {
			if (TestSetup.isFrench) {
				subject = Mappings.getFrenchMapping(subject);
				for (int j = 0; j < content.length; j++) {
					content[j] = Mappings.getFrenchMapping(content[j]);
				}
			}
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "imaps");
			emailSession = Session.getDefaultInstance(properties);
			store = emailSession.getStore("imaps");
			if (emailTo.toLowerCase().equals("customer")) {
				store.connect(Utilities.getProperty("EMAIL_TO_HOST"),
						Utilities.getProperty("EMAIL_TO_CUSTOMER_USERNAME"),
						Utilities.getProperty("EMAIL_TO_CUSTOMER_PASSWORD"));
			} else {
				store.connect(Utilities.getProperty("EMAIL_TO_HOST"), Utilities.getProperty("EMAIL_TO_CSR_USERNAME"),
						Utilities.getProperty("EMAIL_TO_CSR_PASSWORD"));
			}

			emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_WRITE);
			Message[] messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
			loggerForLogs.log(LogStatus.INFO, "Waiting for Email with Subject " + subject + "and sender " + from
					+ "... Messages retrieved: " + messages.length + "---TimeOut: " + emailTimeOut);
			if (messages.length > 0) {
				for (int i = messages.length - 1; i >= 0; i--) {
					Message message = messages[i];
					loggerForLogs.log(LogStatus.INFO, "Email # " + i + "---Sender: " + message.getFrom()[0].toString()
							+ "---Subject: " + message.getSubject());
					if (message.getFrom()[0].toString().contains(from) && message.getSubject().contains(subject)) {
						emailFound = true;
						loggerForLogs.log(LogStatus.INFO, message.getContent().toString());

						String messageContent = message.getContent().toString()
								.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*|\\r|\\n|&nbsp;", "").replace("amp;", "");
						for (int j = 0; j < content.length; j++) {
							if (messageContent.replace(" ", "").contains(content[j].replace(" ", ""))) {
								message.setFlag(Flag.SEEN, true);
							} else {
								loggerForLogs.log(LogStatus.INFO,
										"Not able to locate '" + content[j] + "' in above email.");
								emailFound = false;
								break;
							}
						}
						if (emailFound) {
							String desc = message.getContent().toString();
							Pattern linkPattern = Pattern.compile("<a\\b[^>]*href=\"[^>]*>(.*?)</a>",
									Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
							Matcher pageMatcher = linkPattern.matcher(desc);

							while (pageMatcher.find()) {
								links.put(
										pageMatcher.group(1).toLowerCase()
												.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*|\\r|\\n|&nbsp;", "")
												.replace("amp;", "").trim(),
										StringUtils.substringBetween(pageMatcher.group(0), "href=\"", "\""));
							}
							loggerForLogs.log(LogStatus.INFO, "Email verified successfully.");
						}
					}
				}
			}

			if (!emailFound)
				if (emailTimeOut > 0) {
					loggerForLogs.log(LogStatus.INFO, "Waiting for Email..." + emailTimeOut);
					emailTimeOut -= 3;
					store.close();
					verifyEmail(emailTo, from, subject, content, toBeVerified);
				} else {
					throw new FrameworkException("Email not found within defined limit.");
				}
		} catch (MessagingException | IOException | IllegalStateException e) {
			if (!emailFound && emailTimeOut > 0) {
				loggerForLogs.log(LogStatus.INFO, "Waiting for Email..." + emailTimeOut);
				emailTimeOut -= 3;
				try {
					store.close();
				} catch (MessagingException e1) {

				}
				verifyEmail(emailTo, from, subject, content, toBeVerified);
			} else {
				throw new FrameworkException(
						"Error encountered while verifying email.---" + e.getClass() + "---" + e.getMessage());
			}
		}
	}

	/**
	 * Function to get the current window title.
	 * 
	 * @exception FrameworkException
	 *                in case window title not found.
	 */
	public static String getcurrentwindowTitle() {
		String title = null;
		title = driver.getTitle();
		if (title != null) {
			return title;
		} else {
			throw new FrameworkException("Window " + title + " not found.");
		}

	}

	/**
	 * Function to create connection to database and return result.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * 
	 * @exception FrameworkException.
	 */
	public static ResultSet verifyValuesFromDatabase(String query) throws FrameworkException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("");
			Connection connection = DriverManager.getConnection(Utilities.getProperty("MYSQLDATABASE_URL"),
					Utilities.getProperty("DB_USERNAME"), Utilities.getProperty("DB_PASSWORD"));
			System.out.println(connection);
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			return result;
		} catch (SQLException | ClassNotFoundException e) {
			throw new FrameworkException("Exception encountered while validating data from Database---" + e.getClass()
					+ "---" + e.getMessage());
		}
	}

	public static String getLinkFromEmail(String linkText) throws FrameworkException {
		try {
			linkFromEmail = links.get(linkText);
			if (linkFromEmail == null) {
				throw new FrameworkException("Unable to locate link for '" + linkText + "' in email.");
			} else {
				return linkFromEmail;
			}
		} catch (NullPointerException e) {
			throw new FrameworkException("Unable to locate link for '" + linkText + "' in email.");
		}
	}

	public static void taskFromMAS(String testCaseName, String testData) throws FrameworkException {
		taskFromMAS(testCaseName, testData, false);
	}

	public static void taskFromMAS(String testCaseName, String testData, boolean retrieveResult)
			throws FrameworkException {
		try {
			if (TestSetup.masValidation) {
				String errorMessage = "";
				File file = new File(System.getProperty("user.dir") + "\\temp.txt");
				if (!file.createNewFile()) {
					file.delete();
					file.createNewFile();
				}
				String path[] = { "wscript", System.getProperty("user.dir") + "\\MASInteraction.vbs" };
				String credentials = Utilities.getProperty("MAS_USER_ID") + ":" + Utilities.getProperty("MAS_PASSWORD");
				String params[] = (String[]) ArrayUtils.addAll(path, credentials);
				params = (String[]) ArrayUtils.addAll(params, testCaseName);
				params = (String[]) ArrayUtils.addAll(params, testData);
				params = (String[]) ArrayUtils.addAll(params, System.getProperty("user.dir") + "\\temp.txt");
				Process proc = Runtime.getRuntime().exec(params);
				proc.waitFor();
				FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "\\temp.txt");
				if (proc.exitValue() != 0) {
					errorMessage = IOUtils.toString(inputStream);
				} else {
					if (retrieveResult) {
						Utilities.setProperty("VALUE_FROM_MAS", IOUtils.toString(inputStream));
					}
				}
				file.delete();
				inputStream.close();
				if (!errorMessage.equals("")) {
					throw new FrameworkException("Encountered error while performing task at MAS---" + errorMessage);
				}
				loggerForLogs.log(LogStatus.INFO, "MAS validation successful for " + testData);
			} else {
				loggerForLogs.log(LogStatus.INFO, "As configured, MAS validation not done");
				throw new FrameworkException("As configured, MAS validation not done");
			}
		} catch (IOException | InterruptedException e) {
			throw new FrameworkException("Encountered error while performing task at MAS");
		}
	}
}