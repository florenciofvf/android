package florencio.com.br.chamada.fragmento.matricula;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import florencio.com.br.chamada.dominio.Entidade;
import florencio.com.br.chamada.dominio.Turma;
import florencio.com.br.chamada.fragmento.FragmentoListagem;
import florencio.com.br.chamada.fragmento.FragmentoOuvinte;
import florencio.com.br.chamada.fragmento.turma.TurmaAdaptador;
import florencio.com.br.chamada.util.Constantes;

public class MatriculaTurmaListagem extends ListFragment implements FragmentoListagem {
    private FragmentoOuvinte fragmentoOuvinte;
    private TurmaAdaptador adaptador;
    private List<Turma> objetos;
    private ListView listView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentoOuvinte = (FragmentoOuvinte) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        listView = getListView();
        atualizarListagem();
    }

    @Override
    public void atualizarListagem() {
        objetos = (List<Turma>) fragmentoOuvinte.getChamadaServico().listar(new Turma());
        setAdaptador(false);
    }

    private void setAdaptador(boolean modoExclusao) {
        adaptador = new TurmaAdaptador(objetos, getActivity(), modoExclusao);
        setListAdapter(adaptador);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Turma objeto = objetos.get(position);
        Map<String, Entidade> mapa = new HashMap<>();
        mapa.put(Constantes.TURMA, objeto);
        fragmentoOuvinte.atualizarParametros(null, objeto.getCurso().getNome(), MatriculaListagem.class, MatriculaDialogo.class, mapa);
    }

    @Override
    public void excluir(List<? extends Entidade> entidades) {
    }

    @Override
    public List<? extends Entidade> getSelecionados() {
        return null;
    }
}