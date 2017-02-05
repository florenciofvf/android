package florencio.com.br.chamada.dominio;

import android.content.ContentValues;
import android.database.Cursor;

public class StatusChamada extends Entidade {
    public static final String LETRA = "letra";
    public static final String DESCRICAO = "descricao";

    public static final int LETRA_IDX = 1;
    public static final int DESCRICAO_IDX = 2;

    private String letra;
    private String descricao;

    public StatusChamada() {
    }

    public StatusChamada(Long _id, String letra, String descricao) {
        this._id = _id;
        this.letra = letra;
        this.descricao = descricao;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra.substring(0, 1);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public Entidade criar(Cursor cursor) {
        return new StatusChamada(cursor.getLong(ID_IDX), cursor.getString(LETRA_IDX), cursor.getString(DESCRICAO_IDX));
    }

    @Override
    public ContentValues criarContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(LETRA, letra);
        cv.put(DESCRICAO, descricao);

        return cv;
    }

    @Override
    public String[] getNomeColunas() {
        return new String[] {_ID, LETRA, DESCRICAO};
    }

    @Override
    public String getNomeColunaOrderBy() {
        return LETRA;
    }
}