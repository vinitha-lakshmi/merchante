package com.merchante;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class CommonStep extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(CommonStep.class);

	@Before
	public void beforeScenario(Scenario scenario) throws IOException {
		load_config();
		if (!scenario.getName().contains("api")) {
			try {
				SeleniumFunctions.initializeDriver("chrome");
			} catch (Exception e) {
				logger.error("Failed To Initialize Webdriver..");
			}
		}
	}

	@After(order = 1)
	public void closeBrowser(Scenario scenario) {
		if (!scenario.getName().contains("api")) {
			SeleniumFunctions.closeBrowser();
			if (SeleniumFunctions.getDriver() != null) {
				SeleniumFunctions.quitBrowser();
			}
		}
	}
}
