package testScripts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtility.BaseTest;
import commonUtility.dataProvider;
import helperTestUtility.RetryAnalyzer;
import pageObjectModels.LoginPage;
import pageObjectModels.SearchPage;

/**
 * Test Login Page
 * 
 * @author Niraj.Tiwari
 */
public class TestLoginPage extends BaseTest{
	public static final Logger logger = LogManager.getLogger(TestLoginPage.class);
	
	/**
	 * Verify Login for all roles
	 */
	@Test(dataProvider = "LoginData",retryAnalyzer = RetryAnalyzer.class,dataProviderClass = dataProvider.class,groups= {"verifyLoginFunctionality","Regression","Smoke"})
	public void verifyLoginFunctionality(String userType,String testCaseID,String loginType,String expSearchText) {
		logger.info("TestScript : Running -> verify Login Functionality");

		//Open Application
		LoginPage loginPage = openApplication(System.getProperty("url"));
		logger.info("URL opened: Navigated to Pluralsight Login page");

		//Login to Pluralsight Application
		SearchPage searchPage = archUtil.loginToPluralsightApplication(loginPage, userType,loginType);

		//assert validation
		String actSearchBoxText = searchPage.getSearchPlaceholderText();
		Assert.assertEquals(actSearchBoxText, expSearchText);
		
		//Assert.assertFalse(true);
		
		//Logout from Pluralsight Application
		searchPage.logoutFromPluralsightApplication();
	}

}
