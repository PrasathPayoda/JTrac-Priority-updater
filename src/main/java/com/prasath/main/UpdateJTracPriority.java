package com.prasath.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import java.util.List;

/**
 * @author prasath
 */
public class UpdateJTracPriority {
  public static void main(String[] args) throws InterruptedException {
    String priotityToChange = "3";
    String currentPriority = "2";

    String moduleName = "Account Management";//"Gift Path","Expense Manager";//"Deposits";//"Loan Module";//"Forex Card";//"Expense Manager";//"Efee-School Fee";//"Religious Offering";//"Passbook";//"Expense Manager";//"SafeGold";//"Bharath Bill Payments","Fast Tag";

    String baseURL = "https://uatgateway.federalbank.co.in:8811/jtrac/app/login";
    WebDriver webDriver = new ChromeDriver();
    webDriver.get(baseURL);

    ((ChromeDriver) webDriver).findElementById("loginName3").sendKeys("cdna");
    ((ChromeDriver) webDriver).findElementById("password12").sendKeys("federal");
    ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"form7\"]/table/tbody/tr[2]/td[3]/input").click();
    ((ChromeDriver) webDriver).findElementByXPath("//*[@id=\"dashboardRow53\"]/tr/td[7]/a").click();

    ((ChromeDriver) webDriver).findElementByXPath("/html/body/div[1]/table[1]/tbody/tr/td[4]/a").click();

    ((ChromeDriver) webDriver).findElementById("expression49").sendKeys("has values");

    String module = getModuleXPath(moduleName);
    clickElement((ChromeDriver) webDriver, module,"xpath");

    ((ChromeDriver) webDriver).findElementById("expression53").sendKeys("has values");
    String prioritySelect = "//*[@id=\"columns:9:fragParent:values_"+currentPriority+"\"]";
    clickElement((ChromeDriver) webDriver, prioritySelect,"xpath");

    ((ChromeDriver) webDriver).findElementById("expression41").sendKeys("has values");
    clickElement((ChromeDriver) webDriver, "//*[@id=\"columns:3:fragParent:values_1\"]","xpath");



    ((ChromeDriver) webDriver).findElementByName("search").click();
    List<WebElement> rows = null;
    do{
    WebElement tableElement = getWebElement(((ChromeDriver) webDriver),"/html/body/span/table[2]","xpath");
      rows=tableElement.findElements(By.tagName("tr"));
    for (WebElement rowElement:
      rows) {
      List<WebElement> tds = rowElement.findElements(By.tagName("td"));
      if (tds.size() >= 12) {
        String status = tds.get(2).getText();
        String assignedTo=tds.get(4).getText();
        String devStatus = tds.get(5).getText();
        String moduleText = tds.get(6).getText();
        String priority = tds.get(8).getText();
        String ETA = tds.get(9).getText();
        String devCategory = tds.get(10).getText();


        if(status.equalsIgnoreCase("Closed") || status.equalsIgnoreCase("Rejected")){
          break;
        }

        if (moduleName.equals(moduleText) && ("".equals(priority) || null == priority || !priotityToChange.equals(priority))) {
          System.out.println(tds.get(0).getText() + "\t" + devStatus + "\t" + moduleText + "\t" + priority);

          tds.get(0).click();

          getWebElement((ChromeDriver) webDriver, "//*[@id=\"form56\"]/table/tbody/tr/td[1]/table/tbody/tr[1]/td[2]/div/select", "xpath").sendKeys(devStatus);

          getWebElement((ChromeDriver) webDriver, "//*[@id=\"form56\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[2]/input", "xpath").sendKeys("-");
          if(ETA != null) {
            getWebElement((ChromeDriver) webDriver, "//*[@id=\"field6\"]", "xpath").sendKeys(ETA);
          }

          getWebElement((ChromeDriver) webDriver, "//*[@id=\"form56\"]/table/tbody/tr/td[1]/table/tbody/tr[3]/td[2]/div/select", "xpath").sendKeys(priotityToChange);
          getWebElement((ChromeDriver) webDriver, "//*[@id=\"form56\"]/table/tbody/tr/td[1]/table/tbody/tr[5]/td[2]/div/select", "xpath").sendKeys(devCategory);


          clickElement((ChromeDriver) webDriver, "//*[@id=\"status61\"]", "xpath").sendKeys(status);


          Thread.sleep(2000);
          clickElement((ChromeDriver) webDriver, "//*[@id=\"assignedTo76\"]", "xpath").sendKeys(assignedTo);

          getWebElement((ChromeDriver) webDriver, "//*[@id=\"form56\"]/table/tbody/tr/td[1]/table/tbody/tr[8]/td[2]/textarea", "xpath").sendKeys("Priority changed");

          clickElement((ChromeDriver) webDriver, "//*[@id=\"form56\"]/table/tbody/tr/td[1]/table/tbody/tr[9]/td[2]/input[1]", "xpath");
          clickElement((ChromeDriver) webDriver, "/html/body/table[1]/tbody/tr/td/a", "xpath");
          break;
        }
      }
    }
      }while(rows != null && rows.size() > 0);



