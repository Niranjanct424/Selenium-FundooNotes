package com.bridgelab.fundoo.testcases;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bridgelab.fundoo.util.ExcelReadUtil;

public class UserModuleTest {

	WebDriver driver;

	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver", "D:\\selenium\\chromedriver_win32\\chromedriver.exe");

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

		boolean isLoginExpectedToBeSuccessful = Boolean.valueOf(isLoginExpectedToBeSuccessfulString);

		driver = new ChromeDriver();
		driver.get("http://localhost:4200/login");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(userId);
		driver.findElement(By.xpath("//input[@id='upassword']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@id='loginbutton']")).click();

		Thread.sleep(10000);
		driver.close();

	}

	@DataProvider(name = "user-register-info-excel-data-provider")
	public String[][] registerDataProvider() {
//		return ExcelReadUtil.readExcelInto2DArray("./src/test/resources/register-data.xlsx", "Sheet1", 5);
		return new String[][] { { "Niranjan", "niranjangyadav124@gmail.com", "niranjan@55", "8880846463" },
				{ "yadav", "niruct6@gmail.com", "niranjan@55", "8880846463" }, };
	}

	
	@Test(dataProvider = "user-register-info-excel-data-provider")
	public void register(String userName, String password, String email, String phoneNumber)
			throws InterruptedException {

		driver = new ChromeDriver();
		driver.get("http://localhost:4200/registration");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

//		Boolean.valueOf(isRegistered);
		driver.findElement(By.xpath("//input[@id='runame']")).sendKeys(userName);
		driver.findElement(By.xpath("//input[@id='ruemail']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@id='rupassword']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@id='ruphoneNumber']")).sendKeys(phoneNumber);

		driver.findElement(By.xpath("//button[@id='registersubmit']")).click();
		System.out.println("User Already Exist to Register Try with different mail.....");
		Thread.sleep(10000);
		driver.close();

	}

	@DataProvider(name = "user-forgotMail-data-provider")
	public String[][] forgotMailDataProvider() {
		return ExcelReadUtil.readExcelInto2DArray("./src/test/resources/forgotmail-data.xlsx", "Sheet1", 2);
	}


	@Test(dataProvider = "user-forgotMail-data-provider")
	public void forgotPassword(String forgotMail, String ismailSent) throws InterruptedException {

		driver = new ChromeDriver();
		driver.get("http://localhost:4200/forgotPassword");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		Boolean.valueOf(ismailSent);
		driver.findElement(By.xpath("//input[@id='forgotpasswordmail']")).sendKeys(forgotMail);
		driver.findElement(By.xpath("//button[@id='fpsubmit']")).click();
		System.out.println("Email Sent for Verification.....");
		Thread.sleep(10000);
		driver.close();

	}

	@DataProvider(name = "user-updatePassword-data-provider")
	public String[][] updatePasswordDataProvider() {
		return ExcelReadUtil.readExcelInto2DArray("./src/test/resources/updatepassword-data.xlsx", "Sheet1", 3);
	}

	
	@Test(dataProvider = "user-updatePassword-data-provider")
	public void updatePassword(String password, String conformPassword, String isPassUpdated)
			throws InterruptedException {

		driver = new ChromeDriver();
		driver.get(
				"http://localhost:4200/resetPassword/eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6Mn0.sA4BYWa8edrLSwxJj21NIPWwSt4ggfbA-3PSohMpwVM");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS);

		Boolean.valueOf(isPassUpdated);
		driver.findElement(By.xpath("//input[@id='updatePass']")).sendKeys(password);
		driver.findElement(By.xpath("//input[@id='updateConformPass']")).sendKeys(password);
//		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[@id='updatePasswordSubmit']")).click();
		System.out.println("Password Updated Successfully.....");
		Thread.sleep(10000);
		driver.close();

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
