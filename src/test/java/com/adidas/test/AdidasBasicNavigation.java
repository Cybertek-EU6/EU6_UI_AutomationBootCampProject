package com.adidas.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class AdidasBasicNavigation {

    public static void main(String[] args) {

        WebDriverManager.chromedriver().setup(); // this statement sets up my driver
        // prepare the driver

        WebDriver driver = new ChromeDriver(); // create and open the browser

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("https://www.demoblaze.com/index.html"); // is used to navigate

      //  WebElement locatorLaptop = driver.findElement(By.xpath("//a[.='Laptops']"));
        WebElement locatorLaptop = driver.findElement(By.linkText("Laptops"));

        WebDriverWait wait = new WebDriverWait(driver,10); // Explicit wait

    //    wait.until(ExpectedConditions.elementToBeClickable(locatorLaptop));

        locatorLaptop.click();

    //    wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Sony vaio i5")));

        driver.findElement(By.partialLinkText("Sony vaio i5")).click();

        driver.findElement(By.linkText("Add to cart")).click();


        // the alert does not show up immediately
        wait.until(ExpectedConditions.alertIsPresent());

        // special class from selenium

        Alert alert = driver.switchTo().alert();


        alert.accept();


        String title = driver.getTitle();

        System.out.println("title = " + title);

        driver.close();


    }

}
