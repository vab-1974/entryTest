import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import vab.projects.RegistryPage;
import vab.projects.ResultWindow;
import vab.projects.UtilityClass;


public class RegistryPageTest extends UtilityClass { //Класс контейнер для тестовых классов.
/*
* Класс RegistryPageTest предоставляет вложенным классам доступ к вспомогательным функциям
* и статическим объектам
*/


    public static class fillingFieldsTests {
    /*
    * Класс fillingFieldsTest просто заполняет поля регистрационной формы и проверяет что в поле
    * формы записано то же значение, что и передано  для записи. Валидность занесенного значения
    * с точки зрения формы не проверяется.
    */

        @DataProvider(name="data-provider")
        public Object[][] genExample() {
            return getDataSets(2);
        }

        @BeforeClass
        public void setup() {
            System.setProperty("webdriver.chrome.driver", PathToWebDriver);
            wd = new ChromeDriver();
            wd.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            expWait = new WebDriverWait(wd, Duration.ofSeconds(3));
            wd.get(myURL);
            wd.manage().window().maximize();
            registryPage = new RegistryPage(wd);
        }

        @AfterClass
        public void tearDown() {
            wd.quit();
        }

        @Feature(value = "Запись значения в поле")
        @Test(dataProvider = "data-provider")
        public void testFirstName(String[] content) {
            String fname = content[0];
            registryPage.clearFirstName();
            registryPage.fillFirstName(fname);
            Assert.assertEquals(registryPage.getFirstNameValue(), fname, "Содержимое поля FirstName регформы не совпадает с вводившимся:");
        }

        @Feature(value = "Запись значения в поле")
        @Test(dataProvider = "data-provider")
        public void testLastName(String[] content) {
            String lname = content[1];
            registryPage.clearLastName();
            registryPage.fillLastName(lname);
            Assert.assertEquals(registryPage.getLastNameValue(), lname, "Содержимое поля LastName регформы не совпадает с вводившимся:");
        }

        @Feature(value = "Запись значения в поле")
        @Test(dataProvider = "data-provider")
        public void testEmail(String[] content) {
            String email = content[2];
            registryPage.clearEmail();
            registryPage.fillEmail(email);
            Assert.assertEquals(registryPage.getEmailValue(), email, "Содержимое поля Email регформы не совпадает с вводившимся:");
        }

        @Feature(value = "Запись значения в поле")
        @Test(dataProvider = "data-provider")
        public void testGender(String[] content) {
            String gender = content[3];
            registryPage.setGender(gender);
            Assert.assertEquals(registryPage.getGenderValue(), gender, "Содержимое поля Gender регформы не совпадает с вводившимся:");
        }

        @Feature(value = "Запись значения в поле")
        @Test(dataProvider = "data-provider")
        public void testMobile(String[] content) {
            String mobileNumber = content[4];
            registryPage.clearMobileNumber();
            registryPage.fillMobileNumber(mobileNumber);
            Assert.assertEquals(registryPage.getMobileValue(), mobileNumber, "Содержимое поля Mobile регформы не совпадает с вводившимся:");
        }

        @Feature(value = "Запись значения в поле")
        @Test(dataProvider = "data-provider")
        public void testDateOfBirth(String[] content) {
            String dateOfBirth = content[5];
            registryPage.fillDateOfBirth(dateOfBirth);
            String expected = "%s %s %s".formatted(dateOfBirth.substring(0, 2), getMonthName(dateOfBirth).substring(0, 3), dateOfBirth.substring(6));
            Assert.assertEquals(registryPage.getDateOfBirthValue(), expected, "Содержимое поля DateOfBirth регформы не совпадает с вводившимся:");
        }

        @Feature(value = "Запись значения в поле")
        @Test(dataProvider = "data-provider")
        public void testSubjects(String[] content) {
            String subjects = content[6];
            registryPage.clearSubjects();
            registryPage.setSubjects(subjects);
            Assert.assertEquals(registryPage.getSubjectsValue(), subjects, "Содержимое поля Subjects регформы не совпадает с вводившимся:");
        }

        @Feature(value = "Запись значения в поле")
        @Test(dataProvider = "data-provider")
        public void testHobbies(String[] content) {
            String hobbies=content[7];
            registryPage.clearHobbies();
            registryPage.setHobbies(hobbies);
            HashSet<String> expected = new HashSet<>(); //Сеты использованы для исключения генерации исключенийв том случае,
            HashSet<String> actual = new HashSet<>();  // когда порядок следования элементов не совпадает
            actual.addAll(Arrays.asList(registryPage.getHobbiesValue().split(", ")));
            expected.addAll(Arrays.asList(hobbies.split(", ")));
            Assert.assertEquals(actual, expected, "Содержимое поля Hobby регформы не совпадает с вводившимся:");
        }

