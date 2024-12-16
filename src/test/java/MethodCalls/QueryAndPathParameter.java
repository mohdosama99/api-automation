package MethodCalls;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import org.testng.annotations.Test;

public class QueryAndPathParameter {

	@Test
	void HowtoPassQueryAndPathParameter() {
		given().pathParam("path", "users").queryParam("page", 2).when().get("https://reqres.in/api/{path}").then()
				.statusCode(200).log().all();
	}

}
