package testScripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtility.BaseTest;
import commonUtility.dataProvider;
import pageObjectModels.HomePage;
import pageObjectModels.LoginPage;
import pageObjectModels.SearchPage;

/**
 * Test Login Page
 * 
 * @author Niraj.Tiwari
 */
public class TestHomePageFeatures extends BaseTest{
	/**
	 * Verify Home Page Features
	 *
	 * @throws Throwable
	 */
	@Test(dataProvider = "HomePageFeatures", dataProviderClass = dataProvider.class,groups= {"verifyHomePageFeatures","Regression","Smoke"})
	public void verifyHomePageFeatures(String userType,String testCaseID,String loginType,String expHomePageHeader,String expHomePageDesc) throws Throwable {
		System.out.println("TestScript : Running -> Verify Home Page Features");

		//Open Application
		LoginPage loginPage = openApplication(System.getProperty("url"));
		System.out.println("URL opened: Navigated to Pluralsight Login page");

		//Login to Pluralsight Application
		SearchPage searchpage = archUtil.loginToPluralsightApplication(loginPage, userType,loginType);

		HomePage homePage =searchpage.moveToHomePage();
	    
		String actHomePageHeader = homePage.getHomePageHeader();
		Assert.assertEquals(actHomePageHeader, expHomePageHeader);
		
	    String actHomePageDesc = homePage.getHomePageDescription();
	    Assert.assertEquals(actHomePageDesc, expHomePageDesc);
				
		//Logout from Pluralsight Application
		searchpage.logoutFromPluralsightApplication();
	}

}
