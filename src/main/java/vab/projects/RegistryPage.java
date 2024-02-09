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
    private List<WebElement> hobbies;
    private WebElement genderMale;
    private WebElement genderFemale;
    private WebElement genderOther;
    private WebElement hobbySports;
    private WebElement hobbyReading;
    private WebElement hobbyMusic;


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
        this.genderMale = wd.findElement(By.xpath("//form//input[@type='radio' and @value='Male']"));
        this.genderFemale = wd.findElement(By.xpath("//form//input[@type='radio' and @value='Female']"));
        this.genderOther = wd.findElement(By.xpath("//form//input[@type='radio' and @value='Other']"));
        this.hobbySports = wd.findElement(By.id("hobbies-checkbox-1"));
        this.hobbyReading = wd.findElement(By.id("hobbies-checkbox-2"));
        this.hobbyMusic = wd.findElement(By.id("hobbies-checkbox-3"));
        this.hobbies = wd.findElements(By.xpath("//form//input[@type='checkbox']"));
    }

    public WebElement getFirstNameAsElement() {
        return this.firstName;
    }
    public void fillFirstName(String name) {
        this.firstName.sendKeys(name,Keys.TAB);
    }
    public String getFirstNameValue() {
        return this.firstName.getAttribute("value");
    }
    public void clearFirstName(){this.firstName.clear();}

    public void fillLastName(String name) {
        this.lastName.sendKeys(name);
    }
    public String getLastNameValue() {
        return this.lastName.getAttribute("value");
    }
    public void clearLastName(){this.lastName.clear();}

    public void fillEmail(String email) {
        this.email.sendKeys(email);
    }
    public String getEmailValue() {
        return this.email.getAttribute("value");
    }
    public WebElement getEmailAsElement() {
        return this.email;
    }
    public void clearEmail(){this.email.clear();}

    public void setGender(String gender) {
        this.getGenderAsElement(gender).sendKeys(" ");
    }
    public String getGenderValue() {
       WebElement el = findSelectedGenderAsElement();
       if (el != null) return el.getAttribute("value");
       else return "Not selected";
    }

    public WebElement findSelectedGenderAsElement() {
        WebElement el = null;
        if (this.genderMale.isSelected()) el = this.genderMale;
        else if (this.genderFemale.isSelected()) el = this.genderFemale;
        else if (this.genderOther.isSelected()) el = this.genderOther;
        return el;
    }
    public WebElement getGenderAsElement (String gender) {
        return switch (gender) {
            case "Male" -> this.genderMale;
            case "Female" -> this.genderFemale;
            default -> this.genderOther;
        };
    }

    public void fillMobileNumber(String phoneNumber){
        this.mobileNumber.sendKeys(phoneNumber);
    }
    public String getMobileValue() {
        return this.mobileNumber.getAttribute("value");
    }
    public void clearMobileNumber(){this.mobileNumber.clear();}

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
            String[] hobbiesList = hobbies.split(", ");
            for (String el: hobbiesList) {
             WebElement cb = wd.findElement(By.xpath("//label[text()='" + el + "']/preceding::input[1]"));
             cb.sendKeys(Keys.SPACE);
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
    public WebElement getHobbyAsElement(String hobby){
        return switch (hobby) {
            case "Music" -> this.hobbyMusic;
            case "Sports" -> this.hobbySports;
            case "Reading" -> this.hobbyReading;
            default -> null;
        };
    }
    public void clearHobbies(){
            for (WebElement cb:this.hobbies)
                if (cb.isSelected())
                   cb.sendKeys(Keys.SPACE);
    }

    public void setSubjects(String subjects) {
        if (!subjects.equals("")) {
            String[] subjectsList = subjects.split(", ");
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
    public void clearSubjects() {
        WebElement button = wd.findElement(By.xpath("//div[@id='subjectsContainer']//div[@class='subjects-auto-complete__indicators css-1wy0on6']"));
        if (button.isDisplayed())
            button.click();
    }

    public void loadPicture(String pictName)  {
        Path path;
        try {
            path = Paths.get(Objects.requireNonNull(RegistryPage.class.getResource("/" + pictName)).toURI());
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
    public void clearPicture() {
        this.selectPicture.clear();
    }

    public void fillCurrentAddress(String address) {
        this.currentAddress.sendKeys(address,Keys.TAB);
    }
    public String getCurrentAddressValue () {
        return this.currentAddress.getAttribute("value");
    }
    public void clearCurrentAddress() {
        this.currentAddress.clear();
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
    public WebElement getSubmitButtonAsElement(){
        return this.submitButton;
    }
}
