package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Restful {
	WebDriver driver;
	Actions act;
	String Token,idtext;
	HttpClient httpClient;
	
	public Restful(WebDriver driver) {
		this.driver = driver;
		httpClient = HttpClientBuilder.create().build();
	}
	
	public void getSessionId() {
		idtext = driver.findElement(By.id("session_id")).getText();
		idtext = idtext.substring(12);
	}
	
	public void getToken() {
		getSessionId();
		String url = "http://10.0.1.86/tatoc/advanced/rest/service/token/" + idtext;
		
		HttpGet request = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = httpClient.execute(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedReader rd = null;
		try {
			rd = new BufferedReader(
			        new InputStreamReader(response.getEntity().getContent()));
		} catch (UnsupportedOperationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuffer result = new StringBuffer();
		String line = "";
		try {
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {
			json = (JSONObject) parser.parse(result.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Token = json.get("token").toString();
	}
	
	public void registerToken() {
		String url = "http://10.0.1.86/tatoc/advanced/rest/service/register";
		HttpPost post = new HttpPost(url);
		
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("id", idtext));
		urlParameters.add(new BasicNameValuePair("signature", Token));
		urlParameters.add(new BasicNameValuePair("allow_access", "1"));
		

		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			HttpResponse response = httpClient.execute(post);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("/html/body/div/div[2]/a")).click();
	}
	
	public void clickGoNext() {
		getToken();
		registerToken();
		
	}
}
