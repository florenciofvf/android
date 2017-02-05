package florencio.com.br.chamada.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import florencio.com.br.chamada.dominio.Cliente;

public class Util {

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
}