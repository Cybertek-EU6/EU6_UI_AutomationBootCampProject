package com.adidas.test;

import com.adidas.utilities.AdidasUtility;
import com.adidas.utilities.Driver;
import com.adidas.utilities.WebDriverFactory;
import com.github.javafaker.Faker;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class EU6UtilitySolution {

    /*
    Algorithm: Create reusable methods to add and delete product
                    - They need to return the list price of product for verification
                    - add or remove the price to calculate expected price
                    - get the actual price and ID from order confirmation and compare
                    - create methods that handle filling the form with faker
     */


    WebDriver driver= WebDriverFactory.getDriver("chrome");
    WebDriverWait wait = new WebDriverWait(driver,10);
    AdidasUtility adidas = new AdidasUtility(driver,wait);

    int expectedPurhaseAMount = 0;

    String orderID;
    int purchaseAmount;


    @BeforeClass
    public void setUp(){
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("https://www.demoblaze.com/index.html");
    }

    @AfterClass
    public void tearDown()  {

        driver.quit();
    }

    // we can do this with utility class as well

    @Test
    public void purchaseTest(){

        String[][] purchaseInfo = new String[][] {{"Laptops","Sony vaio i5"}, {"Laptops","Dell i7 8gb"},
                {"Monitors","Apple monitor 24"},{"Phones","Nexus 6"}};

        // adding products scenario
        for (String[] strings : purchaseInfo) {
            expectedPurhaseAMount+= adidas.productAdder(strings[0],strings[1]);
        }

        // removing unwanted products
        String[] unwantedProductsInfo = {"Dell i7 8gb","Apple monitor 24"};
        for (String s : unwantedProductsInfo) {
            expectedPurhaseAMount-= adidas.productRemover(s);
        }

        // Go to cart and click place order button
        driver.findElement(By.xpath("//a[.='Cart']")).click();
        driver.findElement(By.xpath("//button[.='Place Order']")).click();
        adidas.fillCustomerForm();

        driver.findElement(By.xpath("//button[.='Purchase']")).click();


        String confirmation = driver.findElement(By.xpath("//p[@class='lead text-muted ']")).getText();
        System.out.println("confirmation = " + confirmation);

        String[] confirmationArray = confirmation.split("\n");
        orderID = confirmationArray[0];
        System.out.println("orderID = " + orderID);
        purchaseAmount = Integer.parseInt(confirmationArray[1].split(" ")[1]);

        int actualAmount = purchaseAmount;
        System.out.println("actualAmount = " + actualAmount);
        System.out.println("expectedOrderAmmount = " + expectedPurhaseAMount);
        Assert.assertEquals(actualAmount,expectedPurhaseAMount,"The price do NOT match");

        driver.findElement(By.xpath("//button[@class='confirm btn btn-lg btn-primary']")).click();


    }


}
