package florencio.com.br.chamada.util;

import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import florencio.com.br.chamada.dominio.Cliente;
import florencio.com.br.chamada.dominio.Entidade;

public class Util {
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static boolean isVazio(Object objeto) {
        if(objeto == null) {
            return true;
        }

        if(objeto instanceof CharSequence) {
            CharSequence sequence = (CharSequence) objeto;
            return sequence.toString().trim().length() == 0;

        } else if(objeto instanceof Collection<?>) {
            return((Collection<?>)objeto).isEmpty();

        } else if(objeto instanceof Map<?, ?>) {
            return((Map<?, ?>)objeto).isEmpty();

        } else if(objeto instanceof Number) {
            return ((Number)objeto).longValue() == 0;

        }

        return objeto.toString().length() == 0;
    }

    public static String formatarDate(long date) {
        return dateFormat.format(new Date(date));
    }

    public static long parseDate(String data) {
        try {
            return dateFormat.parse(data).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static void setSelected(Spinner combo, Entidade entidade) {
        int tamanho = combo.getCount();

        for(int i=0; i<tamanho; i++) {
            Entidade e = (Entidade) combo.getItemAtPosition(i);

            if(e.get_id() == entidade.get_id()) {
                combo.setSelection(i);
                break;
            }
        }
    }
}