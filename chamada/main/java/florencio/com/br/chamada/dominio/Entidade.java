package florencio.com.br.chamada.dominio;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

public abstract class Entidade implements Serializable {
    public static final String _ID = "_id";
    public static final int ID_IDX = 0;

    protected boolean selecionado;
    protected Long _id;

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

    public abstract Entidade criar(Cursor cursor);

    public abstract ContentValues criarContentValues();

    public abstract String[] getNomeColunas();

    public abstract String getNomeColunaOrderBy();
}