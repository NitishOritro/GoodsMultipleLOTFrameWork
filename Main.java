/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goodsmultiplelot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Main 
{
    public static void main(String[] args) 
    {
        System.setProperty("webdriver.gecko.driver", "E:\\Selenium\\Gecodriver\\geckodriver.exe");
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        
        capabilities.setCapability("marionette", true);
        
        Random rand = new Random(); 
        int ii = rand.nextInt(100000);
        String currentUrl = "";
        String appId = "";
        String tenderID = "";
        
        String paUserID = "pauserrotdormowhs@gmail.com";
        String paPassword = "egp12345";
        
        String hopaUserID = "hoparotdormowhs@gmail.com";
        String hopaPassword = "egp12345";
        
        try
        {
            WebDriver driver = new FirefoxDriver();
         
            driver.get("http://192.168.3.8:8080/");
            ((JavascriptExecutor) driver).executeScript("return window.stop");
            
            WebDriverWait wait = new WebDriverWait(driver, 20);
            Actions builder = new Actions(driver); 
            
            AppCreationPA appCreationPA = new AppCreationPA();
            AppApproveHOPA appApproveHOPA = new AppApproveHOPA();
            TenderCreationPA tenderCreationPA = new TenderCreationPA();
            
            try
            {
                driver.manage().window().maximize();
                currentUrl = driver.getCurrentUrl();
                
                appId = appCreationPA.appCreationPA(driver, ii, currentUrl, wait, paUserID, paPassword);
                appApproveHOPA.appApproveHOPA(driver, ii, currentUrl, wait, appId, hopaUserID, hopaPassword);
                tenderID = tenderCreationPA.tendCreationPA(builder , driver, ii, currentUrl, wait, appId, paUserID, paPassword);
                
            }        
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
