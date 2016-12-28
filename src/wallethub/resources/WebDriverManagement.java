package wallethub.resources;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.Reporter;

public class WebDriverManagement {
	private WebDriver driver = null;
	public WebDriverManagement(WebDriver driver) {
		this.driver = driver;
	}
	
	
	public WebDriver startWebDriverInstance(String url, String browserType){
		switch (browserType) {
		case "gc":
			driver = new ChromeDriver();
			break;
		case "ff":
			driver = new FirefoxDriver();
			break;
		case "safari":
			driver = new SafariDriver();
			break;
		case "ie":
			driver = new InternetExplorerDriver();
			break;
		default:
			System.out.println("Incorrect browser_type=" + browserType);
			Assert.assertFalse(false);
			break;
		}
		
		driver.manage().window().maximize();
		driver.get(url);
		
		return driver;
	}

	public void takeAScreenshot(){
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		File source = screenshot.getScreenshotAs(OutputType.FILE);
		String screenshotPath = System.getProperty("user.dir") + "/src/screenshots/";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
	
		String filename = dateFormat.format(date).toString() + ".png";

		try {
			FileUtils.copyFile(source, new File(screenshotPath + filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}

	public void closeBrowser(){
		System.out.println("Closing browser...");
		driver.close();
	}

	public void navigateTo(String url){
		driver.navigate().to(url);
	}


}
