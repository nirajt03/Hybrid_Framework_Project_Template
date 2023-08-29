package pageObjectModels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import webElementUtilities.WebElementUtlities;

public class CoursePage extends BasePage {

	public CoursePage(WebDriver rdriver) {
		super(rdriver);
	}
	
	private By titleHeader = By.xpath("//div[contains(@class,'title section')]//h1");
	private By courseDescription = By.xpath("//div[@class='text-component']");
	private By authorLink = By.xpath("//span[@class='course-hero-rating']//following-sibling::a");
	private By freeTrailBtn = By.xpath("//a[@id='free_trial']");
	private By playCourseOverviewBtn = By.xpath("//div[@id='course_overview']//a");
	
	public String getCoursePageHeader() throws InterruptedException {
		customWaitInSec(1);
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, titleHeader, 15);
		return WebElementUtlities.getText(driver, driver.findElement(titleHeader));
	}
	
	public String getCourseDescription() {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, courseDescription, 15);
		return WebElementUtlities.getText(driver, driver.findElement(courseDescription));
	}
	
	public boolean validateAuthorLinkVisible() {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, authorLink, 10);
		return WebElementUtlities.isElementVisible(driver, authorLink);
	}
	
	public String getFreeTrailButtonText() {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, freeTrailBtn, 15);
		return WebElementUtlities.getAlertText(driver);
	}
	
	public String getCourseOverviewButtonText() {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, playCourseOverviewBtn, 15);
		return WebElementUtlities.getAlertText(driver);
	}
	
	//move to search page
	
}
