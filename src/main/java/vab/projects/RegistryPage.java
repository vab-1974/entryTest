package vab.projects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Arrays;
public class RegistryPage {
    private WebDriver wd;
    private WebElement firstName;
    private WebElement lastName;
    private WebElement email;
    private WebElement mobileNumber;
    private WebElement dateOfBirth;
    private WebElement subjects;
    private WebElement selectPicture;
    private WebElement currentAddress;
    private WebElement state;
    private WebElement city;
    private WebElement submitButton;

    public RegistryPage(WebDriver driver) {
        this.wd = driver;
        searchElements();
    }
    private void searchElements(){
        this.firstName = wd.findElement(By.id("firstName"));
        this.lastName = wd.findElement(By.id("lastName"));
        this.email = wd.findElement(By.id("userEmail"));
        this.mobileNumber = wd.findElement(By.id("userNumber"));
        this.dateOfBirth  = wd.findElement(By.id("dateOfBirthInput"));
        this.subjects = wd.findElement(By.id("subjectsInput"));
        this.selectPicture = wd.findElement(By.id("uploadPicture"));
        this.currentAddress = wd.findElement(By.id("currentAddress"));
        this.state = wd.findElement(By.cssSelector("div#state"));
        this.city = wd.findElement(By.cssSelector("div#city"));
        this.submitButton = wd.findElement(By.id("submit"));
    }

    public void fillFirstName(String name) {
        this.firstName.sendKeys(name);
    }
    public String getFirstNameValue() {
        return this.firstName.getAttribute("value");
    }
    public void fillLastName(String name) {
        this.lastName.sendKeys(name);
    }
    public String getLastNameValue() {
        return this.lastName.getAttribute("value");
    }
    public void fillEmail(String email) {
        this.email.sendKeys(email);
    }
    public String getEmailValue() {
        return this.email.getAttribute("value");
    }
    public void setGender(String gender) {
        WebElement el = wd.findElement(By.xpath("//input [@value='"+gender+"']"));
        el.sendKeys(" ");
    }

    public String getGenderValue() {
        List<WebElement> rb = wd.findElements(By.xpath("//input[contains(@id,'gender-radio')]"));
        boolean res;
        for (WebElement el:rb) {
            res = el.isSelected();
            if (res)
                return el.getAttribute("value");
        }
        return "Not selected";
    }
    public void fillMobileNumber(String phoneNumber){
        this.mobileNumber.sendKeys(phoneNumber);
    }

