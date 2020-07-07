package com.merchante;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeleniumFunctions {

	private static WebDriver driver = null;
	public static Properties CONFIG = new Properties();
	public static String os = System.getProperty("os.name").toLowerCase();
	public static String localPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumFunctions.class);

	public static void initializeDriver(String Browser) throws IOException {
		driver = defineBrowser(Browser);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		pause(2);
	}

	public static WebDriver defineBrowser(String browser) throws IOException {
		System.setProperty("webdriver.chrome.silentOutput", "true"); // Suppress WebDriver Log Messages
		ChromeOptions options = new ChromeOptions();

		if (browser.equalsIgnoreCase("chrome")) {
			if (os.contains("win")) {
				logger.info("OS: WINDOWS");
				System.setProperty("webdriver.chrome.driver", localPath + "chromedriver.exe");
				options.addArguments("--start-maximized");
				return new ChromeDriver(options);
			} else if (os.contains("mac")) {
				logger.info("OS: MAC");
				System.setProperty("webdriver.chrome.driver", localPath + "chromedriver_mac64");
				options.addArguments("--kiosk");// Mac Fullscreen
				return new ChromeDriver(options);
			} else {
				logger.error("OS DID NOT MATCHED WITH WINDOWS OR MAC..");
				return null;
			}
		} else {
			logger.error("INVALID BROWSER ONLY CHROME IMPLEMENTED..");
			return null;
		}

	}

	public static void getUrl(String url) throws IOException {
		driver.get(url);
	}

	public static void pause(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void closeBrowser() {
		if (driver != null) {
			driver.close();
		}
	}

	public static void quitBrowser() {
		if (driver != null) {
			driver.quit();
		}
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static WebElement waitForVisibilityBySelector(String selector) {
		WebElement elem = null;
		try {
			elem = (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
		} catch (TimeoutException e) {
			logger.error("Exception: Element not found by CSS: " + selector);
		}
		return elem;
	}

	public static WebElement waitForInvisibilityBySelector(String selector) {
		WebElement elem = null;
		try {
			(new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(selector)));
		} catch (TimeoutException e) {
			logger.error("Exception: Element not found by CSS: " + selector);
		}
		return elem;
	}

	public static boolean isElementVisibleBySelector(String selector) {
		boolean flag = false;
		try {
			(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector)));
			flag = true;
		} catch (TimeoutException e) {
			logger.error("Exception: Element not visible by CSS: " + selector);
		}
		return flag;
	}

	public static WebElement findElementBySelector(String selector) {
		waitForVisibilityBySelector(selector);
		return driver.findElement(By.cssSelector(selector));
	}

	public static String getAttributeBySelector(String attrName, String selector) {
		WebElement elem = findElementBySelector(selector);
		String value = "";
		value = elem.getAttribute(attrName);
		return value;
	}

	public static void sendKeysBySelector(String selector, String text) {
		driver.findElement(By.cssSelector(selector)).sendKeys(text);
	}

	public static List<WebElement> getWebElementsListBySelector(String selector) {
		return driver.findElements(By.cssSelector(selector));
	}

	public static void clickElementBySelector(String selector) {
		driver.findElement(By.cssSelector(selector)).click();
	}

	public static void clickElementByXpath(String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}
	
	public static void softAssertValueEquals(String actual, String expected) {
		try {
			assertTrue(actual.equals(expected));
			logger.info("Assertion Passed");
		} catch (AssertionError e) {
			logger.error("Assertion Error. Expected: " + expected + " Actual: " + actual);
		}
	}

	public static String getWebElementTextBySelector(String selector) {
		return driver.findElement(By.cssSelector(selector)).getText();
	}
}
