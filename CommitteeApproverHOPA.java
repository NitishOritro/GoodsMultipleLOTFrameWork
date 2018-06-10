/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goodsmultiplelot;

import static goodsmultiplelot.SeleniumFuction.FindElement;
import static goodsmultiplelot.SeleniumFuction.chkEditor;
import static goodsmultiplelot.SeleniumFuction.dropDownMenuLink;
import static goodsmultiplelot.SeleniumFuction.selectDropdown;
import static goodsmultiplelot.SeleniumFuction.submitButton;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Nitish
 */
public class CommitteeApproverHOPA 
{
    
    public static void committeeApproverHOPA(Actions builder, WebDriver driver, int ii, String currentUrl, WebDriverWait wait, String tenderId, 
            String hopaUserID, String hopaPassword) throws InterruptedException 
    {
        WebElement email = driver.findElement(By.id("txtEmailId"));
        email.clear();

        WebElement password = driver.findElement(By.name("password"));
        password.clear();

        email.sendKeys("hoparotdormowhs@gmail.com");
        password.sendKeys("egp12345");

        WebElement login = driver.findElement(By.id("btnLogin"));
        login.submit();

        String jqueryGridPath = "//*[@id='list']";

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(jqueryGridPath)));

        String ID = tenderId + " (Tender ID)";

        String fileProcessingLink = "";

        String beforeXpath = "//*[@id='list']/tbody/tr[";
        String afterXpath = "]/td/a";

        String beforeAppIDXpath = "//*[@id='list']/tbody/tr[";
        String AfterAppIDXpath = "]/td[4]";
        
        String editorFramePath = "";
        String dropDownPath = "";
        String button = "";
        String menuPath = "";
        String dropDownMenuPath = "";
        
        List<WebElement> allRows = driver.findElements(By.xpath("//*[@id='list']/tbody/tr"));
        String linkAppID = "";
        By by;

        Boolean rowCount = false;
        Boolean flag = false;

        for (int i = 1; i <= allRows.size(); i++) 
        {
            by = By.xpath(beforeAppIDXpath + i + AfterAppIDXpath);
            rowCount = FindElement(driver, by, 1);
            if (rowCount == true) 
            {
                linkAppID = driver.findElement(By.xpath(beforeAppIDXpath + i + AfterAppIDXpath)).getText();

                if (linkAppID.equalsIgnoreCase(ID)) 
                {
                    String s = beforeAppIDXpath + i + AfterAppIDXpath;
                    System.out.println(linkAppID);

                    fileProcessingLink = beforeAppIDXpath + i + "]/td/a[contains(@href,'FileProcessing.jsp')]";
                    by = By.xpath(fileProcessingLink);
                    flag = FindElement(driver, by, 1);

                    if (flag == true) 
                    {
                        driver.findElement(By.xpath(fileProcessingLink)).click();
                        editorFramePath = "//iframe[contains(@class, 'cke_wysiwyg_frame cke_reset')]";
                        chkEditor(driver, wait, editorFramePath);

                        dropDownPath = "//*[@id='txtAction']";

                        selectDropdown(driver, wait, dropDownPath, 2);

                        button = "//*[@id='tbnAdd']";

                        submitButton(driver, button);
                        menuPath = "//*[@id='headTabWorkFlow']";
                        dropDownMenuPath = "//ul/li/a[contains(text(),'Pending task')]";

                        dropDownMenuLink(driver, wait, menuPath, dropDownMenuPath, builder);

                        i = 0;
                        allRows = driver.findElements(By.xpath("//*[@id='list']/tbody/tr"));
                    }
                }
            }
        }
        driver.findElement(By.xpath("//a[contains(@href,'Logout.jsp')]")).click();
    }
    
}
