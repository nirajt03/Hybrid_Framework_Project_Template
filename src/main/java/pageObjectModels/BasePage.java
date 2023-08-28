package pageObjectModels;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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
	WebElementUtlities webElementUtil = new WebElementUtlities();
	
	public BasePage(WebDriver rdriver) {
		this.driver = rdriver;
	}
	
	public static String originalHandle;
	public static String newhandle; 
	public static boolean isAlertAccepted;

	protected By highlightedOption = By.xpath("//li[contains(@class,'highlighted')]");
	
	protected By logoutIcon = By.xpath("//div[@id='logout']");

    //protected By pulseLoaderInvisible = By.xpath("//img[@class='loader-gif']//parent::div[@class='loader-overlay' and @style='display: none;']");
    //protected By pulseLoaderVisible = By.xpath("//img[@class='loader-gif']//parent::div[@class='loader-overlay' and not (contains(@style,'display: none;'))]");
	//protected By otherPulseLoaderVisible = By.xpath("//img[@class='loader-gif']//parent::div[@class='loader-overlay' and @style='']");

	//protected By ribbonLoader = By.xpath("//div[contains(@class,'popup')]//div[contains(@id,'container') and @style='display: block;']");// --> visible then invisible
	//protected By ribbonLoaderText = By.xpath("//div[contains(@class,'popup')]//div[contains(@id,'container') and @style='display: block;']//strong");

	

	


	/**
	 * This method is used to logout to application
	 *
	 * @return
	 * @throws Throwable
	 */
	public LoginPage logoutFromPluralsightApplication() throws Throwable {

		WebElementUtlities.explicitWaitForElementToBeVisible(driver, logoutIcon);
		
		WebElementUtlities.click(driver, driver.findElement(logoutIcon));
		System.out.println("Logout button clicked");
		handleAlert(driver);
		

		System.out.println("Successfully Logged Out from Pluralsight application");
		return (new LoginPage(driver));
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
	 * This method is used to handle alert msg
	 * @return
	 */
	public boolean handleAlert() {
		boolean flag = false;
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		if(wait.until(ExpectedConditions.alertIsPresent())!=null)
		{
			driver.switchTo().alert().accept();
			flag = true;
			try {
				wait = new WebDriverWait(driver,Duration.ofSeconds(10));
				if(wait.until(ExpectedConditions.alertIsPresent())!=null){
					flag = true;
					driver.switchTo().alert().accept(); //Only comes when we want to delete TabooWord
				}
			}catch(Exception e){
				System.out.println("Next expected alert not found");
			}
			try {
				wait = new WebDriverWait(driver,Duration.ofSeconds(10));
				if(wait.until(ExpectedConditions.alertIsPresent())!=null){
					flag = true;
					driver.switchTo().alert().accept(); //Only comes when we want to delete TabooWord
				}
			}catch(Exception e){
				System.out.println("Other expected alert not found");
			}
		}
		return flag;
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
	 * @throws Throwable
	 */
	public void customWaitInSec(long timeOut) throws Throwable {
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
	 * Get Current Date
	 * @return
	 */
	public String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String currentDate = formatter.format(date);
		return currentDate;
	}
	
	/**
	 * Get date in required format
	 * @param date
	 * @return
	 * @throws Throwable
	 */
	public Date formattingDateddMMMyyyy(String date) throws Throwable {
		DateFormat shortFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat mediumFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String shortDate = mediumFormat.format(shortFormat.parse(date));
		Date formattedDate = new SimpleDateFormat("dd-MMM-yyyy").parse(shortDate);
		return formattedDate;
	}

	/**
	 * Formatted Date In Required Format
	 * @param date
	 * @param givenFormat
	 * @param requiredFormat
	 * @return
	 * @throws Throwable
	 */
	public String formattedDateInRequiredFormat(String date,DateFormatType givenFormat,DateFormatType requiredFormat) throws Throwable {
		DateTimeFormatter givenFormatters = DateTimeFormatter.ofPattern(givenFormat.getDateFormat());
		DateTimeFormatter requiredFormatters = DateTimeFormatter.ofPattern(requiredFormat.getDateFormat());
		LocalDate localDate=LocalDate.parse(date, givenFormatters);
		String requiredDate=localDate.format(requiredFormatters);
		System.out.println("The Given Date Format :" +date+" And Expected Date Format is : "+requiredDate);
		return requiredDate;
	}

	/**
	 * Date Format Type
	 */
	public enum DateFormatType {
		ddMMyyyySlash("dd/MM/yyyy"),ddMMyyyyHyphen("dd-MM-yyyy"),ddMMMyyyySlash("dd/MMM/yyyy"),MMddyyyyHyphen("MM-dd-yyyy"),MMddyyyySlash("MM/dd/yyyy"),yyyyMMddHyphen("yyyy-MM-dd"),MMddSlash("MM/dd"), MMMddSpace("MMM dd");

		private String dateFormat; 

		DateFormatType(String dateFormat) {
			this.dateFormat=dateFormat;
		}

		public String getDateFormat() {
			return dateFormat;
		}
	}

	/**
	 * Get Year, Month and Day from String date
	 * @param date
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("rawtypes")
	public HashMap getYearMonthAndDayFromDate(String date) throws Throwable {
		HashMap<String, String> yearMonthAndDay = new HashMap<String, String>();
		Date formattedDate = formattingDateddMMMyyyy(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(formattedDate);
		boolean isSunday = (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
		if (isSunday)
			throw new DateTimeException("The Given Date is Sunday");
		boolean isSaturday = (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY);
		if (isSaturday)
			throw new DateTimeException("The Given Date is Saturday");
		int yearInt = calendar.get(Calendar.YEAR);
		String year = String.valueOf(yearInt);
		int monthint = calendar.get(Calendar.MONTH);
		String month = new DateFormatSymbols().getShortMonths()[monthint];
		int dayInt = calendar.get(Calendar.DAY_OF_MONTH);
		String day = String.valueOf(dayInt);
		yearMonthAndDay.put("Year", year);
		yearMonthAndDay.put("Month", month);
		yearMonthAndDay.put("Day", day);
		return yearMonthAndDay;
	}

	/**
	 * Get Current Time
	 * @return
	 */
	public String getCurrentTime(TimeFormatType formatTime) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(formatTime.getTimeFormat());
		return sdf.format(cal.getTime());
	}

	/**
	 * Time Format Type
	 */
	public enum TimeFormatType {

		hhmmssaColon("hh:mm:ss a"),hhmmaColon("hh:mm a"),hhmmssaColonNoSpace("hh:mm:ssa"),hhmmaColonNoSpace("hh:mma"),HHmmssColon("HH:mm:ss"),HHmmColon("HH:mm");

		private String timeFormat; 

		TimeFormatType(String dateFormat) {
			this.timeFormat=dateFormat;
		}

		public String getTimeFormat() {
			return timeFormat;
		}
	}

	/**
	 * Formatted Time In Required Format
	 * @param time
	 * @param givenFormat
	 * @param requiredFormat
	 * @return
	 * @throws Throwable
	 */
	public String formattedTimeInRequiredFormat(String time,TimeFormatType givenFormat,TimeFormatType requiredFormat) throws Throwable {
		DateTimeFormatter givenFormatters = DateTimeFormatter.ofPattern(givenFormat.getTimeFormat());
		DateTimeFormatter requiredFormatters = DateTimeFormatter.ofPattern(requiredFormat.getTimeFormat());
		LocalTime localTime=LocalTime.parse(time, givenFormatters);
		String requiredTime=localTime.format(requiredFormatters);
		System.out.println("The Given Date Format :" +time+" And Expected Date Format is : "+requiredTime);
		return requiredTime;
	}

	/**
	 * Get Current Date In DD Format
	 * @param date
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public String getCurrentDateInDDFormat(String date) throws Throwable {
		HashMap<String, String> yearMonthAndDay = getYearMonthAndDayFromDate(date);
		return yearMonthAndDay.get("Day");
	}

	/**
	 * Get all grid column in List<String>
	 *
	 * @param headMenuXpath
	 * @return List<String>
	 */
	@SuppressWarnings("unused")
	public List<String> getGridColumnsInList(String headMenuXpath) {
		int index = -1;
		// String headMenuXpath="//*[@id='tablehead']//div";
		List<WebElement> elements = driver.findElements(By.xpath(headMenuXpath));
		List<String> columnNameList = new ArrayList<String>();

		elements.stream().forEach(a -> {
			//webElementUtil.scrollTillElement(driver, a);
			WebElementUtlities.moveToElement(driver, a);
			columnNameList.add(a.getText().trim().toString());
		});
		return columnNameList;
	}

	/**
	 * Get index of column in Grid
	 *
	 * @param allHeadMenu
	 * @param HeadMenu
	 * @return
	 */
	public int getHeadMenuIndex(List<String> allHeadMenu, String HeadMenu) {
		int index = -1;
		if (allHeadMenu.contains(HeadMenu)) {
			index = allHeadMenu.indexOf(HeadMenu);
		}
		return index+1;
	}

	/**
	 * Refresh Page
	 * @throws Throwable
	 */
	public void refreshPage() throws Throwable {
		driver.navigate().refresh();
	}

	/**
	 * Chart Note Status Types
	 * @author Niraj.Tiwari
	 */
	public enum ChartNoteStatus{
		UnOpened("","grey-notWorkedOn"),InProgress("In-Progress","Green-Inprogress"),Submitted("Submitted","blue-submitted");

		private String statusType; 
		private String srcImageType;

		ChartNoteStatus(String statusType,String srcImageType) {
			this.statusType = statusType;
			this.srcImageType = srcImageType;
		}

		public String getChartNoteStatusType() {
			return statusType;
		}

		public String getChartNoteSrcImageType() {
			return srcImageType;
		}
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
	 * Chart Note Details Parameters
	 * @author Niraj.Tiwari
	 */
	public enum ChartNoteDetailsParameters{
		ApptTime("time","appt-time"),ApptDate("","appt-date"),Physician("phy","physician-name"),EncounterId("data-apt-enc","encounter");

		private String schedulePageDetails; 
		private String chartNotePageDetails;

		ChartNoteDetailsParameters(String schedulePageDetails,String chartNotePageDetails) {
			this.schedulePageDetails = schedulePageDetails;
			this.chartNotePageDetails = chartNotePageDetails;
		}

		public String getEncounterDetailsOnSchedulePage() {
			return schedulePageDetails;
		}

		public String getEncounterDetailsOnChartNoteDetailsPage() {
			return chartNotePageDetails;
		}
	}


	/**
	 * Chart Note Details Parameters
	 * @author Niraj.Tiwari
	 */
	public enum PageObjectsModelsPages{
		BasePage("Base Page"),ChartNotesDetailsPage("Chart Notes Details Page"),LoginPage("Login Page"),PhysicianMappingPage("Physician Mapping Page"),ProfilePage("Profile Page"),SchedulePage("Schedule Page");

		private String pageObjectNames; 

		PageObjectsModelsPages(String pageObjectNames) {
			this.pageObjectNames = pageObjectNames;
		}

		public String getPageObjectsModelsPage() {
			return pageObjectNames;
		}
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
	
	/**
	 * Chart Note Review Status
	 * @author Niraj.Tiwari
	 */
	public enum ChartNoteReviewStatus{
		SendForReview("send-for-review","SendForReview"),Undo("UndoTimer","Undo"),ReadyForReview("ready-for-review","ReadyForReview");

		private String reviewStatusImageType;
		private String reviewStatusClassType;
	
		ChartNoteReviewStatus(String reviewStatusImageType,String reviewStatusClassType) {
			this.reviewStatusImageType = reviewStatusImageType;
			this.reviewStatusClassType = reviewStatusClassType;
		}

		public String getReviewStatusImageType() {
			return reviewStatusImageType;
		}
		
		public String getReviewStatusClassType() {
			return reviewStatusClassType;
		}
	}


}
