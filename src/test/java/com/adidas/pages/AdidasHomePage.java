package com.adidas.pages;

import com.adidas.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdidasHomePage {

public AdidasHomePage(){
    PageFactory.initElements(Driver.get(),this);
}

@FindBy(xpath = "//a[.='Laptops']")
    public WebElement Laptops;

@FindBy(xpath = "//a[.='Sony vaio i5']")
    public WebElement Sony;

}
