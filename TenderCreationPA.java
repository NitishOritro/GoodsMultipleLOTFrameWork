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
public class TenderCreationPA {

    public String tendCreationPA(Actions builder, WebDriver driver, int ii, String currentUrl,WebDriverWait wait ,
            String appId, String paUserID, String paPassword) throws InterruptedException 
    {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        
        WebElement email = driver.findElement(By.id("txtEmailId"));
        email.clear();

        WebElement password = driver.findElement(By.name("password"));
        password.clear();

        email.sendKeys("pauserrotdormowhs@gmail.com");
        password.sendKeys("egp12345");

        WebElement login = driver.findElement(By.id("btnLogin"));
        login.submit();

        String menuPath = "//*[@id='headTabApp']";
        String dropDownMenuPath = "//ul/li/a[contains(text(),'My APP')]";

        dropDownMenuLink(driver, wait, menuPath, dropDownMenuPath, builder);

        String jqueryGridPath = "//*[@id='list']";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(jqueryGridPath)));

        String aPPID = appId;
        String linkAppID = "";

        String dashboardLink = "";

        String beforeXpath = "//*[@id='list']/tbody/tr[";
        String afterXpath = "]/td/a";

        String beforeAppIDXpath = "//*[@id='list']/tbody/tr[";
        String AfterAppIDXpath = "]/td[2]";

        WebElement table = driver.findElement(By.id("list"));
        List<WebElement> allRows = table.findElements(By.tagName("tr"));

        for (int i = 2; i < allRows.size(); i++) {
            linkAppID = driver.findElement(By.xpath(beforeAppIDXpath + i + AfterAppIDXpath)).getText();

            if (linkAppID.equalsIgnoreCase(aPPID)) {
                String s = beforeAppIDXpath + i + AfterAppIDXpath;
                System.out.println(linkAppID);

                dashboardLink = beforeAppIDXpath + i + "]/td/a[contains(@href,'APPDashboard.jsp')]";

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dashboardLink)));
                driver.findElement(By.xpath(dashboardLink)).click();
                break;

            }
            //System.out.println(linkAppID);
        }

        submitButton(driver, "//a[contains(@href,'APPWorkflowView.jsp')]", wait);

                    //textarea[@id='txtremark']
        driver.findElement(By.xpath("//textarea[@id='txtremark']")).sendKeys("TEST_Package No");

        submitButton(driver, "//*[@id=\"btnsubmit\"]", wait);

        submitButton(driver, "//*[@id=\"resultTable\"]/tbody/tr[2]/td[8]/a[2]", wait);

        String confirmPath = "//*[@id='popup_ok']";

        clickTenderPopUp(driver, confirmPath);

        String url = driver.getCurrentUrl();

        String tenderId = grabUrlTenderId(url);

        driver.findElement(By.id("integrityPackcnk")).click();

        driver.findElement(By.xpath("//*[@id='txtinvitationRefNo']")).sendKeys("TestSelenium" + tenderId);

        String dateID = "txtpreQualCloseDate";

        String ClosingOpeningDate = getDate(driver, dateID, "CloseOpen");

        jse = (JavascriptExecutor) driver;
        jse.executeScript("$('#txtpreQualCloseDate').trigger('blur')");

        String PublicationDateAndTime = getDate(driver, "txttenderpublicationDate", "publication");
        jse.executeScript("$('#txtpreQualCloseDate').trigger('blur')");

        String LastDateAndTimeBidSecuritySubmission = getDate(driver, "txtlastDateTenderSub", "bidsecurity");
        jse.executeScript("$('#txtlastDateTenderSub').trigger('blur')");

        String TenderDocumentsellingdownloadinGDateTime = getDate(driver, "txttenderLastSellDate", "download");
        jse.executeScript("$('#txttenderLastSellDate').trigger('blur')");

        String getText = "";

        String editorFramePath = "//*[@id='cke_1_contents']/iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";
        chkEditor(driver, wait, editorFramePath, "Eligibility of Bidder/Consultant");

        editorFramePath = "//*[@id='cke_2_contents']/iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";
        chkEditor(driver, wait, editorFramePath, "Selenium Webdriver Test Tender: Brief Description of Goods <br>Multiple Lot</br> and Related Service :");

        String ContractStartDate = "";
        String ContractEndDate = "";

        for (int i = 0; i < 5; i++) 
        {
            //driver.findElement(By.id("chkbidSecDeclaration_"+i+"")).click();
            selectCheckBox(driver, wait, "//*[@id='chkbidSecDeclaration_" + i + "']");

            driver.findElement(By.xpath("//*[@id='locationlot_" + i + "']")).sendKeys("PA Office");
            driver.findElement(By.xpath("//*[@id='tenderSecurityAmount_" + i + "']")).sendKeys("1000");

            ContractStartDate = getDate(driver, "startTimeLotNo_" + i + "", 1);
            jse.executeScript("$('#startTimeLotNo_" + i + "').trigger('blur')");

            ContractEndDate = getDate(driver, "complTimeLotNo_" + i + "", 2);
            jse.executeScript("$('#complTimeLotNo_" + i + "').trigger('blur')");
            Thread.sleep(200);
        }

        submitButton(driver, "//*[@id='btnsubmit']", wait);

        By by = By.xpath("//*[@id='spantxtpreQualCloseDate']/div[@class='reqF_1']");

        Boolean elementID = FindElement(driver, by, 1);

        if (elementID == true) {
            getText = driver.findElement(By.xpath("//*[@id='spantxtpreQualCloseDate']/div[@class='reqF_1']")).getText();
        } else {
            by = By.xpath("//*[@id='demoClose']");

            elementID = FindElement(driver, by, 3);
            if (elementID == true) {
                getText = driver.findElement(By.xpath("//*[@id='demoClose']")).getText();
                if (getText.equalsIgnoreCase("")) {
                    getText = driver.findElement(By.xpath("//*[@id='holiClose']")).getText();
                }
            }
        }

        if (getText.equalsIgnoreCase("Holiday!") || getText.equalsIgnoreCase("Closing and Opening Date can not be weekend!") || getText.equalsIgnoreCase("Weekend!") || getText.equalsIgnoreCase("Closing and Opening Date can not be holiday!")) {
            ClosingOpeningDate = getDate(driver, dateID, 43, "CloseOpen", ClosingOpeningDate);

            jse.executeScript("$('#txtpreQualCloseDate').trigger('blur')");
            submitButton(driver, "//*[@id='btnsubmit']", wait);
        } else {
            submitButton(driver, "//*[@id='btnsubmit']", wait);
        }

        by = By.xpath("//*[@id='msgDeclaration_1']");

        elementID = FindElement(driver, by, 1);
        if (elementID == true) {
            getText = driver.findElement(By.xpath("//*[@id='msgDeclaration_1']")).getText();
            if (getText.equalsIgnoreCase("Please select Bid Declaration Type")) {
                selectCheckBox(driver, wait, "//*[@id='msgDeclaration_1']");
            }
        }

        driver.findElement(By.xpath("//*[@id='txttenderValidity']")).sendKeys("90");

        jse.executeScript("$('#txttenderValidity').trigger('blur')");
        driver.findElement(By.xpath("//*[@id='btnSubmit']")).click();
        
        return tenderId;
    }
    
    
    

}
