package com.adidas.utilities;

import com.github.javafaker.Faker;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdidasUtility {

    WebDriver driver;
    WebDriverWait wait;

    public AdidasUtility(WebDriver driver, WebDriverWait wait){
        this.driver=driver;
        this.wait=wait;
    }

    public int productAdder(String category, String product){
        driver.findElement(By.xpath("//a[.='"+category+"']")).click();

        driver.findElement(By.xpath("//a[.='"+product+"']")).click();

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