        @Feature(value = "Запись значения в поле")
        @Test(dataProvider = "data-provider")
        public void testCurrentAddress(String[] content) {
            String currentAddress = content[8];
            registryPage.clearCurrentAddress();
            registryPage.fillCurrentAddress(currentAddress);
            Assert.assertEquals(registryPage.getCurrentAddressValue(), currentAddress, "Содержимое поля Current Address регформы не совпадает с вводившимся:");
        }

        @Feature(value = "Запись значения в поле")
        @Test(dataProvider = "data-provider")
        public void testPicture(String[] content) {
            String picture = content[9];
            registryPage.clearPicture();
            registryPage.loadPicture(picture);
            Assert.assertEquals(registryPage.getPictureValue(), picture, "Содержимое поля Picture регформы не совпадает с вводившимся:");
        }

        @Feature(value = "Запись значения в поле")
        @Test(dataProvider = "data-provider", priority = 1)
        public void testState(String[] content) {
            String state= content[10];
            registryPage.fillState(state);
        }

        @Feature(value = "Запись значения в поле")
        @Test(dataProvider = "data-provider", priority = 2)
        public void testCity(String[] content) {
            String city=content[11];
            registryPage.fillCity(city);
        }
    }

    public static class AfterSubmitTests {
        /*
         * Класс AfterSubmitTest производит проверку соотвествия данных, кторые передавались
         * в поля регистрационной формы и данных отображенных в окне результата после нажатия Submit
         */
        @Parameters()
        public AfterSubmitTests(){}

        @BeforeTest
        public void setup() {
            System.setProperty("webdriver.chrome.driver", PathToWebDriver);
            wd = new ChromeDriver();
            wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            expWait = new WebDriverWait(wd, Duration.ofSeconds(5));
            wd.get(myURL);
            wd.manage().window().maximize();
            registryPage = new RegistryPage(wd);
        }

        @AfterClass
        public void tearDown() {
            resultWin.clickClose();
            wd.quit();
        }

        @BeforeClass
        public void fillRegistryForm() {
            Object[] dataSet = genDataSet();
            registryPage.getFirstNameAsElement().click();
            registryPage.fillFirstName((String) dataSet[0]);
            registryPage.fillLastName((String) dataSet[1]);
            registryPage.fillEmail((String) dataSet[2]);
            registryPage.setGender((String) dataSet[3]);
            registryPage.fillMobileNumber((String) dataSet[4]);
            registryPage.fillDateOfBirth((String) dataSet[5]);
            registryPage.setSubjects((String) dataSet[6]);
            registryPage.setHobbies((String) dataSet[7]);
            registryPage.fillCurrentAddress((String) dataSet[8]);
            registryPage.loadPicture((String) dataSet[9]);
            registryPage.fillState((String) dataSet[10]);
            registryPage.fillCity((String) dataSet[11]);
            expWait.until(driver -> registryPage.getSubmitButtonAsElement().isEnabled());
            registryPage.clickSubmit();
            resultWin = new ResultWindow(wd);
        }

        @Feature(value = "Соответствие значению во всплывающем окне")
        @Test
        public void testFirstName() {
            Assert.assertEquals(resultWin.getHeader(), "Thanks for submitting the form",
                    "Неожиданный заголовок всплывающего окна: ");
        }

        @Feature(value = "Соответствие значению во всплывающем окне")
        @Test
        public void testLastName() {
            Assert.assertEquals(resultWin.getValueByLabel("Student Name"),
                    registryPage.getFirstNameValue() + " " + registryPage.getLastNameValue(),
                    "Содержимое поля Student Name всплывающего окна не соответствует заданному:");
        }

        @Feature(value = "Соответствие значению во всплывающем окне")
        @Test
        public void testEmail() {
            Assert.assertEquals(resultWin.getValueByLabel("Student Email"),
                    registryPage.getEmailValue(),
                    "Содержимое поля Student Email всплывающего окна не соответствует заданному:");
        }

        @Feature(value = "Соответствие значению во всплывающем окне")
        @Test
        public void testGender() {
            Assert.assertEquals(resultWin.getValueByLabel("Gender"),
                    registryPage.getGenderValue(),
                    "Содержимое поля Gender всплывающего окна не соответствует заданному:");
        }

        @Feature(value = "Соответствие значению во всплывающем окне")
        @Test
        public void testMobile() {
            Assert.assertEquals(resultWin.getValueByLabel("Mobile"),
                    registryPage.getMobileValue(),
                    "Содержимое поля Mobile всплывающего окна не соответствует заданному:");
        }

        @Feature(value = "Соответствие значению во всплывающем окне")
        @Test
        public void testDateOfBirth() {
            String actual = resultWin.getValueByLabel("Date of Birth");
            actual = "%s %s".formatted(actual.substring(0, 6), actual.substring(actual.length() - 4));
            Assert.assertEquals(actual, registryPage.getDateOfBirthValue(),
                    "Содержимое поля DateOfBirth всплывающего окна не соответствует заданному:");
        }

