package MethodCalls;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class HowtoCheckCookies {

	@Test
	void cookies() {
		Response res = given().pathParam("path", "users").queryParam("page", 2).when()
				.get("https://reqres.in/api/{path}");
		Map<String, String> cookies = res.getCookies();

		for (String s : cookies.keySet()) {
			System.out.println(s + " " + res.getCookie(s));
		}

	}

}
