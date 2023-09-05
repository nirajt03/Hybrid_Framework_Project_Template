package testScripts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtility.BaseTest;
import commonUtility.dataProvider;
import helperTestUtility.RetryAnalyzer;
import pageObjectModels.LoginPage;

/**
 * Test Login Page
 * 
 * @author Niraj.Tiwari
 */
public class TestLoginPageNegativeScenarios extends BaseTest{
	public static final Logger logger = LogManager.getLogger(TestLoginPageNegativeScenarios.class);
	
	/**
	 * Verify Login Page Negative Scenarios
	 */
	@Test(dataProvider = "NegativeLoginScenarios", retryAnalyzer = RetryAnalyzer.class, dataProviderClass = dataProvider.class,groups= {"verifyLoginPageNegativeScenarios","Regression","Smoke"})
	public void verifyLoginPageNegativeScenarios(String userType,String testCaseID,String username,String password,String expErrorMessage) {
		logger.info("TestScript : Running -> verify Login Page Negative Scenarios");

		//Open Application
		LoginPage loginPage = openApplication(System.getProperty("url"));
		logger.info("URL opened: Navigated to Pluralsight Login page");

		//Login to Pluralsight Application
		String ribbonText = archUtil.checkNegativeLoginScenarios(loginPage, username, password);
	    Assert.assertEquals(ribbonText, expErrorMessage);
	}

}
