package action;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FileHandle {
	WebDriver driver;
	String sig;
	
	public FileHandle(WebDriver driver) {
		this.driver = driver;
	}
	
	public void submitSignature() {
		readSignature();
		driver.findElement(By.xpath("//*[@id=\"signature\"]")).sendKeys(sig);
		driver.findElement(By.xpath("/html/body/div/div[2]/form/input[2]")).click();
	}
	
	public void readSignature() {
		clickDownload();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file = new File("/home/hardiksingha/Downloads/file_handle_test.dat");
		String line = null;
		Scanner scnr = null;
		try {
			scnr = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(scnr.hasNextLine()){
		   line = scnr.nextLine();
		}
		sig = line.substring(11);
		
		file.delete();
	}
	
	public void clickDownload(){
		driver.findElement(By.xpath("/html/body/div/div[2]/a")).click();
	}
}
