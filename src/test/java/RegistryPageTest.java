import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import static java.lang.Thread.*;
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
        wd.get(myURL);
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wd.manage().window().maximize();
        registryPage = new RegistryPage(wd);
 }
    @Rule
    public final ErrorCollector collector = new ErrorCollector();
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
    }
    @Parameterized.Parameters()
    public static Iterable<Object[]> dataForTest() {
        int  numOfRunsTest = 5; //Количество повторов теста
        ds = getDataSets(numOfRunsTest);
        return Arrays.asList(ds);
    }
    @Before
    public void freeze() {
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void test() throws InterruptedException {
        registryPage.fillFirstName(this.firstName); sleep(200);
        registryPage.fillLastName(this.lastName); sleep(200);
        registryPage.fillEmail(this.email); sleep(200);
        registryPage.setGender(this.gender); sleep(200);
        registryPage.fillMobileNumber(this.mobileNumber); sleep(200);
        registryPage.fillDateOfBirth(this.dateOfBirth); sleep(200);
        registryPage.setSubjects(this.subjects); sleep(200);
        registryPage.setHobbies(this.hobbies); sleep(200);
        registryPage.fillCurrentAddress(this.currentAddress); sleep(200);
        registryPage.loadPicture(this.picture); sleep(200);
        registryPage.fillState(this.state); sleep(200);
        registryPage.fillCity(this.city); sleep(200);
        try { Assert.assertEquals("Имя в регформе не совпадает с переданным значением:",this.firstName,registryPage.getFirstNameValue());}
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Фамилия в регформе не совпадает с переданным значением:",this.lastName,registryPage.getLastNameValue());}
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Email в регформе не совпадает с переданным значением:",this.email,registryPage.getEmailValue());}
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Пол в регформе не совпадает с переданным значением:",this.gender,registryPage.getGenderValue());}
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Телефон в регформе не совпадает с переданным значением:",this.mobileNumber,registryPage.getMobileValue());}
        catch (AssertionError e) {collector.addError(e);}
        try {
            String expected = "%s %s %s".formatted(this.dateOfBirth.substring(0,2),getMonthName(this.dateOfBirth).substring(0,3),this.dateOfBirth.substring(6));
            Assert.assertEquals("Дата рождения в регформе не совпадает с переданным значением:",expected,registryPage.getDateOfBirthValue());
        }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Темы в регформе не совпадают с переданным значением:",this.subjects,registryPage.getSubjectsValue());}
        catch (AssertionError e) {collector.addError(e);}
        try {
            HashSet<String> expected = new HashSet<>();
            HashSet<String> actual = new HashSet<>();
            actual.addAll(Arrays.asList ( registryPage.getHobbiesValue().split(", ")));
            expected.addAll(Arrays.asList ( this.hobbies.split(", ")));
            Assert.assertEquals("Хобби в регформе не совпадают с переданным значением:", expected, actual);
        }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Адрес в регформе не совпадает с переданным значением:",this.currentAddress,registryPage.getCurrentAddressValue());}
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Имя файла картинки в регформе не совпадает с переданным значением:",this.picture,registryPage.getPictureValue());}
        catch (AssertionError e) {collector.addError(e);}

        registryPage.clickSubmit();
        sleep(3000);

        resultWin = new ResultWindow(wd);
        sleep(500);
        try { Assert.assertEquals("Неожиданный заголовок окна результата","Thanks for submitting the form", resultWin.getHeader()); }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("ФИО в окне результата не соответствует заданному:",this.firstName+" "+this.lastName, resultWin.getValueByLabel("Student Name")); }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Email в окне результата не соответствует заданному:",this.email,resultWin.getValueByLabel("Student Email")); }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Пол в окне результата не соответствует заданнному:",this.gender,resultWin.getValueByLabel("Gender")); }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Телефон в окне результата не соответствует заданному:",this.mobileNumber,resultWin.getValueByLabel("Mobile")); }
        catch (AssertionError e) {collector.addError(e);}
        try {
            String expected = "%s %s,%s".formatted(this.dateOfBirth.substring(0,2),getMonthName(this.dateOfBirth),this.dateOfBirth.substring(6));
            Assert.assertEquals("Дата рождения в окне результата не соответствует заданной:",expected,resultWin.getValueByLabel("Date of Birth")); }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Темы в окне результата не соответствуют заданному:",this.subjects,resultWin.getValueByLabel("Subjects")); }
        catch (AssertionError e) {collector.addError(e);}
        try {
            HashSet<String> expected = new HashSet<>();
            HashSet<String> actual = new HashSet<>();
            actual.addAll(Arrays.asList (resultWin.getValueByLabel("Hobbies").split(", ")));
            expected.addAll(Arrays.asList ( this.hobbies.split(", ")));
            Assert.assertEquals("Хобби в окне результата не соответствуют переданным значениям:", expected, actual);
        }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Имя картинки в окне результата не соответствует заданному:", this.picture,resultWin.getValueByLabel("Picture")); }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Адрес в окне результата не соответствует заданному:", this.currentAddress,resultWin.getValueByLabel("Address")); }
        catch (AssertionError e) {collector.addError(e);}
        try { Assert.assertEquals("Страна и(или) город в окне результата не соответствуют заданному:", registryPage.getStateCityValue(),resultWin.getValueByLabel("State and City")); }
        catch (AssertionError e) {collector.addError(e);}

        resultWin.clickClose();
    }

    @AfterClass
    public static void tearDown() {
       wd.quit();
    }
}