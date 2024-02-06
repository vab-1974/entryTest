import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import static java.lang.Thread.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import vab.projects.RegistryPage;
import vab.projects.ResultWindow;
import vab.projects.UtilityClass;

@RunWith(Parameterized.class)
public class RegistryPageTest extends UtilityClass {
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String mobileNumber;
    private String dateOfBirth;
    private String subjects;
    private String hobbies;
    private String currentAddress;
    private String picture;
    private String state;
    private String city;

    @BeforeClass
    public static void setup() {
        wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        expWait = new WebDriverWait(wd, Duration.ofSeconds(5));
        wd.get(myURL);
        wd.manage().window().maximize();
        registryPage = new RegistryPage(wd);
    }
    @Before
    public void freeze() {
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public static void tearDown() {
        wd.quit();
    }

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    // Конструктор параметризатор тестового класса
    public RegistryPageTest (String fn, String ln, String mail, String gender, String phone,
                             String db, String subj, String hobby,String address, String picture, String state,String city) {
        this.firstName=fn;
        this.lastName=ln;
        this.email=mail;
        this.gender=gender;
        this.mobileNumber=phone;
        this.dateOfBirth=db;
        this.subjects=subj;
        this.hobbies=hobby;
        this.currentAddress=address;
        this.picture=picture;
        this.state=state;
        this.city=city;
        fillRegistryForm();
    }
    //Функция-источник набора тестовых параметров
    @Parameterized.Parameters()
    public static Iterable<Object[]> dataForTest() {
        int  numOfRunsTest = 5;                         //Количество повторов теста
        ds = getDataSets(numOfRunsTest);
        return Arrays.asList(ds);
    }
    @Test
    public void RunAllTests(){
        testBeforeSubmit();
        expWait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
        registryPage.clickSubmit();
        resultWin = new ResultWindow(wd);
        expWait.until(ExpectedConditions.elementToBeClickable(By.id("closeLargeModal")));
        testAfterSubmit();
        resultWin.clickClose();
    }
    public void fillRegistryForm() {
        registryPage.getFirstName().click();
        registryPage.fillFirstName(this.firstName);
        expWait.until(ExpectedConditions.attributeContains(registryPage.getFirstName(), "value", this.firstName));
        registryPage.fillLastName(this.lastName);
        registryPage.fillEmail(this.email);
        registryPage.setGender(this.gender);
        registryPage.fillMobileNumber(this.mobileNumber);
        registryPage.fillDateOfBirth(this.dateOfBirth);
        registryPage.setSubjects(this.subjects);
        registryPage.setHobbies(this.hobbies);
        registryPage.fillCurrentAddress(this.currentAddress);
        registryPage.loadPicture(this.picture);
        registryPage.fillState(this.state);
        registryPage.fillCity(this.city);
    }

    public void testBeforeSubmit() {
        try {
            Assert.assertEquals("Содержимое поля FirstName регформы не совпадает с вводившимся:", this.firstName, registryPage.getFirstNameValue());
        } catch (AssertionError e) {
            collector.addError(e);
        }
        try {
            Assert.assertEquals("Содержимое поля LastName регформы не совпадает с вводившимся:", this.lastName, registryPage.getLastNameValue());
        } catch (AssertionError e) {
            collector.addError(e);
        }
        try {
            Assert.assertEquals("Содержимое поля Email регформы не совпадает с вводившимся:", this.email, registryPage.getEmailValue());
        } catch (AssertionError e) {
            collector.addError(e);
        }
        try {
            Assert.assertEquals("Содержимое поля Gender регформы не совпадает с вводившимся:", this.gender, registryPage.getGenderValue());
        } catch (AssertionError e) {
            collector.addError(e);
        }
        try {
            Assert.assertEquals("Содержимое поля Mobile регформы не совпадает с вводившимся:", this.mobileNumber, registryPage.getMobileValue());
        } catch (AssertionError e) {
            collector.addError(e);
        }
        try {
            String expected = "%s %s %s".formatted(this.dateOfBirth.substring(0, 2), getMonthName(this.dateOfBirth).substring(0, 3), this.dateOfBirth.substring(6));
            Assert.assertEquals("Содержимое поля DateOfBirth регформы не совпадает с вводившимся:", expected, registryPage.getDateOfBirthValue());
        } catch (AssertionError e) {
            collector.addError(e);
        }
        try {
            Assert.assertEquals("Содержимое поля Subjects регформы не совпадает с вводившимся:", this.subjects, registryPage.getSubjectsValue());
        } catch (AssertionError e) {
            collector.addError(e);
        }
        try {
            HashSet<String> expected = new HashSet<>(); //Сеты использованы для исключения генерации исключенийв том случае,
            HashSet<String> actual = new HashSet<>();  // когда порядок следования элементов не совпадает
            actual.addAll(Arrays.asList(registryPage.getHobbiesValue().split(", ")));
            expected.addAll(Arrays.asList(this.hobbies.split(", ")));
            Assert.assertEquals("Содержимое поля Hobby регформы не совпадает с вводившимся:", expected, actual);
        } catch (AssertionError e) {
            collector.addError(e);
        }
        try {
            Assert.assertEquals("Содержимое поля Current Address регформы не совпадает с вводившимся:", this.currentAddress, registryPage.getCurrentAddressValue());
        } catch (AssertionError e) {
            collector.addError(e);
        }
        try {
            Assert.assertEquals("Содержимое поля Picture регформы не совпадает с вводившимся:", this.picture, registryPage.getPictureValue());
        } catch (AssertionError e) {
            collector.addError(e);
        }
    }

    public void testAfterSubmit() {
        try { Assert.assertEquals("Неожиданный заголовок всплывающего окна: ","Thanks for submitting the form", resultWin.getHeader()); }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Содержимое поля Student Name всплывающего окна не соответствует заданному:",this.firstName+" "+this.lastName, resultWin.getValueByLabel("Student Name")); }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Содержимое поля Student Email всплывающего окна не соответствует заданному:",this.email,resultWin.getValueByLabel("Student Email")); }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Содержимое поля Gender всплывающего окна не соответствует заданному:",this.gender,resultWin.getValueByLabel("Gender")); }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Содержимое поля Mobile всплывающего окна не соответствует заданному:",this.mobileNumber,resultWin.getValueByLabel("Mobile")); }
        catch (AssertionError e) {collector.addError(e);}
        try {
            String expected = "%s %s,%s".formatted(this.dateOfBirth.substring(0,2),getMonthName(this.dateOfBirth),this.dateOfBirth.substring(6));
            Assert.assertEquals("Содержимое поля DateOfBirth всплывающего окна не соответствует заданному:",expected,resultWin.getValueByLabel("Date of Birth")); }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Содержимое поля Subjects Name всплывающего окна не соответствует заданному:",this.subjects,resultWin.getValueByLabel("Subjects")); }
        catch (AssertionError e) {collector.addError(e);}
        try {
            HashSet<String> expected = new HashSet<>();
            HashSet<String> actual = new HashSet<>();
            actual.addAll(Arrays.asList (resultWin.getValueByLabel("Hobbies").split(", ")));
            expected.addAll(Arrays.asList ( this.hobbies.split(", ")));
            Assert.assertEquals("Содержимое поля Hobbies всплывающего окна не соответствует заданному:", expected, actual);
        }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Содержимое поля Picture всплывающего окна не соответствует заданному:", this.picture,resultWin.getValueByLabel("Picture")); }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Содержимое поля Address всплывающего окна не соответствует заданному:", this.currentAddress,resultWin.getValueByLabel("Address")); }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Содержимое поля State and City всплывающего окна не соответствует заданному:", registryPage.getStateCityValue(),resultWin.getValueByLabel("State and City")); }
        catch (AssertionError e) {collector.addError(e);}
    }
}