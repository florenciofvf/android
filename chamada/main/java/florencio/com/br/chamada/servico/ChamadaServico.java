package florencio.com.br.chamada.servico;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import florencio.com.br.chamada.dominio.CabecalhoChamada;
import florencio.com.br.chamada.dominio.Chamada;
import florencio.com.br.chamada.dominio.Entidade;
import florencio.com.br.chamada.dominio.Matricula;
import florencio.com.br.chamada.dominio.StatusChamada;
import florencio.com.br.chamada.dominio.Turma;
import florencio.com.br.chamada.persistencia.Repositorio;

public class ChamadaServico {
    private Repositorio repositorio;

    public ChamadaServico(Context contexto) {
        repositorio = new Repositorio(contexto);
    }

    public List<? extends Entidade> listar(Entidade entidade) {
        return repositorio.listar(entidade);
    }

    public List<Entidade> listarParaCombo(Entidade entidade) {
        List<Entidade> resp = new ArrayList<>();
        resp.add(entidade);

        List<Entidade> entidades = (List<Entidade>) repositorio.listar(entidade);
        resp.addAll(entidades);

        return resp;
    }

    public void salvar(Entidade entidade) throws ChamadaExcecao {
        if(entidade instanceof CabecalhoChamada) {
            CabecalhoChamada cabecalho = (CabecalhoChamada) entidade;
            if(!cabecalho.ehNovo()) {
                repositorio.salvar(cabecalho);
                return;
            }

            List<StatusChamada> listaStatus = (List<StatusChamada>) listar(new StatusChamada());

            if(listaStatus.isEmpty()) {
                return;
            }

            StatusChamada status = listaStatus.get(0);

            Matricula matricula = new Matricula();
            matricula.setTurma(cabecalho.getTurma());
            List<Matricula> matriculas = (List<Matricula>) listar(matricula);

            repositorio.salvar(cabecalho);

            for(Matricula mat : matriculas) {
                Chamada chamada = new Chamada();
                chamada.setCabecalho(cabecalho);
                chamada.setMatricula(mat);
                chamada.setStatus(status);
                repositorio.salvar(chamada);
            }

        } else {
            repositorio.salvar(entidade);
        }
    }

    public void excluir(Entidade entidade) throws ChamadaExcecao {
        repositorio.excluir(entidade);
    }

    public List<CabecalhoChamada> listarCabecalhoChamada(Turma turma) {
        return repositorio.listarCabecalhoChamada(turma);
    }
}