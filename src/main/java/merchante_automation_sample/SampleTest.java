package merchante_automation_sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class SampleTest {

	public static void main(String[] args) throws ParseException {
		  

		  JSONObject companyObj = new JSONObject();
		  companyObj.put("name", "merchantE");
		  companyObj.put("phone", "(888) 288-2692");
		  companyObj.put("website", "www.merchante.com");
		  
		  JSONObject addressObj = new JSONObject();
		  addressObj.put("street", "1150 Sanctuary Parkway");
		  addressObj.put("suite", "300");
		  addressObj.put("city", "Alpharetta");
		  addressObj.put("zipcode", "30009");
		  
		  JSONObject root = new JSONObject();
		  root.put("name", "vinitha lakshmi");
		  root.put("username", "vinitha_lakshmi");
		  root.put("email", "qa@merchante.com");
		  root.put("company", companyObj);
		  root.put("address", addressObj);		  

		  JSONObject obj = (JSONObject) root.get("company");
		  System.out.println(obj.get("website"));
		  
		  
	}

}
