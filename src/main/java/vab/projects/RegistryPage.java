package vab.projects;

import org.openqa.selenium.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
public class RegistryPage {
    public final String[] names = {"John","Bob","Dan","Sam","Jack","Tomas","Oliver","Will"};
    public final String[] lastnames = {"Smith","Braun","Baker","Jackson","Goldman","Gates","Wilson","Parkinson"};
    public final String[] subjectsList = {"Maths","English","Biology","Commerce","Hystory"};
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
    public void fillLastName(String name) {
        this.lastName.sendKeys(name);
    }
    public String getStudentNameValue() {
        return this.firstName.getAttribute("value") +" "+this.lastName.getAttribute("value");
    }

    public void fillEmail(String email) {
        this.email.sendKeys(email);
    }
    public String getEmailValue() {
        return this.email.getAttribute("value");
    }
    public void setRandomGender() {
        int genderNum = new Random().nextInt(3)+1;
        String condition = "gender-radio-" + genderNum;
        wd.findElement(By.xpath("//label [@for='"+condition+"']")).click();
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
    public void fillMobileNumber(){
        String phone = "8";
        for(int i=0;i<9;i++)
            phone=phone+ new Random().nextInt(10);
        this.mobileNumber.sendKeys(phone);
    }
    public void fillMobileNumber(String mobileNumber) {
        this.mobileNumber.sendKeys(mobileNumber);
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
    public void setRandomHobbies() {
        List<WebElement> checkBoxes = wd.findElements(By.cssSelector("label[for^='hobbies']"));
        for (WebElement el: checkBoxes)
        {
            if ((new Random().nextInt(100)) > 49) {
                el.click();
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
                result = result+label.getText()+", ";
            }
        }
        if (result.endsWith(", "))
            result=result.substring(0,result.length()-2);

        return result;
    }
    public void setSubjects() {
        int NumberOfSubject = new Random().nextInt (subjectsList.length);
        for (int i=0; i<NumberOfSubject;i++) {
            this.subjects.sendKeys(subjectsList[i]);
            this.subjects.sendKeys(Keys.TAB);
        }
    }
    public String getSubjectsValue() {
        List<WebElement> subj = wd.findElements(By.xpath("//div[@id='subjectsContainer']//div[@class='css-12jo7m5 subjects-auto-complete__multi-value__label']"));
        String result ="";
        for (WebElement el:subj)
            if (el.getText()!="")
                result = result + el.getText()+", ";
        if (result.endsWith(", "))
            result=result.substring(0,result.length()-2);
        return result;
    }

    public void loadPicture()  {
        Path path;
        try {
            path = Paths.get(RegistryPage.class.getResource("/MyRealFoto.jpg").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
       this.selectPicture.sendKeys(path.toString());
    }
    public String getPictureValue()  {
       return this.selectPicture.getAttribute("value");
    }

    public void fillCurrentAddress(String address) {
        this.currentAddress.sendKeys(address,Keys.TAB);
    }
    public String getCurrentAddressValue () {
        return this.currentAddress.getAttribute("value");
    }
    public void fillState () {
        this.state.click();
        WebElement el = wd.switchTo().activeElement();
        el.sendKeys(Keys.SPACE);
    }
    public void fillCity () {
        this.city.click();
        WebElement el = wd.switchTo().activeElement();
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
