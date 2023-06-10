package artifact;

import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.*;


import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class CommentsTest extends ApiAbstract {
	//If the endpoints are this similar there's probably a good way to abstract the tests in a real world setting
	private final String thisEndpoint = "comments";

	@Test
	public void testEndpointGet() {
		int numRecords=500;
		Response response;
		get(baseUrl + thisEndpoint)
			.then()
			.statusCode(HttpStatus.SC_OK)
			.contentType(ContentType.JSON)
			.extract();
        response = get(baseUrl + thisEndpoint);
        List<String> resIDs = response.jsonPath().get("id");
        assertEquals("Expecting items",numRecords,resIDs.size());
        //Checking expected number of items returned based on where the test edges are, schema validation, etc are common tests
        Headers allHeaders = response.getHeaders();
        //As well as validating headers if needed for auth, perhaps or in specialized situations. Headers not usually checked
	}

	@Test
	public void testEndpointPost() {
		post(baseUrl + thisEndpoint)
			.then()
			.statusCode(HttpStatus.SC_CREATED)
			.body("id",equalTo(101));
		/* Check resources to see if anything actually happened here (databases, aws message queues, etc)
		 * 
		 * When I ran it the endpoint failed with a 
		 * 500, but the document said it was there. On postman it worked for me. In a real world setting I'd
		 * troubleshoot this a bit more but I think you are probably looking for approach and code flow at
		 * this time.
		 */
	}

	@Test
	public void testEndpointDelete() {
		delete(baseUrl + thisEndpoint)
			.then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
		/* Endpoint is not found when I'm trying it but in docs this was a path. If it worked 
		 * this is where I might check for soft delete/hard delete in database. Some static
		 * testing might be around what happens if the database has to be restored from backup
		 * and a user opted out of marketing emails, for example. Even if not changed, understanding
		 * risks is what I believe part of QA is.
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
			.get(baseUrl + thisEndpoint + "/asdf"). //I wouldn't put in this here but the endpoint ignored the query
		then()
			.statusCode(HttpStatus.SC_NOT_FOUND);
	}

	@Test
	public void testEndpointCookiesOrAuth() {
		//Possibly some auth checking here to ensure someone didn't accidently reset a config or something if necessary
	}
}
