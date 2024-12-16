package MethodCalls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonToken;
import com.github.javafaker.Faker;

import io.restassured.internal.support.FileReader;

public class DiffWayToCreatePostRequestBody {

	//Using HashMap
	// @Test(priority = 1)
	void testPostUsingHashMap() {
		HashMap data = new HashMap();
		data.put("id", 1);
		data.put("username", "Test123");
		data.put("firstName", "Test");
		data.put("lastName", "Aut");
		data.put("email", "testaut123@gmail.com");
		data.put("password", "Test@123");
		data.put("phone", "9087654321");
		data.put("userStatus", 0);

		given().contentType("application/json").body(data).when().post("https://petstore.swagger.io/v2/user").then()
				.statusCode(200).log().all();

	}

	// using JsonObject
	// @Test(priority = 2)
	void testPostUsingJsonObject() {
		JSONObject data = new JSONObject();
		data.put("id", 1);
		data.put("username", "Test123");
		data.put("firstName", "Test");
		data.put("lastName", "Aut");
		data.put("email", "testaut123@gmail.com");
		data.put("password", "Test@123");
		data.put("phone", "9087654321");
		data.put("userStatus", 0);
		given().contentType("application/json").body(data.toString()).when().post("https://petstore.swagger.io/v2/user")
				.then().statusCode(200).log().all();
	}

	Faker faker = new Faker();
	POJO payload = new POJO();

	// using pojo class.
	// @Test(priority = 3)
	void testPostUsingPOJO() {
		payload.setId(faker.idNumber().hashCode());
		payload.setUsername(faker.name().username());
		payload.setFirstName(faker.name().firstName());
		payload.setLastName(faker.name().lastName());
		payload.setEmail(faker.internet().safeEmailAddress());
		payload.setPassword(faker.internet().password());
		payload.setPhone(faker.phoneNumber().cellPhone());
		// payload.setUserStatus(0);

		given().contentType("application/json").body(payload).when().post("https://petstore.swagger.io/v2/user").then()
				.statusCode(200).log().all();

	}
	
	// using external json file.
	@Test(priority = 4)
	void testPostUsingJsonFile() {

		try {
			String filePath = System.getProperty("user.dir") + "/body.json";
			File f = new File(filePath);
			FileInputStream fis = new FileInputStream(new File(filePath));
			JSONTokener jr = new JSONTokener(fis);
			JSONObject js = new JSONObject(jr);
			given().contentType("application/json").body(js.toString()).when().post("https://petstore.swagger.io/v2/user")
					.then().statusCode(200).log().all();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
