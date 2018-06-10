/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goodsmultiplelot;

import static goodsmultiplelot.SeleniumFuction.chkEditor;
import static goodsmultiplelot.SeleniumFuction.selectDateByJs;
import static goodsmultiplelot.SeleniumFuction.selectDropdown;
import static goodsmultiplelot.SeleniumFuction.submitButton;
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
/**
 *
 * @author Nitish
 */
public class AppApproveHOPA 
{
    public static void appApproveHOPA(WebDriver driver, int ii, String currentUrl, WebDriverWait wait, String appId, String hopaUserID, String hopaPassword) 
    {
        WebElement email = driver.findElement(By.id("txtEmailId"));
        email.clear();

        WebElement password = driver.findElement(By.name("password"));
        password.clear();

        email.sendKeys(hopaUserID);
        password.sendKeys(hopaPassword);

        WebElement login = driver.findElement(By.id("btnLogin"));
        login.submit();

        String jqueryGridPath = "//*[@id='list']";

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(jqueryGridPath)));

        String aPPID = appId + " (APP ID)";
        String linkAppID = "";

        String fileProcessingLink = "";

        String beforeXpath = "//*[@id='list']/tbody/tr[";
        String afterXpath = "]/td/a";

        String beforeAppIDXpath = "//*[@id='list']/tbody/tr[";
        String AfterAppIDXpath = "]/td[4]";

        WebElement table = driver.findElement(By.id("list"));
        List<WebElement> allRows = table.findElements(By.tagName("tr"));

        for (int i = 1; i <= allRows.size(); i++) 
        {
            linkAppID = driver.findElement(By.xpath(beforeAppIDXpath + i + AfterAppIDXpath)).getText();

            if (linkAppID.equalsIgnoreCase(aPPID)) 
            {
                String s = beforeAppIDXpath + i + AfterAppIDXpath;
                System.out.println(linkAppID);

                fileProcessingLink = beforeAppIDXpath + i + "]/td/a[contains(@href,'FileProcessing.jsp')]";
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(fileProcessingLink)));
                driver.findElement(By.xpath(fileProcessingLink)).click();
                break;
            }
            
        }

        
        String editorFramePath = "//iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";
        chkEditor(driver, wait, editorFramePath);

        String dropDownPath = "//*[@id='txtAction']";

        selectDropdown(driver, wait, dropDownPath, 2);

        String button = "//*[@id='tbnAdd']";

        submitButton(driver, button);

        driver.findElement(By.xpath("//a[contains(@href,'Logout.jsp')]")).click();
    }

}
