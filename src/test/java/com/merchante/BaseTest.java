package com.merchante;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.restassured.response.Response;
import com.merchante.dto.CommentDTO;
import com.merchante.dto.PostDTO;
import com.merchante.dto.UserDTO;

public class BaseTest {

	public static Properties CONFIG = new Properties();
	private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
	public static String methodName;
	public static String url;
	public static String endPoint;

	public static void load_config() throws IOException, FileNotFoundException {
		FileInputStream fn;
		fn = new FileInputStream("config.properties");
		CONFIG.load(fn);
		url = CONFIG.getProperty("url");
		endPoint = CONFIG.getProperty("api.endpoint");
		logger.info("Url:" + url);
		logger.info("End Point:" + endPoint);

	}

	public static void getMethodName() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[2];
		methodName = e.getMethodName();
		logger.info("Inside method:" + e.getMethodName());
	}

	public void openUrl() throws IOException {
		SeleniumFunctions.getUrl(url);
		SeleniumFunctions.pause(3);
	}

	@SuppressWarnings("unchecked")
	protected String getRequestParams(PostDTO dto) {
		JSONObject params = new JSONObject();
		params.put("firstName", dto.getFirstName());
		params.put("lastName", dto.getLastName());
		return params.toJSONString();
	}

	@SuppressWarnings("unchecked")
	protected String getRequestParams(CommentDTO dto) {
		JSONObject params = new JSONObject();
		params.put("user", dto.getuser());
		params.put("comment", dto.getcomment());
		params.put("sport", dto.getSport());
		return params.toJSONString();
	}

	@SuppressWarnings("unchecked")
	protected String getRequestParams(UserDTO dto) {
		JSONObject companyObj = new JSONObject();
		companyObj.put("name", dto.getCompanyName());
		companyObj.put("phone", dto.getCompanyPhone());
		companyObj.put("website", dto.getCompanyWebsite());

		JSONObject addressObj = new JSONObject();
		addressObj.put("street", dto.getAddressStreet());
		addressObj.put("suite", dto.getAddressSuite());
		addressObj.put("city", dto.getAddressCity());
		addressObj.put("zipcode", dto.getAddressZipCode());

		JSONObject root = new JSONObject();
		root.put("name", dto.getName());
		root.put("username", dto.getUserName());
		root.put("email", dto.getEmail());
		root.put("company", companyObj);
		root.put("address", addressObj);

		return root.toJSONString();
	}

	protected JSONObject getJsonParse(Response response) {
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) new JSONParser().parse(response.asString());
		} catch (Exception e) {
			logger.error("Exception parsing RESPONSE to JSON: " + e);
		}
		return jsonObject;
	}

}
