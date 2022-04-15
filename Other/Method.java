package Other;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Method {

    public static boolean IsNum (String str){
        if (str.equals("")) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    public static String getTime() {
        return new SimpleDateFormat("HH:mm").format(new Date());
    }

}
