package com.adidas.test;

import com.adidas.TestBaseAdidas;
import com.adidas.pages.AdidasHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class BasicNavigationWİthTestNG extends TestBaseAdidas {

    @Test

    public void testNavigation(){


        AdidasHomePage homePage = new AdidasHomePage();

        List<WebElement> prices = homePage.prices;

        for (WebElement price : prices) {
            System.out.println("price.getText() = " + price.getText());
        }


        homePage.Laptops.click();

        wait.until(ExpectedConditions.visibilityOf(homePage.Sony));

        Assert.assertTrue(homePage.Sony.isDisplayed());


    }

}
