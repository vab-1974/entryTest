import org.junit.*;
import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import vab.projects.RegistryPage;
import vab.projects.ResultWindow;
import vab.projects.UtilityClass;

public class RegistryPageTest extends UtilityClass {

    //Группы тестов
    public interface BeforeSubmit { }

    public interface AfterSubmit { }

    /* ************************************************************************ */
    /* *********************    Before SubmitTests    ************************* */
    /* ************************************************************************ */

    @Category(BeforeSubmit.class)
    @FixMethodOrder(MethodSorters.NAME_ASCENDING)
    @RunWith(Parameterized.class)
    public static class BeforeSubmitTests {
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
            wd.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            expWait = new WebDriverWait(wd, Duration.ofSeconds(3));
            wd.get(myURL);
            wd.manage().window().maximize();
            registryPage = new RegistryPage(wd);
        }

        @AfterClass
        public static void tearDown() {
            wd.quit();
        }

        public BeforeSubmitTests(String fn, String ln, String mail, String gender, String phone, String db, String subj,
                                 String hobby, String address, String picture, String state, String city) {
            this.firstName = fn;
            this.lastName = ln;
            this.email = mail;
            this.gender = gender;
            this.mobileNumber = phone;
            this.dateOfBirth = db;
            this.subjects = subj;
            this.hobbies = hobby;
            this.currentAddress = address;
            this.picture = picture;
            this.state = state;
            this.city = city;
        }

