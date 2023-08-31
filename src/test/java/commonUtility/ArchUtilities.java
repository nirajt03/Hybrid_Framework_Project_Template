package commonUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.WebDriver;

import excelUtilities.ExcelUtilities;
import exceptions.NoRowFoundException;
import exceptions.ObjectLengthNotCorrectException;
import pageObjectModels.LoginPage;
import pageObjectModels.SearchPage;
import webElementUtilities.WebElementUtlities;

/**
 * Arch Utilities
 * 
 *@author Niraj.Tiwari
 */
public class ArchUtilities  {


	WebElementUtlities webelementUtil = new WebElementUtlities();
	protected WebDriver driver;
	public ArchUtilities(WebDriver rdriver) {
		this.driver = rdriver;

	}
	public ArchUtilities() {

	}

	/**
	 * Get User Credential
	 * @param userType
	 * @return
	 * @throws Throwable
	 */
	public HashMap<String, String> getUserCredential(String userType,String loginType) throws Throwable  {

		String path = System.getProperty("driverFilePath");
		ExcelUtilities excelUtil = new ExcelUtilities(path);
		HashMap<String, String> map = new HashMap<String, String>();

		// Get Sheet Object
		Sheet sheetObject = excelUtil.getSheetObject("LoginTestData");

		String[][] searchData={{"UserType",userType}, {"LoginType",loginType}};
		ArrayList<HashMap<String, String>> rowData = ExcelUtilities.getAllRowsData(sheetObject,searchData);

		// Get the Username
		String userNameValue = rowData.get(0).get("Username");

		// Get the Password
		String passwordValue = rowData.get(0).get("Password");

		map.put("Username", userNameValue);
		map.put("Password", passwordValue);
		return map;
	}

	/**
	 * Login To Pluralsight application
	 * @param <T>
	 * @param loginPage
	 * @param userType
	 * @return
	 * @throws Throwable
	 */
	public SearchPage loginToPluralsightApplication(LoginPage loginPage,String userType,String loginType) throws Throwable  {
		// Get User Credential
		HashMap<String, String> usercredential = getUserCredential(userType,loginType);

		//Login pluralsight application
		SearchPage searchpage = loginPage.pluralsightApplicationLogin( usercredential.get("Username"), usercredential.get("Password"));
		System.out.println("Successfully Login To Pluralsight Application Using"+ usercredential.get("Username")+ "::"+usercredential.get("Password")+"Credential");
		return searchpage;
	}

	/**
	 * Check Negative Login Scenarios
	 * @param loginPage
	 * @param userType
	 * @param loginType
	 * @return
	 * @throws Throwable
	 */
	public String checkNegativeLoginScenarios(LoginPage loginPage, String username, String password) throws Throwable {
		
		//Login pluralsight application
		return loginPage.pluralsightApplicationNegativeLoginScenarios(username, password);
	}

	/**
	 * Get Test Data
	 * @param TestScriptName
	 * @return
	 * @throws Throwable
	 */
	public ArrayList<HashMap<String, String>> getTestData(String TestScriptName) throws Throwable {

		ExcelUtilities excelUtilities = new ExcelUtilities();

		// Get Workbook
		Workbook book = excelUtilities.getWorkbook(System.getProperty("driverFilePath"));

		Sheet testScriptSheetObj = excelUtilities.getSheetObject(book,"TestScripts");

		ArrayList<ArrayList<String>> testDataSheetNameList = excelUtilities.getMultipleColumnDataBasedOnOneColumnValue(testScriptSheetObj,"TestScriptName",TestScriptName,"TestDataSheetName");
		String testDataSheetName=(testDataSheetNameList.get(0)).get(0);

		Sheet testDataSheetObj  = excelUtilities.getSheetObject(book, testDataSheetName);

		ArrayList<HashMap<String, String>> allTestData=new ArrayList<HashMap<String, String>>();

		String[][] srchData= {{"TestCaseID","Not To Run"}};
		allTestData = getRowDataBasedOnTestcaseID(testDataSheetObj,srchData);
		return allTestData;
	}

	/**
	 * Get Row Data Based On Test Case ID
	 * @param sheetObject
	 * @param srchCriteriaArray
	 * @return
	 * @throws Throwable
	 */
	public ArrayList<HashMap<String, String>> getRowDataBasedOnTestcaseID(Sheet sheetObject,String[][] srchCriteriaArray) throws Throwable
	{    
		for(String [] arr:srchCriteriaArray) {
			if(arr.length !=2) {
				throw new ObjectLengthNotCorrectException("Sreach Object inner Array Should have 2 elements, "
						+ "while "+arr.length+" was given");
			}
		}

		DataFormatter formatter = new DataFormatter();

		ArrayList<HashMap<String,String>> allRowData = new ArrayList<HashMap<String,String>>();    

		List<String> allColmnNames = ExcelUtilities.getAllColumnNames(sheetObject);        
		int rowCount=ExcelUtilities.getRowCount(sheetObject);
		boolean oneRowFound=false;

		for(int i=1;i<rowCount;i++) {
			boolean srchFlag=true;
			for(String [] arr:srchCriteriaArray) {
				int column2CheckIndx=ExcelUtilities.getColumnIndexFrmColumnName(sheetObject,arr[0]);
				Row rowObj=sheetObject.getRow(i);
				Cell cellObj=rowObj.getCell(column2CheckIndx);
				String cellValue=formatter.formatCellValue(cellObj);
				srchFlag = srchFlag && (!(cellValue.trim().toUpperCase()).equals(arr[1].trim().toUpperCase()));            
			}
			if (srchFlag){
				oneRowFound=true;
				HashMap<String,String> rowData=new HashMap<String,String>();
				for(String colName:allColmnNames) {
					int columnIndex = ExcelUtilities.getColumnIndexFrmColumnName(sheetObject, colName);
					rowData.put(colName.trim(),formatter.formatCellValue(sheetObject.getRow(i).getCell(columnIndex)).trim());
				}
				allRowData.add(rowData);
			}
		}
		if(!oneRowFound) {    
			String message = "";
			for(String [] arr:srchCriteriaArray) {
				message = message + arr[0]+"="+arr[1]+" ";
			}
			throw new NoRowFoundException("No Rows found in sheet="+sheetObject.getSheetName()+" for search criteria "+ message);
		}
		return allRowData; 
	}

	/**
	 * Get Current Date in MMDDYYYY
	 * @return
	 */
	public static String getCurrentDateinMMDDYYYY() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String currentDate = formatter.format(date);
		return currentDate;
	}
}