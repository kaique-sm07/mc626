/*
 * The BodgeIt Store and its related class files.
 *
 * The BodgeIt Store is a vulnerable web application suitable for pen testing
 *
 * Copyright 2011 psiinon@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thebodgeitstore.selenium.tests;

import java.io.File;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.mockito.Mockito.*;

import com.thoughtworks.selenium.SeleneseTestCase;

/*
 * Note that this is an example of how to use ZAP with Selenium tests,
 * not a good example of how to write good Selenium tests!
 */
public class FunctionalTest extends SeleneseTestCase {

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
	
public void tstSearch() {
		
		// CT1
		driver.get(site + "search.jsp");
		sleep();
		WebElement link = driver.findElement(By.name("q"));
		link.sendKeys("Thingie 1");
		
		link = driver.findElement(By.name("query"));
		link.submit();
		sleep();
		
		link = driver.findElement(By.cssSelector("body > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > font:nth-child(2) > table:nth-child(4) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(2)"));
		assertTrue(link.getText().equals("Usltu fxygnb n utje xwyxs e qvcwfqt eshper dehkgjy hpxx mjci daylso om nxenr xdeh nron daswajf evtruk. Ouvm tjx yenmdns ffachb vxkof iatsbgq b guhsspp qhx adqosbp l oddeqx gmbqk tblfgtu vhycjre yndbh fjbf ydfhpil ds."));
		
		
		
		// CT2
		driver.get(site + "search.jsp");
		sleep();
		link = driver.findElement(By.name("q"));
		link.sendKeys("");
		
		link = driver.findElement(By.name("query"));
		link.submit();
		sleep();
		String[] list = {"Basic Widget", "Complex Widget", "Weird Widget", "Thingie 1", "Thingie 2", "Thingie 3", "Thingie 4", "Thingie 5", "TGJ AAA", "TGJ ABB", "TGJ CCC", "TGJ CCD", "TGJ EFF", "TGJ GGG", "TGJ HHI", "TGJ JJJ", "Whatsit called", "Whatsit weigh", "Whatsit feel like", "Whatsit taste like", "Whatsit sound like", "GZ XT4", "GZ ZX3", "GZ FZ8", "GZ K77", "Zip a dee doo dah", "Doo dah day", "Bonzo dog doo dah", "Tipofmytongue", "Mindblank", "Youknowwhat", "Whatnot"};
		int n = 2;
		for (String item : list) {
			link = driver.findElement(By.cssSelector("body > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > font:nth-child(2) > table:nth-child(4) > tbody:nth-child(1) > tr:nth-child("+ n++ +") > td:nth-child(1)"));
			assertTrue(link.getText().equals(item));
			
		}
		
		
		// CT3
		driver.get(site + "search.jsp");
		sleep();
		link = driver.findElement(By.name("q"));
		link.sendKeys("greqgerqgew");
		
		link = driver.findElement(By.name("query"));
		link.submit();
		sleep();
		
		link = driver.findElement(By.cssSelector("body > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > font:nth-child(2) > div:nth-child(4) > b:nth-child(1)"));
		assertTrue(link.getText().equals("No Results Found"));
		
		
		
		// CT4
		driver.get(site + "search.jsp");
		sleep();
		link = driver.findElement(By.name("q"));
		link.sendKeys("<script>alert(\"XSS\")</script>");
		
		link = driver.findElement(By.name("query"));
		link.submit();
		
//		link = driver.findElement(By.cssSelector("body > center:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > font:nth-child(2) > div:nth-child(5) > b:nth-child(1)"));
//		assertTrue(link.getText().equals("No Results Found"));
	}
	
	public void checkMenu(String linkText, String page) {
		sleep();
		WebElement link = driver.findElement(By.linkText(linkText));
		link.click();
		sleep();

		assertEquals(site + page, driver.getCurrentUrl());
	}
	
	public void registerUser(String user, String password) {
		driver.get(site + "login.jsp");
		checkMenu("Register", "register.jsp");
		
		WebElement link = driver.findElement(By.name("username"));
		link.sendKeys(user);

		link = driver.findElement(By.name("password1"));
		link.sendKeys(password);
		
		link = driver.findElement(By.name("password2"));
		link.sendKeys(password);
		
		link = driver.findElement(By.id("submit"));
		link.click();
		sleep();
		
	}

	public void loginUser(String user, String password) {
		driver.get(site + "login.jsp");
		
		WebElement link = driver.findElement(By.name("username"));
		link.sendKeys(user);

		link = driver.findElement(By.name("password"));
		link.sendKeys(password);
		
		link = driver.findElement(By.id("submit"));
		link.click();
		sleep();
	}

	
	public void tstRegisterAndLoginUser() {
		// Create random username so we can rerun test
		this.registerUser(test.getValidUser(), test.getValidPassword1());
		assertTrue(driver.getPageSource().indexOf("You have successfully registered with The BodgeIt Store.") > 0);
		checkMenu("Logout", "logout.jsp");
		
		this.loginUser(test.getValidUser(), test.getValidPassword1());
		assertTrue(driver.getPageSource().indexOf("You have logged in successfully:") > 0);
	}
	
