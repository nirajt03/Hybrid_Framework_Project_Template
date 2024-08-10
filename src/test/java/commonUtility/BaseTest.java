package commonUtility;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import annotations.FrameworkAnnotation;
import excelUtilities.ExcelUtility;
import exceptions.FileDoesNotExistsException;
import exceptions.InCorrectConfigConfigParameters;
import helperTestUtility.BrowserFactory;
import helperTestUtility.DriverFactory;
import helperTestUtility.ReportLogs;
import helperTestUtility.RetryListerner;
import pageObjectModels.LoginPage;
import reportUtilities.ReportingUtility;
import runner.TestNGSuite;
import screenRecorderUtilities.ScreenRecorderUtility;
import screenRecorderUtilities.ScreenRecorderUtility.TypeOfScreen;

/**
 * Base Test
 * 
 * @author Niraj.Tiwari
 */
@Listeners({RetryListerner.class, ReportingUtility.class})
//@Listeners(ReportingUtility.class)
public class BaseTest {

	public static final Logger logger = LogManager.getLogger(BaseTest.class);

	protected BrowserFactory bf = new BrowserFactory();
    static Set<String> manualTCIDsSet = new TreeSet<String>();
	WebDriver driver;
	ChromeOptions options;

	String path= System.getProperty("testScriptName");
	public ArchUtilities archUtil= new ArchUtilities();
	public ExcelUtility excelUtil = new ExcelUtility();

	String testScriptName;
	String className;
	private int passedTests=0;
	private int failedTests=0;
	private int skipedTests=0;

	public BaseTest() {

	}

	/**
	 * Get Class Name
	 * @return
	 */
	public String getClassName() {
		String packageName = this.getClass().getPackage().getName().trim();
		logger.info("Qualified Test Package name : " + packageName);
		return packageName;
	}

	@BeforeSuite(alwaysRun=true)
	public void beforeSuite()  {
		int thresholdDays = 10;
		String testClassName = getClassName();
		try {
			ScreenRecorderUtility.startRecord(TypeOfScreen.RegularScreen,testClassName);
			ScreenRecorderUtility.deleteOlderFilesAndDirectories(thresholdDays, TimeUnit.DAYS,".avi");			
			logger.info("Screen Recording Started ..!!");
		} catch (Exception e) {
			e.printStackTrace();	
		}
		//from test ng suite
		//TestNg class's part - must set below properties
		String  path = System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\hybridFrameworkTestDriver.xlsx";
		try {
			TestNGSuite.validateInputFile(path);
		} catch (FileDoesNotExistsException e) {
			e.printStackTrace();
		}
		System.setProperty("driverFilePath", path);
		try {
			System.setProperty("url",TestNGSuite.geturl());
		} catch (InCorrectConfigConfigParameters e) {
			e.printStackTrace();
		}
		System.setProperty("AppName Details","Pluralsight");
		System.setProperty("Environment Details","QA");
		String applicationTitle = "Pluralsight";
		System.setProperty("Application Title", applicationTitle +" Automation");
		System.setProperty("Report Name",applicationTitle+" Automation Test Report");
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass(ITestContext testcontext) {
		DriverFactory.getInstance().setDriver(bf.createBrowserInstance("Chrome"));
		driver = DriverFactory.getInstance().getDriver();
		testcontext.setAttribute("driver", driver);
		logger.info("Chrome Browser Initiated successfully");
	}

	@BeforeMethod(alwaysRun=true)
	public void beforeMethod(Object[] data) {
		List<Object> myModel = Arrays.asList(data);
		int lastIndex = myModel.size()-1;
		Object object = myModel.get(lastIndex);
		if (object.toString().contains("TCs") ) {
			String[] objectArr = object.toString().split(",");
			for (String obj : objectArr) {
				manualTCIDsSet.add(obj.trim());
			}
		}
		logger.info("Manual TC IDs for current test : "+manualTCIDsSet);
	}

	@AfterMethod(alwaysRun=true)
	public void afterMethod(ITestResult result) {

		ReportLogs.addAuthors(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrameworkAnnotation.class).author());
		ReportLogs.addCategories(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(FrameworkAnnotation.class).category());

		//testScriptName = result.getMethod().getMethodName();
		testScriptName = result.getMethod().getRealClass().getSimpleName();
		System.setProperty("testScriptName", testScriptName);
		try {
			if (result.getStatus() == ITestResult.SUCCESS) {
				++passedTests;
			} else if (result.getStatus() == ITestResult.FAILURE) {
				++failedTests;
			} else if (result.getStatus() == ITestResult.SKIP) {
				++skipedTests;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		ReportLogs.unloadExtent();

		int manualTCIDs = manualTCIDsSet.size();
		System.setProperty("Manual TC IDs Count",String.valueOf(manualTCIDs));
		logger.info("Manual TC IDs Count for current test : "+manualTCIDs);
		try {
			int totalTestCases = ExcelUtility.getTotalTestCases(passedTests,failedTests,skipedTests);
			double passPercentage = ExcelUtility.calculatePercentage(passedTests,totalTestCases);
			logger.info("Pass Percentage : "+passPercentage+" %");
			String sheetName = "TestScripts";
			String filePath = System.getProperty("driverFilePath");
			String testScriptName= System.getProperty("testScriptName");
			excelUtil.setPassPercentage(passPercentage, filePath,testScriptName,sheetName);
		} catch (Exception e) {
			System.out.println(e.getCause());
		}finally {
			//driver.close();//driver.quit();
			DriverFactory.getInstance().closeDriver();
			logger.info("Chrome Browser Closed");
		}
	}

	@AfterSuite(alwaysRun=true)
	public void afterSuite() {
		try {
			ScreenRecorderUtility.stopRecord();
			logger.info("Screen Recording Stopped ..!!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	/**
	 * Open Application : URL
	 * @param url
	 * @return
	 */
	protected LoginPage openApplication(String url) {
		this.driver.get(url);
		logger.info("Login Page Displayed");
		return (new LoginPage(driver));
	}

	/** 
	 * This method is used to covert String value to boolean
	 * @param value
	 * @return
	 */
	public boolean stringToBoolean(String value) {
		boolean flag = false;
		value = value.toUpperCase();
		if (value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("YES")) {
			flag = true;
		} else if (value.equals("N") || value.equalsIgnoreCase("NO")) {
			flag = false;
		}
		return flag;
	}	

}
