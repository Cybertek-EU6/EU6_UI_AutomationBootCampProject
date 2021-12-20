package com.adidas.pages;

import com.adidas.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AdidasHomePage {

public AdidasHomePage(){
    PageFactory.initElements(Driver.get(),this);
}

@FindBy(xpath = "//a[.='Laptops']")  // used instead of findElement method
    public WebElement Laptops;

@FindBy(xpath = "//a[.='Sony vaio i5']")
    public WebElement Sony;

// how to findElements method with @FindBy annotation

@FindBy(xpath = "//h5[contains(text(),'$')]")
public List<WebElement> prices;

}
