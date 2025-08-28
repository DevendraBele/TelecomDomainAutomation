package com.selenium.capstone;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class apiTesting extends extentReportSetup {
	
	String email = "quality_analyst_" + System.currentTimeMillis() + "@fakegmail.com";
	String token;
	String lToken;
	String userId;
	
	@Test(priority = 1)
	public void AddNewUser() {
		
       test = extent.createTest("Add New User");
       String endPoint = "https://thinking-tester-contact-list.herokuapp.com/users";
       Response user = given()
    		   .header("Content-type", "application/json")
    		   .body("{\r\n    "
    		   		+ "\"firstName\": \"Meena\",\r\n"
    		   		+ "\"lastName\": \"Sharman\",\r\n"
    		   		+ "\"email\": \"" + email + "\",\r\n"
    		   		+ "\"password\": \"meenasharman5556\""
    		   		+ "\r\n}")
    		   .when()
    		   .post(endPoint);
       
       System.out.println(user.asPrettyString());
       Assert.assertEquals(user.getStatusCode(), 201);
       Assert.assertTrue(user.statusLine().contains("Created"));
       token = user.jsonPath().getString("token");
       test.pass("New user added successfully. Token: " + token);
	}
	
	
	@Test(priority = 2, dependsOnMethods = "AddNewUser")
	public void GetUserProfile() {
		
		test = extent.createTest("Get User Profile");
		String endPoint = "https://thinking-tester-contact-list.herokuapp.com/users/me";
		Response user = given()
				.header("Authorization", "Bearer " + token)
				.header("Content-Type", "application/json")
				.when()
				.patch(endPoint);
	
		System.out.println("\n\n" + user.asPrettyString());
		Assert.assertEquals(user.getStatusCode(), 200);
		Assert.assertTrue(user.statusLine().contains("OK"), "OK");
		test.pass("user profile got successfull");
	}
	
	
	@Test(priority = 3, dependsOnMethods = "AddNewUser")
	public void  UpdateUser() {
		
		test = extent.createTest("Update User");
		String endPoint = " https://thinking-tester-contact-list.herokuapp.com/users/me";
		Response user1 = given()
				.header("Authorization", "Bearer " + token)
				.header("Content-Type", "application/json")
				.body("{\r\n"
						+ "\"firstName\": \"Meena\",\r\n"
						+ "\"lastName\": \"Sharman\",\r\n"
						+ "\"email\": \"" + email + "\",\r\n"
						+ "\"password\": \"meenasharman3738\""
						+ "\r\n}")
				.when()
				.patch(endPoint);
		
		System.out.println("\n\n" + user1.asPrettyString());
		Assert.assertEquals(user1.getStatusCode(), 200);
		Assert.assertTrue(user1.statusLine().contains("OK"), "OK");
		test.pass("user updated successfully");
	}
	
	@Test(priority = 4, dependsOnMethods = "AddNewUser")
	public void LoginUser() {
		
		test = extent.createTest("Login User");
		String endPoint = "https://thinking-tester-contact-list.herokuapp.com/users/login";
		Response user = given()
				.header("Content-Type", "application/json")
				.body("{\r\n"
						+ "\"email\": \"" + email + "\",\r\n"
						+ "\"password\": \"meenasharman3738\""
						+ "\r\n}")
				.when()
				.post(endPoint);
		
		System.out.println("\n\n" + user.asPrettyString());
		Assert.assertEquals(user.getStatusCode(),200);
		Assert.assertTrue(user.statusLine().contains("OK"), "OK");
		lToken = user.jsonPath().getString("token");
		test.pass("User Login Successfull");
	}
	
	@Test (priority = 5, dependsOnMethods = "LoginUser")
	public void AddContact() {
		test = extent.createTest("Add Contact");
		String endPoint = "https://thinking-tester-contact-list.herokuapp.com/contacts";
		Response user = given()
				.header("Authorization", "Bearer " + lToken)
				.header("Content-Type", "application/json")
				.body("{\r\n"
						+ "\"firstName\": \"Stewie\",\r\n"
						+ "\"lastName\": \"Griffin\",\r\n"
						+ "\"birthdate\": \"1970-01-01\",\r\n"
						+ "\"email\": \"Stewiegriffin@fake.com\",\r\n"
						+ "\"phone\": \"8005555555\",\r\n"
						+ "\"street1\": \"1 Main St.\",\r\n"
						+ "\"street2\": \"Apartment A\",\r\n"
						+ "\"city\": \"Anytown\",\r\n"
						+ "\"stateProvince\": \"KS\",\r\n"
						+ "\"postalCode\": \"12345\",\r\n"
						+ "\"country\": \"USA\"\r\n"
						+ "}")
				.when()
				.post(endPoint);
		
		System.out.println("\n\n " + user.asPrettyString());
		Assert.assertEquals(user.getStatusCode(),201);
		Assert.assertTrue(user.statusLine().contains("Created"), "Created");
		test.pass("Contact Added Successfully");
	}
	
	
	@Test(priority = 6, dependsOnMethods = "AddContact")
	public void GetContactList() {
		
		test = extent.createTest("Get Contact List");
		String endPoint = "https://thinking-tester-contact-list.herokuapp.com/contacts";
		Response user = given()
				.header("Authorization", "Bearer " + lToken)
				.header("Content-Type", "application/json")
				.when()
				.get(endPoint);
		
		test.pass("\n\n " + user.asPrettyString());
		Assert.assertEquals(user.getStatusCode(),200);
		Assert.assertTrue(user.statusLine().contains("OK"), "OK");
		test.pass("Get contact list sucessfully");
	}
	
	@Test(priority = 7, dependsOnMethods = "AddContact")
	public void GetContact() {
		
		test = extent.createTest("Get Contact");
		String endPoint = "https://thinking-tester-contact-list.herokuapp.com/contacts/";
		Response user = given()
				.header("Authorization", "Bearer " + lToken)
				.header("Content-Type", "application/json")
				.when()
				.get(endPoint);
		
		System.out.println("\n\n " + user.asPrettyString());
		Assert.assertEquals(user.getStatusCode(),200);
		Assert.assertTrue(user.statusLine().contains("OK"), "OK");
		userId = user.jsonPath().getString("[0]._id");
		test.pass("Get contact sucessfully");	
	}
	
	@Test(priority = 8, dependsOnMethods = "GetContact")
	public void UpdateContact() {
		
		test = extent.createTest("Update Contact");
		String endPoint = "https://thinking-tester-contact-list.herokuapp.com/contacts/";
		Response user = given()
				.header("Authorization", "Bearer " + lToken)
				.header("Content-Type", "application/json")
				.body("{\n"
						+ "  \"firstName\": \"meg\",\n"
						+ "  \"lastName\": \"griffin\",\n"
						+ "  \"birthdate\": \"1992-02-02\",\n"
						+ "  \"email\": \"meggriffin@fake.com\",\n"
						+ "  \"phone\": \"8005554242\",\n"
						+ "  \"street1\": \"13 School St.\",\n"
						+ "  \"street2\": \"Apt. 5\",\n"
						+ "  \"city\": \"Washington\",\n"
						+ "  \"stateProvince\": \"QC\",\n"
						+ "  \"postalCode\": \"A1A1A1\",\n"
						+ "  \"country\": \"Canada\"\n"
						+ "}")
				.when()
				.put(endPoint + userId);
		
		System.out.println("\n\n " + user.asPrettyString());
		Assert.assertEquals(user.getStatusCode(),200);
		Assert.assertTrue(user.statusLine().contains("OK"), "OK");
		Assert.assertEquals(user.jsonPath().getString("email"),"meggriffin@fake.com");
		test.pass("Contact updated sucessfully");
	}
	
	@Test(priority = 9, dependsOnMethods = "UpdateContact")
	public void UpdateContact1() {
		
		test = extent.createTest("Update Contact");
		String endPoint = "https://thinking-tester-contact-list.herokuapp.com/contacts/";
		Response user = given()
				.header("Authorization", "Bearer " + lToken)
				.header("Content-Type", "application/json")
				.body("{\n"
						+ "  \"firstName\": \"Peter\"\n"
						+ "}")
				.when()
				.patch(endPoint + userId);
		System.out.println("\n\n" + user.asPrettyString());
		Assert.assertEquals(user.getStatusCode(),200);
		Assert.assertTrue(user.statusLine().contains("OK"), "OK");
		Assert.assertEquals(user.jsonPath().getString("firstName"),"Peter");
		test.pass("Contact updated sucessfully");
	}
	
	@Test(priority = 10, dependsOnMethods = "LoginUser")
	public void LogoutUser() {
		
		test = extent.createTest("Logout User");
		String endPoint = "https://thinking-tester-contact-list.herokuapp.com/users/logout";
		Response user = given()
				.header("Authorization", "Bearer " + lToken)
				.header("Content-Type", "application/json")
				.when()
				.post(endPoint);
		
		System.out.println("\n\n " + user.asPrettyString());
		Assert.assertEquals(user.getStatusCode(),200);
		Assert.assertTrue(user.statusLine().contains("OK"), "OK");
		test.pass("User Logged Out");
		
	}
	
	
}
