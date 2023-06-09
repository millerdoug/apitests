package artifact;

import org.testng.annotations.BeforeClass;

public abstract class ApiAbstract {
	private final static String endpoint = "https://jsonplaceholder.typicode.com/";
	protected static String baseUrl = "";
	
	//In case of different test/production environments or clusters
	@BeforeClass
	public static void beforeTests() {
		if (System.getProperty("baseUrl")==null || System.getProperty("baseUrl").equals("")) {
			baseUrl=endpoint;
		} else {
			baseUrl = System.getProperty("baseUrl");
		}
	}
	

}
