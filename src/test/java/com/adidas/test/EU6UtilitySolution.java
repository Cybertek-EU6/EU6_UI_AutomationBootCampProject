package com.adidas.test;

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


    int expectedPurhaseAMount = 0;

    String orderID;
    int purchaseAmount;

    int listPrice;
    int addingPrice;
    int cartPrice;

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
            expectedPurhaseAMount+=productAdder(strings[0],strings[1]);
        }

        // removing unwanted products
        String[] unwantedProductsInfo = {"Dell i7 8gb","Apple monitor 24"};
        for (String s : unwantedProductsInfo) {
            expectedPurhaseAMount-=productRemover(s);
        }

        // Go to cart and click place order button
        driver.findElement(By.xpath("//a[.='Cart']")).click();
        driver.findElement(By.xpath("//button[.='Place Order']")).click();
        fillCustomerForm();

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


   private int productAdder(String category, String product){
       driver.findElement(By.linkText(category)).click();

        driver.findElement(By.linkText(product)).click();

        WebElement purchasePrice = driver.findElement(By.xpath("//h3[@class='price-container']"));
        String amountString = purchasePrice.getText();
        String[] arrayAmount = amountString.split(" ");
        int amount = Integer.parseInt(arrayAmount[0].substring(1));

        WebElement addCart = driver.findElement(By.xpath("//a[.='Add to cart']"));
        addCart.click();

        // accept pop up
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();

        // go to home page
        WebElement homeLink = driver.findElement(By.xpath("(//a[@class='nav-link'])[1]"));
        homeLink.click();

        return amount;
  }

    public int productRemover(String product){

        // click cart
        WebElement cart = driver.findElement(By.xpath("//a[.='Cart']"));
        cart.click();
        // define some locators
        String productPath = "//td[.='" + product + "']";
        String productPricePath = productPath + "/../td[3]";
        String deleteLinkPath = productPath + "/../td[4]/a";

        // get the price of product to be deleted
        String deletedProductPrice = driver.findElement(By.xpath(productPricePath)).getText();

        // delete the product
        driver.findElement(By.xpath(deleteLinkPath)).click();

        // wait until it is deleted
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(productPath)));
        return Integer.parseInt(deletedProductPrice);
    }

    public void fillCustomerForm() {

        Faker faker = new Faker();

        driver.findElement(By.xpath("//input[@id='name']")).sendKeys(faker.name().fullName());
        driver.findElement(By.xpath("//input[@id='country']")).sendKeys(faker.country().name());
        driver.findElement(By.xpath("//input[@id='city']")).sendKeys(faker.country().capital());
        driver.findElement(By.xpath("//input[@id='card']")).sendKeys(faker.business().creditCardNumber());
        driver.findElement(By.xpath("//input[@id='month']")).sendKeys("04");
        driver.findElement(By.xpath("//input[@id='year']")).sendKeys("2024");
        driver.findElement(By.xpath("//button[text()='Purchase']")).click();
    }
}
