package action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class QueryGate {
	WebDriver driver;
	
	public QueryGate(WebDriver driver) {
		this.driver = driver;
	}
	
	public void clickProceed(String name, String pass) {
		driver.findElement(By.id("name")).sendKeys(name);
		driver.findElement(By.id("passkey")).sendKeys(pass);
		driver.findElement(By.id("submit")).click();
	}
	
	public void getCredentials() throws SQLException, ClassNotFoundException {
		String symbol = getSymbol().toLowerCase();
		Connection con;
		String name = null , pass = null;
		int id = 0;
		
		try {
		    Class.forName("com.mysql.jdbc.Driver");   
		    con = DriverManager.getConnection("jdbc:mysql://10.0.1.86/tatoc","tatocuser","tatoc01");
		} catch (SQLException e) {
		    throw new RuntimeException(e);
		}
		
		Statement stmt = con.createStatement();	
		ResultSet rs = stmt.executeQuery("Select id from identity where symbol = '"+symbol+"';");
		
		
		while(rs.next()) {
			id = rs.getInt(1);
		}
		
		ResultSet rs2 = stmt.executeQuery("Select name, passkey from credentials where id = '"+id+"';");
		
		while(rs2.next()) {
			name = rs2.getString(1);
			pass = rs2.getString(2);
		}
		clickProceed(name, pass);
		
	}
	
	public String getSymbol() {
		return driver.findElement(By.name("symboldisplay")).getText();
	}
}
