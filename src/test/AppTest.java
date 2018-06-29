package test;

import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import action.HoverMenu;
import action.QueryGate;

public class AppTest
{
	WebDriver driver;
    
	public void assertResults(String check) {
		String title = driver.getTitle();
    	Assert.assertTrue(title.contains(check));
	}
	
	@Test
	public void firstPage() {
		HoverMenu hovermenu = new HoverMenu(driver);
		hovermenu.clickGoNext();
	}
	
	@Test(dependsOnMethods = { "firstPage" })
	public void SecondPage() throws ClassNotFoundException, SQLException {
		QueryGate querygate = new QueryGate(driver);
		querygate.getCredentials();
	}
	
	@BeforeClass()
    public void openHomePage() {
    	driver = new ChromeDriver();
		driver.get("http://10.0.1.86/tatoc/advanced/hover/menu");
    }
    
    @AfterClass()
    public void closeHomePage() {
    	//driver.quit();
    }
    
}
