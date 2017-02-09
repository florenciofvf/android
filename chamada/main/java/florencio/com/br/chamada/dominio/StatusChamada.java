package florencio.com.br.chamada.dominio;

import android.content.ContentValues;
import android.database.Cursor;

public class StatusChamada extends Entidade {
    public static final String LETRA = "letra";
    public static final String DESCRICAO = "descricao";
    public static final String ORDEM = "ordem";

    public static final int LETRA_IDX = 1;
    public static final int DESCRICAO_IDX = 2;
    public static final int ORDEM_IDX = 3;

    private String letra;
    private String descricao;
    private Integer ordem;

    public StatusChamada() {
    }

    public StatusChamada(Long _id) {
        this._id = _id;
    }

    public StatusChamada(String descricao) {
        this.descricao = descricao;
    }

    public StatusChamada(Long _id, String letra, String descricao, Integer ordem) {
        this._id = _id;
        this.letra = letra;
        this.descricao = descricao;
        this.ordem = ordem;
    }

    public String getLetra() {
        return letra;
    }

    @Override
    public String toString() {
        return descricao;
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
        return new StatusChamada(cursor.getLong(ID_IDX),
                cursor.getString(LETRA_IDX),
                cursor.getString(DESCRICAO_IDX), cursor.getInt(ORDEM_IDX));
    }

    @Override
    public ContentValues criarContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(LETRA, letra);
        cv.put(DESCRICAO, descricao);
        cv.put(ORDEM, ordem);

        return cv;
    }

    @Override
    public String[] getNomeColunas() {
        return new String[] {_ID, LETRA, DESCRICAO, ORDEM};
    }

    @Override
    public String getNomeColunaOrderBy() {
        return ORDEM;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }
}