        @Parameterized.Parameters()
        public static Iterable<Object[]> dataForTest() {
            int numOfRunsTest = 5;                         //Количество повторов теста
            ds = getDataSets(numOfRunsTest);
            return Arrays.asList(ds);
        }
        @Test public void A_testFirstName() {
            registryPage.clearFirstName();
            registryPage.fillFirstName(this.firstName);
            Assert.assertEquals("Содержимое поля FirstName регформы не совпадает с вводившимся:", this.firstName, registryPage.getFirstNameValue());
        }
        @Test public void B_testLastName() {
            registryPage.clearLastName();
            registryPage.fillLastName(this.lastName);
            Assert.assertEquals("Содержимое поля LastName регформы не совпадает с вводившимся:", this.lastName, registryPage.getLastNameValue());
        }
        @Test public void C_testEmail() {
            registryPage.clearEmail();
            registryPage.fillEmail(this.email);
            Assert.assertEquals("Содержимое поля Email регформы не совпадает с вводившимся:", this.email, registryPage.getEmailValue());
        }
        @Test public void D_testGender() {
            registryPage.setGender(this.gender);
            Assert.assertEquals("Содержимое поля Gender регформы не совпадает с вводившимся:", this.gender, registryPage.getGenderValue());
        }
        @Test public void E_testMobile() {
            registryPage.clearMobileNumber();
            registryPage.fillMobileNumber(this.mobileNumber);
            Assert.assertEquals("Содержимое поля Mobile регформы не совпадает с вводившимся:", this.mobileNumber, registryPage.getMobileValue());
        }
        @Test public void F_testDateOfBirth() {
            registryPage.fillDateOfBirth(this.dateOfBirth);
            String expected = "%s %s %s".formatted(this.dateOfBirth.substring(0, 2), getMonthName(this.dateOfBirth).substring(0, 3), this.dateOfBirth.substring(6));
            Assert.assertEquals("Содержимое поля DateOfBirth регформы не совпадает с вводившимся:", expected, registryPage.getDateOfBirthValue());
        }
        @Test public void G_testSubjects() {
            registryPage.clearSubjects();
            registryPage.setSubjects(this.subjects);
            Assert.assertEquals("Содержимое поля Subjects регформы не совпадает с вводившимся:", this.subjects, registryPage.getSubjectsValue());
        }
        @Test public void H_testHobbies() {
            registryPage.clearHobbies();
            registryPage.setHobbies(this.hobbies);
            HashSet<String> expected = new HashSet<>(); //Сеты использованы для исключения генерации исключенийв том случае,
            HashSet<String> actual = new HashSet<>();  // когда порядок следования элементов не совпадает
            actual.addAll(Arrays.asList(registryPage.getHobbiesValue().split(", ")));
            expected.addAll(Arrays.asList(this.hobbies.split(", ")));
            Assert.assertEquals("Содержимое поля Hobby регформы не совпадает с вводившимся:", expected, actual);
        }
        @Test public void I_testCurrentAdress() {
            registryPage.clearCurrentAddress();
            registryPage.fillCurrentAddress(this.currentAddress);
            Assert.assertEquals("Содержимое поля Current Address регформы не совпадает с вводившимся:", this.currentAddress, registryPage.getCurrentAddressValue());
        }
        @Test public void J_testPicture() {
            registryPage.clearPicture();
            registryPage.loadPicture(this.picture);
            Assert.assertEquals("Содержимое поля Picture регформы не совпадает с вводившимся:", this.picture, registryPage.getPictureValue());
        }
        @Test public void K_testState() {
            registryPage.fillState(this.state);
        }
        @Test public void L_testCity() {
            registryPage.fillCity(this.city);
        }
    }

    /* ************************************************************************ */
    /* *********************     AfterSubmitTests     ************************* */
    /* ************************************************************************ */

    @RunWith(Parameterized.class)
    @Category(AfterSubmit.class)
    public static class AfterSubmitTests{
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

        @AfterClass
        public static void tearDown() {
            wd.quit();
        }

        @Parameterized.Parameters()
        public static Iterable<Object[]> dataForTest() {
            int numOfRunsTest = 10;                         //Количество повторов теста
            ds = getDataSets(numOfRunsTest);
            return Arrays.asList(ds);
        }

        public AfterSubmitTests(String fn, String ln, String mail, String gender, String phone, String db, String subj,
                             String hobby, String address, String picture, String state, String city) {
            this.firstName = fn;
            this.lastName = ln;
            this.email = mail;
            this.gender = gender;
            this.mobileNumber = phone;
            this.dateOfBirth = db;
            this.subjects = subj;
            this.hobbies = hobby;
            this.currentAddress = address;
            this.picture = picture;
            this.state = state;
            this.city = city;
        }

        @Before
        public void fillRegistyForm() {
            if (registryPage.getFirstNameValue().equals(this.firstName)) return;
            if (resultWin != null) resultWin.clickClose();
            registryPage.getFirstNameAsElement().click();
            registryPage.fillFirstName(this.firstName);
            expWait.until(ExpectedConditions.attributeContains(registryPage.getFirstNameAsElement(), "value", this.firstName));
            registryPage.fillLastName(this.lastName);
            registryPage.fillEmail(this.email);
            registryPage.setGender(this.gender);
            expWait.until(ExpectedConditions.elementToBeSelected(registryPage.getGenderAsElement(this.gender)));
            registryPage.fillMobileNumber(this.mobileNumber);
            registryPage.fillDateOfBirth(this.dateOfBirth);
            registryPage.setSubjects(this.subjects);
            registryPage.setHobbies(this.hobbies);
            registryPage.fillCurrentAddress(this.currentAddress);
            registryPage.loadPicture(this.picture);
            registryPage.fillState(this.state);
            registryPage.fillCity(this.city);
            expWait.until(new Function<WebDriver,Boolean> (){
                                @Override
                                public Boolean apply(WebDriver driver) {
                                    return registryPage.getSubmitButtonAsElement().isEnabled();}
            });
            registryPage.clickSubmit();
            resultWin = new ResultWindow(wd);
        }
        @After
        public void closeResultWin(){

//            resultWin.clickClose();
        }

        @Test public void testFirstName() {
            Assert.assertEquals("Неожиданный заголовок всплывающего окна: ", "Thanks for submitting the form", resultWin.getHeader());
        }
        @Test public void testLastName() {
            Assert.assertEquals("Содержимое поля Student Name всплывающего окна не соответствует заданному:", this.firstName + " " + this.lastName, resultWin.getValueByLabel("Student Name"));
        }
        @Test public void testEmail() {
            Assert.assertEquals("Содержимое поля Student Email всплывающего окна не соответствует заданному:", this.email, resultWin.getValueByLabel("Student Email"));
        }
        @Test public void testGender() {
            Assert.assertEquals("Содержимое поля Gender всплывающего окна не соответствует заданному:", this.gender, resultWin.getValueByLabel("Gender"));
        }
        @Test public void testMobile() {
            Assert.assertEquals("Содержимое поля Mobile всплывающего окна не соответствует заданному:", this.mobileNumber, resultWin.getValueByLabel("Mobile"));
        }
        @Test public void testDateOfBirth() {
            String expected = "%s %s,%s".formatted(this.dateOfBirth.substring(0, 2), getMonthName(this.dateOfBirth), this.dateOfBirth.substring(6));
            Assert.assertEquals("Содержимое поля DateOfBirth всплывающего окна не соответствует заданному:", expected, resultWin.getValueByLabel("Date of Birth"));
        }
        @Test public void testSubjects() {
            Assert.assertEquals("Содержимое поля Subjects Name всплывающего окна не соответствует заданному:", this.subjects, resultWin.getValueByLabel("Subjects"));
        }
        @Test public void testHobbies() {
            HashSet<String> expected = new HashSet<>();
            HashSet<String> actual = new HashSet<>();
            actual.addAll(Arrays.asList(resultWin.getValueByLabel("Hobbies").split(", ")));
            expected.addAll(Arrays.asList(this.hobbies.split(", ")));
            Assert.assertEquals("Содержимое поля Hobbies всплывающего окна не соответствует заданному:", expected, actual);
        }
        @Test public void testPicture() {
            Assert.assertEquals("Содержимое поля Picture всплывающего окна не соответствует заданному:", this.picture, resultWin.getValueByLabel("Picture"));
        }
        @Test public void testCurrentAddress() {
            Assert.assertEquals("Содержимое поля Address всплывающего окна не соответствует заданному:", this.currentAddress, resultWin.getValueByLabel("Address"));
        }
        @Test public void testStateCity() {
            Assert.assertEquals("Содержимое поля State and City всплывающего окна не соответствует заданному:", registryPage.getStateCityValue(), resultWin.getValueByLabel("State and City"));
        }
    }

    /* ************************************************************************ */
    /* *********************           SUITES         ************************* */
    /* ************************************************************************ */

    @RunWith(Categories.class)
    @Categories.IncludeCategory(BeforeSubmit.class)
    @Suite.SuiteClasses(BeforeSubmitTests.class)
    public static class InputIntoFieldsTesting {
        @Test
        public void runAll() {}
    }

    @RunWith(Categories.class)
    @Categories.IncludeCategory(AfterSubmit.class)
    @Suite.SuiteClasses(AfterSubmitTests.class)
    public static class ValuesInResultWinTesting {
        @Test
        public void runAll() {}
    }
}