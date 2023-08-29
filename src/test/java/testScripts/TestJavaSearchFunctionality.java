package testScripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtility.BaseTest;
import commonUtility.dataProvider;
import pageObjectModels.LoginPage;
import pageObjectModels.SearchPage;

/**
 * Test Login Page
 * 
 * @author Niraj.Tiwari
 */
public class TestJavaSearchFunctionality extends BaseTest{
	/**
	 * Verify Java Search Functionality
	 *
	 * @throws Throwable
	 */
	@Test(dataProvider = "JavaSearchFunctionality", dataProviderClass = dataProvider.class,groups= {"verifyJavaSearchFunctionality","Regression","Smoke"})
	public void verifyJavaSearchFunctionality(String userType,String testCaseID,String loginType,String expSearchText) throws Throwable {
		System.out.println("TestScript : Running -> Verify Java Search Functionality");

		//Open Application
		LoginPage loginPage = openApplication(System.getProperty("url"));
		System.out.println("URL opened: Navigated to Pluralsight Login page");

		//Login to Pluralsight Application
		SearchPage searchpage = archUtil.loginToPluralsightApplication(loginPage, userType,loginType);

	    
		
		
		

				
		//Logout from Pluralsight Application
		searchpage.logoutFromPluralsightApplication();
	}

}
