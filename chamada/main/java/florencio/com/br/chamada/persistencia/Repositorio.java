package florencio.com.br.chamada.persistencia;

import android.content.Context;

public class Repositorio {
    private BancoHelper banco;

    public Repositorio(Context contexto) {
        banco = new BancoHelper(contexto);
    }
}