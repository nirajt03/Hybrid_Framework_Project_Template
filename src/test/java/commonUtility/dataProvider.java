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
	 */
	@DataProvider(name = "LoginData")
	public Object[][] getLoginTestData() {

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
	 */
	@DataProvider(name = "NegativeLoginScenarios")
	public Object[][] getNegativeLoginScenariosData() {

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
	 */
	@DataProvider(name = "CoursePageFeatures")
	public Object[][] getCoursePageFeaturesData() {

		ArrayList<HashMap<String, String>> testData = archUtl.getTestData("verifyCoursePageFeatures");
		
		int row = testData.size();
        int col = testData.get(0).size();
        Object[][] obj = new Object[row][col-1];

		int i=0;
		for(HashMap<String, String> map:testData) {
			obj[i][0]=map.get("UserType");
			obj[i][1]=map.get("TestCaseID");
			obj[i][2]=map.get("LoginType");
			obj[i][3]=map.get("CourseName");
			obj[i][4]=map.get("ExpCourseHeaderText");
			obj[i][5]=map.get("ExpCourseDescriptionText");
			obj[i][6]=map.get("ExpFreeTrailText");
			obj[i][7]=map.get("ExpCourseOverviewText");
			
			i++;
		}
		return obj;
	}
	
	/**
	 * Home Page Features
	 * @return
	 */
	@DataProvider(name = "HomePageFeatures")
	public Object[][] getHomePageFeaturesData() {

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
	 */
	@DataProvider(name = "JavaSearchFunctionality")
	public Object[][] getJavaSearchFunctionalityData() {

		ArrayList<HashMap<String, String>> testData = archUtl.getTestData("verifyJavaSearchFunctionality");
		
		int row = testData.size();
        int col = testData.get(0).size();
        Object[][] obj = new Object[row][col-1];

		int i=0;
		for(HashMap<String, String> map:testData) {
			obj[i][0]=map.get("UserType");
			obj[i][1]=map.get("TestCaseID");
			obj[i][2]=map.get("LoginType");
			obj[i][3]=map.get("CourseName");
			obj[i][4]=map.get("ExpCourseHeaderText");
			obj[i][5]=map.get("ExpCourseDescriptionText");
			
			i++;
		}
		return obj;
	}
	
	/**
	 * Search Page Features
	 * @return
	 */
	@DataProvider(name = "SearchPageFeatures")
	public Object[][] getSearchPageFeaturesData() {

		ArrayList<HashMap<String, String>> testData = archUtl.getTestData("verifySearchPageFeatures");
		
		int row = testData.size();
        int col = testData.get(0).size();
        Object[][] obj = new Object[row][col-1];

		int i=0;
		for(HashMap<String, String> map:testData) {
			obj[i][0]=map.get("UserType");
			obj[i][1]=map.get("TestCaseID");
			obj[i][2]=map.get("LoginType");
			obj[i][3]=map.get("CourseName");
			
			i++;
		}
		return obj;
	}
	
	
}