package MethodCalls;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class HowtoCheckHeaders {

	@Test
	void headers() {
		Response res = given().pathParam("path", "users").queryParam("page", 2).when()
				.get("https://reqres.in/api/{path}");
		Headers hd = res.headers();

		for (Header h : hd) {
			System.out.println(h.getName() + " -----> " + h.getValue());
		}

	}

}
