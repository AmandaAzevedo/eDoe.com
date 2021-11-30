package tech.amandaam.eDoe.api.util;

import java.text.Normalizer;

public class StringUtil {

    public static String removeAccents(String string){
        String strNoAccent = Normalizer.normalize(string, Normalizer.Form.NFD);
        strNoAccent = strNoAccent.replaceAll("[^\\p{ASCII}]", "");
        return strNoAccent;
    }

    public static String removeSpaces(String string){
        return string.trim();
    }

    public static String toLowerCase(String string){
        return string.toLowerCase();
    }

    public static String normalize(String string){
        string = StringUtil.removeAccents(string);
        string = StringUtil.removeSpaces(string);
        string = StringUtil.toLowerCase(string);
        return string;
    }
}
