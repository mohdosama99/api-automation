package MethodCalls;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.ResponseAwareMatcher.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class PrintsLog {

	@Test
	void Logging() {
		Response res = given().pathParam("path", "users").queryParam("page", 2).when().get("https://reqres.in/api/{path}");
		
	}

}
