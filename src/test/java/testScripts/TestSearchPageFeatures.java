package testScripts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import commonUtility.BaseTest;
import commonUtility.dataProvider;
import helperTestUtility.RetryAnalyzer;
import pageObjectModels.LoginPage;
import pageObjectModels.SearchPage;
import pageObjectModels.SearchPage.SearchPageFilterTags;
import pageObjectModels.SearchPage.SearchPageNavBarListTabs;

/**
 * Test Login Page
 * 
 * @author Niraj.Tiwari
 */
public class TestSearchPageFeatures extends BaseTest{
	public static final Logger logger = LogManager.getLogger(TestSearchPageFeatures.class);
	
	/**
	 * Verify Search Page Features
	 */
	@Test(dataProvider = "SearchPageFeatures",retryAnalyzer = RetryAnalyzer.class, dataProviderClass = dataProvider.class,groups= {"verifySearchPageFeatures","Regression","Smoke"})
	public void verifySearchPageFeatures(String userType,String testCaseID,String loginType,String courseName) {
		logger.info("TestScript : Running -> Verify Search Page Features");

		//Open Application
		LoginPage loginPage = openApplication(System.getProperty("url"));
		logger.info("URL opened: Navigated to Pluralsight Login page");

		//Login to Pluralsight Application
		SearchPage searchPage = archUtil.loginToPluralsightApplication(loginPage, userType,loginType);

		searchPage.searchRequiredCourseInSearchBox(courseName);

		//Check Filter options
		searchPage.selectRequiredFilterTab(SearchPageFilterTags.SkillLevels);		
		boolean isSkillLevelDivVisible = searchPage.checkSelectedFilterHeaderActive(SearchPageFilterTags.SkillLevels.getSearchPageFilterTagName());
		Assert.assertTrue(isSkillLevelDivVisible, "Failed to assert Skill Level filter options");
		List<String> listOfSkillLevelFilters = searchPage.getListOfSelectedFilterOptions(SearchPageFilterTags.SkillLevels);
		assertThat(listOfSkillLevelFilters)
			.hasSize(3)
			.contains("Advanced","Beginner","Intermediate");
		Assert.assertTrue(searchPage.closeSelectedFilterActiveDiv(SearchPageFilterTags.SkillLevels.getSearchPageFilterTagName()), "Failed to close Skill Level filter options");

		searchPage.selectRequiredFilterTab(SearchPageFilterTags.Roles);		
		boolean isRolesDivVisible = searchPage.checkSelectedFilterHeaderActive(SearchPageFilterTags.Roles.getSearchPageFilterTagName());
		Assert.assertTrue(isRolesDivVisible, "Failed to assert Skill Level filter options");
		List<String> listOfRolesFilters = searchPage.getListOfSelectedFilterOptions(SearchPageFilterTags.Roles);
		assertThat(listOfRolesFilters)
			.hasSize(6)
			.contains("Business Professional","Creative Professional","Data Professional","IT Ops","Information & Cyber Security","Software Development");
		Assert.assertTrue(searchPage.closeSelectedFilterActiveDiv(SearchPageFilterTags.Roles.getSearchPageFilterTagName()), "Failed to close Roles filter options");

		//Check Nav Bar Tabs
		searchPage.clearAllTabs();

		searchPage.clickCourseTabDetails();
		List<String> listOfCourseDetails = searchPage.getCoursesListDetails();		
		assertThat(listOfCourseDetails)
			.hasSize(24)
			.containsOnlyOnce("Java Fundamentals: The Java Language")
			.doesNotContain("Python");

		List<SearchPageNavBarListTabs> listOfTabOptions = new ArrayList<SearchPage.SearchPageNavBarListTabs>();
		listOfTabOptions.add(SearchPageNavBarListTabs.Blog);
		listOfTabOptions.add(SearchPageNavBarListTabs.Resources);
		listOfTabOptions.add(SearchPageNavBarListTabs.Authors);
		
		List<String> listOfTabDetails = searchPage.validateOtherTabsDetails(listOfTabOptions);
		assertThat(listOfTabDetails)
			.hasSize(3)
			.contains("Blog posts","Resources","Authors");
		
		//Logout from Pluralsight Application
		searchPage.logoutFromPluralsightApplication();
	}

}