        @Feature(value = "Соответствие значению во всплывающем окне")
        @Test
        public void testSubjects() {
            Assert.assertEquals(resultWin.getValueByLabel("Subjects"),
                    registryPage.getSubjectsValue(),
                    "Содержимое поля Subjects Name всплывающего окна не соответствует заданному:");
        }

        @Feature(value = "Соответствие значению во всплывающем окне")
        @Test
        public void testHobbies() {
            HashSet<String> expected = new HashSet<>();
            HashSet<String> actual = new HashSet<>();
            actual.addAll(Arrays.asList(resultWin.getValueByLabel("Hobbies").split(", ")));
            expected.addAll(Arrays.asList(registryPage.getHobbiesValue().split(", ")));
            Assert.assertEquals(actual, expected, "Содержимое поля Hobbies всплывающего окна не соответствует заданному:");
        }

        @Feature(value = "Соответствие значению во всплывающем окне")
        @Test
        public void testPicture() {
            Assert.assertEquals(resultWin.getValueByLabel("Picture"),
                    registryPage.getPictureValue(),
                    "Содержимое поля Picture всплывающего окна не соответствует заданному:");
        }

        @Feature(value = "Соответствие значению во всплывающем окне")
        @Test
        public void testCurrentAddress() {
            Assert.assertEquals(resultWin.getValueByLabel("Address"),
                    registryPage.getCurrentAddressValue(),
                    "Содержимое поля Address всплывающего окна не соответствует заданному:");
        }

        @Feature(value = "Соответствие значению во всплывающем окне")
        @Test
        public void testStateCity() {
            Assert.assertEquals(resultWin.getValueByLabel("State and City"),
                    registryPage.getStateCityValue(),
                    "Содержимое поля State and City всплывающего окна не соответствует заданным:");

        }
    }

    public class FormValidationsTests {
        /*
         * Класс FormValidationsTests производит проверку реакции формы, на данные помещаемые в поля
         * регистрационной формы, анализируя наличие псевдоклассов :valid/:invalid у соотвествующих элементов
         */

        @BeforeClass
        public static void setup() {
            System.setProperty("webdriver.chrome.driver", PathToWebDriver);
            wd = new ChromeDriver();
            wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            expWait = new WebDriverWait(wd, Duration.ofSeconds(5));
            wd.get(myURL);
            wd.manage().window().maximize();
            registryPage = new RegistryPage(wd);
            registryPage.clickSubmit(); //переводим форму в состояние проверки заполнения
        }

        @AfterClass
        public static void tearDown() {
            wd.close();
        }

        @Feature(value = "Валидация формой введенных данных")
        @Test
        public void testFirstNamePositiveCase() {
            registryPage.clearFirstName();
            String fn =genLetDigString(1,10);
            registryPage.fillFirstName(fn);
            List<WebElement> list = wd.findElements(By.cssSelector("input:valid"));
            boolean found = list.contains(registryPage.getFirstNameAsElement());
            Assert.assertTrue(found, "%s %s".formatted("Элемент email не найден в списке валидных! В First Name вводили: ", fn));
        }

        @Feature(value = "Валидация формой введенных данных")
        @Test
        public void testFirstNameNegativeCase() {
            registryPage.clearFirstName();
            List<WebElement> list = wd.findElements(By.cssSelector("input:invalid"));
            boolean found = list.contains(registryPage.getFirstNameAsElement());
            Assert.assertTrue(found, "Элемент не найден в списке невалидных! В First Name вводили пустую строку.");
        }

        @Feature(value = "Валидация формой введенных данных")
        @Test
        public void testEmailPositiveCase() {
            registryPage.clearEmail();
            String email ="%s@%s.%s".formatted(genLetDigString(1,10), genLetDigString(1,10), genLetterString(2,5));
            registryPage.fillEmail(email);
            List<WebElement> list = wd.findElements(By.cssSelector("input:valid"));
            boolean found = list.contains(registryPage.getEmailAsElement());
            Assert.assertTrue(found, "%s %s".formatted("Элемент email не найден в списке валидных! В email вводили: ", email));
        }

        @Feature(value = "Валидация формой введенных данных")
        @Test
        public void testEmailNegativeCase() {
            registryPage.clearEmail();
            String email ="%s. %s".formatted(genLetDigString(1,10), genLetDigString(2,5));
            registryPage.fillEmail(email);
            List<WebElement> list = wd.findElements(By.cssSelector("input:invalid"));
            boolean found = list.contains(registryPage.getEmailAsElement());
            Assert.assertTrue(found,  "%s %s".formatted("Элемент email не найден в списке невалидных! В email вводили: ", email));
        }
    }
}
