package com.merchante.ui.steps;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.merchante.BaseTest;
import com.merchante.PageObjects;
import com.merchante.SeleniumFunctions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class StepDef extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(StepDef.class);

	@Given("^page is opened successfully$")
	public void page_is_opened_successfully() throws IOException {
		getMethodName();
		openUrl();
		SeleniumFunctions.waitForVisibilityBySelector(PageObjects.HeaderTitle.getProperty());
		SeleniumFunctions.waitForVisibilityBySelector(PageObjects.UserTab.getProperty());
		SeleniumFunctions.clickElementBySelector(PageObjects.UserTab.getProperty());
		logger.info("Launched page successfully");
	}

	@Then("^I create new user \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_create_new_user_and_and(String userName, String password, String email) throws Throwable {
		getMethodName();
		SeleniumFunctions.clickElementBySelector(PageObjects.NewUser.getProperty());
		SeleniumFunctions.waitForVisibilityBySelector(PageObjects.InputUserName.getProperty());
		SeleniumFunctions.sendKeysBySelector(PageObjects.InputUserName.getProperty(), userName);
		SeleniumFunctions.sendKeysBySelector(PageObjects.InputPassword.getProperty(), password);
		SeleniumFunctions.sendKeysBySelector(PageObjects.InputEmail.getProperty(), email);
		SeleniumFunctions.clickElementBySelector(PageObjects.InputSubmit.getProperty());
		logger.info("Created New User successfully");
	}

	@Then("^verify banner message \"([^\"]*)\"$")
	public void verify_banner_message(String expectedMessage) throws Throwable {
		getMethodName();
		SeleniumFunctions.waitForVisibilityBySelector(PageObjects.FlashNotice.getProperty());
		String actualMessage = SeleniumFunctions.getWebElementTextBySelector(PageObjects.FlashNotice.getProperty());
		assertTrue("Banner message not matching. Actual: " + actualMessage + " Expected: " + expectedMessage, actualMessage.equals(expectedMessage));
		SeleniumFunctions.clickElementBySelector(PageObjects.UserTab.getProperty());
		logger.info("Verified message " + expectedMessage);
	}

	@Then("^I delete the new user \"([^\"]*)\"$")
	public void i_delete_the_new_user(String userName) throws Throwable {
		getMethodName();
		List<WebElement> tableRows = SeleniumFunctions.getWebElementsListBySelector(PageObjects.UserTableRows.getProperty());
		for (WebElement row : tableRows) {
			String _userName = row.findElement(By.cssSelector("td.col-username")).getText();
			if (_userName.equals(userName)) {
				row.findElement(By.cssSelector("td.col-actions a.delete_link")).click();
				SeleniumFunctions.switchToAlertAndAccept();
			}
		}
		logger.info("User deleted successfully");
	}

	@Given("^I verify field errors \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_verify_field_errors_and_and(String userNameError, String passwordError, String emailError) throws Throwable {
		getMethodName();
		String _errMessage = "";
		if (StringUtils.isNotEmpty(userNameError)) {
			_errMessage = SeleniumFunctions.findElementBySelector(PageObjects.UserError.getProperty()).getText();
			assertTrue("Username error message mismatch", userNameError.equals(_errMessage));
		}
		if (StringUtils.isNotEmpty(passwordError)) {
			_errMessage = SeleniumFunctions.findElementBySelector(PageObjects.PasswordError.getProperty()).getText();
			assertTrue("Password error message mismatch", passwordError.equals(_errMessage));
		}
		if (StringUtils.isNotEmpty(emailError)) {
			_errMessage = SeleniumFunctions.findElementBySelector(PageObjects.EmailError.getProperty()).getText();
			assertTrue("Email error message mismatch", emailError.equals(_errMessage));
		}
		logger.info("Error messages validated successfully");
	}

	@Then("^filter username \"([^\"]*)\" user \"([^\"]*)\"$")
	public void filter_username_user(String filterOption, String userName) throws Throwable {
		getMethodName();
		WebDriver dr = SeleniumFunctions.getDriver();
		Select select = new Select(dr.findElement(By.cssSelector(PageObjects.SelectUserNameFilterOptions.getProperty())));
		select.selectByVisibleText(filterOption);
		SeleniumFunctions.sendKeysBySelector("input#q_username", userName);
	}

	@And("^filter email \"([^\"]*)\" user \"([^\"]*)\"$")
	public void filter_email_user(String filterOption, String userName) throws Throwable {
		getMethodName();
		SeleniumFunctions.selectDropDownBySelector(PageObjects.SelectEmailFilterOptions.getProperty(), filterOption);
		SeleniumFunctions.sendKeysBySelector("input#q_email", userName);
	}

	@And("^filter date \"([^\"]*)\" to \"([^\"]*)\"$")
	public void filter_date_to(String fromDate, String toDate) throws Throwable {
		getMethodName();
		WebElement fromElem = SeleniumFunctions.findElementBySelector(PageObjects.InputDateFrom.getProperty());
		WebElement toElem = SeleniumFunctions.findElementBySelector(PageObjects.InputDateTo.getProperty());
		SeleniumFunctions.setAttribute(fromElem, "value", fromDate);
		SeleniumFunctions.setAttribute(toElem, "value", toDate);
	}

	@And("^submit the filter options$")
	public void submit_the_filter_options() throws Throwable {
		getMethodName();
		SeleniumFunctions.clickElementBySelector(PageObjects.InputSubmit.getProperty());
		logger.info("Filter submitted successfully");
	}

	@Then("^I verify filtered record \"([^\"]*)\"$")
	public void i_verify_filtered_record(String count) throws Throwable {
		getMethodName();
		int _count = Integer.parseInt(count);
		if (_count > 0) {
			List<WebElement> tableRows = SeleniumFunctions.getWebElementsListBySelector(PageObjects.UserTableRows.getProperty());
			assertTrue("Filtered Row count not matching", tableRows.size() == _count);
			logger.info("Filter verified successfully");
		} else {
			SeleniumFunctions.findElementBySelector("span.blank_slate");
			logger.info("No Users found successfully");
		}
	}
}
