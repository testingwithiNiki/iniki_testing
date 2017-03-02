package firsttestngteamproject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.browserlaunchers.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FootballMeLinkVerification {
	
	 //declare public variable "driver"	 
	  WebDriver driver;
	
	//connect to a browser and open first page  
	@BeforeMethod 
	  public void setupApplication() throws Exception 
	  {  
	 	
		//set system property, so that we can access chrome driver
		  System.setProperty("webdriver.chrome.driver", "c://Users/Irina/Downloads/chromedriver_win32/chromedriver.exe");
		 
		  //It will open the Chrome browser 
		  driver=new ChromeDriver();
		  driver.manage().window().maximize();
		  //Open itest.infootball.me page in chrome browser
		  driver.get("http://itest.infootball.me/country/de");
		  //wait for page to load
		  Thread.sleep(1000); 
		  
		  Reporter.log("======Application Started====", true);
		  
	   } 
	
      //found broken links	
  @Test(priority=1,description="open the first page and count links on it")
 public void Login() {
	  
	  String currentURL=driver.getCurrentUrl();
	  //check that we are on right page by validating URL syntax"
	  Assert.assertTrue(currentURL.contains("country/de"));
	  //used to find all the elements in the current web page matching 
	  //to tag <a href="https://
	  //and store it in to list of WebElement links  <a ,,,, /a>
	  List<WebElement> links=driver.findElements(By.tagName("a"));
	  System.out.print("Total links are "+links.size()+"  ");
	  
	  for(int i=0;i<links.size();i++)	
	  {
		  //since links is the string 
		  WebElement ele=links.get(i);
		  //html link format start with href=
		  String url=ele.getAttribute("href");
		  verifyLinkActive(url);
	  }
  }
  
     
  private static void verifyLinkActive(String linkUrl) {
	// Exception handling 
	  
	  try
	  {
		  URL url=new URL(linkUrl);
		  HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();
		  httpURLConnect.setConnectTimeout(3000);	
		  httpURLConnect.connect();
		  
		  if(httpURLConnect.getResponseCode()==200)
		  {
			  System.out.println(linkUrl+"-"+httpURLConnect.getResponseMessage());
		  }
		  if(httpURLConnect.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND)
		  {
			  System.out.println(linkUrl+"-"+httpURLConnect.getResponseMessage()+"-"+HttpURLConnection.HTTP_NOT_FOUND);
			  
		  }
		  
	  } catch (Exception e){
		  System.out.print("Broken link "+linkUrl);
	  }
	
}

//close driver and exit   
@AfterMethod
  public void closeApplication() 
  {  
 	   
 	  driver.quit(); 
 	  Reporter.log("======Close Browser Session====", true); 
 	  
   }
 
}
