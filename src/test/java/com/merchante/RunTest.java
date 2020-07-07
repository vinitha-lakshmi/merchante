package com.merchante;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = { "pretty", "html:target/reports", "json:target/reports/cucumber-report.json" }, 
		glue = { "com.merchante" }, 
		features = "src/test/resources/features/",
		//tags = "@NEWUSER",
		monochrome = true
)

public class RunTest {
	public void runTest() {
	}
}

