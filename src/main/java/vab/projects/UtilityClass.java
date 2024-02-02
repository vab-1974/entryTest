package vab.projects;

import org.openqa.selenium.WebDriver;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class UtilityClass {
    public static String myURL="https://demoqa.com/automation-practice-form";
    public static RegistryPage registryPage;
    public static ResultWindow resultWin;
    public static WebDriver wd;
    public static final List<String> subjectsList = Arrays.asList("Maths","English","Biology","Commerce","Hystory");
    public static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final String digits ="0123456789";
    public static String genString(int length, String symbolsSet) {
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++)
            sb.append(symbolsSet.charAt(new Random().nextInt(symbolsSet.length())));
        return sb.toString();
    }
    public static String genLetterString(int minLength, int maxLength) {
        if ((maxLength - minLength) >= 0)
                return genString(new Random().nextInt(maxLength-minLength+1)+minLength,letters);
        else return "";
    }
    public static String genDigitString(int minLength, int maxLength) {
        if ((maxLength - minLength) >= 0)
            return genString(new Random().nextInt(maxLength-minLength+1)+minLength,digits);
        else return "";
    }
    public static String genLetDigString (int minLength, int maxLength) {
        if ((maxLength - minLength) >= 0)
            return genString(new Random().nextInt(maxLength-minLength+1)+minLength,digits+letters);
        else return "";
    }
}
