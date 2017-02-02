package florencio.com.br.chamada.dominio;

import android.util.Log;

import java.io.Serializable;

public abstract class Entidade implements Serializable {
    private boolean selecionado;
    private Long _id;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public boolean ehNovo() {
        return _id == null || _id.longValue() == 0;
    }
}