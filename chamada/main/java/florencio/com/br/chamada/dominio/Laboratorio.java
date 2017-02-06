package florencio.com.br.chamada.dominio;

import android.content.ContentValues;
import android.database.Cursor;

public class Laboratorio extends Entidade {
    public static final String NOME = "nome";
    public static final String CAPACIDADE = "capacidade";

    public static final int NOME_IDX = 1;
    public static final int CAPACIDADE_IDX = 2;

    private String nome;
    private Integer capacidade;

    public Laboratorio() {
    }

    public Laboratorio(Long _id) {
        this._id = _id;
    }

    public Laboratorio(String nome) {
        this.nome = nome;
    }

    public Laboratorio(Long _id, String nome, Integer capacidade) {
        this._id = _id;
        this.nome = nome;
        this.capacidade = capacidade;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public Entidade criar(Cursor cursor) {
        return new Laboratorio(cursor.getLong(ID_IDX), cursor.getString(NOME_IDX), cursor.getInt(CAPACIDADE_IDX));
    }

    @Override
    public ContentValues criarContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(NOME, nome);
        cv.put(CAPACIDADE, capacidade);

        return cv;
    }

    @Override
    public String[] getNomeColunas() {
        return new String[] {_ID, NOME, CAPACIDADE};
    }

    @Override
    public String getNomeColunaOrderBy() {
        return NOME;
    }
}