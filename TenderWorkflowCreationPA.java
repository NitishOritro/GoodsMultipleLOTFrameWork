/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goodsmultiplelot;

import static goodsmultiplelot.SeleniumFuction.FindElement;
import static goodsmultiplelot.SeleniumFuction.chkEditor;
import static goodsmultiplelot.SeleniumFuction.clickTenderPopUp;
import static goodsmultiplelot.SeleniumFuction.dropDownMenuLink;
import static goodsmultiplelot.SeleniumFuction.grabUrlAppId;
import static goodsmultiplelot.SeleniumFuction.grabUrlTenderId;
import static goodsmultiplelot.SeleniumFuction.selectCheckBox;
import static goodsmultiplelot.SeleniumFuction.selectDateByJs;
import static goodsmultiplelot.SeleniumFuction.submitButton;
import static goodsmultiplelot.SeleniumFuction.getDate;
import static goodsmultiplelot.SeleniumFuction.getDate;
import static goodsmultiplelot.SeleniumFuction.getDate;
import static goodsmultiplelot.SeleniumFuction.selectDropdown;
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
public class TenderWorkflowCreationPA 
{

    public static void tenderWorkflowCreationPA(Actions builder, WebDriver driver, int ii, String currentUrl, WebDriverWait wait, String tenderId,
            String paUserID, String paPassword) throws InterruptedException 
    {
        WebElement email = driver.findElement(By.id("txtEmailId"));
        email.clear();

        WebElement password = driver.findElement(By.name("password"));
        password.clear();

        email.sendKeys(paUserID);
        password.sendKeys(paPassword);

        WebElement login = driver.findElement(By.id("btnLogin"));
        login.submit();

        JavascriptExecutor jse = (JavascriptExecutor) driver;

        String menuPath = "//*[@id='headTabTender']";
        String dropDownMenuPath = "//a[contains(text(),'My Tender')]";

        dropDownMenuLink(driver, wait, menuPath, dropDownMenuPath, builder);
        submitButton(driver, "//a[contains(text(),'Under Preparation')]", wait);

        String tenderID = tenderId + ",\nTestSelenium" + tenderId;
        String linktenderID = "";

        String dashboardLink = "";

        WebElement table = driver.findElement(By.id("resultTable"));
        List<WebElement> allRows = table.findElements(By.tagName("tr"));
        String beforeAppIDXpath = "//*[@id='resultTable']/tbody/tr[";
        String AfterAppIDXpath = "]/td[2]";

        for (int i = 1; i < allRows.size(); i++) 
        {
            linktenderID = driver.findElement(By.xpath(beforeAppIDXpath + i + AfterAppIDXpath)).getText();
            if (linktenderID.equalsIgnoreCase(tenderID)) 
            {
                String s = beforeAppIDXpath + i + AfterAppIDXpath;
                System.out.println(linktenderID);
                dashboardLink = beforeAppIDXpath + i + "]/td[7]/a[contains(@href,'TenderDashboard.jsp')]";
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dashboardLink)));
                driver.findElement(By.xpath(dashboardLink)).click();
                break;
            }
        }

        submitButton(driver, "//a[contains(@href,'OpenComm.jsp')]", wait);
        submitButton(driver, "//a[contains(text(),'Notify Committee Members')]", wait); //notice tab
        driver.findElement(By.xpath("//*[@id='txtaRemarks']")).sendKeys("Notify");
        submitButton(driver, "//*[@id='btnPublish']", wait);
        submitButton(driver, "//a[contains(@href,'EvalComm.jsp')]", wait);
        submitButton(driver, "//a[contains(text(),'Notify Committee Members')]", wait);
        driver.findElement(By.xpath("//*[@id='txtaRemarks']")).sendKeys("Notify");
        submitButton(driver, "//*[@id='btnPublish']", wait);
        submitButton(driver, "//a[contains(@href,'Notice.jsp')]", wait);
        submitButton(driver, "//a[contains(@href,'PEEncHash.jsp')]", wait);

        allRows = driver.findElements(By.xpath("//*[@id='frmEnc']/table/tbody/tr"));

        String beforeXpath = "//*[@id='frmEnc']/table/tbody/tr[";
        String afterXpath = "]/td/input";
        Boolean selected = false;
        
        By by;
        
        Boolean rowCount = false;

        for (int i = 1; i <= allRows.size(); i++) 
        {
            dashboardLink = beforeXpath + i + afterXpath;
            by = By.xpath(beforeXpath + i + afterXpath);
            rowCount = FindElement(driver, by, 1);
            if (rowCount == true) 
            {
                System.out.println(dashboardLink);
                System.out.println("Tick method " + i);

                selectCheckBox(driver, wait, dashboardLink);
            }
        }

        submitButton(driver, "//*[@id='hdnsubmit']", wait);
        submitButton(driver, "//a[contains(@href,'CreateWorkflow.jsp')]", wait);
        submitButton(driver, "//a[contains(@href,'FileProcessing.jsp')]", wait);

        String editorFramePath = "//iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";
        chkEditor(driver, wait, editorFramePath);

        String dropDownPath = "//*[@id='txtAction']";

        selectDropdown(driver, wait, dropDownPath, 1);
        String button = "//*[@id='tbnAdd']";
        submitButton(driver, button);

        driver.findElement(By.xpath("//a[contains(@href,'Logout.jsp')]")).click();
    }
}
