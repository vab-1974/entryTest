package vab.projects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ResultWindow {
    private WebDriver wd;

    private WebElement headerElement;
    private List<WebElement> dataTableRows;
    public ResultWindow(WebDriver driver) {
        this.wd = driver;
        initElements();
    }

    public void initElements(){
        headerElement = wd.findElement(By.id("example-modal-sizes-title-lg"));
        dataTableRows = wd.findElements(By.xpath("//table[@class='table table-dark table-striped table-bordered table-hover']/tbody/tr"));
    }

    public String getHeader() {
        return headerElement.getText();
    }

    public String getValueByLabel (String param) {
        for (WebElement el:dataTableRows) {
            String s = el.findElement(By.xpath("./td[1]")).getText();
            if (s.contains(param))
               return el.findElement(By.xpath("./td[2]")).getText();
        }
        return "";
    }
}
