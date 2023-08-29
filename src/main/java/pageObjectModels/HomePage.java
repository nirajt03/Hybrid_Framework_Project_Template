package pageObjectModels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import webElementUtilities.WebElementUtlities;

public class HomePage extends BasePage{

	public HomePage(WebDriver rdriver) {
		super(rdriver);
	}

	private By homePageHeader = By.xpath("//div[@id='content']//h1");
	private By homePageDescription = By.xpath("//div[@id='content']//p");
	
	
	/**
	 * get Home Page Header
	 * @return
	 * @throws InterruptedException
	 */
	public String getHomePageHeader() throws InterruptedException {
		customWaitInSec(1);
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, homePageHeader, 15);
		return WebElementUtlities.getText(driver, driver.findElement(homePageHeader));
	}
	
	/**
	 * get Home Page Description
	 * @return
	 */
	public String getHomePageDescription() {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, homePageDescription, 15);
		return WebElementUtlities.getText(driver, driver.findElement(homePageDescription));
	}
	
}
