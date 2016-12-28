package facebookmessage.suites;

import org.testng.annotations.Test;
import org.testng.internal.annotations.ITest;

import config.ConfigurationManagement;
import facebookmessage.resources.HomeScreen;
import facebookmessage.resources.LogInScreen;
import wallethub.resources.WebDriverManagement;
import org.testng.annotations.BeforeTest;
import java.io.IOException;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;




public class FacebookMessage {
	
	static WebDriver driver = null;
	WebDriverManagement webDriver = new WebDriverManagement(driver);
	LogInScreen login = null;
	HomeScreen home = null;
	
	String username = "";
	String password = "";
	String message = "";
	
	@Test
	public void postAFacebookMessage() throws InterruptedException{
		
		// 1. Sign in to FaceBook
		login.verifyCurrentScreenIsSignInScreen();
		login.inputEmailAddress(username);
		login.inputPassword(password);
		login.clickSignIn();
		
		
		// 2. Post a message
		home.verifyCurrentScreenIsHomeScreen();
		home.invokeMessage();
		home.inputMessage(message);
		Thread.sleep(1000); // sleep 1 second after message was input
		home.clickPost();
		
		// 3. Verify message after posted
		
		home.verifyMessageAfterPosted(message);
		
		Thread.sleep(5000);
	}
  
	@BeforeTest
	public void beforeTest() {
		
		ConfigurationManagement config = new ConfigurationManagement();
		Map<String, String> configHashMap = null;
		try {
			configHashMap = config.getPropValues();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String browserType = configHashMap.get("browserType");
		long timeOut = Long.parseLong(configHashMap.get("timeOut"));
		
		String url = configHashMap.get("faceBookURL");
		username = configHashMap.get("faceBookUsername");
		password = configHashMap.get("faceBookPassword");
		message = configHashMap.get("faceBookMessage");
		

		driver = webDriver.startWebDriverInstance(url, browserType);
		login = new LogInScreen(driver, timeOut);
		home = new HomeScreen(driver, timeOut);
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus()==ITestResult.FAILURE){
			webDriver.takeAScreenshot();
			webDriver.closeBrowser();
		} else webDriver.closeBrowser();
	}
	
}
