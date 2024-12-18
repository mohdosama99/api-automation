package api.test;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {
	public Logger logger;
	public ExtentTest test;
	User userpayLoad = new User();

	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String UserID, String UserName, String FirstName, String LastName, String Email,
			String Password, String Phone) {
		userpayLoad.setId(Integer.parseInt(UserID));
		userpayLoad.setUsername(UserName);
		userpayLoad.setFirstName(FirstName);
		userpayLoad.setLastName(LastName);
		userpayLoad.setEmail(Email);
		userpayLoad.setPassword(Password);
		userpayLoad.setPhone(Phone);

		// generate logs
		Configurator.setLevel(this.getClass().getPackageName(), Level.DEBUG);
		logger = LogManager.getLogger(this.getClass());
		logger.debug("Logger initialized for class: " + this.getClass().getSimpleName());

		logger.info("********** Creating User **********");
		logger.debug("Payload for POST request: " + userpayLoad.toString());
		Response res = UserEndPoints.createUser(userpayLoad);
		res.then().log().all();
		logger.debug("Response received: " + res.asString());
		Assert.assertEquals(res.getStatusCode(), 200);
		logger.info("********** User is created **********");
	}
	
	@Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
	public void testDeleteUserByName(String UserName) {
		logger.info("********** Deleting User **********");
	    logger.debug("Deleting user with username: " + this.userpayLoad.getUsername());
	    //test.info("Try to delete Username: "+ this.userpayLoad.getUsername());
	    Response response = UserEndPoints.deleteUser(UserName);
	    response.then().log().all();
	    logger.debug("Response received for delete: " + response.asString());
	    Assert.assertEquals(response.getStatusCode(), 200);
	    //test.info("User Deleted");
	    logger.info("********** User Deleted **********");
	}

}