    Thread.sleep(5000);
    webDriver.quit();

  }



  private static String getModuleXPath(String moduleName) {
    String modulePath=null;
    if(moduleName.equalsIgnoreCase("Fast Tag"))
        modulePath = "//*[@id=\"columns:7:fragParent:values_29\"]";
    else if(moduleName.equalsIgnoreCase("Bharath Bill Payments"))
      modulePath = "//*[@id=\"columns:7:fragParent:values_4\"]";
    else if(moduleName.equalsIgnoreCase("SafeGold"))
      modulePath = "//*[@id=\"columns:7:fragParent:values_33\"]";
    else if(moduleName.equalsIgnoreCase("Expense Manager"))
      modulePath = "//*[@id=\"columns:7:fragParent:values_32\"]";
    else if(moduleName.equalsIgnoreCase("Passbook"))
      modulePath = "//*[@id=\"columns:7:fragParent:values_14\"]";
    else if(moduleName.equalsIgnoreCase("Religious Offering"))
      modulePath = "//*[@id=\"columns:7:fragParent:values_24\"]";
    else if(moduleName.equalsIgnoreCase("Forex Card"))
      modulePath = "//*[@id=\"columns:7:fragParent:values_30\"]";
    else if(moduleName.equalsIgnoreCase("Efee-School Fee"))
      modulePath = "//*[@id=\"columns:7:fragParent:values_6\"]";
    else if(moduleName.equalsIgnoreCase("Loan Module"))
      modulePath = "//*[@id=\"columns:7:fragParent:values_11\"]";
    else if(moduleName.equalsIgnoreCase("Deposits"))
      modulePath = "//*[@id=\"columns:7:fragParent:values_7\"]";
    else if(moduleName.equalsIgnoreCase("Account Management"))
      modulePath = "//*[@id=\"columns:7:fragParent:values_10\"]";
    else if(moduleName.equalsIgnoreCase("Gift Path"))
      modulePath = "//*[@id=\"columns:7:fragParent:values_38\"]";

    return modulePath;
  }

  private static WebElement clickElement(ChromeDriver webDriver, String element,String findBy) throws InterruptedException {
    WebElement moduleSelectionElement = getWebElement(webDriver, element,findBy);
    moduleSelectionElement .click();
    return  moduleSelectionElement;
  }

  private static WebElement getWebElement(ChromeDriver webDriver, String element,String findBy) throws InterruptedException {
    WebElement moduleSelectionElement = null;
    do{
      try {
        if("xpath".equals(findBy))
          moduleSelectionElement = webDriver.findElementByXPath(element);
        else if("class".equals(findBy))
          moduleSelectionElement = webDriver.findElementByClassName(element);


      }catch(Exception e){
        System.out.println("Fetching element ==>"+element +" by "+findBy);
        Thread.sleep(1000);
      }
    }while( moduleSelectionElement == null );
    return moduleSelectionElement;
  }

}
