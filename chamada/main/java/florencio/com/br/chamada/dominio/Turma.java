package florencio.com.br.chamada.dominio;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

public class Turma extends Entidade {
    public static final String          INICIO = "inicio";
    public static final String        CURSO_ID = "curso_id";
    public static final String    INSTRUTOR_ID = "instrutor_id";
    public static final String  LABORATORIO_ID = "laboratorio_id";
    public static final String   FREQUENCIA_ID = "frequencia_id";
    public static final String STATUS_TURMA_ID = "status_turma_id";
    public static final String        TURNO_ID = "turno_id";

    public static final int          INICIO_IDX = 1;
    public static final int        CURSO_ID_IDX = 2;
    public static final int      CURSO_NOME_IDX = 3;
    public static final int    INSTRUTOR_ID_IDX = 4;
    public static final int  LABORATORIO_ID_IDX = 5;
    public static final int   FREQUENCIA_ID_IDX = 6;
    public static final int STATUS_TURMA_ID_IDX = 7;
    public static final int        TURNO_ID_IDX = 8;

    private Long inicio;
    private Curso curso;
    private Instrutor instrutor;
    private Laboratorio laboratorio;
    private Frequencia frequencia;
    private StatusTurma statusTurma;
    private Turno turno;

    public Turma() {
    }

    public Turma(Long _id) {
        this._id = _id;
    }

    @Override
    public Entidade criar(Cursor cursor) {
        Turma t = new Turma();

        t.set_id(           cursor.getLong(ID_IDX));
        t.setInicio(        cursor.getLong(INICIO_IDX));
        t.setCurso(         new Curso(cursor.getLong(CURSO_ID_IDX), cursor.getString(CURSO_NOME_IDX), null));
        t.setInstrutor(     new Instrutor(cursor.getLong(   INSTRUTOR_ID_IDX    )));
        t.setLaboratorio(   new Laboratorio(cursor.getLong( LABORATORIO_ID_IDX  )));
        t.setFrequencia(    new Frequencia(cursor.getLong(  FREQUENCIA_ID_IDX   )));
        t.setStatusTurma(   new StatusTurma(cursor.getLong( STATUS_TURMA_ID_IDX )));
        t.setTurno(         new Turno(cursor.getLong(       TURNO_ID_IDX        )));

        return t;
    }

    @Override
    public ContentValues criarContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(INICIO,          inicio);
        cv.put(CURSO_ID,        curso.get_id());
        cv.put(INSTRUTOR_ID,    instrutor.get_id());
        cv.put(LABORATORIO_ID,  laboratorio.get_id());
        cv.put(FREQUENCIA_ID,   frequencia.get_id());
        cv.put(STATUS_TURMA_ID, statusTurma.get_id());
        cv.put(TURNO_ID,        turno.get_id());

        return cv;
    }

    public String getStringConsulta() {
        StringBuilder sb = new StringBuilder("select t._id, t.inicio, c._id, c.nome, t.instrutor_id, t.laboratorio_id, t.frequencia_id, t.status_turma_id, t.turno_id from Turma t");
        sb.append(" inner join Curso c on c._id = t.curso_id");
        return sb.toString();
    }

    @Override
    public String[] getNomeColunas() {
        return null;
    }

    @Override
    public String getNomeColunaOrderBy() {
        return INICIO;
    }

    public Long getInicio() {
        return inicio;
    }

    public void setInicio(Long inicio) {
        this.inicio = inicio;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Instrutor getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Instrutor instrutor) {
        this.instrutor = instrutor;
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Frequencia getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Frequencia frequencia) {
        this.frequencia = frequencia;
    }

    public StatusTurma getStatusTurma() {
        return statusTurma;
    }

    public void setStatusTurma(StatusTurma statusTurma) {
        this.statusTurma = statusTurma;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }
}









