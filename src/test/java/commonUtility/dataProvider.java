package commonUtility;

import java.util.ArrayList;
import java.util.HashMap;


import org.testng.annotations.DataProvider;

/**
 * Data Provider
 * 
 * @author Niraj.Tiwari
 */
public class dataProvider {

	private ArchUtilities archUtl= new ArchUtilities();

	public dataProvider() {

	}

	/**
	 * LoginData
	 * @return
	 * @throws Throwable
	 */
	@DataProvider(name = "LoginData")
	public Object[][] getLoginTestData() throws Throwable {

		ArrayList<HashMap<String, String>> testData = archUtl.getTestData("verifyLoginFunctionality");
		
		int row = testData.size();
        int col = testData.get(0).size();
        Object[][] obj = new Object[row][col-3];

		int i=0;
		for(HashMap<String, String> map:testData) {
			obj[i][0]=map.get("UserType");
			obj[i][1]=map.get("TestCaseID");
			obj[i][2]=map.get("LoginType");
			obj[i][3]=map.get("ExpSearchText");
			
			i++;
		}
		return obj;
	}
	
	/**
	 * Negative Login Scenarios
	 * @return
	 * @throws Throwable
	 */
	@DataProvider(name = "NegativeLoginScenarios")
	public Object[][] getNegativeLoginScenariosData() throws Throwable {

		ArrayList<HashMap<String, String>> testData = archUtl.getTestData("verifyLoginPageNegativeScenarios");
		
		int row = testData.size();
        int col = testData.get(0).size();
        Object[][] obj = new Object[row][col-1];

		int i=0;
		for(HashMap<String, String> map:testData) {
			obj[i][0]=map.get("UserType");
			obj[i][1]=map.get("TestCaseID");
			obj[i][2]=map.get("Username");
			obj[i][3]=map.get("Password");
			obj[i][4]=map.get("ExpErrorMessage");
			
			i++;
		}
		return obj;
	}
	
	/**
	 * Course Page Features
	 * @return
	 * @throws Throwable
	 */
	@DataProvider(name = "CoursePageFeatures")
	public Object[][] getCoursePageFeaturesData() throws Throwable {

		ArrayList<HashMap<String, String>> testData = archUtl.getTestData("verifyCoursePageFeatures");
		
		int row = testData.size();
        int col = testData.get(0).size();
        Object[][] obj = new Object[row][col-1];

		int i=0;
		for(HashMap<String, String> map:testData) {
			obj[i][0]=map.get("UserType");
			obj[i][1]=map.get("TestCaseID");
			obj[i][2]=map.get("LoginType");
			obj[i][3]=map.get("ExpSearchText");
			
			i++;
		}
		return obj;
	}
	
	/**
	 * Home Page Features
	 * @return
	 * @throws Throwable
	 */
	@DataProvider(name = "HomePageFeatures")
	public Object[][] getHomePageFeaturesData() throws Throwable {

		ArrayList<HashMap<String, String>> testData = archUtl.getTestData("verifyHomePageFeatures");
		
		int row = testData.size();
        int col = testData.get(0).size();
        Object[][] obj = new Object[row][col-1];

		int i=0;
		for(HashMap<String, String> map:testData) {
			obj[i][0]=map.get("UserType");
			obj[i][1]=map.get("TestCaseID");
			obj[i][2]=map.get("LoginType");
			obj[i][3]=map.get("ExpHomePageHeader");
			obj[i][4]=map.get("ExpHomePageDesc");
			
			i++;
		}
		return obj;
	}
	
	/**
	 * Java Search Functionality
	 * @return
	 * @throws Throwable
	 */
	@DataProvider(name = "JavaSearchFunctionality")
	public Object[][] getJavaSearchFunctionalityData() throws Throwable {

		ArrayList<HashMap<String, String>> testData = archUtl.getTestData("verifyJavaSearchFunctionality");
		
		int row = testData.size();
        int col = testData.get(0).size();
        Object[][] obj = new Object[row][col-1];

		int i=0;
		for(HashMap<String, String> map:testData) {
			obj[i][0]=map.get("UserType");
			obj[i][1]=map.get("TestCaseID");
			obj[i][2]=map.get("LoginType");
			obj[i][3]=map.get("ExpSearchText");
			
			i++;
		}
		return obj;
	}
	
	/**
	 * Search Page Features
	 * @return
	 * @throws Throwable
	 */
	@DataProvider(name = "SearchPageFeatures")
	public Object[][] getSearchPageFeaturesData() throws Throwable {

		ArrayList<HashMap<String, String>> testData = archUtl.getTestData("verifySearchPageFeatures");
		
		int row = testData.size();
        int col = testData.get(0).size();
        Object[][] obj = new Object[row][col-1];

		int i=0;
		for(HashMap<String, String> map:testData) {
			obj[i][0]=map.get("UserType");
			obj[i][1]=map.get("TestCaseID");
			obj[i][2]=map.get("LoginType");
			obj[i][3]=map.get("ExpSearchText");
			
			i++;
		}
		return obj;
	}
	
	
}