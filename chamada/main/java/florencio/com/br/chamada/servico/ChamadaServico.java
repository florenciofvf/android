package florencio.com.br.chamada.servico;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import florencio.com.br.chamada.dominio.Entidade;
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
        repositorio.salvar(entidade);
    }

    public void excluir(Entidade entidade) throws ChamadaExcecao{
        repositorio.excluir(entidade);
    }
}