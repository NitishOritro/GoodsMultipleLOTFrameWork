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
public class CommitteeFormation 
{
    public static void committeeFormation(Actions builder, WebDriver driver, int ii, String currentUrl, WebDriverWait wait, String tenderId) throws InterruptedException 
    {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        
        submitButton(driver, "//a[contains(@href,'Notice.jsp')]", wait); //notice tab

        submitButton(driver, "//a[contains(text(),'Add Currency')]", wait); //add currency link

        selectDropdown(driver, wait, "//*[@id='ddlCurrency']", 3);  // American Dollar

        submitButton(driver, "//*[@id='btnAddCurrency']", wait); //add currency 

        submitButton(driver, "//a[contains(text(),'Go Back To Dashboard')]", wait);

        submitButton(driver, "//a[contains(text(),'Add Currency Rate')]", wait);

        driver.findElement(By.xpath("//*[@id='currencyRate_12']")).clear();
        driver.findElement(By.xpath("//*[@id='currencyRate_12']")).sendKeys("85.0000");
        //JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("$('#currencyRate_12').trigger('blur')");

        submitButton(driver, "//*[@id='btnsubmit']", wait);

        driver.switchTo().alert().accept();

        submitButton(driver, "//a[contains(@href,'AddPckLotEstCost.jsp')]", wait);

        //Official Cost Estimate
        for (int i = 0; i < 5; i++) 
        {
            driver.findElement(By.xpath("//*[@id='taka_" + i + "']")).clear();

            driver.findElement(By.xpath("//*[@id='taka_" + i + "']")).sendKeys("10");
            jse.executeScript("$('#taka_" + i + "').trigger('blur')");
        }
        submitButton(driver, "//*[@id='submit']", wait);

        //Tender Committee Details
        submitButton(driver, "//*[@id='tcTab']", wait);

        submitButton(driver, "//a[contains(@href,'TenderCommFormation.jsp')]", wait);

        driver.findElement(By.xpath("//*[@id='txtcommitteeName']")).sendKeys("TC");

        clickTenderPopUp(driver, "//*[@id='addmem']");

        Boolean tcMemberDone = false;
        String hopaName = "";
        String procurementRole = "";
        List<WebElement> rows = driver.findElements(By.xpath("//*[@id='pe1']/table/tbody/tr"));
        //allRows = table.findElements(By.tagName("tr")); 

        String beforeXpath = "//*[@id='pe1']/table/tbody/tr[";
        String afterXpath = "]/td[4]";
        String roleNameXpath = "]/td[2]";

        String clickLink = "";
                //*[@id='pe1']/table/tbody/tr[2]/td[4]
        //*[@id='pe1']/table/tbody/tr
        int memeberCount = 0;
        int tcMemberCount = 0;
        for (int i = 2; i <= rows.size(); i++) 
        {
            procurementRole = driver.findElement(By.xpath(beforeXpath + i + afterXpath)).getText();
            if (memeberCount < 5) 
            {
                if (procurementRole.equalsIgnoreCase("HOPA") || procurementRole.equalsIgnoreCase("TC")) 
                {
                    if (procurementRole.equalsIgnoreCase("HOPA")) 
                    {
                        hopaName = driver.findElement(By.xpath(beforeXpath + i + roleNameXpath)).getText();
                    }
                    if (procurementRole.equalsIgnoreCase("TC")) 
                    {
                        tcMemberCount++;
                    }
                    String s = beforeXpath + i + "]/td/label/input";
                    System.out.println(procurementRole);

                    clickLink = beforeXpath + i + "]/td/label/input";
                    memeberCount++;

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));
                    driver.findElement(By.xpath(clickLink)).click();
                }
            } 
            else 
            {
                break;
            }

        }
        if (memeberCount < 5) 
        {
            for (int i = 2; i <= rows.size(); i++) 
            {
                procurementRole = driver.findElement(By.xpath(beforeXpath + i + afterXpath)).getText();
                if (memeberCount < 5) 
                {
                    if (procurementRole.equalsIgnoreCase("TOC")) 
                    {
                        String s = beforeXpath + i + "]/td/label/input";
                        System.out.println(procurementRole);

                        clickLink = beforeXpath + i + "]/td/label/input";
                        memeberCount++;

                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));
                        driver.findElement(By.xpath(clickLink)).click();

                    }
                } 
                else if (memeberCount == 5) 
                {
                    tcMemberDone = true;
                    break;
                }

            }
        }

        if (tcMemberDone != true) 
        {
            for (int i = 2; i <= rows.size(); i++) 
            {
                procurementRole = driver.findElement(By.xpath(beforeXpath + i + afterXpath)).getText();

                if (memeberCount < 5) 
                {
                    if (procurementRole.equalsIgnoreCase("TEC")) 
                    {
                        String s = beforeXpath + i + "]/td/label/input";
                        System.out.println(procurementRole);

                        clickLink = beforeXpath + i + "]/td/label/input";
                        memeberCount++;

                        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));
                        driver.findElement(By.xpath(clickLink)).click();

                    }
                } 
                else 
                {
                    break;
                }

            }
        }

        submitButton(driver, "//button[1]", wait);
        rows = driver.findElements(By.xpath("//*[@id='members']/tbody/tr"));

        beforeXpath = "//*[@id=\"members\"]/tbody/tr[";
        afterXpath = "]/td[2]";
        roleNameXpath = "]/td[1]";
        for (int i = 1; i <= rows.size(); i++) 
        {
            procurementRole = driver.findElement(By.xpath(beforeXpath + i + roleNameXpath)).getText();

            if (procurementRole.equalsIgnoreCase(hopaName)) 
            {
                //String s = beforeXpath+i+"]/td/label/input";
                System.out.println(procurementRole);

                clickLink = "//*[@id='cmbMemRole" + (i - 1) + "']";

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));
                selectDropdown(driver, wait, clickLink, 0);

            } 
            else if (i == 2) 
            {
                System.out.println(procurementRole);

                clickLink = "//*[@id='cmbMemRole" + (i - 1) + "']";

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));
                selectDropdown(driver, wait, clickLink, 1);
            } 
            else 
            {

                System.out.println(procurementRole);

                clickLink = "//*[@id='cmbMemRole" + (i - 1) + "']";

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));
                selectDropdown(driver, wait, clickLink, 2);
            }
        }

        submitButton(driver, "//*[@id='btnSubmit']", wait);

        //Notify TC Memebers
        submitButton(driver, "//a[contains(text(),'Notify Committee Members')]", wait);
        driver.findElement(By.xpath("//*[@id='txtaRemarks']")).sendKeys("TC");
        submitButton(driver, "//*[@id='btnPublish']", wait);

        //Tender Committee Process
        submitButton(driver, "//a[contains(@href,'OpenComm.jsp')]", wait);
        submitButton(driver, "//a[contains(@href,'OpenCommFormation.jsp')]", wait);
        driver.findElement(By.xpath("//*[@id='txtcommitteeName']")).sendKeys("TOC");
        submitButton(driver, "//*[@id='addmem']", wait);

        Boolean tocMemberDone = false;

        rows = driver.findElements(By.xpath("//*[@id='pe1']/table/tbody/tr"));
        //allRows = table.findElements(By.tagName("tr")); 

        beforeXpath = "//*[@id='pe1']/table/tbody/tr[";
        afterXpath = "]/td[4]";
        roleNameXpath = "]/td[2]";

        clickLink = "";
        int tocMemberCount = 0;
        memeberCount = 0;
        for (int i = 2; i <= rows.size(); i++) 
        {
            procurementRole = driver.findElement(By.xpath(beforeXpath + i + afterXpath)).getText();

            if (memeberCount < 2) {
                if (procurementRole.equalsIgnoreCase("HOPA") || procurementRole.equalsIgnoreCase("TOC")) 
                {
                    if (procurementRole.equalsIgnoreCase("HOPA")) 
                    {
                        hopaName = driver.findElement(By.xpath(beforeXpath + i + roleNameXpath)).getText();
                    }
                    if (procurementRole.equalsIgnoreCase("TOC")) 
                    {
                        tocMemberCount++;
                    }

                    String s = beforeXpath + i + "]/td/label/input";
                    System.out.println(procurementRole);

                    clickLink = beforeXpath + i + "]/td/label/input";
                    memeberCount++;

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));
                    driver.findElement(By.xpath(clickLink)).click();

                }
            } 
            else 
            {
                break;
            }

        }

        submitButton(driver, "//button[1]", wait);

        rows = driver.findElements(By.xpath("//*[@id='members']/tbody/tr"));

        beforeXpath = "//*[@id='members']/tbody/tr[";
        afterXpath = "]/td[2]";
        roleNameXpath = "]/td[1]";
        for (int i = 1; i <= rows.size(); i++) 
        {
            procurementRole = driver.findElement(By.xpath(beforeXpath + i + roleNameXpath)).getText();

            if (procurementRole.equalsIgnoreCase(hopaName)) 
            {
                
                System.out.println(procurementRole);

                clickLink = "//*[@id='cmbMemRole" + (i - 1) + "']";

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));
                selectDropdown(driver, wait, clickLink, 0);

            } 
            else 
            {
                System.out.println(procurementRole);

                clickLink = "//*[@id='cmbMemRole" + (i - 1) + "']";

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));
                selectDropdown(driver, wait, clickLink, 2);
            }
        }

        submitButton(driver, "//*[@id='btnSubmit']", wait);

        //Process file in Workflow
        submitButton(driver, "//a[contains(@href,'CreateWorkflow.jsp')]", wait);
        submitButton(driver, "//a[contains(@href,'FileProcessing.jsp')]", wait);

        String editorFramePath = "//iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";
        chkEditor(driver, wait, editorFramePath);

        String dropDownPath = "//*[@id=\"txtAction\"]";

        selectDropdown(driver, wait, dropDownPath, 1);
        submitButton(driver, "//*[@id='tbnAdd']", wait);

                //Evaluation Process
        submitButton(driver, "//a[contains(@href,'EvalComm.jsp')]", wait);
        submitButton(driver, "//a[contains(@href,'CommFormation.jsp')]", wait);
        driver.findElement(By.xpath("//*[@id='txtcommitteeName']")).sendKeys("TEC");
        submitButton(driver, "//*[@id='addmem']", wait);

        Boolean tecMemberDone = false;
        tocMemberCount = 0;
        rows = driver.findElements(By.xpath("//*[@id='pe1']/table/tbody/tr"));
        //allRows = table.findElements(By.tagName("tr")); 

        beforeXpath = "//*[@id='pe1']/table/tbody/tr[";
        afterXpath = "]/td[4]";
        roleNameXpath = "]/td[2]";
        String paName = "";

        clickLink = "";
        memeberCount = 0;

        for (int i = 2; i <= rows.size(); i++) 
        {
            procurementRole = driver.findElement(By.xpath(beforeXpath + i + afterXpath)).getText();

            if (memeberCount < 3) {
                if (procurementRole.equalsIgnoreCase("PA") || procurementRole.equalsIgnoreCase("TEC")) 
                {
                    if (procurementRole.equalsIgnoreCase("PA")) 
                    {
                        paName = driver.findElement(By.xpath(beforeXpath + i + roleNameXpath)).getText();
                    }
                    if (procurementRole.equalsIgnoreCase("TEC")) 
                    {
                        tocMemberCount++;
                    }
                    String s = beforeXpath + i + "]/td/label/input";
                    System.out.println(procurementRole);

                    clickLink = beforeXpath + i + "]/td/label/input";
                    memeberCount++;

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));
                    driver.findElement(By.xpath(clickLink)).click();
                }
            } 
            else 
            {
                break;
            }
        }

        submitButton(driver, "//button[1]", wait);

        rows = driver.findElements(By.xpath("//*[@id='members']/tbody/tr"));

        beforeXpath = "//*[@id='members']/tbody/tr[";
        afterXpath = "]/td[2]";
        roleNameXpath = "]/td[1]";
        for (int i = 1; i <= rows.size(); i++) 
        {
            procurementRole = driver.findElement(By.xpath(beforeXpath + i + roleNameXpath)).getText();

            if (procurementRole.equalsIgnoreCase(paName)) 
            {
                //String s = beforeXpath+i+"]/td/label/input";
                System.out.println(procurementRole);

                clickLink = "//*[@id='cmbMemRole" + (i - 1) + "']";

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));
                selectDropdown(driver, wait, clickLink, 0);

            } 
            else if (i == 2) 
            {
                System.out.println(procurementRole);

                clickLink = "//*[@id='cmbMemRole" + (i - 1) + "']";

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));
                selectDropdown(driver, wait, clickLink, 1);
            } 
            else if (i == 3) 
            {
                System.out.println(procurementRole);

                clickLink = "//*[@id='cmbMemRole" + (i - 1) + "']";

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(clickLink)));
                selectDropdown(driver, wait, clickLink, 2);
            }
        }

        submitButton(driver, "//*[@id='btnSubmit']", wait);

                //Process file in Workflow
        //submitButton(driver, "//a[contains(@href,'CommFormation.jsp')]", wait);
        submitButton(driver, "//a[contains(@href,'CreateWorkflow.jsp')]", wait);
        submitButton(driver, "//a[contains(@href,'FileProcessing.jsp')]", wait);

        editorFramePath = "//iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";
        chkEditor(driver, wait, editorFramePath);

        dropDownPath = "//*[@id=\"txtAction\"]";

        selectDropdown(driver, wait, dropDownPath, 1);
        submitButton(driver, "//*[@id='tbnAdd']", wait);

                //Whole Workflow
        submitButton(driver, "//a[contains(@href,'TenderDocPrep.jsp')]", wait);
        submitButton(driver, "//a[contains(@href,'CreateGrandSummary.jsp')]", wait);
        //*[@id="saveoredit"]
        submitButton(driver, "//*[@id='saveoredit']", wait);

        //a[contains(@href,'TenderDocPrep.jsp')]
        submitButton(driver, "//a[contains(@href,'TenderDocPrep.jsp')]", wait);

        submitButton(driver, "//a[contains(@href,'Notice.jsp')]", wait);

        submitButton(driver, "//a[contains(@href,'PEEncHash.jsp')]", wait);

        driver.findElement(By.xpath("//a[contains(@href,'Logout.jsp')]")).click();

    }
}
