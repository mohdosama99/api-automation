package MethodCalls;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.ResponseAwareMatcher.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class ParsingJsonResponseData {
	
	//@Test(priority = 1)
	void testJsonResponse() {
		given()
			.contentType("ContentType.JSON")
		.when()
			.get("https://reqres.in/api/users?page=2")
		.then()
			.statusCode(200)
			.body("total", equalTo(12));

	}
	//@Test(priority = 2)
	void testReponseUsingTestNG() {
		Response res = given()
				.contentType("ContentType.JSON")
				.when()
					.get("https://reqres.in/api/users?page=2");
		Assert.assertEquals(res.getStatusCode(), 200);
		Assert.assertEquals(res.jsonPath().getString("total"), "12");
		
	}
	
	@Test(priority = 3)
	void testReponseUsingJsonObject() {
		Response res = given()
				.contentType(ContentType.JSON)
				.when()
					.get("https://reqres.in/api/users?page=2");
		
        List<String> emails = res.jsonPath().getList("data.email");
        
        String expectedEmail = "michael.lawson@reqres.in";
        
        Assert.assertTrue(emails.contains(expectedEmail), "Expected email not found in the response!");
        emails.forEach(System.out::println);
		
//		JSONObject js = new JSONObject(res.asString());
//		
//		for(int i=0; i< js.getJSONArray("data").length(); i++) {
//			String email = js.getJSONArray("data").getJSONObject(i).get("email").toString();
//			System.out.println(email);
//			Assert.assertTrue(email.equals("michael.lawson@reqres.in"));
//			
//		}
	}

}
