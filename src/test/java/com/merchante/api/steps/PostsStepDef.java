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
import com.merchante.dto.PostDTO;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class PostsStepDef extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(PostsStepDef.class);
	private static final String postsURI = "posts/";
	private PostDTO dto;

	@Given("^I post data \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_post_data_and(String fName, String lName) throws Throwable {
		RestAssured.baseURI = endPoint;
		RequestSpecification request = RestAssured.given();
		dto = new PostDTO(fName, lName);
		dto.setId("BkHc-mb1P");
		request.body(getRequestParams(dto));
		Response response = request.contentType(ContentType.JSON).post(postsURI);

		// Verify Response code is 201
		int responseCode = response.getStatusCode();
		assertTrue("Response status code is not 200", responseCode == 201);

		// Verify response ID
		JSONObject jsonObject = getJsonParse(response);
		if (jsonObject != null && jsonObject.get("id").toString() != "") {
			dto.setId(jsonObject.get("id").toString());
		} else {
			assertTrue("Exception parsing response JSON", false);

		}

		logger.info("POST successful with ID: " + dto.getId());
	}

	@Then("^I get the posts data to verify it$")
	public void i_get_the_posts_data_to_verify_it() throws Throwable {
		RestAssured.baseURI = endPoint;
		RequestSpecification request = RestAssured.given();
		Response response = request.get(postsURI + dto.getId());

		// Verify Response code is 200
		int responseCode = response.getStatusCode();
		assertTrue("Response status code is not 200", responseCode == 200);

		JSONObject jsonObject = getJsonParse(response);
		if (jsonObject != null) {
			String fName = jsonObject.get("firstName").toString();
			String lName = jsonObject.get("lastName").toString();
			assertTrue("First name is not matching", fName.equals(dto.getFirstName()));
			assertTrue("Last name is not matching", lName.equals(dto.getLastName()));
		} else {
			assertTrue("Exception parsing response JSON", false);
		}

		logger.info("GET verified successfull for ID: " + dto.getId());
	}

	@Then("^I update the data \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_update_the_data_and(String fName, String lName) throws Throwable {
		dto.setFirstName(fName);
		dto.setLastName(lName);
		RestAssured.baseURI = endPoint;
		RequestSpecification request = RestAssured.given();
		request.body(getRequestParams(dto));
		Response response = request.contentType(ContentType.JSON).put(postsURI + dto.getId());

		// Verify Response code is 200
		int responseCode = response.getStatusCode();
		assertTrue("Response status code is not 200", responseCode == 200);

		logger.info("POST verified successfully for ID: " + dto.getId());
	}

	@Then("^I delete the posts data$")
	public void i_delete_the_posts_data() throws Throwable {
		RestAssured.baseURI = endPoint;
		RequestSpecification request = RestAssured.given();
		Response response = request.delete(postsURI + dto.getId());
		logger.error("Delete status code: " + response.getStatusCode());

		response = request.get(postsURI + dto.getId());
		JSONObject jsonObject = getJsonParse(response);
		assertTrue("Exception deleting ID" + dto.getId(), jsonObject.isEmpty());

		logger.info("Deleted ID: " + dto.getId());
	}

}
