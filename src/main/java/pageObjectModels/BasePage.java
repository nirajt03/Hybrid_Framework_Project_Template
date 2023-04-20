package pageObjectModels;

import org.openqa.selenium.WebDriver;
import webElementUtilities.WebElementUtlities;

/**
 * Base Page
 * @author Niraj.Tiwari
 */
public class BasePage {

	protected WebDriver driver;
	WebElementUtlities webElementUtil = new WebElementUtlities();
	
	public BasePage(WebDriver rdriver) {
		this.driver = rdriver;
	}
	
	


}
