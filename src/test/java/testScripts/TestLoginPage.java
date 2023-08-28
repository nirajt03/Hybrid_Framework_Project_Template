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
public class TestLoginPage extends BaseTest{
	/**
	 * Verify Login for all roles
	 *
	 * @throws Throwable
	 */
	@Test(dataProvider = "LoginData", dataProviderClass = dataProvider.class,groups= {"verifyLoginFunctionality","Regression","Smoke"})
	public void verifyLoginFunctionality(String userType,String testCaseID,String loginType,String expSearchText) throws Throwable {
		System.out.println("TestScript : Running -> verify Login Functionality");

		// Open Application
		LoginPage loginPage = openApplication(System.getProperty("url"));
		System.out.println("URL opened");
		System.out.println("Navigated to Pluralsight Login page");

		SearchPage searchpage = archUtil.loginToPluralsightApplication(loginPage, userType,loginType);

		System.out.println("At home page" +expSearchText);

		Assert.assertFalse(true);
		
		//Logout from Pluralsight Application
		searchpage.logoutFromPluralsightApplication();
	}

}
