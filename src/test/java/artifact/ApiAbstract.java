package artifact;

import org.testng.annotations.BeforeClass;

public abstract class ApiAbstract {
	private final static String defaultEndpoint = "https://jsonplaceholder.typicode.com/";
	protected static String baseUrl = "";
	
	//In case of different test/production environments or clusters, use environment variable
	@BeforeClass
	public static void beforeTests() {
		if (System.getProperty("baseUrl")==null || !System.getProperty("baseUrl").contains("https://")) {
			baseUrl=defaultEndpoint;
		} else {
			baseUrl = System.getProperty("baseUrl");
		}
	}
	

}
