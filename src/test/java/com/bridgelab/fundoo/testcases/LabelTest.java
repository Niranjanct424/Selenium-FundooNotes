package com.bridgelab.fundoo.testcases;

import static org.testng.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

public class LabelTest {
 
	public WebDriver driver;
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;

	@BeforeMethod
	public void setup() {
//		System.setProperty("webdriver.chrome.driver", "D:\\selenium\\chromedriver_win32\\chromedriver.exe");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:4200/login");
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);
	}
	
	@BeforeTest
	public void beforeTest() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/labelmodulereport.html");

		htmlReporter.config().setDocumentTitle("Fundoo-Note-Automation Report"); // Tile of report
		htmlReporter.config().setReportName("Label Module Functional Testing"); // Name of the report
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
	

		@Test(dataProvider = "user-ids-passwords-excel-data-provider")
		public void createLabelTest(String userId, String password)
				throws InterruptedException {

			test = extent.createTest("EditLabelTest");

			driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
			driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
			driver.findElement(By.xpath("//button[@id='loginbutton']")).click();

			Thread.sleep(15000);
			String expected = driver.getCurrentUrl();
			String actual = "http://localhost:4200/dashboard";
			assertEquals(actual, expected);
			Thread.sleep(2000);

			driver.findElement(By.xpath("//mat-icon[contains(text(),'menu')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//span[contains(text(),'Edit labels')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//input[@id='labelName']")).sendKeys("wonderLabel");
			Thread.sleep(5000);
			driver.findElement(By.xpath("//span[@class='mat-button-wrapper']//mat-icon[@class='mat-icon notranslate material-icons mat-icon-no-color'][contains(text(),'check')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//span[contains(text(),'Done')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//span[contains(text(),'Edit labels')]")).click();
			Thread.sleep(5000);
			
			driver.close();

		}
		
		
		
		@Test(dataProvider = "user-ids-passwords-excel-data-provider")
		public void updateLabelTest(String userId, String password)
				throws InterruptedException {

			test = extent.createTest("EditLabelTest");

			driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
			driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
			driver.findElement(By.xpath("//button[@id='loginbutton']")).click();

			Thread.sleep(15000);
			String expected = driver.getCurrentUrl();
			String actual = "http://localhost:4200/dashboard";
			assertEquals(actual, expected);
			Thread.sleep(2000);

			driver.findElement(By.xpath("//mat-icon[contains(text(),'menu')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//span[contains(text(),'Edit labels')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//input[@id='labelinputdata']")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//input[@id='labelinputdata']")).clear();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//input[@id='labelinputdata']")).sendKeys("updatedlabel");
			Thread.sleep(5000);
			driver.findElement(By.xpath("//button[@id='upl']")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//span[contains(text(),'Done')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//span[contains(text(),'Edit labels')]")).click();
			Thread.sleep(5000);
			
			driver.close();

		}

		
		
		@Test(dataProvider = "user-ids-passwords-excel-data-provider")
		public void deleteLabelTest(String userId, String password)
				throws InterruptedException {

			test = extent.createTest("EditLabelTest");

			driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
			driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
			driver.findElement(By.xpath("//button[@id='loginbutton']")).click();

			Thread.sleep(15000);
			String expected = driver.getCurrentUrl();
			String actual = "http://localhost:4200/dashboard";
			assertEquals(actual, expected);
			Thread.sleep(2000);

			driver.findElement(By.xpath("//mat-icon[contains(text(),'menu')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//span[contains(text(),'Edit labels')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@class='cdk-overlay-container']//div[4]//div[1]//button[1]//span[1]//mat-icon[1]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//span[contains(text(),'Done')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//mat-icon[contains(text(),'refresh')]")).click();
			Thread.sleep(7000);	
			driver.findElement(By.xpath("//mat-icon[contains(text(),'menu')]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//span[contains(text(),'Edit labels')]")).click();
			Thread.sleep(5000);
			
			driver.close();

		}
		
		
		@Test(dataProvider = "user-ids-passwords-excel-data-provider")
		public void addLabelToNoteTest(String userId, String password)
				throws InterruptedException, AWTException {

			test = extent.createTest("EditLabelTest");

			driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
			driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
			driver.findElement(By.xpath("//button[@id='loginbutton']")).click();

			Thread.sleep(15000);
			String expected = driver.getCurrentUrl();
			String actual = "http://localhost:4200/dashboard";
			assertEquals(actual, expected);
			Thread.sleep(2000);
			
			driver.findElement(By.xpath("/html[1]/body[1]/app-root[1]/app-dashboard[1]/div[1]/div[2]/mat-sidenav-container[1]/mat-sidenav-content[1]/app-displaynotes[1]/div[3]/div[1]/div[3]/app-note[1]/div[1]/div[1]/mat-card[1]/div[4]/app-icons[1]/div[1]/button[5]/span[1]/mat-icon[1]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//button[contains(text(),'Add label')]")).click();
			Thread.sleep(5000);
//			WebElement webElement = 
			driver.findElement(By.xpath("//mat-checkbox[@id='mat-checkbox-1']//div[@class='mat-checkbox-inner-container mat-checkbox-inner-container-no-side-margin']")).click();
//			webElement.click();
			Thread.sleep(5000);
//			driver.findElement(By.xpath("")).click();
//			Thread.sleep(5000);
			Robot robot = new Robot();
			robot.mouseMove(900, 420);
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			Thread.sleep(5000);
			driver.findElement(By.xpath("//mat-icon[contains(text(),'refresh')]")).click();
			Thread.sleep(7000);	
			
			driver.close();
		}
		
		@Test(dataProvider = "user-ids-passwords-excel-data-provider")
		public void removeLabelToNoteTest(String userId, String password)
				throws InterruptedException {

			test = extent.createTest("EditLabelTest");

			driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
			driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
			driver.findElement(By.xpath("//button[@id='loginbutton']")).click();

			Thread.sleep(15000);
			String expected = driver.getCurrentUrl();
			String actual = "http://localhost:4200/dashboard";
			assertEquals(actual, expected);
			Thread.sleep(2000);
			
			driver.findElement(By.xpath("//mat-icon[@class='mat-icon notranslate mat-chip-remove mat-chip-trailing-icon material-icons mat-icon-no-color']")).click();
			Thread.sleep(5000);
			
			driver.findElement(By.xpath("//mat-icon[contains(text(),'refresh')]")).click();
			Thread.sleep(7000);
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
