package api.test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker = new Faker();
	User userpayLoad = new User();
	
	public Logger logger;

	@BeforeClass
	public void setupData() {
		userpayLoad.setId(faker.idNumber().hashCode());
		userpayLoad.setUsername(faker.name().username());
		userpayLoad.setFirstName(faker.name().firstName());
		userpayLoad.setLastName(faker.name().lastName());
		userpayLoad.setEmail(faker.internet().safeEmailAddress());
		userpayLoad.setPassword(faker.internet().password(5, 10));
		userpayLoad.setPhone(faker.phoneNumber().cellPhone());
		
		//generate logs
		Configurator.setLevel(this.getClass().getPackageName(), Level.DEBUG);
		logger = LogManager.getLogger(this.getClass());
		logger.debug("Logger initialized for class: " + this.getClass().getSimpleName());
		
	}

	@Test(priority = 1)
	public void testPostUser() {
		logger.info("********** Creating User **********");
	    logger.debug("Payload for POST request: " + userpayLoad.toString());
	    Response res = UserEndPoints.createUser(userpayLoad);
	    res.then().log().all();
	    logger.debug("Response received: " + res.asString());
	    AssertJUnit.assertEquals(res.getStatusCode(), 200);
	    logger.info("********** User is created **********");
	}

	@Test(priority = 2)
	public void testGetuserByName() {
		logger.info("********** Reading User Info **********");
	    logger.debug("Requesting user info for username: " + this.userpayLoad.getUsername());
	    Response response = UserEndPoints.readUser(this.userpayLoad.getUsername());
	    response.then().log().all();
	    logger.debug("Response received: " + response.asString());
	    AssertJUnit.assertEquals(response.getStatusCode(), 200);
	    logger.info("********** User Info is Displayed **********");
	}

	@Test(priority = 3)
	public void testUpdateUserByName() {
		logger.info("********** Updating User **********");
	    userpayLoad.setFirstName(faker.name().firstName());
	    userpayLoad.setLastName(faker.name().lastName());
	    userpayLoad.setEmail(faker.internet().safeEmailAddress());
	    logger.debug("Payload for PUT request: " + userpayLoad.toString());

	    Response response = UserEndPoints.updateUser(this.userpayLoad.getUsername(), userpayLoad);
	    response.then().log().body();
	    logger.debug("Response received for update: " + response.asString());
	    AssertJUnit.assertEquals(response.getStatusCode(), 200);

	    Response response2 = UserEndPoints.readUser(this.userpayLoad.getUsername());
	    response2.then().log().all();
	    logger.debug("Updated user info: " + response2.asString());
	    logger.info("********** User data is Updated **********");
	}
	
	@Test(priority = 4)
	public void testDeleteUserByName() {
		logger.info("********** Deleting User **********");
	    logger.debug("Deleting user with username: " + this.userpayLoad.getUsername());
	    Response response = UserEndPoints.deleteUser(this.userpayLoad.getUsername());
	    response.then().log().all();
	    logger.debug("Response received for delete: " + response.asString());
	    AssertJUnit.assertEquals(response.getStatusCode(), 200);
	    logger.info("********** User Deleted **********");
	}

}
