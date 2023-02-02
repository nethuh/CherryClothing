package lk.ijse.CherryClothing.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static boolean regex (String type,String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(type);
        return matcher.matches();
    }
}
