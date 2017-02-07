package florencio.com.br.chamada.dominio;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

public class Chamada extends Entidade {
    public static final String CABECALHO_CHAMADA_ID = "cabecalho_chamada_id";
    public static final String    STATUS_CHAMADA_ID = "status_chamada_id";
    public static final String         MATRICULA_ID = "matricula_id";

    private CabecalhoChamada cabecalho;
    private StatusChamada status;
    private Matricula matricula;

    @Override
    public Entidade criar(Cursor cursor) {
        return null;
    }

    @Override
    public ContentValues criarContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(CABECALHO_CHAMADA_ID, cabecalho.get_id());
        cv.put(MATRICULA_ID,         matricula.get_id());
        cv.put(STATUS_CHAMADA_ID,    status.get_id());

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

    public CabecalhoChamada getCabecalho() {
        return cabecalho;
    }

    public void setCabecalho(CabecalhoChamada cabecalho) {
        this.cabecalho = cabecalho;
    }

    public StatusChamada getStatus() {
        return status;
    }

    public void setStatus(StatusChamada status) {
        this.status = status;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public void mudarStatus(List<StatusChamada> listaStatus) {
        int i = 0;

        for(; i<listaStatus.size(); i++) {
            StatusChamada sta = listaStatus.get(i);
            if(sta.get_id() == status.get_id()) {
                break;
            }
        }

        i++;

        if(i < listaStatus.size()) {
            status = listaStatus.get(i);
        } else {
            status = listaStatus.get(0);
        }
    }
}









