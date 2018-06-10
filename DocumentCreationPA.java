/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goodsmultiplelot;

import static goodsmultiplelot.SeleniumFuction.FindElement;
import static goodsmultiplelot.SeleniumFuction.chkEditor;
import static goodsmultiplelot.SeleniumFuction.clickTenderPopUp;
import static goodsmultiplelot.SeleniumFuction.docFiilUp;
import static goodsmultiplelot.SeleniumFuction.dropDownMenuLink;
import static goodsmultiplelot.SeleniumFuction.grabUrlAppId;
import static goodsmultiplelot.SeleniumFuction.grabUrlTenderId;
import static goodsmultiplelot.SeleniumFuction.selectCheckBox;
import static goodsmultiplelot.SeleniumFuction.selectDateByJs;
import static goodsmultiplelot.SeleniumFuction.submitButton;
import static goodsmultiplelot.SeleniumFuction.getDate;
import static goodsmultiplelot.SeleniumFuction.getDate;
import static goodsmultiplelot.SeleniumFuction.getDate;
import static goodsmultiplelot.SeleniumFuction.printUrl;
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
 * @author Nitish Ranjan Bhowmik
 */
public class DocumentCreationPA 
{
    public static void documentCreationPA(Actions builder, WebDriver driver, int ii, String currentUrl, WebDriverWait wait, String tenderId) throws InterruptedException 
    {
        submitButton(driver, "//a[contains(@href,'TenderDocPrep.jsp')]", wait);

        String formTenderID = "";
        String beforeActionLinkID = "";
        String afterActionLinkID = "";
        String fromDashBoardLinkID = "";
        String beforeAppIDXpath = "//table[";
        String AfterAppIDXpath = "]/tbody/tr/th[contains(text(),'Form Name 2')]";

        String genearateXpath = "";

        //By by;
        Boolean flag = false;
        Boolean discountFrom = false;
        
        By by;

        List<WebElement> allRows = driver.findElements(By.xpath("//table"));
        String createFormWorkFlow = "";

        int docCount = 0;

        for (int i = 1; i <= allRows.size(); i++) 
        {
            if (docCount < 5) 
            {
                String ss = beforeAppIDXpath + i + AfterAppIDXpath;
                by = By.xpath(beforeAppIDXpath + i + AfterAppIDXpath);
                flag = FindElement(driver, by, 1);
                if (flag == true) 
                {
                    formTenderID = driver.findElement(By.xpath(beforeAppIDXpath + i + AfterAppIDXpath)).getText();
                    createFormWorkFlow = driver.findElement(By.xpath(beforeAppIDXpath + i + AfterAppIDXpath)).getText();
                    if (formTenderID.equalsIgnoreCase("Form Name 2")) 
                    {
                        docCount++;
                        beforeActionLinkID = beforeAppIDXpath + i + "]/tbody/tr[";
                        afterActionLinkID = "]/td[3]/a[contains(@href,'TenderTableDashboard.jsp')]";
                        for (int j = 1; j <= 5; j++) 
                        {
                            fromDashBoardLinkID = beforeActionLinkID + j + afterActionLinkID;
                            by = By.xpath(fromDashBoardLinkID);
                            flag = FindElement(driver, by, 1);
                            if (flag == true) 
                            {
                                submitButton(driver, fromDashBoardLinkID, wait);
                                printUrl(driver);
                                submitButton(driver, "//a[contains(text(),'Fill up the Tables')]", wait);
                                printUrl(driver);

                                genearateXpath = "//*[@id='frmTableCreation']/table[2]/tbody/tr/td[contains(text(),'Discount Form')]";
                                by = By.xpath(genearateXpath);
                                discountFrom = FindElement(driver, by, 1);

                                if (discountFrom == true) 
                                {
                                    submitButton(driver, "//*[@id='sucolumnbBtnCreateEdit']", wait);
                                    driver.switchTo().alert().accept();
                                    submitButton(driver, "//a[contains(text(),'Tender Document')]", wait);
                                } 
                                else 
                                {
                                    submitButton(driver, "//a[contains(text(),'Add Row')]", wait);
                                    //submitButton(driver, "//a[contains(text(),'Form Dashboard')]", wait);
                                    docFiilUp(driver, wait);
                                    submitButton(driver, "//*[@id='sucolumnbBtnCreateEdit']", wait);
                                    driver.switchTo().alert().accept();

                                    //submitButton(driver, "//a[contains(text(),'Form Dashboard')]", wait);
                                    submitButton(driver, "//a[contains(text(),'Tender Document')]", wait);
                                    printUrl(driver);
                                }
                            }
                        }
                    }
                }
            }
        }

        beforeAppIDXpath = "//table[";
        AfterAppIDXpath = "]/tbody/tr/td/a";

        String createIndex = "";

        allRows = driver.findElements(By.xpath("//table"));
        docCount = 0;

        for (int i = 1; i <= allRows.size(); i++) 
        {
            if (docCount < 5) 
            {
                createIndex = beforeAppIDXpath + i + AfterAppIDXpath;
                by = By.xpath(beforeAppIDXpath + i + AfterAppIDXpath);

                flag = FindElement(driver, by, 1);
                if (flag == true) 
                {
                    createFormWorkFlow = driver.findElement(By.xpath(createIndex)).getText();
                    if (createFormWorkFlow.equalsIgnoreCase("Create")) 
                    {
                        docCount++;
                        driver.findElement(By.xpath("//table/tbody/tr/td/a[contains(text(),'Create')]")).click();
                        submitButton(driver, "//*[@id='saveoredit']", wait);
                        submitButton(driver, "//a[contains(@href,'TenderDocPrep.jsp')]", wait);
                    }
                }
            } 
            else 
            {
                break;
            }
        }
    }

}
