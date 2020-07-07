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
import com.merchante.dto.CommentDTO;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class CommentsStepDef extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(CommentsStepDef.class);
	private static final String commentsURI = "comments/";
	private CommentDTO dto;

	@Given("^I comment data \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_comment_data_and(String user, String comment, String sport) throws Throwable {
		getMethodName();
		RestAssured.baseURI = endPoint;
		RequestSpecification request = RestAssured.given();
		
		// Generate payload using DTO and RequestParams
		dto = new CommentDTO(user,comment,sport);
		request.body(getRequestParams(dto));
		
		// POST
		Response response = request.contentType(ContentType.JSON).post(commentsURI);

		// Verify Response status code is 201
		int responseCode = response.getStatusCode();
		assertTrue("Response status code is not 200", responseCode == 201);

		// Verify response
		JSONObject jsonObject = getJsonParse(response);
		if (jsonObject != null && jsonObject.get("id").toString() != "") {
			dto.setId(jsonObject.get("id").toString()); // set ID in DTO
		} else {
			assertTrue("Exception parsing response JSON", false);
		}

		logger.info("POST successful with ID: " + dto.getId());
	}

	@Then("^I get the comment data to verify it$")
	public void i_get_the_comment_data_to_verify_it() throws Throwable {
		getMethodName();
		RestAssured.baseURI = endPoint;
		RequestSpecification request = RestAssured.given();
		
		// GET
		Response response = request.get(commentsURI + dto.getId());

		// Verify Response status code is 200
		int responseCode = response.getStatusCode();
		assertTrue("Response status code is not 200", responseCode == 200);

		// Verify response data matches our request data
		JSONObject jsonObject = getJsonParse(response);
		if (jsonObject != null) {
			String user = jsonObject.get("user").toString();
			String comment = jsonObject.get("comment").toString();
			String sport = jsonObject.get("sport").toString();
			assertTrue("User is not matching", user.equals(dto.getuser()));
			assertTrue("Comment is not matching", comment.equals(dto.getcomment()));
			assertTrue("Sport is not matching", sport.equals(dto.getSport()));
		} else {
			assertTrue("Exception parsing response JSON", false);
		}

		logger.info("GET verified successfully for ID: " + dto.getId());
	}

	@Then("^I update the comment data \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
	public void i_update_the_comment_data_and(String user, String comment, String sport) throws Throwable {
		getMethodName();
		RestAssured.baseURI = endPoint;
		RequestSpecification request = RestAssured.given();

		// Generate payload using DTO and RequestParams
		dto.setuser(user);
		dto.setcomment(comment);
		dto.setSport(sport);
		request.body(getRequestParams(dto));

		// PUT
		Response response = request.contentType(ContentType.JSON).put(commentsURI + dto.getId());

		// Verify Response code is 200
		int responseCode = response.getStatusCode();
		assertTrue("Response status code is not 200", responseCode == 200);

		logger.info("POST verified successfully for ID: " + dto.getId());
	}

	@Then("^I delete the comment data$")
	public void i_delete_the_comment_data() throws Throwable { // common methods can be moved to base class
		getMethodName();
		RestAssured.baseURI = endPoint;
		RequestSpecification request = RestAssured.given();

		// DELETE
		Response response = request.delete(commentsURI + dto.getId());
		logger.error("Delete status code: " + response.getStatusCode()); // as response has error logging for now without failing

		// Verify delete is successful
		response = request.get(commentsURI + dto.getId());
		JSONObject jsonObject = getJsonParse(response);
		assertTrue("Exception deleting ID" + dto.getId(), jsonObject.isEmpty());

		logger.info("Deleted ID: " + dto.getId());
	}

}