	public void tstAddProductsToBasket() {
		driver.get(site + "product.jsp?typeid=1");
		sleep();
		driver.findElement(By.linkText("Basic Widget")).click();
		sleep();
		driver.findElement(By.xpath("/html/body/center/table/tbody/tr[3]/td/table/tbody/tr/td[2]/form/center/table/tbody/tr[2]/td[4]/a[2]/img")).click();
		String text = driver.findElement(By.id("quantity")).getText();
		driver.findElement(By.xpath("/html/body/center/table/tbody/tr[3]/td/table/tbody/tr/td[2]/form/center/table/tbody/tr[2]/td[4]/a[1]/img")).click();
		driver.findElement(By.id("submit")).click();
		sleep();

		assertTrue(driver.getPageSource().indexOf("$1.20") > 0);	
	}

	
	public void tstBasketLogoutLogin() {
		driver.get(site);
		sleep();
		driver.findElement(By.linkText("Logout")).click();
		sleep();
		this.loginUser(test.getValidUser(), test.getValidPassword1());
		driver.findElement(By.linkText("Your Basket")).click();
		assertTrue(driver.getPageSource().indexOf("$0.00") > 0);	
	}

	
	public void tstLogin_Fail_NaoCadastrado() {
		//Cria usuario mas não cadastra
		String user = "nao.cadastrado@ig.com";
		this.loginUser(user, "senha123");
		assertTrue(driver.getPageSource().indexOf("You supplied an invalid name or password") > 0);
	}
	
	public void tstLogin_Fail_SenhaInvalida() {
		//Cria usuario 
		String user = "jose2@jose.jose";
		//Registra usuário
		this.registerUser(user, "senhavalida123", "senhavalida123");
		//Loga com esse usuário criado
		this.loginUser(user, "senhainvalida123");
		assertTrue(driver.getPageSource().indexOf("You supplied an invalid name or password") > 0);
	}
	
	public void tstLogin_Fail_SqlInjection() {
		//Cria usuario
		String user = "injection@sql.com";
		this.loginUser(user, "senha' or '1' = '1");
		assertTrue(driver.getPageSource().indexOf("You have logged in successfully:") > 0);
	}
	
	public void tstLogin_Success() {
		//Cria usuario 
		String user = "jose@jose.jose";
		//Registra usuário
		this.registerUser(user, "senhavalida123", "senhavalida123");
		//Loga com esse usuário criado
		this.loginUser(user, "senhavalida123");
		assertTrue(driver.getPageSource().indexOf("You have logged in successfully:") > 0);
	}
	
	private void registerUser(String user, String string, String string2) {
		driver.get(site + "login.jsp");
		checkMenu("Register", "register.jsp");
		
		WebElement link = driver.findElement(By.name("username"));
		link.sendKeys(user);

		link = driver.findElement(By.name("password1"));
		link.sendKeys(string);
		
		link = driver.findElement(By.name("password2"));
		link.sendKeys(string2);
		
		link = driver.findElement(By.id("submit"));
		link.click();
		sleep();
		
	}
	
	
	public void tstRegisterUser() {
		// Create random username so we can rerun test
		String randomUser = test.getValidUser();
		this.registerUser(randomUser, test.getValidPassword1(), test.getValidPassword2());
		assertTrue(driver.getPageSource().indexOf("You have successfully registered with The BodgeIt Store.") > 0);
	}
	
	public void tstRegisterUserWithInvalidUsername() {
		// Create random username so we can rerun test, but without @ part
		String randomUser = test.getInvalidUser();
		this.registerUser(randomUser, test.getValidPassword1(), test.getValidPassword2());
		assertTrue(driver.getPageSource().indexOf("Invalid username - please supply a valid email address.") > 0);
	}
	
	public void tstRegisterUserWithInvalidPassword() {
		// Create random username so we can rerun test, but with invalid password
		String randomUser = test.getValidUser();
		this.registerUser(randomUser, test.getInvalidPassword1(), test.getInvalidPassword1());
		assertTrue(driver.getPageSource().indexOf("You must supply a password of at least 5 characters.") > 0);
	}
	
	public void tstRegisterUserWithInvalidUsernameAndPassword() {
		String randomUser = test.getInvalidUser();
		this.registerUser(randomUser, test.getInvalidPassword1(), test.getInvalidPassword1());
		assertTrue(driver.getPageSource().indexOf("Invalid username - please supply a valid email address.") > 0);
	}
	
	public void tstRegisterUserWithDifferentPasswords() {
		String randomUser = test.getValidUser();
		this.registerUser(randomUser, test.getValidPassword1(), test.getInvalidPassword2());
		assertTrue(driver.getPageSource().indexOf("The passwords you have supplied are different.") > 0);
	}
	

	public void testAll() {
		tstRegisterUser();
		tstRegisterUserWithInvalidUsername();
		tstRegisterUserWithInvalidPassword();
		tstRegisterUserWithInvalidUsernameAndPassword();
		tstRegisterUserWithDifferentPasswords();
		tstLogin_Fail_NaoCadastrado();
		tstLogin_Fail_SenhaInvalida();
		tstLogin_Fail_SqlInjection();
		tstLogin_Success();
		tstAddProductsToBasket();
		tstBasketLogoutLogin();
		tstSearch();
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
