package florencio.com.br.chamada.dominio;

import android.content.ContentValues;
import android.database.Cursor;

public class Instrutor extends Entidade {
    public static final String NOME = "nome";
    public static final String EMAIL = "email";

    public static final int NOME_IDX = 1;
    public static final int EMAIL_IDX = 2;

    private String nome;
    private String email;

    public Instrutor() {
    }

    public Instrutor(String nome) {
        this.nome = nome;
    }

    public Instrutor(Long _id, String nome, String email) {
        this._id = _id;
        this.nome = nome;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Entidade criar(Cursor cursor) {
        return new Instrutor(cursor.getLong(ID_IDX), cursor.getString(NOME_IDX), cursor.getString(EMAIL_IDX));
    }

    @Override
    public ContentValues criarContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(NOME, nome);
        cv.put(EMAIL, email);

        return cv;
    }

    @Override
    public String[] getNomeColunas() {
        return new String[] {_ID, NOME, EMAIL};
    }

    @Override
    public String getNomeColunaOrderBy() {
        return NOME;
    }
}