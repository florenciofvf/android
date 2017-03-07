package florencio.com.br.crudlink.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static Long parseDate(String data) {
        if(isEmpty(data)) {
            return null;
        }

        try {
            Date d = dateFormat.parse(data);
            return d.getTime();
        } catch(Exception e) {
            throw new IllegalArgumentException(data);
        }
    }

    public static String formatDate(Long data) {
        if(data == null) {
            return Constantes.VAZIO;
        }

        String s = dateFormat.format(new Date(data));
        return s;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }
}
