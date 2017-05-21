package com.thebodgeitstore.selenium.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.thebodgeitstore.pageObjects.BasketPage;
import com.thoughtworks.selenium.SeleneseTestCase;

import junit.framework.TestCase;

public class BasketTest extends SeleneseTestCase {
	
	private WebDriver driver;
	private String site = "https://bodgeit.herokuapp.com/";
	private MockitoTest test = mock(MockitoTest.class);
	
	public void setUp() throws Exception {
		String target = System.getProperty("zap.targetApp");
		if (target != null && target.length() > 0) {
			// Theres an override
			site = target;
		}
		
		String randomUser = RandomStringUtils.randomAlphabetic(10);
		when(test.getValidUser()).thenReturn(randomUser + "@test.com");
		when(test.getInvalidUser()).thenReturn("teste");
		when(test.getValidPassword1()).thenReturn("password");
		when(test.getValidPassword2()).thenReturn("password");
		when(test.getInvalidPassword1()).thenReturn("pas");
		when(test.getInvalidPassword2()).thenReturn("passw");
		
		
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
	
	public void tstAddProductsToBasket() {
		driver.get(site + "product.jsp?typeid=1");
		sleep();
		BasketPage.link_basic(driver).click();
		sleep();
		BasketPage.button_plus(driver).click();
		BasketPage.button_minus(driver).click();
		BasketPage.button_submit(driver).click();
		sleep();

		assertTrue(driver.getPageSource().indexOf("$1.20") > 0);	
	}

	
	public void tstBasketLogoutLogin() {
		driver.get(site);
		sleep();
		driver.findElement(By.linkText("Logout")).click();
		sleep();
		//this.loginUser(test.getValidUser(), test.getValidPassword1());
		driver.findElement(By.linkText("Your Basket")).click();
		assertTrue(driver.getPageSource().indexOf("$0.00") > 0);	
	}
	
	public void testAll() {
		tstAddProductsToBasket();
//		tstBasketLogoutLogin();

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
		BasketTest test = new BasketTest();
		test.setUp();
		test.testAll();
		test.tearDown();
		
	}

}
