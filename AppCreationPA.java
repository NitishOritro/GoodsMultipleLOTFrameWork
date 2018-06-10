/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goodsmultiplelot;

import static goodsmultiplelot.SeleniumFuction.grabUrlAppId;
import static goodsmultiplelot.SeleniumFuction.selectDateByJs;
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
public class AppCreationPA 
{
    public String appCreationPA(WebDriver driver, int ii, String currentUrl, WebDriverWait wait, 
            String paUserID, String paPassword) 
    {
        WebElement email = driver.findElement(By.id("txtEmailId"));
        email.clear();

        WebElement password = driver.findElement(By.name("password"));
        password.clear();

        email.sendKeys(paUserID);
        password.sendKeys(paPassword);

        WebElement login = driver.findElement(By.id("btnLogin"));
        login.submit();

        String menuPath = "//*[@id='headTabApp']";

        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(menuPath)));  // locating the main menu

        WebElement menu = driver.findElement(By.xpath(menuPath));
        Actions builder = new Actions(driver);
        builder.moveToElement(menu).build().perform();

        String dropDownMenuPath = "//ul/li/a[contains(text(),'Create APP')]";

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dropDownMenuPath)));

        WebElement menuOption = driver.findElement(By.xpath(dropDownMenuPath));
        builder.moveToElement(menuOption).click().build().perform();

        //Thread.sleep(1800);
        String budgetType = "//*[@id='cmbBudgetType']";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(budgetType)));
        Select budgetTypeSelect = new Select(driver.findElement(By.xpath(budgetType)));
        budgetTypeSelect.selectByIndex(1);

        driver.findElement(By.xpath("//*[@id='ActivityName']")).sendKeys("TEST_Activitity" + ii);
        driver.findElement(By.xpath("//*[@id='txtAppCode']")).sendKeys("Letter Ref. No" + ii);

        String aPPType = "//*[@id='cmbdepoplanWork']";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(aPPType)));
        Select aPPTypeSelect = new Select(driver.findElement(By.xpath(aPPType)));
        aPPTypeSelect.selectByIndex(3);

        login = driver.findElement(By.id("buttonNext"));
        login.submit();

        String procurementCategory = "//*[@id='cmbProcureNature']";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(procurementCategory)));
        Select procurementCategorySelect = new Select(driver.findElement(By.xpath(procurementCategory)));
        procurementCategorySelect.selectByIndex(1);

        driver.findElement(By.xpath("//*[@id='txtPackageNo']")).sendKeys("TEST_Package No" + ii);
        driver.findElement(By.xpath("//*[@id='txtaPackageDesc']")).sendKeys("TEST_Package_Description" + ii);

        for (int i = 1; i <= 4; i++) 
        {
            driver.findElement(By.xpath("//*[@id='linkAddLot']")).click();
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;

        //Add 5 packages to the lot 
        
        String lotDescription[] = {"", "Computer", "Router", "Server", "Monitor", "Generator"};

        for (int i = 1; i <= 5; i++) 
        {
            driver.findElement(By.xpath("//*[@id='txtLotNo_" + i + "']")).sendKeys(Integer.toString(i));
            driver.findElement(By.xpath("//*[@id='txtLotDesc_" + i + "']")).sendKeys(lotDescription[i]);
            driver.findElement(By.xpath("//*[@id='txtQuantity_" + i + "']")).sendKeys("10");
            driver.findElement(By.xpath("//*[@id='txtUnit_" + i + "']")).sendKeys("NOS");
            WebElement element = driver.findElement(By.xpath("//*[@id='txtEstimateCost_" + i + "']"));
            driver.findElement(By.xpath("//*[@id='txtEstimateCost_" + i + "']")).sendKeys("10");
            js.executeScript("$('#txtEstimateCost_" + i + "').trigger('change')");
        }

        String category = "//*[@id='hrefCPV']";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(category)));
        driver.findElement(By.xpath(category)).click();

        String parentWindow = driver.getWindowHandle();
        System.out.println("before " + driver.getTitle());
        Set<String> s1 = driver.getWindowHandles();

        Iterator<String> i1 = s1.iterator();

        while (i1.hasNext()) 
        {
            String jsTreePath = "//li[@id='170']/a";
            String buttonPath = "//*[@id='btnGetCheckedNode']";
            String childWindow = i1.next();
            if (!parentWindow.equalsIgnoreCase(childWindow)) 
            {
                driver.switchTo().window(childWindow);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(jsTreePath)));
                driver.findElement(By.xpath(jsTreePath)).click();
                System.out.println("before " + driver.getTitle());
                //Thread.sleep(1000);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(buttonPath)));
                driver.findElement(By.xpath(buttonPath)).click();
                //driver.close();
            }
        }

        driver.switchTo().window(parentWindow);
        System.out.println("before " + driver.getTitle());

        String procurementType = "//*[@id='cmbProcureType']";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(procurementType)));
        Select procurementTypeSelect = new Select(driver.findElement(By.xpath(procurementType)));
        procurementTypeSelect.selectByIndex(1);

        String procurementMethod = "//*[@id='cmbProcureMethod']";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(procurementMethod)));
        Select procurementMethodSelect = new Select(driver.findElement(By.xpath(procurementMethod)));
        procurementMethodSelect.selectByIndex(2);

        String sourceofFund = "//*[@id='cmbSourceOfFund']";
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(sourceofFund)));
        Select sourceofFundSelect = new Select(driver.findElement(By.xpath(sourceofFund)));
        sourceofFundSelect.selectByIndex(2);

        WebElement login1 = driver.findElement(By.xpath("//*[@id='btnNext']"));

        login1.click();

        String datePath = "//*[@id='txtRfqdtadvtift']";

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dt = new Date();

        Calendar cl = Calendar.getInstance();
        cl.setTime(dt);;
        cl.add(Calendar.DAY_OF_YEAR, 1);
        dt = cl.getTime();

        String str = df.format(dt);

        System.out.println("the date today is " + str);

        WebElement date = driver.findElement(By.id("txtRfqdtadvtift"));
        String dateVal = str;
        selectDateByJs(driver, date, dateVal);

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        //jse.executeScript("calculateTotalNoofDays();");
        js.executeScript("$('#txtRfqdtadvtift').trigger('blur')");

        driver.findElement(By.xpath("//*[@id='txtRfqexpdtopenNo']")).sendKeys("10");
        js.executeScript("$('#txtRfqexpdtopenNo').trigger('blur')");
        driver.findElement(By.xpath("//*[@id='txtRfqdtsubevaRptNo']")).sendKeys("10");
        js.executeScript("$('#txtRfqdtsubevaRptNo').trigger('blur')");
        //driver.findElement(By.xpath("//*[@id='txtRfqexpdtopenNo']")).sendKeys("10");
        js.executeScript("$('#txtRfqexpdtAppawdNo').trigger('blur')");
        //driver.findElement(By.xpath("//*[@id='txtRfqexpdtopenNo']")).sendKeys("10");
        js.executeScript("$('#txtRfqexpdtLtrIntAwdNo').trigger('blur')");
        driver.findElement(By.xpath("//*[@id='txtRfqdtIssNOANo']")).sendKeys("10");
        js.executeScript("$('#txtRfqdtIssNOANo').trigger('blur')");
        driver.findElement(By.xpath("//*[@id='txtRfqexpdtSignNo']")).sendKeys("10");
        js.executeScript("$('#txtRfqexpdtSignNo').trigger('blur')");

        login = driver.findElement(By.xpath("//*[@id='btnSave']"));

        login.click();

        String createWorkflowLink = "//a[contains(@href,'CreateWorkflow.jsp')]";

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(createWorkflowLink)));

        menuOption = driver.findElement(By.xpath(createWorkflowLink));
        builder.moveToElement(menuOption).click().build().perform();

        String fileProcessingLink = "//a[contains(@href,'FileProcessing.jsp')]";

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(fileProcessingLink)));

        menuOption = driver.findElement(By.xpath(fileProcessingLink));
        builder.moveToElement(menuOption).click().build().perform();

        String editorFramePath = "//iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";

        wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(editorFramePath)));

        WebElement editorFrame = driver.findElement(By.xpath(editorFramePath));

        driver.switchTo().frame(editorFrame);

        WebElement body = driver.findElement(By.tagName("body"));

        body.clear();
        body.sendKeys("some text");

        driver.switchTo().defaultContent();

        String actionPath = "//*[@id='txtAction']";
        wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(actionPath)));

        Select actionPathSelect = new Select(driver.findElement(By.xpath(actionPath)));
        actionPathSelect.selectByIndex(1);

        login = driver.findElement(By.xpath("//*[@id='tbnAdd']"));

        login.click();

        currentUrl = driver.getCurrentUrl();

        System.out.println(currentUrl);

        String appId = grabUrlAppId(currentUrl);
        System.out.println("Random " + ii);

        driver.findElement(By.xpath("//a[contains(@href,'Logout.jsp')]")).click();
        
        return appId;

    }

}
