package testScripts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
public class TestJavaSearchFunctionality extends BaseTest{
	public static final Logger logger = LogManager.getLogger(TestJavaSearchFunctionality.class);
	
	/**
	 * Verify Java Search Functionality
	 */
	@Test(dataProvider = "JavaSearchFunctionality",retryAnalyzer = RetryAnalyzer.class, dataProviderClass = dataProvider.class,groups= {"verifyJavaSearchFunctionality","Regression","Smoke"})
	public void verifyJavaSearchFunctionality(String userType,String testCaseID,String loginType,String courseName,String expCourseHeaderText,String expCourseDescriptionText) {
		logger.info("TestScript : Running -> Verify Java Search Functionality");

		//Open Application
		LoginPage loginPage = openApplication(System.getProperty("url"));
		logger.info("URL opened: Navigated to Pluralsight Login page");

		//Login to Pluralsight Application
		SearchPage searchPage = archUtil.loginToPluralsightApplication(loginPage, userType,loginType);

		searchPage.searchRequiredCourseInSearchBox("Java");

		searchPage.clearAllTabs();

		//Validate Java Course Search Functionality
		searchPage.clickCourseTabDetails();
		List<String> listOfCourseDetails = searchPage.getCoursesListDetails();		
		assertThat(listOfCourseDetails)
			.hasSize(24)
			.containsOnlyOnce("Java Fundamentals: The Java Language")
			.doesNotContain("Python");

		CoursePage coursePage= searchPage.moveToCoursePage(courseName);

		String courseHeaderText = coursePage.getCoursePageHeader();
		Assert.assertEquals(courseHeaderText, expCourseHeaderText);

		String courseDescriptionText = coursePage.getCourseDescription();
		Assert.assertEquals(courseDescriptionText, expCourseDescriptionText);

		//Logout from Pluralsight Application
		searchPage.logoutFromPluralsightApplication();
	}

}
