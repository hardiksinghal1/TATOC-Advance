package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


public class HoverMenu {
	
	WebDriver driver;
	Actions act;
	
	public HoverMenu(WebDriver driver) {
		this.driver = driver;
	}
	
	public void hover() {
		act = new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("/html/body/div/div[2]/div[2]")));
		
	}
	
	public void clickGoNext() {
		hover();
		WebElement subMenu = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/span[5]"));
		act.moveToElement(subMenu);
		act.click().build().perform();
		//driver.findElements(By.className("menuitem")).get(3).click();
	}
}
