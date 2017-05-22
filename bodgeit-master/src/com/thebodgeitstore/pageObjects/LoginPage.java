package com.thebodgeitstore.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

  private static WebElement element = null;

  public static WebElement lnk_Username(WebDriver driver) {
    element = driver.findElement(By.name("username"));
    return element;
  }

  public static WebElement lnk_Password(WebDriver driver) {
    element = driver.findElement(By.name("password"));
    return element;
  }

  public static WebElement lnk_Submit(WebDriver driver) {
    element = driver.findElement(By.id("submit"));
    return element;
  }
}
