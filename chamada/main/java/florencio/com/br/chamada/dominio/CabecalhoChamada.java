package florencio.com.br.chamada.dominio;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CabecalhoChamada extends Entidade {
    public static final String  DATA_HORA = "dataHora";
    public static final String   TURMA_ID = "turma_id";
    public static final String OBSERVACAO = "observacao";

    private Turma turma;
    private Long dataHora;
    private String observacao;

    private final List<Chamada> chamadas;

    public CabecalhoChamada() {
        chamadas = new ArrayList<>();
    }

    public void addChamada(Chamada chamada) {
        chamadas.add(chamada);
    }

    public List<Chamada> getChamadas() {
        return chamadas;
    }

    public Chamada getChamada(int posicao) {
        return chamadas.get(posicao);
    }

    public int getTotalChamadas() {
        return chamadas.size();
    }

    @Override
    public Entidade criar(Cursor cursor) {
        return null;
    }

    @Override
    public ContentValues criarContentValues() {
        ContentValues cv = new ContentValues();

        Long data = dataHora;

        if(data == null) {
            data = new Date().getTime();
        }

        cv.put(DATA_HORA,  data);
        cv.put(TURMA_ID,   turma.get_id());
        cv.put(OBSERVACAO, observacao);

        return cv;
    }

    @Override
    public String[] getNomeColunas() {
        return null;
    }

    @Override
    public String getNomeColunaOrderBy() {
        return null;
    }

    public Long getDataHora() {
        return dataHora;
    }

    public void setDataHora(Long dataHora) {
        this.dataHora = dataHora;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}






