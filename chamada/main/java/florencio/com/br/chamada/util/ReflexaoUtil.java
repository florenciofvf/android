package florencio.com.br.chamada.util;

import android.os.Bundle;

import florencio.com.br.chamada.fragmento.FragmentoDialogo;
import florencio.com.br.chamada.fragmento.FragmentoListagem;
import florencio.com.br.chamada.fragmento.FragmentoParametro;

public class ReflexaoUtil {

    public static FragmentoListagem criarFragmentoListagem(Class<?> classe, FragmentoParametro parametro) {
        try {
            FragmentoListagem listagem = (FragmentoListagem) classe.newInstance();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constantes.FRAGMENTO_PARAMETRO, parametro);
            listagem.setArguments(bundle);
            return listagem;
        } catch(Exception e) {
            throw new IllegalArgumentException("criarFragmentoListagem");
        }
    }

    public static FragmentoDialogo criarFragmentoDialogo(Class<?> classe, FragmentoParametro parametro) {
        try {
            FragmentoDialogo dialogo = (FragmentoDialogo) classe.newInstance();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constantes.FRAGMENTO_PARAMETRO, parametro);
            dialogo.setArguments(bundle);
            return dialogo;
        } catch (Exception e) {
            throw new IllegalArgumentException("criarFragmentoDialogo");
        }
    }
}