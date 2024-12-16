package MethodCalls;

import static io.restassured.RestAssured.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import java.util.HashMap;

import org.testng.annotations.Test;

public class PatchRequest {

	public static int id;

	@Test(priority = 1)
	void getUsers() {
		when().get("https://reqres.in/api/users?page=2")

				.then().statusCode(200).log().all();
	}

	@Test(priority = 2)
	void createusers() {
		HashMap data = new HashMap();
		data.put("name", "Test");
		data.put("job", "QA");
		id = given().contentType("application/json").body(data)

				.when().post("https://reqres.in/api/users").jsonPath().getInt("id");
		System.out.println("user id: " + id);

		// .then().statusCode(201).log().all();

	}

	@Test(dependsOnMethods = { "createusers" }, priority = 3)
	void updateUser() {
		HashMap data = new HashMap();
		data.put("name", "Autt");
		data.put("job", "QA2");

		given().contentType("application/json").body(data)

				.when().patch("https://reqres.in/api/users/" + id)

				.then().statusCode(200).log().all();

	}

	@Test(dependsOnMethods = { "createusers" }, priority = 4)
	void deleteUser() {
		given().when().delete("https://reqres.in/api/users/" + id)

				.then().statusCode(204).log().all();
	}

}
