package florencio.com.br.chamada.dominio;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

public class Matricula extends Entidade {
    public static final String       DATA = "data";
    public static final String CLIENTE_ID = "cliente_id";
    public static final String   TURMA_ID = "turma_id";

    public static final int          DATA_IDX = 1;
    public static final int    CLIENTE_ID_IDX = 2;
    public static final int  CLIENTE_NOME_IDX = 3;
    public static final int CLIENTE_EMAIL_IDX = 4;
    public static final int      TURMA_ID_IDX = 5;

    private Long data;
    private Cliente cliente;
    private Turma turma;

    @Override
    public Entidade criar(Cursor cursor) {
        Matricula t = new Matricula();

        t.set_id(    cursor.getLong(ID_IDX));
        t.setData(   cursor.getLong(DATA_IDX));
        t.setCliente(new Cliente(cursor.getLong(CLIENTE_ID_IDX), cursor.getString(CLIENTE_NOME_IDX), cursor.getString(CLIENTE_EMAIL_IDX)));
        t.setTurma( new Turma(cursor.getLong(  TURMA_ID_IDX    )));

        return t;
    }

    @Override
    public ContentValues criarContentValues() {
        ContentValues cv = new ContentValues();

        Long dataMatricula = data;

        if(dataMatricula == null) {
            dataMatricula = new Date().getTime();
        }

        cv.put(DATA,       dataMatricula);
        cv.put(CLIENTE_ID, cliente.get_id());
        cv.put(TURMA_ID,   turma.get_id());

        return cv;
    }

    public String getStringConsulta() {
        StringBuilder sb = new StringBuilder("select m._id, m.data, c._id, c.nome, c.email, m.turma_id from Matricula m");

        sb.append(" inner join Cliente c on c._id = m.cliente_id");
        sb.append(" inner join Turma t on t._id = m.turma_id");
        sb.append(" where m.turma_id = " + turma.get_id());

        return sb.toString();
    }

    @Override
    public String[] getNomeColunas() {
        return null;
    }

    @Override
    public String getNomeColunaOrderBy() {
        return DATA;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}









