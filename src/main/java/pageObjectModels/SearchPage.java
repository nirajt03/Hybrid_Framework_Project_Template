package pageObjectModels;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import webElementUtilities.WebElementUtlities;

public class SearchPage  extends BasePage{

	public SearchPage(WebDriver rdriver) {
		super(rdriver);
	}

	//Filters option
	private By filtersTab = By.xpath("//div[@id='Filters']");	
	
	//Courses tab
	private By coursesTab = By.xpath("//div[@id='courses_tabs']");
	private By coursesSearchDiv = By.xpath("//div[contains(@class,'search-results-rows')]");
	
	
	//div[@id='courses_tabs']//a[text()='All']
	//a[text()='All']//parent::li[contains(@class,'ui-tabs-active')]
	//a[text()='Courses']//parent::li[contains(@class,'ui-tabs-active')]
	//div[@id='tabs']//div[@id='tabs-2' and @style='display: block;']
	//div[@id='tabs']//div[@id='tabs-2' and @style='display: none;']
	
	//(//div[contains(@class,'search-results-rows')]//div[contains(@class,'columns')])[2]
	//(//div[contains(@class,'columns')]//div[contains(@class,'icon')]//img)[2]
	//(//div[contains(@class,'columns')]//div[contains(@class,'title')]//a)[2]
	//(//div[contains(@class,'columns')]//div[contains(@class,'details')]//div)[5]
	
	//(//div[@id='courses_tabs']//p)[3]
	
	/**
	 * Search Page Filter Tags
	 * @author Niraj.Tiwari
	 */
	public enum SearchPageFilterTags{
		SkillLevels("Skill Levels"),Authors("Authors"),Roles("Roles"),Certifications("Certifications");

		private String searchPageFilterTags; 

		SearchPageFilterTags(String searchPageFilterTags) {
			this.searchPageFilterTags = searchPageFilterTags;
		}

		public String getSearchPageFilterTagName() {
			return searchPageFilterTags;
		}
	}
	
	/**
	 * Search Page NavBar List Tabs
	 * @author Niraj.Tiwari
	 */
	public enum SearchPageNavBarListTabs{
		All("All"),Courses("Courses"),Blog("Blog"),Resources("Resources"),Authors("Authors");

		private String searchPageNavBarListTab; 

		SearchPageNavBarListTabs(String searchPageNavBarListTab) {
			this.searchPageNavBarListTab = searchPageNavBarListTab;
		}

		public String getSearchPageNavBarListTabName() {
			return searchPageNavBarListTab;
		}
	}
	
	//---------------------------Filter tabs methods------------------------------------
	
	/**
	 * select Required Skill Tab
	 * @param filterTag
	 */
	public void selectRequiredSkillTab(SearchPageFilterTags filterTag) {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, filtersTab, 10);
		String filterTagValue = filterTag.getSearchPageFilterTagName();
		By filterTagDiv = By.xpath("//div[text()='"+filterTagValue+"']");
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, filterTagDiv, 10);
		WebElementUtlities.click(driver, driver.findElement(filterTagDiv));
	}
	
	/**
	 * check Selected Filter Header Active
	 * @param filterTagName
	 * @return
	 */
	public boolean checkSelectedFilterHeaderActive(String filterTagName) {
		By activeFilterHeader = By.xpath("//div[.='"+filterTagName+"']//parent::h3[contains(@class,'accordion-header-active')]");
		if(!WebElementUtlities.isElementVisible(driver, activeFilterHeader)) {
			throw new ElementNotInteractableException("Element not found");
		}
		return true;
	}

	/**
	 * get List Of Selected Filter Options
	 * @param filterTag
	 * @return
	 */
	public List<String> getListOfSelectedFilterOptions(SearchPageFilterTags filterTag){
		new ArrayList<String>();
		selectRequiredSkillTab(filterTag);
		String filterTagValue = filterTag.getSearchPageFilterTagName();
		checkSelectedFilterHeaderActive(filterTagValue);		
		By filterTagDiv = By.xpath("//div[text()='"+filterTagValue+"']//ancestor::h3[contains(@class,'ui-accordion-header-active')]//following-sibling::div[contains(@class,'ui-accordion-content-active')]//span");
		List<WebElement> listOfSelectedFilterBy = driver.findElements(filterTagDiv);
		return WebElementUtlities.getElementsText(driver, listOfSelectedFilterBy);
	}
	
	/**
	 * close Selected Filter Active Div
	 * @param filterTagName
	 * @return
	 */
	public boolean closeSelectedFilterActiveDiv(String filterTagName) {
		checkSelectedFilterHeaderActive(filterTagName);
		By activeFilterHeader = By.xpath("//div[.='"+filterTagName+"']//parent::h3[contains(@class,'accordion-header-collapsed')]");
		if(!WebElementUtlities.isElementVisible(driver, activeFilterHeader)) {
			throw new ElementNotInteractableException("Element not found");
		}
		return true;
	}
	
	//---------------------------Nav Bar tabs methods------------------------------------

	public HomePage moveToHomePage() {
		navigateToDifferentPage(System.getProperty("user.dir") + "\\src\\main\\resources\\WebPages\\HomePage.html");
		return new HomePage(driver);
	}
	
	
	//move to course page
}
