package vab.projects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.DecimalFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

public abstract class UtilityClass {
    public static String myURL="https://demoqa.com/automation-practice-form";
    public static RegistryPage registryPage;
    public static ResultWindow resultWin;
    public static WebDriver wd;
    public static Object[][] ds;
    public static Random random = new Random();
    public static WebDriverWait expWait;
    public static final List<String> subjectsList = Arrays.asList("Maths","English","Biology","Commerce","History");
    public static final List<String> hobbiesList = Arrays.asList("Sports","Reading","Music");
    public static final List<String> genderList = Arrays.asList("Male","Female","Other");
    public static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final String digits ="0123456789";

    public static String genString(int length, String symbolsSet) {
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++)
            sb.append(symbolsSet.charAt(random.nextInt(symbolsSet.length())));
        return sb.toString();
    }
    public static String genLetterString(int minLength, int maxLength) {
        if ((maxLength - minLength) >= 0)
                return genString(random.nextInt(maxLength-minLength+1)+minLength,letters);
        else return "";
    }
    public static String genDigitString(int minLength, int maxLength) {
        if ((maxLength - minLength) >= 0)
            return genString(random.nextInt(maxLength-minLength+1)+minLength,digits);
        else return "";
    }
    public static String genLetDigString (int minLength, int maxLength) {
        if ((maxLength - minLength) >= 0)
            return genString(random.nextInt(maxLength-minLength+1)+minLength,digits+letters);
        else return "";
    }
    public static String getRandomNumber (int min, int max) {
        DecimalFormat df = new DecimalFormat(("00"));
        return df.format(random.nextInt(max-min)+min);
    }
    public static String getOneOf (List<String> list) {
        return list.get(random.nextInt(list.size()));
    }
    public static String getManyOf (List<String> list) {
        Set<String> strings = new HashSet<>();
        int n = random.nextInt(list.size());
        for (int i=0;i<n;i++){
            strings.add(list.get(random.nextInt(list.size())));
        }
        String result = strings.toString();
        if (result.equals("[]"))
            result="";
        else result=result.substring(1,result.length()-1);
        return result;
    }
    public static String[] genTestDataSet() {
        String[] result = new String[12];
        result[0] = genLetDigString(1,10);
        result[1] = genLetDigString(1,10);
        result[2] = genLetDigString(1,10)+
                "@"+ genLetDigString(1,10)+
                "."+ genLetterString(2,5);
        result[3] = getOneOf(genderList);
        result[4] = genString(10,digits);
        result[5] = getRandomNumber(1,28)+"."+getRandomNumber(1,12)+"."+getRandomNumber(2020,2023);
        result[6] = getManyOf(subjectsList);
        result[7] = getManyOf(hobbiesList);
        result[8] = genLetDigString(1,50);
        result[9] = "MyRealFoto.jpg";
        result[10] = getOneOf(Arrays.asList("1","2","3","4"));
        result[11] = getOneOf(Arrays.asList("1","2","3","4"));
        return result;
    }
    public static Object[][] getDataSets(int numSets) {
        Object[][] result = new Object[numSets][12];
        for (int i=0;i<numSets;i++) {
            result[i]= genTestDataSet();
        }
        return result;
    }

    public static String getMonthName (String date){
        int monthNum = Integer.parseInt(date.substring(3,5));
        return Month.of( monthNum ).getDisplayName( TextStyle.FULL , Locale.US );
    }
}