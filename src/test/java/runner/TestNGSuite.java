package runner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.ITestNGListener;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import excelUtilities.ExcelUtilities;
import exceptions.FileDoesNotExistsException;
import exceptions.InCorrectConfigConfigParameters;
import helperTestUtility.RetryListerner;
import reportUtilities.ReportingUtility;

/**
 * Test NG Suite
 * 
 * @author Niraj.Tiwari
 */
public class TestNGSuite {

	public static final Logger logger = LogManager.getLogger(TestNGSuite.class);
	
	public static void main(String[] args) throws Throwable {
		String  path = System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\hybridFrameworkTestDriver.xlsx";
		logger.info("Excel sheet Path : " +path);
		validateInputFile(path);
		System.setProperty("driverFilePath", path);
		String testExeType= getTestExecutionType().trim();
		System.setProperty("url",geturl());

		//Create TestNG Suite
		List<XmlSuite> suiteList = new ArrayList<XmlSuite>();
		List<XmlClass> classList = new ArrayList<XmlClass>();

		List<Class<? extends ITestNGListener>> listenerList = new ArrayList<Class<? extends ITestNGListener>>();

		XmlSuite suiteName = new XmlSuite();
		suiteName.setName("ChartSyncSuite");

		XmlTest testName = new XmlTest(suiteName);
		testName.setName("ChartSyncTest");

		//all test scripts
		classList.add(new XmlClass("testScripts.TestLoginPage"));
		classList.add(new XmlClass("testScripts.TestLoginPageNegativeScenarios"));
		classList.add(new XmlClass("testScripts.TestCoursePageFeatures"));
		classList.add(new XmlClass("testScripts.TestHomePageFeatures"));
		classList.add(new XmlClass("testScripts.TestSearchPageFeatures"));
		classList.add(new XmlClass("testScripts.TestJavaSearchFunctionality"));
		
		List<String> methods = new ArrayList<String>();
		TestNG TestNGRun = new TestNG();
		if(testExeType.equals("Custom") || testExeType.equals("Regression") || testExeType.equals("Smoke") || 
				testExeType.equals("Login")) {
			methods = getTestScriptToExecute(testExeType);
		}else {
			methods.add(testExeType);
		}

		testName.setIncludedGroups(methods);
		testName.setXmlClasses(classList) ;

		listenerList.add(ReportingUtility.class);
		listenerList.add(RetryListerner.class);
		suiteList.add(suiteName);
		TestNGRun.setXmlSuites(suiteList);

		TestNGRun.setListenerClasses(listenerList);
		logger.info("Running Test Suite for "+testExeType+" group");
		TestNGRun.run();
	}

	public static void validateInputFile(String path) throws FileDoesNotExistsException {
		File f = new File(path);
		if(!f.exists()) {
			throw new FileDoesNotExistsException("File dmdaTestDriver.xlsx not found under path: "+f.getParentFile());
		}else {
			if (f.length()==0) {
				throw new FileDoesNotExistsException("File dmdaTestDriver.xlsx found under path: "+f.getParentFile() + " is empty");
			}
		}
	}

	public static String geturl() throws Throwable {
		ExcelUtilities xlsUtil= new ExcelUtilities(System.getProperty("driverFilePath"));
		Sheet sheetObj = xlsUtil.getSheetObject("Config");
		ArrayList<ArrayList<String>> urlList = xlsUtil.getMultipleColumnDataBasedOnOneColumnValue(sheetObj,"Attribute","Env-URL","Value");
		if(urlList.size() == 0) {
			throw new InCorrectConfigConfigParameters("No url value found for url-uat");
		}
		if (urlList.size() > 1){
			throw new InCorrectConfigConfigParameters("Multiple values found for url-uat");
		}
		return urlList.get(0).get(0);

	}

	public static String getClientCode() throws Throwable{
		ExcelUtilities xlsUtil= new ExcelUtilities(System.getProperty("driverFilePath"));
		ArrayList<String> testTypeList=xlsUtil.getRowData("Driver",1);
		return testTypeList.get(1);
	}

	private static ArrayList<String> getTestScriptToExecute(String groupName) throws Throwable {
		ExcelUtilities xlsUtil= new ExcelUtilities(System.getProperty("driverFilePath"));
		Sheet sheetObj = xlsUtil.getSheetObject("TestScripts");
		ArrayList<ArrayList<String>> test2Execute= new ArrayList<ArrayList<String>>();
		if (groupName.equals("Custom")||groupName.equals("Smoke")||groupName.equals("Regression"))
			test2Execute = xlsUtil.getMultipleColumnDataBasedOnOneColumnValue(sheetObj,"Execute","Y","TestScriptName");
		else
			test2Execute = getTestScriptNamesBasedOnGroupsColumn(groupName,"TestScriptName");
		ArrayList<String> test2ExecuteList = new ArrayList<String>();
		for(ArrayList<String>obj:test2Execute) {
			for(String val:obj) {
				test2ExecuteList.add(val.trim());
			}
		}
		return test2ExecuteList;
	}

	private static String getTestExecutionType() throws Throwable{
		ExcelUtilities xlsUtil= new ExcelUtilities(System.getProperty("driverFilePath"));
		ArrayList<String> testTypeList=xlsUtil.getRowData("Driver",0);
		String executionType = testTypeList.get(1);
		if (!(executionType.equals("Custom") || executionType.equals("Regression") || executionType.equals("Smoke") || executionType.equals("Login"))){
			throw new IllegalArgumentException("Test Execution: "+executionType+" is invalid");
		}
		return executionType;
	}

	//Group wise run via TestNgSuite
	@SuppressWarnings("static-access")
	public static  ArrayList<ArrayList<String>> getTestScriptNamesBasedOnGroupsColumn(String groupName, String... extColumnName) throws Throwable{
		ExcelUtilities xlsUtil= new ExcelUtilities(System.getProperty("driverFilePath"));
		Sheet sheetObj = xlsUtil.getSheetObject("TestScripts");
		ArrayList<ArrayList<String>> columData = new ArrayList<ArrayList<String>>();
		String[][]srchCriteria={{"Groups",groupName}};
		ArrayList<HashMap<String, String>> rowAllData = xlsUtil.getAllRowsData(sheetObj,srchCriteria);
		for(HashMap<String, String> map:rowAllData) {
			ArrayList<String> temp = new ArrayList<String>();
			String value=map.get("Execute");
			if (value.equals("Y")) {
				for(String colname:extColumnName) {
					temp.add(map.get(colname));
				}
			}
			columData.add(temp);
		}
		ArrayList<String> stringCollection = new ArrayList<String>();
		for (int i = 0; i < columData.size(); ++i) {
			stringCollection.addAll(columData.get(i));
		}
		if (stringCollection.isEmpty()) {
			throw new IllegalArgumentException("No TestScript To Execute for group : "+groupName);
		}
		return columData;
	}

	/** This method is used to covert String value to boolean
	 * @param value
	 * @return
	 */
	public static boolean stringToBoolean(String value) {
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
