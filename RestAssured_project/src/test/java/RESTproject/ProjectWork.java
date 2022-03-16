package RESTproject;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class ProjectWork {
	@Test
	public void createuser(ITestContext user, ITestContext pass) throws JsonProcessingException {
		
		POJO p= new POJO();
		p.setUsername("s5");
		p.setFirstname("Sushmita");
		p.setLastname("Sahoo");
		p.setEmail("sahoo@ml.com");
		p.setPassword("Welcome123");
		p.setPhone("978878");
		
		String user1= p.getUsername();
		String pass1=p.getPassword();
		
		System.out.println(user1);
		System.out.println(p.getFirstname());
		System.out.println(p.getLastname());
		System.out.println(p.getEmail());
		System.out.println(pass1);
		System.out.println(p.getPhone());
		
		ObjectMapper obj1= new ObjectMapper();
		
		String respbody= obj1.writerWithDefaultPrettyPrinter().writeValueAsString(p);
		System.out.println(respbody);
		
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		Response r= RestAssured.given()
			.contentType(ContentType.JSON)
			.body(respbody)
			.when()
			.post("/user")
			.then()
			.statusCode(200).log().all().extract().response();
		
		System.out.println(respbody);
		
		int code = r.jsonPath().getInt("code");
		Assert.assertEquals(code, 200);
		
		user.setAttribute("username", user1);
		pass.setAttribute("password", pass1);
		
	}
	
	@Test(dependsOnMethods="createuser", enabled=true)
	public void userLogin(ITestContext user, ITestContext pass) {
		
		String u1= user.getAttribute("username").toString();
		String p1=user.getAttribute("password").toString();
		
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		RestAssured.given()
		.contentType(ContentType.JSON).queryParam("username", u1).queryParam("password", p1)
		.when()
		.get("/user/login")
		.then()
		.statusCode(200).log().all();
		
	}
	
	@Test(dependsOnMethods="createuser")
	public void updateuser(ITestContext user, ITestContext pass) throws JsonProcessingException {
		
		String u1= user.getAttribute("username").toString();
				
				
		JSONObject p1= new JSONObject();
		p1.put("username", "s5");
		p1.put("firstname", "Heli");
		p1.put("lastname","Patel");
		p1.put("email", "heli@ml.com");
		p1.put("password", "Wel123");
		p1.put("phone", "76727");
		
		ObjectMapper obj1= new ObjectMapper();
		
		String respbody= obj1.writerWithDefaultPrettyPrinter().writeValueAsString(p1);
		System.out.println(respbody);
		
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		RestAssured.given()
			.contentType(ContentType.JSON)
			.body(respbody)
			.when()
			.put("/user/"+u1)
			.then()
			.statusCode(200).log().all();
		
		System.out.println(respbody);
		
	}

	@Test(dependsOnMethods="userLogin")
public void userLogout(ITestContext user, ITestContext pass) {
		
				
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		RestAssured.given()
		.get("/user/logout")
		.then()
		.statusCode(200).log().all();
		
	}


@Test(dependsOnMethods="userLogout")
public void deleteuser(ITestContext user) {
	
	String u1= user.getAttribute("username").toString();
	
	RestAssured.baseURI="https://petstore.swagger.io/v2";
	RestAssured.given()
	.delete("/user/"+u1)
	.then()
	.statusCode(200).log().all();
}
	
}
