package com.thebodgeitstore.selenium.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.File;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.thebodgeitstore.pageObjects.LoginPage;
import com.thebodgeitstore.pageObjects.RegisterPage;

public class LoginTests {
  private WebDriver driver;
  private String site = "https://bodgeit.herokuapp.com/";
  private MockitoTest test = mock(MockitoTest.class);
  private RegisterPage registerPage;

  public static void main(String[] args) throws Exception {
    FunctionalTest test = new FunctionalTest();
    test.setUp();
    test.testAll();
    test.tearDown();
  }

  public void setUp() throws Exception {
    String target = System.getProperty("zap.targetApp");
    if (target != null && target.length() > 0) {
      // Theres an override
      site = target;
    }

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

  public void checkMenu(String linkText, String page) {
    sleep();
    WebElement link = driver.findElement(By.linkText(linkText));
    link.click();
    sleep();

    assertEquals(site + page, driver.getCurrentUrl());
  }

  public void registerUser(String user, String password1, String password2) {
    driver.get(site + "login.jsp");
    checkMenu("Register", "register.jsp");

    WebElement link = registerPage.lnk_Username(driver);
    link.sendKeys(user);

    link = registerPage.lnk_Password1(driver);
    link.sendKeys(password1);

    link = registerPage.lnk_Password2(driver);
    link.sendKeys(password2);

    link = registerPage.lnk_Submit(driver);
    link.click();
    sleep();
  }

  public void loginUser(String user, String password) {
    driver.get(site + "login.jsp");

    WebElement link = LoginPage.lnk_Username(driver);
    link.sendKeys(user);

    link = LoginPage.lnk_Password(driver);
    link.sendKeys(password);

    link = registerPage.lnk_Submit(driver);
    link.click();
    sleep();
  }

  
  public void tstLogin_Fail_NaoCadastrado() {
    // Cria usuario mas não cadastra
    String randomUser = RandomStringUtils.randomAlphabetic(10) + "@test.com";
    this.loginUser(randomUser, "senha123");
    assertTrue(driver.getPageSource().indexOf("You supplied an invalid name or password") > 0);
  }

  public void tstLogin_Fail_SenhaInvalida() {
    String randomUser = RandomStringUtils.randomAlphabetic(10);
    when(test.getValidUser()).thenReturn(randomUser + "@test.com");
    when(test.getValidPassword1()).thenReturn("password");
    when(test.getInvalidPassword1()).thenReturn("pas");

    // Cria usuario
    String user = "jose2@jose.jose";
    // Registra usuário
    this.registerUser(user, "senhavalida123", "senhavalida123");
    // Loga com esse usuário criado
    this.loginUser(user, "senhainvalida123");
    assertTrue(driver.getPageSource().indexOf("You supplied an invalid name or password") > 0);
  }

  public void tstLogin_Fail_SqlInjection() {
    // Cria usuario
    String user = "injection@sql.com";
    this.loginUser(user, "senha' or '1' = '1");
    assertTrue(driver.getPageSource().indexOf("You have logged in successfully:") > 0);
  }

  public void tstLogin_Success() {
    // Cria usuario
    String user = "jose@jose.jose";
    // Registra usuário
    this.registerUser(user, "senhavalida123", "senhavalida123");
    // Loga com esse usuário criado
    this.loginUser(user, "senhavalida123");
    assertTrue(driver.getPageSource().indexOf("You have logged in successfully:") > 0);
  }

  public void tstRegisterUser() {
    // Create random username so we can rerun test
    String randomUser = test.getValidUser();
    this.registerUser(randomUser, test.getValidPassword1(), test.getValidPassword2());
    assertTrue(driver.getPageSource()
        .indexOf("You have successfully registered with The BodgeIt Store.") > 0);
  }

  public void tstRegisterUserWithInvalidUsername() {
    // Create random username so we can rerun test, but without @ part
    String randomUser = test.getInvalidUser();
    this.registerUser(randomUser, test.getValidPassword1(), test.getValidPassword2());
    assertTrue(driver.getPageSource()
        .indexOf("Invalid username - please supply a valid email address.") > 0);
  }

  public void tstRegisterUserWithInvalidPassword() {
    // Create random username so we can rerun test, but with invalid password
    String randomUser = test.getValidUser();
    this.registerUser(randomUser, test.getInvalidPassword1(), test.getInvalidPassword1());
    assertTrue(
        driver.getPageSource().indexOf("You must supply a password of at least 5 characters.") > 0);
  }

  public void tstRegisterUserWithInvalidUsernameAndPassword() {
    String randomUser = test.getInvalidUser();
    this.registerUser(randomUser, test.getInvalidPassword1(), test.getInvalidPassword1());
    assertTrue(driver.getPageSource()
        .indexOf("Invalid username - please supply a valid email address.") > 0);
  }

  public void tstRegisterUserWithDifferentPasswords() {
    String randomUser = test.getValidUser();
    this.registerUser(randomUser, test.getValidPassword1(), test.getInvalidPassword2());
    assertTrue(
        driver.getPageSource().indexOf("The passwords you have supplied are different.") > 0);
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
}
