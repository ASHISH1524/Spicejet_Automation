package package1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.time.chrono.ChronoZonedDateTime;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.SubmitElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class TestCase1 {

	WebDriver driver;
	
	By roundTrip = By.xpath("/html/body/form/div[4]/div[2]/div/div[5]/div[2]/div[2]/div[2]/div[3]/div/div[2]/table/tbody/tr/td[2]/input");
	By from= By.xpath("//*[@id=\'ctl00_mainContent_ddl_originStation1_CTXT\']");
	By to =By.xpath("//*[@id=\"ctl00_mainContent_ddl_destinationStation1_CTXT\"]");
	By dateBy=By.xpath("//*[@id=\"ctl00_mainContent_view_date1\"]");
	By dDateFrom = By.xpath("//*[@id=\"ui-datepicker-div\"]/div[1]/table/tbody/tr[5]/td[2]/a");
	By dateBy1 =By.xpath("//*[@id=\"ctl00_mainContent_view_date2\"]");
	By dDateto =By.xpath("//*[@id=\"ui-datepicker-div\"]/div[1]/table/tbody/tr[6]/td[2]/a");
	By adult =By.xpath("//*[@id=\"divpaxinfo\"]");
	By adult2 =By.xpath("//*[@id=\"ctl00_mainContent_ddl_Adult\"]");
	By submit =By.xpath("//*[@id=\"ctl00_mainContent_btn_FindFlights\"]");
	By text =By.xpath("//*[@id=\"selectMainBody\"]/div[6]/div");
	
	@BeforeTest
	public void browser_properties()
	{
		System.setProperty("webdriver.edge.driver", "C:\\Users\\Ashish Mahato\\Downloads\\edgedriver_win64 (1)\\msedgedriver.exe");
		driver= new EdgeDriver();
		driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	}
	
	@Test(dataProvider="ExcelData")
	public void testcase1(String excelfrom, String excelto) throws InterruptedException, IOException
	{
		driver.get("https://www.spicejet.com/");
		driver.findElement(roundTrip).click();
		driver.findElement(from).sendKeys(excelfrom);
		driver.findElement(to).click();
		Thread.sleep(1000);
		driver.findElement(to).sendKeys(excelto);
		Thread.sleep(1000);
		driver.findElement(dateBy).click();
	    driver.findElement(dDateFrom).click();
	    Thread.sleep(1000);
	    driver.findElement(dateBy1).click();
	    driver.findElement(dDateto).click();
	    driver.findElement(adult).click();
	    driver.findElement(adult2).click();
	    Actions actions = new Actions(driver);
	    new Select(driver.findElement(adult2)).selectByVisibleText("2");
	    driver.findElement(submit).click();
	    Thread.sleep(1000);
	  //taking the Screenshot
	  		TakesScreenshot scrShot =((TakesScreenshot)driver);
	  		File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
	  		FileUtils.copyFile(scrFile, new File("C:\\\\kjkj\\\\javaprg\\\\Spicejet1\\\\src\\\\ScreenShot\\Screenshot.png"));
	  		System.out.println(driver.findElement(text).getText());
	}
	
	@DataProvider (name="ExcelData")
	public Object[][]  getdata() throws BiffException
	{
		Object[][] data = getExcelData("C:\\kjkj\\javaprg\\Spicejet1\\src\\TestData\\TestDataSheet.xls","Sheet1");
		return data;
	}
	
	
	//Fetching the data from the Excel Sheet
			public String[][] getExcelData(String fileName , String sheetName) throws BiffException
			{
				String[][] arrayExcelData = null;
				try {
					FileInputStream fs = new FileInputStream(fileName);
					Workbook wb = Workbook.getWorkbook(fs);
					Sheet sh = wb.getSheet(sheetName);

					int totalNoOfCols = sh.getColumns();
					int totalNoOfRows = sh.getRows();

					arrayExcelData = new String[totalNoOfRows - 1][totalNoOfCols];

					for (int i = 1; i < totalNoOfRows; i++) {

						for (int j = 0; j < totalNoOfCols; j++) {
							arrayExcelData[i - 1][j] = sh.getCell(j, i).getContents();
						}

					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
					e.printStackTrace();
				}
				return arrayExcelData;
				
			}
			@AfterTest                                                        //This is after test method
			public void closeBrowser()
			{
				driver.quit();
			}
}
