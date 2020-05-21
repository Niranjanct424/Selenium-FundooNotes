package com.bridgelab.fundoo.testcases;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.bridgelab.fundoo.util.ExcelReadUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UserModuleTest {

	public WebDriver driver;
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;


	 @BeforeMethod
	 public void setup() {
//		 System.setProperty("webdriver.chrome.driver", "C:\\Users\\dell\\Desktop\\chromedriver_win32\\chromedriver.exe");
		 WebDriverManager.chromedriver().setup();
		 driver = new ChromeDriver();
		 driver.manage().window().maximize();
	 }
	 
	
	
	@BeforeTest
	public void beforeTest() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/usermodulereport.html");

		htmlReporter.config().setDocumentTitle("Automation Report"); // Tile of report
		htmlReporter.config().setReportName("User Module Functional Testing"); // Name of the report
		htmlReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		// Passing General information
		extent.setSystemInfo("Host-name ", "localhost");
		extent.setSystemInfo("OS ", "Windows 10");
		extent.setSystemInfo("Tester-Name ", "Niranjan");
		extent.setSystemInfo("Browser ", "Crome");
		extent.setSystemInfo("Application-Name ", "FundooNoteApplication");

	}

	@AfterTest
	public void endReport() {
		extent.flush();
	}

	// Create the Data Provider and give the data provider a name
	@DataProvider(name = "user-ids-passwords-excel-data-provider")
	public String[][] userIdsAndPasswordsDataProvider() {
		return ExcelReadUtil.readExcelInto2DArray("./src/test/resources/login-data.xlsx", "Sheet1", 3);
	}

	// Use the data provider
	@Test(dataProvider = "user-ids-passwords-excel-data-provider")
	public void loginTest(String userId, String password, String isLoginExpectedToBeSuccessfulString)
			throws InterruptedException {

		Boolean.valueOf(isLoginExpectedToBeSuccessfulString);
		test = extent.createTest("LoginTest");
		driver.get("http://localhost:4200/login");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();

		Thread.sleep(25000);
		String expected = driver.getCurrentUrl();
		String actual = "http://localhost:4200/dashboard";
		assertEquals(actual, expected);
		Thread.sleep(2000);

		driver.close();

	}

	@DataProvider(name = "user-register-info-excel-data-provider")
	public String[][] registerDataProvider() {
		return new String[][] { { "Niranjan", "niranjangyadav124@gmail.com", "niranjan@55", "8880846463" },
				{ "yadav", "niruct6@gmail.com", "niranjan@55", "8880846463" }, };
	}

	@Test(dataProvider = "user-register-info-excel-data-provider")
	public void register(String userName, String password, String email, String phoneNumber)
			throws InterruptedException {

		test = extent.createTest("RegisterUserTest");
		driver.get("http://localhost:4200/registration");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='runame']")).sendKeys(userName);
		driver.findElement(By.xpath("//input[@id='ruemail']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@id='rupassword']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@id='ruphoneNumber']")).sendKeys(phoneNumber);

		driver.findElement(By.xpath("//button[@id='registersubmit']")).click();
		System.out.println("User Already Exist to Register Try with different mail.....");
		Thread.sleep(20000);
		driver.close();

	}

	@DataProvider(name = "user-forgotMail-data-provider")
	public String[][] forgotMailDataProvider() {
		return ExcelReadUtil.readExcelInto2DArray("./src/test/resources/forgotmail-data.xlsx", "Sheet1", 2);
	}

	@Test(dataProvider = "user-forgotMail-data-provider")
	public void forgotPassword(String forgotMail, String ismailSent) throws InterruptedException {

		test = extent.createTest("ForgotPasswordTest");
		driver.get("http://localhost:4200/forgotPassword");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		Boolean.valueOf(ismailSent);
		driver.findElement(By.xpath("//input[@id='forgotpasswordmail']")).sendKeys(forgotMail);
		driver.findElement(By.xpath("//button[@id='fpsubmit']")).click();
		System.out.println("Email Sent for Verification.....");

		Thread.sleep(20000);
		driver.close();

	}

	@DataProvider(name = "user-updatePassword-data-provider")
	public String[][] updatePasswordDataProvider() {
		return ExcelReadUtil.readExcelInto2DArray("./src/test/resources/updatepassword-data.xlsx", "Sheet1", 3);
	}

	@Test(dataProvider = "user-updatePassword-data-provider")
	public void updatePassword(String password, String conformPassword, String isPassUpdated)
			throws InterruptedException {

		test = extent.createTest("UpdatePasswordTest");
		driver.get(
				"http://localhost:4200/resetPassword/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6Mn0.sA4BYWa8edrLSwxJj21NIPWwSt4ggfbA-3PSohMpwVM");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		Boolean.valueOf(isPassUpdated);
		driver.findElement(By.xpath("//input[@id='updatePass']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@id='updateConformPass']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@id='updatePasswordSubmit']")).click();
		System.out.println("Password Updated Successfully.....");

		
		Thread.sleep(30000);
		String expected = driver.getCurrentUrl();
		String actual = "http://localhost:4200/login";
		assertEquals(actual, expected);
		Thread.sleep(5000);
		
		driver.close();

	}
	
	 @AfterMethod
	 public void tearDown(ITestResult result) throws IOException {
	  if (result.getStatus() == ITestResult.FAILURE) {
	   test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
	   test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in extent report
	   String screenshotPath = UserModuleTest.getScreenshot(driver, result.getName());
	   test.addScreenCaptureFromPath(screenshotPath);// adding screen shot
	  } else if (result.getStatus() == ITestResult.SKIP) {
	   test.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
	  }
	  else if (result.getStatus() == ITestResult.SUCCESS) {
	   test.log(Status.PASS, "Test Case PASSED IS " + result.getName());
	  }
	  driver.quit();
	 }
	 
	 
	 
	 public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		  String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		  TakesScreenshot ts = (TakesScreenshot) driver;
		  File source = ts.getScreenshotAs(OutputType.FILE);
		  
		  // after execution, you could see a folder "FailedTestsScreenshots" under src folder
		  String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
		  File finalDestination = new File(destination);
		  FileUtils.copyFile(source, finalDestination);
		  return destination;
		 }
	 

	/**
	 * Test to display user credentials fetching from xlsx sheet deepToString() to
	 * display details which array contains all user data like username and password
	 */
	@Test
	public void readFromExcel() {

		String[][] data = ExcelReadUtil.readExcelInto2DArray("./src/test/resources/login-data.xlsx", "Sheet1", 3);
		System.out.println(Arrays.deepToString(data));
		String[][] data1 = ExcelReadUtil.readExcelInto2DArray("./src/test/resources/register-data.xlsx", "Sheet1", 5);
		System.out.println(Arrays.deepToString(data1));
		String[][] data2 = ExcelReadUtil.readExcelInto2DArray("./src/test/resources/forgotmail-data.xlsx", "Sheet1", 2);
		System.out.println(Arrays.deepToString(data2));
		String[][] data3 = ExcelReadUtil.readExcelInto2DArray("./src/test/resources/updatepassword-data.xlsx", "Sheet1",
				3);
		System.out.println(Arrays.deepToString(data3));

	}
}
