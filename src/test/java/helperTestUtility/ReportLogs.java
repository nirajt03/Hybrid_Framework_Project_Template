package helperTestUtility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import enums.CategoryType;
import reportUtilities.ReportingUtility;



/**
 * Report Logs : Adds custom logs to extent reports and prints it in console and application log file. 
 * @author Niraj.Tiwari
 *  Apr 22, 2024 : 11:56:17 AM
 */
public class ReportLogs extends ReportingUtility{
	
	public static final Logger logger = LogManager.getLogger(ReportLogs.class);
	
	private static ThreadLocal<ExtentTest> extentNode = new ThreadLocal<>();
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	public ReportLogs() {
		
	}
		
	/**
	 * Get Extent Node
	 * @return ExtentTest
	 */
	private static ExtentTest getExtentNode() {
		extentNode = ReportingUtility.dataProviderTest;
		return extentNode.get();
	}
	
	/**
	 * get Extent Test 
	 * @return ExtentTest
	 */
	private static ExtentTest getExtentTest() {
		extentTest = ReportingUtility.methodTest;
		return extentTest.get();
	}		
	
	/**
	 * Unload Extent: Unloads extent objects from ThreadLocal pool
	 */
	public static void unloadExtent() {
		extentNode.remove();
		extentTest.remove();
	}
	
	/**
	 * Add Authors : adds authors to extent test
	 * @param authors
	 */
	public static void addAuthors(String[] authors) {
		for(String temp:authors) {
			getExtentTest().assignAuthor(temp);
		}
	}
	
	/**
	 * Add Categories : adds categories to extent test
	 * @param categories
	 */
	public static void addCategories(CategoryType[] categories) {
		for(CategoryType temp:categories) {
			getExtentTest().assignCategory(temp.getCategoryType());
		}
	}
		
	/**
	 * Add Log
	 * @param status
	 * @param message
	 */
	public static void addLog(Status status,String message) {
		if (status.equals(Status.PASS)) {
			getExtentNode().log(status, message);
		}if (status.equals(Status.FAIL)) {
			getExtentNode().log(status, message);
		}if (status.equals(Status.INFO)) {
			getExtentNode().log(status, message);
		}if (status.equals(Status.WARNING)) {
			getExtentNode().log(status, message);
		}if (status.equals(Status.SKIP)) {
			getExtentNode().log(status, message);
		}
		logger.info("Log Message: "+status +" - "+message);
	}
	
	/**
	 * Add Log With MarkUp (Background)
	 * @param status
	 * @param message
	 */
	public static void addLogWithMarkUp(Status status,String message) {
		if (status.equals(Status.PASS)) {
			getExtentNode().log(status, MarkupHelper.createLabel(message, ExtentColor.GREEN));
		}if (status.equals(Status.FAIL)) {
			getExtentNode().log(status, MarkupHelper.createLabel(message, ExtentColor.RED));
		}if (status.equals(Status.INFO)) {
			getExtentNode().log(status, MarkupHelper.createLabel(message, ExtentColor.BLUE));
		}if (status.equals(Status.WARNING)) {
			getExtentNode().log(status, MarkupHelper.createLabel(message, ExtentColor.YELLOW));
		}if (status.equals(Status.SKIP)) {
			getExtentNode().log(status, MarkupHelper.createLabel(message, ExtentColor.ORANGE));
		}
		logger.info("Log Message: "+status +" - "+message);
	}
	
	/**
	 * Add Log With Error
	 * @param status
	 * @param throwable
	 */
	public static void addLogWithError(Status status,Throwable throwable) {
		if (status.equals(Status.PASS)) {
			getExtentNode().log(status, throwable);
		}if (status.equals(Status.FAIL)) {
			getExtentNode().log(status, throwable);
		}if (status.equals(Status.INFO)) {
			getExtentNode().log(status, throwable);
		}if (status.equals(Status.WARNING)) {
			getExtentNode().log(status, throwable);
		}if (status.equals(Status.SKIP)) {
			getExtentNode().log(status, throwable);
		}
		logger.info("Log Message: "+status +" : ");
		System.out.print("Log Message: "+status +" : ");
		throwable.printStackTrace();
	}

	/**
	 * Add Log With Screenshot
	 * @param status
	 * @param message
	 */
	public static void addLogWithScreenshot(Status status,String message) {
		if (status.equals(Status.PASS)) {
			getExtentNode().log(status, message, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build());
		}if (status.equals(Status.FAIL)) {
			getExtentNode().log(status, message, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build());
		}if (status.equals(Status.INFO)) {
			getExtentNode().log(status, message, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build());
		}if (status.equals(Status.WARNING)) {
			getExtentNode().log(status, message, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build());
		}if (status.equals(Status.SKIP)) {
			getExtentNode().log(status, message, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build());
		}
		logger.info("Log Message: "+status +" - "+message);
	}
	
	/**
	 * Add Log With Error And Screenshot
	 * @param status
	 * @param throwable
	 */
	public static void addLogWithErrorAndScreenshot(Status status,Throwable throwable) {
		if (status.equals(Status.PASS)) {
			getExtentNode().log(status,throwable, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build());
		}if (status.equals(Status.FAIL)) {
			getExtentNode().log(status,throwable, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build());
		}if (status.equals(Status.INFO)) {
			getExtentNode().log(status,throwable, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build());
		}if (status.equals(Status.WARNING)) {
			getExtentNode().log(status,throwable, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build());
		}if (status.equals(Status.SKIP)) {
			getExtentNode().log(status,throwable, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build());
		}
		logger.info("Log Message: "+status +" : ");
		System.out.print("Log Message: "+status +" : ");
		throwable.printStackTrace();
	}
		
	/**
	 * Add Log For String Comparision
	 * @param actual
	 * @param expected
	 * @param message
	 */
	public static void addLogForStringComparision(String actual, String expected, String message) {
		if (actual.contains(expected)) {
			addLogWithMarkUp(Status.PASS,message + ": actual - " + "<b><i>" + actual + "</i></b>" + " & expected - " + "<b><i>" + expected + "</i></b>");
		} else {
			addLogWithMarkUp(Status.FAIL,message + ": actual - " + "<b><i>" + actual + "</i></b>" + " & expected - " + "<b><i>" + expected + "</i></b>");
		}
	}
	
	/**
	 * Get Base64 Image : screenshot it the form of base64 string
	 * @return String
	 * @throws WebDriverException
	 */
	private static String getBase64Image() throws WebDriverException {
		WebDriver driver = DriverFactory.getInstance().getDriver();
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
	}

}
