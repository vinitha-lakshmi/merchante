package com.merchante.api.steps;

import static org.junit.Assert.assertTrue;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.merchante.BaseTest;
import com.merchante.dto.UserDTO;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class UserStepDef extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(UserStepDef.class);
	private static final String usersURI = "users/";
	private UserDTO dto;

	@Given("^I set the user data$")
	public void i_set_the_user_data() throws Throwable {
		getMethodName();
		RestAssured.baseURI = endPoint;
		RequestSpecification request = RestAssured.given();
		
		// Hard Coded for understanding. We can get this data from data file (Excel etc)
		dto = new UserDTO("vinitha lakshmi", "vinitha_lakshmi", "qa@merchante.com", "1150 Sanctuary Parkway", "300", "Alpharetta", "30009", "Merchant eSolutions", "(888) 288-2692", "www.merchante.com");
		request.body(getRequestParams(dto));
		
		// POST
		Response response = request.contentType(ContentType.JSON).post(usersURI);

		// Verify Response code is 201
		int responseCode = response.getStatusCode();
		assertTrue("Response status code is not 200", responseCode == 201);

		// Verify response
		JSONObject jsonObject = getJsonParse(response);
		if (jsonObject != null && jsonObject.get("id").toString() != "") {
			dto.setId(jsonObject.get("id").toString());
		} else {
			assertTrue("Exception parsing response JSON", false);

		}

		logger.info("POST successful with ID: " + dto.getId());
	}

	@Then("^I get the user data to verify it$")
	public void i_get_the_user_data_to_verify_it() throws Throwable {
		getMethodName();
		RestAssured.baseURI = endPoint;
		RequestSpecification request = RestAssured.given();
		
		// GET
		Response response = request.get(usersURI + dto.getId());

		// Verify Response code is 200
		int responseCode = response.getStatusCode();
		assertTrue("Response status code is not 200", responseCode == 200);

		// Verify response data matches our request data
		JSONObject jsonObject = getJsonParse(response);
		if (jsonObject != null) {
			 JSONObject companyObj = (JSONObject) jsonObject.get("company");
			 String companyName = companyObj.get("name").toString();
			assertTrue("Website is not matching", companyName.equals(dto.getCompanyName()));
		} else {
			assertTrue("Exception parsing response JSON", false);
		}

		logger.info("GET verified successfully for ID: " + dto.getId());
	}

	@Then("^I update the user data$")
	public void i_update_the_user_data() throws Throwable {
		getMethodName();
		RestAssured.baseURI = endPoint;
		RequestSpecification request = RestAssured.given();
		
		// Generate payload using DTO and RequestParams
		dto.setCompanyName("MerchantE");
		request.body(getRequestParams(dto));
		
		// PUT
		Response response = request.contentType(ContentType.JSON).put(usersURI + dto.getId());

		// Verify Response code is 200
		int responseCode = response.getStatusCode();
		assertTrue("Response status code is not 200", responseCode == 200);

		logger.info("POST verified successfully for ID: " + dto.getId());
	}

	@Then("^I delete the user data$")
	public void i_delete_the_user_data() throws Throwable {
		getMethodName();
		RestAssured.baseURI = endPoint;
		RequestSpecification request = RestAssured.given();
		
		// DELETE
		Response response = request.delete(usersURI + dto.getId());
		logger.error("Delete status code: " + response.getStatusCode());

		// Verify delete is successful
		response = request.get(usersURI + dto.getId());
		JSONObject jsonObject = getJsonParse(response);
		assertTrue("Exception deleting ID" + dto.getId(), jsonObject.isEmpty());

		logger.info("Deleted ID: " + dto.getId());
	}

}
