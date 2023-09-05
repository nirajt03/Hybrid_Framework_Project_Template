package testScripts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtility.BaseTest;
import commonUtility.dataProvider;
import helperTestUtility.RetryAnalyzer;
import pageObjectModels.HomePage;
import pageObjectModels.LoginPage;
import pageObjectModels.SearchPage;

/**
 * Test Login Page
 * 
 * @author Niraj.Tiwari
 */
public class TestHomePageFeatures extends BaseTest{
	public static final Logger logger = LogManager.getLogger(TestHomePageFeatures.class);
	
	/**
	 * Verify Home Page Features
	 */
	@Test(dataProvider = "HomePageFeatures",retryAnalyzer = RetryAnalyzer.class, dataProviderClass = dataProvider.class,groups= {"verifyHomePageFeatures","Regression","Smoke"})
	public void verifyHomePageFeatures(String userType,String testCaseID,String loginType,String expHomePageHeader,String expHomePageDesc) {
		logger.info("TestScript : Running -> Verify Home Page Features");

		//Open Application
		LoginPage loginPage = openApplication(System.getProperty("url"));
		logger.info("URL opened: Navigated to Pluralsight Login page");

		//Login to Pluralsight Application
		SearchPage searchPage = archUtil.loginToPluralsightApplication(loginPage, userType,loginType);

		//move to home page
		HomePage homePage =searchPage.moveToHomePage();
	    
		//assert validations
		String actHomePageHeader = homePage.getHomePageHeader();
		Assert.assertEquals(actHomePageHeader, expHomePageHeader);
		
	    String actHomePageDesc = homePage.getHomePageDescription();
	    Assert.assertEquals(actHomePageDesc, expHomePageDesc);
				
		//Logout from Pluralsight Application
		searchPage.logoutFromPluralsightApplication();
	}

}
