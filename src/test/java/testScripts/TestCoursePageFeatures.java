package testScripts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtility.BaseTest;
import commonUtility.dataProvider;
import helperTestUtility.RetryAnalyzer;
import pageObjectModels.CoursePage;
import pageObjectModels.LoginPage;
import pageObjectModels.SearchPage;

/**
 * Test Login Page
 * 
 * @author Niraj.Tiwari
 */
public class TestCoursePageFeatures extends BaseTest{
	public static final Logger logger = LogManager.getLogger(TestCoursePageFeatures.class);
	
	/**
	 * Verify Course Page Features
	 */
	@Test(dataProvider = "CoursePageFeatures",retryAnalyzer = RetryAnalyzer.class,dataProviderClass = dataProvider.class,groups= {"verifyCoursePageFeatures","Regression","Smoke"})
	public void verifyCoursePageFeatures(String userType,String testCaseID,String loginType,String courseName,String expCourseHeaderText, 
			String expCourseDescriptionText,String expFreeTrailButtonText, String expCourseOverviewText) {
		
		logger.info("TestScript : Running -> Verify Course Page Features");

		//Open Application
		LoginPage loginPage = openApplication(System.getProperty("url"));
		logger.info("URL opened: Navigated to Pluralsight Login page");

		//Login to Pluralsight Application
		SearchPage searchPage = archUtil.loginToPluralsightApplication(loginPage, userType,loginType);

		searchPage.clearAllTabs();

		searchPage.clickCourseTabDetails();
		
		CoursePage coursePage= searchPage.moveToCoursePage(courseName);
		
		//Validate course page details
		String courseHeaderText = coursePage.getCoursePageHeader();
		Assert.assertEquals(courseHeaderText, expCourseHeaderText);
		
		String courseDescriptionText = coursePage.getCourseDescription();
		Assert.assertEquals(courseDescriptionText, expCourseDescriptionText);
		
		boolean isAuthorLinkVisible = coursePage.validateAuthorLinkVisible();
		Assert.assertTrue(isAuthorLinkVisible, "Author Link is not visible");
		
		String freeTrailButtonText = coursePage.getFreeTrailButtonText();
		Assert.assertEquals(freeTrailButtonText, expFreeTrailButtonText);
		
		String courseOverviewText = coursePage.getCourseOverviewButtonText();
		Assert.assertEquals(courseOverviewText, expCourseOverviewText);
				
		//Logout from Pluralsight Application
		searchPage.logoutFromPluralsightApplication();
	}

}
