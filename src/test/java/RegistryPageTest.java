import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import vab.projects.RegistryPage;
import vab.projects.ResultWindow;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegistryPageTest {
    public static RegistryPage registryPage;
    public static ResultWindow resultWin;
    public static WebDriver wd;
    public static final List<String> subjectsList = Arrays.asList("Maths","English","Biology","Commerce","Hystory");

    @BeforeClass
    public static void setup() {
        wd = new ChromeDriver();
        wd.get("https://demoqa.com/automation-practice-form");
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wd.manage().window().maximize();
        registryPage = new RegistryPage(wd);
        fillRegistryForm();
 }
    public static void fillRegistryForm() {
        registryPage.fillFirstName("Иван");
        registryPage.fillLastName("Петров");
        registryPage.fillEmail("I.Petrov@pochta.net");
        registryPage.setRandomGender();
        registryPage.fillMobileNumber();
        registryPage.fillDateOfBirth("30.09.2020");
        registryPage.setSubjects(subjectsList);
        registryPage.setRandomHobbies();
        registryPage.fillCurrentAddress("432027, Russia, Ulyanovsk, Goncharova st., 1");
        registryPage.loadPicture();
        registryPage.fillState();
        registryPage.fillCity();
        registryPage.clickSubmit();
        resultWin = new ResultWindow(wd);
    }


    @Test ()
    public void testHeader(){
        Assert.assertEquals("Строки не равны: ","Thanks for submitting the form",resultWin.getHeader());
    }
    @Test
    public void testNameFilling() {
        Assert.assertEquals(registryPage.getStudentNameValue(), resultWin.getValueByLabel("Student Name"));
    }
     @Test
    public void testEmailFilling() {
        Assert.assertEquals(registryPage.getEmailValue(),resultWin.getValueByLabel("Student Email"));
    }
    @Test
    public void testGenderSetting() {
       Assert.assertEquals(registryPage.getGenderValue(),resultWin.getValueByLabel("Gender"));
    }
    @Test
    public void testMobileFilling() {
       Assert.assertEquals(registryPage.getMobileValue(),resultWin.getValueByLabel("Mobile"));
    }
    @Test
    public void testDateOfBirthFilling() {
       String s =resultWin.getValueByLabel("Date of Birth");
       s = s.substring(0,6)+ " " + s.substring(s.length()-4);
       Assert.assertEquals(registryPage.getDateOfBirthValue(),s);
    }
    @Test
    public void testSubjectsFilling() {
       Assert.assertEquals(registryPage.getSubjectsValue(),resultWin.getValueByLabel("Subjects"));
    }
    @Test
    public void testHobbiesFilling() {
       Assert.assertEquals(registryPage.getHobbiesValue(),resultWin.getValueByLabel("Hobbies"));
    }

    @Test
    public void testPictureLoading() {
       String s = registryPage.getPictureValue();
       s=s.substring(s.lastIndexOf("\\")+1);
       Assert.assertEquals(s,resultWin.getValueByLabel("Picture"));
    }

    @Test
    public void testCurrentAddressFilling() {
        Assert.assertEquals(registryPage.getCurrentAddressValue(),resultWin.getValueByLabel("Address"));
    }

    @Test
    public void testStateCityFilling() {
        Assert.assertEquals(registryPage.getStateCityValue(),resultWin.getValueByLabel("State and City"));
    }

    @AfterClass
    public static void tearDown() {
        wd.quit();
    }
}

