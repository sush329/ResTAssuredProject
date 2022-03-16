package IBM.RestAssured_project;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class restassuredday1 {
	
	@Test(enabled=false)
	public void Testcase() {
		
		Response res= RestAssured.get("http://localhost:3000/comments");
		String responsebody= res.asString();
		//System.out.println(responsebody);
		System.out.println(res.getStatusCode());
		System.out.println(res.getStatusLine());
		System.out.println(res.getHeaders());
		System.out.println(res.jsonPath().get("body"));
	}
	
	@Test
	public void Testcase2() {
		//RestAssured.baseURI="http://localhost:3000";
		//RestAssured.given().get("/comments").then().statusCode(200).log().all();
		//RestAssured.given().delete("/comments").then().statusCode(200).log().all();
		
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		RestAssured.
		given()
		.contentType(ContentType.JSON).queryParam("username", "sush123").queryParam("password", "Welcome").when().get("/user/login")
		.then().statusCode(200).log().all();
	}

}
