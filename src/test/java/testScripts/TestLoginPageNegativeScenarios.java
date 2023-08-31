package testScripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtility.BaseTest;
import commonUtility.dataProvider;
import pageObjectModels.LoginPage;

/**
 * Test Login Page
 * 
 * @author Niraj.Tiwari
 */
public class TestLoginPageNegativeScenarios extends BaseTest{
	/**
	 * Verify Login Page Negative Scenarios
	 *
	 * @throws Throwable
	 */
	@Test(dataProvider = "NegativeLoginScenarios", dataProviderClass = dataProvider.class,groups= {"verifyLoginPageNegativeScenarios","Regression","Smoke"})
	public void verifyLoginPageNegativeScenarios(String userType,String testCaseID,String username,String password,String expErrorMessage) throws Throwable {
		System.out.println("TestScript : Running -> verify Login Page Negative Scenarios");

		//Open Application
		LoginPage loginPage = openApplication(System.getProperty("url"));
		System.out.println("URL opened: Navigated to Pluralsight Login page");

		//Login to Pluralsight Application
		String ribbonText = archUtil.checkNegativeLoginScenarios(loginPage, username, password);
	    Assert.assertEquals(ribbonText, expErrorMessage);
	}

}
