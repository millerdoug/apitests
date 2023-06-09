package artifact;

import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.*;


import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApiTest extends ApiAbstract {
	private final String thisEndpoint = "posts";

	@Test
	public void testEndpointGet() {
		Response response;
		get(baseUrl + thisEndpoint)
			.then()
			.statusCode(HttpStatus.SC_OK)
			.contentType(ContentType.JSON)
			.extract();
        response = get(baseUrl + thisEndpoint);
        List<String> resIDs = response.jsonPath().get("id");
        assertEquals("Expecting items",100,resIDs.size());
	}

	@Test
	public void testEndpointPost() {
		post(baseUrl + thisEndpoint)
			.then()
			.statusCode(HttpStatus.SC_CREATED)
			.body("id",equalTo(101));
		//Check backend resources to see if anything actually happened
	}

	@Test
	public void testEndpointDelete() {
		delete(baseUrl + thisEndpoint)
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
		/* Endpoint is not found but in docs this was a path. If it worked this is where I might 
		 * check for soft delete/hard delete in database. Some static testing might be around 
		 * what happens if the database has to be restored from backup and a user opted out of 
		 * marketing emails, for example. Even if not changed, understanding risks is what I believe
		 * part of QA is.
		 */
		
	}

	@Test
	public void testEndpointInvalid() {
		patch(baseUrl + thisEndpoint)
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
		given()
			.queryParam("Param","InvalidData").
		when()
			.get(baseUrl + thisEndpoint + "/asdf").
		then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
	}

	@Test
	public void testEndpointCookiesOrAuth() {
		//Possibly some auth checking here to ensure someone didn't accidently reset a config or something
	}
}
