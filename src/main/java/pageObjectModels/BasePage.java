package pageObjectModels;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Stopwatch;

import webElementUtilities.WebElementUtlities;

/**
 * Base Page
 * @author Niraj.Tiwari
 */
public class BasePage {

	protected WebDriver driver;

	public BasePage(WebDriver rdriver) {
		this.driver = rdriver;
	}

	public static String originalHandle;
	public static String newhandle; 
	public static boolean isAlertAccepted;

	protected By highlightedOption = By.xpath("//li[contains(@class,'highlighted')]");

	//Common Header elements	
	protected By logoutIcon = By.xpath("//div[@id='logout']");
	protected By getStartedIcon = By.xpath("//div[@id='getStarted']");
	protected By pluralsightIcon = By.xpath("//div[@id='sitename']");
	protected By coursesLink = By.xpath("//div[@id='courses']");
	protected By headerSearchBox = By.xpath("//input[contains(@class,'header_search')]");


	/**
	 * Logout From Pluralsight Application
	 * @return
	 * @throws Throwable
	 */
	public LoginPage logoutFromPluralsightApplication() {

		WebElementUtlities.explicitWaitForElementToBeVisible(driver, logoutIcon);

		WebElementUtlities.click(driver, driver.findElement(logoutIcon));
		System.out.println("Logout button clicked");
		handleAlert(driver);

		System.out.println("Successfully Logged Out from Pluralsight application");
		return (new LoginPage(driver));
	}

