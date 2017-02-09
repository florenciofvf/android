package florencio.com.br.chamada.dominio;

import android.content.ContentValues;
import android.database.Cursor;

public class Turma extends Entidade {
    public static final String          INICIO = "inicio";
    public static final String        CURSO_ID = "curso_id";
    public static final String    INSTRUTOR_ID = "instrutor_id";
    public static final String  LABORATORIO_ID = "laboratorio_id";
    public static final String   FREQUENCIA_ID = "frequencia_id";
    public static final String STATUS_TURMA_ID = "status_turma_id";
    public static final String        TURNO_ID = "turno_id";

    public static final int            INICIO_IDX = 1;
    public static final int          CURSO_ID_IDX = 2;
    public static final int        CURSO_NOME_IDX = 3;
    public static final int      INSTRUTOR_ID_IDX = 4;
    public static final int    INSTRUTOR_NOME_IDX = 5;
    public static final int    LABORATORIO_ID_IDX = 6;
    public static final int  LABORATORIO_NOME_IDX = 7;
    public static final int     FREQUENCIA_ID_IDX = 8;
    public static final int   FREQUENCIA_NOME_IDX = 9;
    public static final int   STATUS_TURMA_ID_IDX = 10;
    public static final int STATUS_TURMA_NOME_IDX = 11;
    public static final int          TURNO_ID_IDX = 12;
    public static final int        TURNO_NOME_IDX = 13;

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
        t.setCurso(         new Curso(      cursor.getLong(        CURSO_ID_IDX ), cursor.getString(        CURSO_NOME_IDX ), null));
        t.setInstrutor(     new Instrutor(  cursor.getLong(    INSTRUTOR_ID_IDX ), cursor.getString(    INSTRUTOR_NOME_IDX ), null));
        t.setLaboratorio(   new Laboratorio(cursor.getLong(  LABORATORIO_ID_IDX ), cursor.getString(  LABORATORIO_NOME_IDX ), null));
        t.setFrequencia(    new Frequencia( cursor.getLong(   FREQUENCIA_ID_IDX ), cursor.getString(   FREQUENCIA_NOME_IDX ), null));
        t.setStatusTurma(   new StatusTurma(cursor.getLong( STATUS_TURMA_ID_IDX ), cursor.getString( STATUS_TURMA_NOME_IDX ), null));
        t.setTurno(         new Turno(      cursor.getLong(        TURNO_ID_IDX ), cursor.getString(        TURNO_NOME_IDX ), null));

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
        StringBuilder sb = new StringBuilder("select turma._id, turma.inicio,");

        sb.append(" curso._id, curso.nome,");
        sb.append(" instr._id, instr.nome,");
        sb.append(" labor._id, labor.nome,");
        sb.append(" frequ._id, frequ.nome,");
        sb.append(" statu._id, statu.nome,");
        sb.append(" turno._id, turno.nome");
        sb.append(" from Turma turma");
        sb.append(" inner join Curso       curso on curso._id = turma.curso_id");
        sb.append(" inner join Instrutor   instr on instr._id = turma.instrutor_id");
        sb.append(" inner join Laboratorio labor on labor._id = turma.laboratorio_id");
        sb.append(" inner join Frequencia  frequ on frequ._id = turma.frequencia_id");
        sb.append(" inner join StatusTurma statu on statu._id = turma.status_turma_id");
        sb.append(" inner join Turno       turno on turno._id = turma.turno_id");
        sb.append(" order by turma.inicio");

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









