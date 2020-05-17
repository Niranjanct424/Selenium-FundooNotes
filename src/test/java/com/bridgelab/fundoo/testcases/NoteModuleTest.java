package com.bridgelab.fundoo.testcases;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.text.Document;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Sleeper;
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

public class NoteModuleTest {
	/**
	 * close() command closes the browser window which is currently active quit()
	 * command closes all the browser windows and terminate a WebDriver session
	 * 
	 */

	public WebDriver driver;
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;

	@BeforeMethod
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "D:\\selenium\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@BeforeTest
	public void beforeTest() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/notemodulereport.html");

		htmlReporter.config().setDocumentTitle("Fundoo-Note-Automation Report"); // Tile of report
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
		return ExcelReadUtil.readExcelInto2DArray("./src/test/resources/NoteUser.xlsx", "Sheet1", 2);
	}

	// Use the data provider
	@Test(dataProvider = "user-ids-passwords-excel-data-provider")
	public void createAndSearchNoteTest(String userId, String password)
			throws InterruptedException {

		test = extent.createTest("createAndSearchNoteTest");
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

		driver.findElement(By.xpath("//input[@id='opennote']")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("notetitle")).sendKeys("Automation Seleniumn");
		driver.findElement(By.id("notedescreption")).sendKeys("Testing Note");
		driver.findElement(By.xpath("//button[@id='notesubmit']")).click();
		Thread.sleep(12000);

		driver.findElement(By.className("refresh")).click();
		Thread.sleep(7000);
		
		driver.findElement(By.className("search-input")).sendKeys("Automation Seleniumn");
		Thread.sleep(7000);	
		driver.close();

	}

	@Test(dataProvider = "user-ids-passwords-excel-data-provider")
	public void viewNotes(String userId, String password) throws InterruptedException {
		test = extent.createTest("GridListViewNoteTest");
		driver.get("http://localhost:4200/login");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(25000);

		driver.findElement(
				By.cssSelector("button#notelistview.mat-icon-button.mat-button-base.ng-star-inserted\r\n" + ""))
				.click();
		Thread.sleep(10000);
		driver.findElement(
				By.cssSelector("button#notegridview.mat-icon-button.mat-button-base.ng-star-inserted\r\n" + ""))
				.click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//img[contains(@src,'../assets/images/profile.jpg')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("button#signoutuser.mat-raised-button.mat-button-base")).click();
		Thread.sleep(5000);
		driver.close();

	}

	
	@Test(dataProvider = "user-ids-passwords-excel-data-provider")
	public void signinSignoutTest(String userId, String password) throws InterruptedException {
		test = extent.createTest("GridListViewNoteTest");
		driver.get("http://localhost:4200/login");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(25000);

		driver.findElement(By.xpath("//img[contains(@src,'../assets/images/profile.jpg')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("button#signoutuser.mat-raised-button.mat-button-base")).click();
		Thread.sleep(5000);
		driver.close();
	}

	
	@Test(dataProvider = "user-ids-passwords-excel-data-provider")
	public void updateNoteTest(String userId, String password) throws InterruptedException {
		test = extent.createTest("updateNoteTest");
		driver.get("http://localhost:4200/login");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(10000);
		
		driver.findElement(By.xpath("//mat-card-title[contains(text(),'Niranjan')]")).click();
		driver.findElement(By.id("uptitle")).clear();
		driver.findElement(By.id("uptitle")).sendKeys("Niranjanup");
		driver.findElement(By.id("updescreption")).clear();
		driver.findElement(By.id("updescreption")).sendKeys("NoteUpdated Scuccesfully");
		driver.findElement(By.cssSelector("button#updatesubmit.close.mat-button.mat-button-base")).click();
		Thread.sleep(15000);
		driver.close();
		
	}
	
	@Test(dataProvider = "user-ids-passwords-excel-data-provider")
	public void trashNoteTest(String userId, String password) throws InterruptedException {
		test = extent.createTest("trashNoteTest");
		driver.get("http://localhost:4200/login");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[1]/div[2]/mat-sidenav-container[1]/mat-sidenav-content[1]/app-displaynotes[1]/div[1]/div[2]/div[1]/div[2]/app-note[1]/div[1]/div[1]/mat-card[1]/div[4]/app-icons[1]/div[1]/button[5]/span[1]/mat-icon[1]")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//button[contains(text(),'Delete Note')]")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//mat-icon[contains(text(),'refresh')]")).click();
		
		driver.findElement(By.xpath("//mat-icon[contains(text(),'menu')]")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//span[contains(text(),'Trash')]")).click();
		Thread.sleep(10000);
		driver.close();
	}
	
	
	@Test(dataProvider = "user-ids-passwords-excel-data-provider")
	public void getAllReminderNotesTest(String userId, String password) throws InterruptedException {	
		test = extent.createTest("GetAllReminderNotes");
		driver.get("http://localhost:4200/login");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//mat-icon[contains(text(),'menu')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//span[contains(text(),'Reminders')]")).click();
		Thread.sleep(5000);
		driver.close();

	}
	
	@Test(dataProvider = "user-ids-passwords-excel-data-provider")
	public void getAllArchiveNotesTest(String userId, String password) throws InterruptedException {	
		test = extent.createTest("GetAllArchiveNotes");
		driver.get("http://localhost:4200/login");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//mat-icon[contains(text(),'menu')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//span[contains(text(),'Archive')]")).click();
		Thread.sleep(5000);
		driver.close();

	}
	
	@Test(dataProvider = "user-ids-passwords-excel-data-provider")
	public void getAllTrashedNotesTest(String userId, String password) throws InterruptedException {	
		test = extent.createTest("GetAllTrashedNotes");
		driver.get("http://localhost:4200/login");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//mat-icon[contains(text(),'menu')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//span[contains(text(),'Trash')]")).click();
		Thread.sleep(5000);
		driver.close();

	}
	
	

	

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
			test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in extent
																					// report
			String screenshotPath = UserModuleTest.getScreenshot(driver, result.getName());
			test.addScreenCaptureFromPath(screenshotPath);// adding screen shot
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Test Case PASSED IS " + result.getName());
		}
		driver.quit();
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

}
