package florencio.com.br.chamada.dominio;

import android.content.ContentValues;
import android.database.Cursor;

public class Frequencia extends Entidade {
    public static final String NOME = "nome";
    public static final String DESCRICAO = "descricao";

    public static final int NOME_IDX = 1;
    public static final int DESCRICAO_IDX = 2;

    private String nome;
    private String descricao;

    public Frequencia() {
    }

    public Frequencia(Long _id, String nome, String descricao) {
        this._id = _id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public Entidade criar(Cursor cursor) {
        return new Frequencia(cursor.getLong(ID_IDX), cursor.getString(NOME_IDX), cursor.getString(DESCRICAO_IDX));
    }

    @Override
    public ContentValues criarContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(NOME, nome);
        cv.put(DESCRICAO, descricao);

        return cv;
    }

    @Override
    public String[] getNomeColunas() {
        return new String[] {_ID, NOME, DESCRICAO};
    }

    @Override
    public String getNomeColunaOrderBy() {
        return NOME;
    }
}