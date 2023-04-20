package commonUtility;

import java.util.ArrayList;
import java.util.HashMap;


import org.testng.annotations.DataProvider;

import excelUtilities.ExcelUtilities;
import webElementUtilities.WebElementUtlities;

/**
 * Data Provider
 * 
 * @author Niraj.Tiwari
 */
public class dataProvider {

	WebElementUtlities webElementUtil = new WebElementUtlities();
	ExcelUtilities excelUtil = new ExcelUtilities();

	public dataProvider() {

	}

	String filePath = System.getProperty("driverFilePath");

	/**
	 * LoginData
	 * @return
	 * @throws Throwable
	 */
	@DataProvider(name = "LoginData")
	public Object[][] getLoginTestData() throws Throwable {

		ArrayList<HashMap<String, String>> testData = excelUtil.getTestData("verifyLoginFunctionality", filePath);

		int row = testData.size();
		int col = testData.get(0).size();
		Object[][] obj = new Object[row][col-3];

		int i=0;
		for(HashMap<String, String> map:testData) {
			obj[i][0]=map.get("UserType");
			obj[i][1]=map.get("TestCaseID");
			obj[i][2]=map.get("LoginType");
			obj[i][3]=map.get("UserText");
			i++;
		}
		return obj;
	}


}