	/**
	 * Search Required Course In Search Box
	 * @param courseToBeSearched
	 */
	public void searchRequiredCourseInSearchBox(String courseToBeSearched) {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver, headerSearchBox, 10);
		WebElementUtlities.setText(driver, driver.findElement(headerSearchBox), courseToBeSearched);
		driver.findElement(headerSearchBox).sendKeys(Keys.ENTER);
	}

	/**
	 * Get Site Header Text
	 * @return
	 */
	public String getSiteHeaderText() {
		return WebElementUtlities.getAttributeByValue(driver, driver.findElement(pluralsightIcon));
	}

	/**
	 * get Course Link Text
	 * @return
	 */
	public String getCourseLinkText() {
		return WebElementUtlities.getAttributeByValue(driver, driver.findElement(coursesLink));
	}

	/**
	 * get Search Placeholder Text
	 * @return
	 */
	public String getSearchPlaceholderText() {
		return WebElementUtlities.getAttributeUsingValue(driver, driver.findElement(headerSearchBox),"placeholder");
	}

	/**
	 * get Started Icon Text
	 * @return
	 */
	public String getStartedIconText() {
		return WebElementUtlities.getAttributeByValue(driver, driver.findElement(headerSearchBox));
	}

	/**
	 * Handle Alert On Finish
	 * @param driver
	 */
	public static void handleAlert(WebDriver driver) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
			System.out.println("Accepted pop up alert");
		}catch(NoAlertPresentException ex) {
			System.out.println("Alert is NOT Displayed");
		}
	}

	/**
	 * Handle Alert On Finish
	 * @param driver
	 */
	public static void handleAlertOnFinish(WebDriver driver) {
		try{
			Alert alert = driver.switchTo().alert();
			alert.accept();
			isAlertAccepted=true;
			System.out.println("Accepted pop up alert");
		}catch(NoAlertPresentException ex) {
			System.out.println("Alert is NOT Displayed");
		}
	}

	/**
	 * Cancel Alert On Finish
	 * @param driver
	 */
	public static void cancelAlertOnFinish(WebDriver driver) {
		try{
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
			System.out.println("Canceled pop up alert");
		}catch(NoAlertPresentException ex) {
			System.out.println("Alert is NOT Displayed");
		}
	}

	/**
	 * Wait For Alert Popup
	 * @param driver
	 * @return
	 */
	public static boolean waitForAlertPopup(WebDriver driver) {
		boolean flag=false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		if(wait.until(ExpectedConditions.alertIsPresent())==null)
			System.out.println("No alert");
		else {
			System.out.println("Alert present");
			flag=true;
		}
		return flag;
	}

	/**
	 * Get Alert Text
	 * @param driver
	 * @return
	 */
	public static String getAlertText(WebDriver driver) {
		String alertText = driver.switchTo().alert().getText();
		return alertText;
	}

	/**
	 * Wait Till The Loader is Invisible
	 * @param driver
	 * @param visibleLoaderXpath
	 * @throws Throwable
	 */
	public void waitTillTheLoaderInvisible(By visibleLoaderXpath, By invisibleLoaderXpath) throws Throwable {
		int timeOut = 50;
		Stopwatch stopwatch = Stopwatch.createStarted();
		int loaderCount;
		loaderCount = driver.findElements(visibleLoaderXpath).size();
		if (loaderCount == 1) {
			do {
				customWaitInSec(2);
				System.out.println("Loader Visible");
				loaderCount = driver.findElements(visibleLoaderXpath).size();
				if (stopwatch.elapsed().getSeconds() > timeOut)
					break;
			} while (loaderCount == 1);
			System.out.println("Loader Invisible");
			stopwatch.stop();
		}
		customWaitInSec(1);
		WebElementUtlities.explicitWaitForElementToBeInVisible(driver, invisibleLoaderXpath);
	}

	/**
	 * Custom Wait In Sec
	 * @param timeOut
	 * @throws InterruptedException 
	 */
	public void customWaitInSec(long timeOut) throws InterruptedException  {
		TimeUnit.SECONDS.sleep(timeOut);
	}


	/**
	 * Click On Button
	 * @param buttonName
	 * @param buttonTag
	 * @throws Throwable 
	 */
	public void clickOnButton(String buttonName,ButtonTag buttonTag) throws Throwable {
		WebElement buttonElement=null;
		if(buttonTag.equals(ButtonTag.input))
			buttonElement=driver.findElement(By.xpath("//input[@value='"+buttonName+"']"));
		else if (buttonTag.equals(ButtonTag.Button))
			buttonElement=driver.findElement(By.xpath("//button[normalize-space()='"+buttonName+"']"));

		WebElementUtlities.explicitWaitForAllElementToBeVisible(driver, buttonElement);
		WebElementUtlities.moveToElement(driver, buttonElement);

		WebElementUtlities.click(driver, buttonElement);
		System.out.println("Clicked On "+buttonName+" Button");
	}

	/**
	 * Button Tag
	 */
	public enum ButtonTag {
		input,Button;
	}

	/**
	 * Is Success Pop Up Box Msg Visible
	 * @param driver
	 * @param popUpBox - select inner or login page boxes
	 * @return
	 * @throws Throwable
	 */
	public boolean isSuccessPopUpBoxMsgVisible(WebDriver driver, By popUpBox) throws Throwable {
		boolean flag = false;
		flag = WebElementUtlities.isElementVisible(driver,popUpBox);
		return flag;
	}

	/**
	 * Handle Success Pop Up Box 
	 * @param handlingOperation - select required btn operation applicable on popup
	 */
	public void handleSuccessPopUpBox(By handlingOperation) {
		WebElementUtlities.explicitWaitForElementToBeVisible(driver,handlingOperation);
		WebElementUtlities.click(driver, driver.findElement(handlingOperation));
	}

	/**
	 * Refresh Page
	 * @throws Throwable
	 */
	public void refreshPage() {
		driver.navigate().refresh();
	}

	/**
	 * Switch To New Window
	 * @param btnToBeClick : Clicks a button after which new window is opened
	 * @return
	 * @throws Throwable
	 */
	public String switchToNewWindow(WebElement btnToBeClick) throws Throwable {
		WebElementUtlities.click(driver,btnToBeClick );
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		for(String newWindow : driver.getWindowHandles()) {
			if (!newWindow.equals(getParentWindow())) {
				driver.switchTo().window(newWindow);
				driver.manage().window().maximize();
				return newWindow;
			}
		}
		return null;
	}

	/**
	 * Get Parent Window
	 * @return
	 */
	public String getParentWindow() {
		originalHandle = driver.getWindowHandle();
		return originalHandle;
	}

	/**
	 * Get Current Window Handle
	 * @return
	 */
	public String getCurrentWindowHandle() {
		return newhandle = driver.getWindowHandle();
	}

	/**
	 * Switch To Required Window
	 * @param requiredWindow
	 */
	public void switchToRequiredWindow(String requiredWindow) {
		driver.switchTo().window(requiredWindow);
		System.out.println("Switched to required window");
	}

	/**
	 * Checks if New Window is Opened
	 * @param driver
	 * @param parentWindowHandle
	 * @return
	 */
	public boolean isNewWindowSelected(WebDriver driver, String parentWindowHandle) {
		try {
			return !driver.getWindowHandle().equals(parentWindowHandle);
		} catch (Exception ex) {
			driver.switchTo().window(parentWindowHandle);
			return false;
		}
	}

	/**
	 * Close Required Window
	 * @param parentWindowHandle : Parent window to confirm it not closes main window
	 * @param requiredWindow : Window to be closed 
	 */
	public void closeRequiredWindow(String parentWindowHandle, String requiredWindow) {
		if(requiredWindow.equals(newhandle)) {
			boolean newHandleIsAliveOrSelected = isNewWindowSelected(driver,parentWindowHandle);
			if(newHandleIsAliveOrSelected) {
				System.out.println("Closed required window");
				driver.close();
			}
		}
	}
	
	public void navigateToDifferentPage(String url) {
		driver.navigate().to(url);
	}

	/**
	 * Switch Window And Get Title
	 * @param btnToBeClick : Clicks a button after which new window is opened
	 * @return
	 */
	public String switchWindowAndGetTitle(WebElement btnToBeClick) {
		String originalHandle = driver.getWindowHandle();
		String tabTitle = null;
		WebElementUtlities.click(driver,btnToBeClick );

		for(String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				tabTitle = getTitle();
				driver.close();
			}
		}
		driver.switchTo().window(originalHandle);
		return tabTitle;
	}

	/**
	 * Get Title
	 * @return
	 */
	public String getTitle() {
		String title = driver.getTitle();
		return title;
	}

	/**
	 * Get Random Operation Element
	 * @param list
	 * @param totalOperation
	 * @return
	 */
	public List<String> getRandomOperationElements(List <String> list, int totalOperation) {
		Random random = new Random();
		List<String> newList = new ArrayList<>();
		for (int i = 0; i < totalOperation; i++) {
			int randomIndex = random.nextInt(list.size());
			newList.add(list.get(randomIndex));
		}
		return newList;
	}


}
