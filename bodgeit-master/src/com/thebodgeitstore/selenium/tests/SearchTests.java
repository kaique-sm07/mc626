package com.thebodgeitstore.selenium.tests;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.mockito.Mockito.*;

import com.thebodgeitstore.pageObjects.SearchPage;

import junit.framework.TestCase;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class SearchTests extends TestCase {
	private WebDriver driver;
	private String site = "https://bodgeit.herokuapp.com/";
	private MockitoTest test = mock(MockitoTest.class);
	private SearchPage searchPage;
	
	public void setUp() throws Exception {
		String target = System.getProperty("zap.targetApp");
		if (target != null && target.length() > 0) {
			// Theres an override
			site = target;
		}
		
		when(test.getValidItem()).thenReturn("Thingie 1");
		when(test.getInvalidItem()).thenReturn("Item que não existe");
		when(test.getXSSCode()).thenReturn("<script>alert(\"XSS\")</script>");
		
		final File file = new File("chromedriver");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

		driver = new ChromeDriver();
		this.setDriver(driver);
	}
	
	private void sleep() {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// Ignore
		}
		
	}
	
	// CT1 Search one item that exists and check if there is an item with that name on the result page
	public void tstSearchOne() {
		search(test.getValidItem());
		WebElement htmlObj = driver.findElement(By.cssSelector("body > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > font:nth-child(2) > table:nth-child(4) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(1)"));
		assertTrue(htmlObj.getText().equals(test.getValidItem()));
	}
	
	// CT2 Search blank and check if all of the DB items are present
	public void tstSearchAll() {
		search("");
		
		String[] list = {"Basic Widget", "Complex Widget", "Weird Widget", "Thingie 1", "Thingie 2", "Thingie 3", "Thingie 4", "Thingie 5", "TGJ AAA", "TGJ ABB", "TGJ CCC", "TGJ CCD", "TGJ EFF", "TGJ GGG", "TGJ HHI", "TGJ JJJ", "Whatsit called", "Whatsit weigh", "Whatsit feel like", "Whatsit taste like", "Whatsit sound like", "GZ XT4", "GZ ZX3", "GZ FZ8", "GZ K77", "Zip a dee doo dah", "Doo dah day", "Bonzo dog doo dah", "Tipofmytongue", "Mindblank", "Youknowwhat", "Whatnot"};
		int n = 2;
		for (String item : list) {
			WebElement htmlObj = driver.findElement(By.cssSelector("body > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > font:nth-child(2) > table:nth-child(4) > tbody:nth-child(1) > tr:nth-child("+ n++ +") > td:nth-child(1)"));
			assertTrue(htmlObj.getText().equals(item));	
		}	
	}
	
	// CT3 Searches for some string that is not an item on the DB and checks if the correct message appears
	public void tstSearchInvalid() {
		search(test.getInvalidItem());
		
		WebElement htmlObj = driver.findElement(By.cssSelector("body > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > font:nth-child(2) > div:nth-child(4) > b:nth-child(1)"));
		assertTrue(htmlObj.getText().equals("No Results Found"));
	}
	
	// CT4 Input a JS code on the search field and checks if the alert is shown on the results page
	public void tstSearchXSS() {
		search(test.getXSSCode());
		WebElement htmlObj = driver.findElement(By.cssSelector("body > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > font:nth-child(2) > div:nth-child(5) > b:nth-child(1)"));
		assertTrue(htmlObj.getText().equals("No Results Found"));
	}
	
	// input the search string and click on the submit button
	public void search(String searchString) {
		driver.get(site + "search.jsp");
		sleep();
		WebElement htmlObj = searchPage.txt_Query(driver);
		htmlObj.sendKeys(searchString);
		
		htmlObj = searchPage.btn_Submit(driver);
		htmlObj.submit();
		sleep();
	}
	
	public void testAll() {
		tstSearchOne();
		tstSearchAll();
		tstSearchInvalid();
		
		// As the xss problem does not occur on chrome, and we must use chrome webdriver,
		// we are not testing this.
		//tstSearchXSS();
	}
	
	public void tearDown() throws Exception {
		driver.close();
	}

	protected WebDriver getDriver() {
		return driver;
	}

	protected void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	protected String getSite() {
		return site;
	}

	protected void setSite(String site) {
		this.site = site;
	}
	
	public static void main(String[] args) throws Exception {
		FunctionalTest test = new FunctionalTest();
		test.setUp();
		test.testAll();
		test.tearDown();	
	}
}
