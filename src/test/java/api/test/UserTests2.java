package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {
	Faker faker;
	User userPayLoad;
	public Logger logger;
	
	@BeforeClass
	public void setup() {
		faker = new Faker();
		userPayLoad = new User();
		userPayLoad.setId(faker.idNumber().hashCode());
		userPayLoad.setUsername(faker.name().username());
		userPayLoad.setFirstname(faker.name().firstName());
		userPayLoad.setLastname(faker.name().lastName());
		userPayLoad.setEmail(faker.internet().safeEmailAddress());
		userPayLoad.setPassword(faker.internet().password(5,10));
		userPayLoad.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger = LogManager.getLogger(this.getClass());
		
	}
	
	@Test(priority=1)
	public void testPostUser() {
		logger.info("******** Creating user *********");
		logger.debug("debugging");
		Response response = UserEndPoints2.createUser(userPayLoad);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		logger.info("******** User is created *********");
	}
	
	@Test(priority=2)
	public void testGetUserByName() {
		logger.info("******** Reading user info *********");
		logger.debug("debugging");
		Response response = UserEndPoints2.readUser(this.userPayLoad.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("******** User info is displayed *********");
		
	}
	
	@Test(priority=3)
	public void testUpdateUserByName() {
		
		logger.info("******** Updating user info *********");
		logger.debug("debugging");
		
		//update data using payload 
		userPayLoad.setFirstname(faker.name().firstName());
		userPayLoad.setLastname(faker.name().lastName());
		userPayLoad.setEmail(faker.internet().safeEmailAddress());
		
		Response response = UserEndPoints2.updateUser(this.userPayLoad.getUsername(), userPayLoad);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);  //testng assertion
		response.then().log().body().statusCode(200);   //chai assertion
		
		//checking data after update
		Response responseAfterUpdate = UserEndPoints2.readUser(this.userPayLoad.getUsername());
		responseAfterUpdate.then().log().all();
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);	
		logger.info("******** user is updated *********");
	}
	
	@Test(priority=4)
	public void testDeleteUserByName() {
		
		logger.info("******** Deleting user info *********");
		logger.debug("debugging");
		
		Response response = UserEndPoints2.deleteUser(this.userPayLoad.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("******** user is deleted *********");
			
	}

}
