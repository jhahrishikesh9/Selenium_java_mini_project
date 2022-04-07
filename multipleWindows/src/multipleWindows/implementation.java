package multipleWindows;

import java.util.*;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverInfo;
import org.openqa.selenium.opera.OperaDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class implementation{
	private WebDriver driver;
	private String baseUrl;
	@SuppressWarnings("deprecation")
	
	//setting up webDrivers
	@BeforeTest
	public  WebDriver getDriver() throws Exception {
		
		readExcel.setExcelFile();
		String browser=readExcel.getCellData(1, 1);
		//Setting up Chrome WebDriver 
		if(browser.equalsIgnoreCase("chrome")) { 
		System.setProperty("webdriver.chrome.driver",readExcel.getCellData(3, 1)); 
		driver=new ChromeDriver(); 
		}
		  
		//Setting up Firefox WebDriver
		else if(browser.equalsIgnoreCase("firefox")) {
		System.setProperty("webdriver.gecko.driver",readExcel.getCellData(6, 1)); 
		driver=new FirefoxDriver();
		}
		
		//If entered incorrect browser 
		else {
		System.out.println(readExcel.getCellData(7, 1)); 
		System.exit(0); 
		}

		driver.manage().window().maximize(); //maximizing window
		return driver;
	}
	
	
	@Test
	//verifying the original page and calling the child window
	public void implement() {
		String baseUrl = readExcel.getCellData(2, 1);
		System.out.print(baseUrl);
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS); 
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS); 
		String title = driver.getTitle();
		
		//checking title
		if(title.equalsIgnoreCase("Rediff.com: News | Rediffmail | Stock Quotes | Shopping")) {
			System.out.print("\nTitle verified successfully\n");
		}
		else{
			System.out.println("Not verified");
		}
		
		//click on create account
		driver.findElement(By.linkText("Create Account")).click();
		
		//finding number of links in the webpage and printing it
		List<WebElement> link = driver.findElements(By.tagName("a"));
	      System.out.println("The number of links is " + link.size());
	      for(WebElement l:link) {
	    	  System.out.print(l+"\n"); //printing the links
	      }
	    driver.findElement(By.partialLinkText("terms and conditions")).click(); 
	    
	    
	    //child window handling
        String parentWindow = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();

        // Here we will check if child window has other child windows and will fetch the heading of the child window
        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            driver.switchTo().window(ChildWindow);
                if (!parentWindow.equalsIgnoreCase(ChildWindow)) {
                //driver.switchTo().window(ChildWindow);
                String childTitle = driver.getTitle();
                if(childTitle.equalsIgnoreCase("Rediffmail: Terms and Conditions")) {
                	System.out.println("Child element title is verified sucessfully");
                	
                }
                else {
                	System.out.println("Not verified successfully");
                }
                
            }
            
        }
        
        driver.switchTo().window(parentWindow);  //switching back to parent window
        end();

	}
	@AfterTest
	public void end() {
		driver.quit();
 
		//end();
	}
}