    public String getMobileValue() {
        return this.mobileNumber.getAttribute("value");
    }
    public void fillDateOfBirth (String dateOfBirth) {
        List<String> months = Arrays.asList("January", "February","March","April","May","June","July",
                "August","September","October","November","December");
        Pattern mask = Pattern.compile("^\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d$");
        Matcher matcher = mask.matcher(dateOfBirth);
        if (matcher.find()) {
            int numberOfDay = Integer.parseInt(dateOfBirth.substring(0,2));
            int numberOfMonth = Integer.parseInt(dateOfBirth.substring(3,5));
            int numberOfYear = Integer.parseInt(dateOfBirth.substring(6));
            this.dateOfBirth.click();
            this.dateOfBirth.sendKeys(Keys.TAB);
            WebElement prevMonth = wd.findElement(By.xpath("//button[text()='Previous Month']"));
            WebElement nextMonth = wd.findElement(By.xpath("//button[text()='Next Month']"));
            WebElement currentMonthYear = wd.findElement(By.xpath("//div[@class='react-datepicker__current-month react-datepicker__current-month--hasYearDropdown react-datepicker__current-month--hasMonthDropdown']"));
            String currentMonth = currentMonthYear.getText();
            currentMonth=currentMonth.substring(0,currentMonth.length()-5);
            int monthDiff = months.indexOf(currentMonth)-numberOfMonth+1;
            for (int i=0;i<Math.abs(monthDiff);i++)
                if (monthDiff > 0)
                    prevMonth.click();
                else
                    nextMonth.click();
            currentMonth = currentMonthYear.getText();
            String currentYear = currentMonth.substring(currentMonth.length()-4);
            int yearDiff = (Integer.parseInt(currentYear)-numberOfYear)*12;
            for (int i=0;i<Math.abs(yearDiff);i++)
                if (yearDiff > 0)
                    prevMonth.click();
                else
                    nextMonth.click();
            List<WebElement> day = wd.findElements(By.xpath("//div[@class='react-datepicker__week']/div[text()='"+numberOfDay+"']"));
            if (day.size()==1)
                day.get(0).click();
            else
            if (day.size()==2 && numberOfDay<15)
                day.get(0).click();
            else
            if (day.size()==2)
                day.get(1).click();
            else
                this.dateOfBirth.sendKeys(Keys.ESCAPE);
        }
    }
    public String getDateOfBirthValue() {
        return this.dateOfBirth.getAttribute("value");
    }
    public void setHobbies(String hobbies) {
        if (!hobbies.equals("")) {
            List<String > hobbiesList = Arrays.asList(hobbies.split(", "));
            for (String el: hobbiesList) {
             WebElement cb = wd.findElement(By.xpath("//label[text()='" + el + "']"));
             cb.click();
            }
        }
    }
    public String getHobbiesValue() {
        String result ="";
        List<WebElement> checkBoxes = wd.findElements(By.cssSelector("input[id^='hobbies']"));
        for (WebElement el: checkBoxes)
        {
            if (el.isSelected()) {
                String cbID = el.getAttribute("id");
                WebElement label = wd.findElement(By.xpath("//label[@for='" + cbID + "']"));
                result = result.concat(label.getText()+", ");
            }
        }
        if (result.endsWith(", "))
            result=result.substring(0,result.length()-2);

        return result;
    }
    public void setSubjects(String subjects) {
        if (!subjects.equals("")) {
            List<String > subjectsList = Arrays.asList(subjects.split(", "));
            for (String subj: subjectsList) {
                this.subjects.sendKeys(subj);
                this.subjects.sendKeys(Keys.TAB);
            }
        }
    }
    public String getSubjectsValue() {
        List<WebElement> subj = wd.findElements(By.xpath("//div[@id='subjectsContainer']//div[@class='css-12jo7m5 subjects-auto-complete__multi-value__label']"));
        String result ="";
        for (WebElement el:subj)
            if (!Objects.equals(el.getText(), ""))
                result = "%s%s, ".formatted(result, el.getText());
        if (result.endsWith(", "))
            result=result.substring(0,result.length()-2);
        return result;
    }

    public void loadPicture(String pictName)  {
        Path path;
        try {
            path = Paths.get(RegistryPage.class.getResource("/"+pictName).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
       this.selectPicture.sendKeys(path.toString());
    }
    public String getPictureValue()  {
       String result = this.selectPicture.getAttribute("value");
       result= result.substring(result.lastIndexOf('\\')+1);
       return result;
    }

    public void fillCurrentAddress(String address) {
        this.currentAddress.sendKeys(address,Keys.TAB);
    }
    public String getCurrentAddressValue () {
        return this.currentAddress.getAttribute("value");
    }
    public void fillState (String numKeys) {
        int numClicks = Integer.parseInt(numKeys);
        this.state.click();
        WebElement el = wd.switchTo().activeElement();
        for (int i=0; i<numClicks;i++)
            el.sendKeys(Keys.ARROW_DOWN);
        el.sendKeys(Keys.SPACE);
    }
    public void fillCity (String numKeys) {
        int numClicks = Integer.parseInt(numKeys);
        this.city.click();
        WebElement el = wd.switchTo().activeElement();
        for (int i=0; i<numClicks;i++)
            el.sendKeys(Keys.ARROW_DOWN);
        el.sendKeys(Keys.SPACE);
    }
    public String getStateCityValue() {
        WebElement state = this.state.findElement(By.xpath(".//div[@class=' css-1uccc91-singleValue']"));
        WebElement city = this.city.findElement(By.xpath(".//div[@class=' css-1uccc91-singleValue']"));
        return state.getText() +" "+city.getText();
    }
    public void clickSubmit() {
        this.submitButton.sendKeys(Keys.RETURN);
    }

}
