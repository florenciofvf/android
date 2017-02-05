package florencio.com.br.chamada.fragmento.status_turma;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.Entidade;
import florencio.com.br.chamada.dominio.StatusTurma;
import florencio.com.br.chamada.fragmento.FragmentoListagem;
import florencio.com.br.chamada.fragmento.FragmentoOuvinte;
import florencio.com.br.chamada.servico.ChamadaExcecao;
import florencio.com.br.chamada.util.Constantes;

public class StatusTurmaListagem extends ListFragment implements FragmentoListagem {
    private FragmentoOuvinte fragmentoOuvinte;
    private StatusTurmaAdaptador adaptador;
    private List<StatusTurma> objetos;
    private ActionMode actionMode;
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
        listView.setOnItemLongClickListener(new OuvinteLongClickListener());
        atualizarListagem();
    }

    @Override
    public void atualizarListagem() {
        objetos = (List<StatusTurma>) fragmentoOuvinte.getChamadaServico().listar(new StatusTurma());
        setAdaptador(false);
    }

    private void setAdaptador(boolean modoExclusao) {
        adaptador = new StatusTurmaAdaptador(objetos, getActivity(), modoExclusao);
        adaptador.registerDataSetObserver(new ObservadorAdapater());
        setListAdapter(adaptador);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(actionMode == null) {
            StatusTurma objeto = objetos.get(position);
            fragmentoOuvinte.clickItemListagem(objeto);
        } else {
            atualizarTituloSelecionados();
        }
    }

    @Override
    public List<? extends Entidade> getSelecionados() {
        List<StatusTurma> lista = new ArrayList<>();
        for(StatusTurma c : objetos) {
            if(c.isSelecionado()) {
                lista.add(c);
            }
        }
        return lista;
    }

    private void atualizarTituloSelecionados() {
        int total = getSelecionados().size();
        String titulo = getResources().getQuantityString(R.plurals.msg_selecionado, total, total);
        actionMode.setTitle(titulo);
    }

    private void selecaoTodos(boolean b) {
        for(StatusTurma c : objetos) {
            c.setSelecionado(b);
        }
        adaptador.notifyDataSetChanged();
    }

    private class ObservadorAdapater extends DataSetObserver {
        @Override
        public void onChanged() {
            atualizarTituloSelecionados();
        }
    }

    private class OuvinteLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if(actionMode == null) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                actionMode = activity.startSupportActionMode(new OuvinteActionModeCallback());

                StatusTurma objeto = objetos.get(position);
                objeto.setSelecionado(true);
                atualizarTituloSelecionados();
                setAdaptador(true);

                return true;
            }

            return false;
        }
    }

    private class ChecarTodos implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AppCompatCheckBox checkTodos = (AppCompatCheckBox)v;
            selecaoTodos(checkTodos.isChecked());
        }
    }

    private class OuvinteActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_excluir, menu);
            MenuItem itemTodos = menu.findItem(R.id.menuItemCheckTodos);
            AppCompatCheckBox checkTodos = (AppCompatCheckBox) MenuItemCompat.getActionView(itemTodos);
            checkTodos.setPadding(0, 0, Constantes.PADDING_RIGHT_CHECK_TODOS, 0);
            checkTodos.setOnClickListener(new ChecarTodos());
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if(item.getItemId() == R.id.menuItemExcluir) {
                excluir(getSelecionados());
                actionMode.finish();
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            for(Entidade c : getSelecionados()) {
                c.setSelecionado(false);
            }
            setAdaptador(false);
            actionMode = null;
        }
    }

    @Override
    public void excluir(List<? extends Entidade> entidades) {
        if(entidades.isEmpty()) {
            return;
        }

        String msg = null;
        int erros = 0;

        for(Entidade e : entidades) {
            try {
                fragmentoOuvinte.getChamadaServico().excluir(e);
            } catch (ChamadaExcecao chamadaExcecao) {
                erros++;
            }
        }

        if(erros == 0) {
            msg = getResources().getQuantityString(R.plurals.msg_sucesso_excluir, entidades.size());
        } else {
            msg = getResources().getQuantityString(R.plurals.msg_erro_excluir, erros, erros);
        }

        atualizarListagem();
        fragmentoOuvinte.mensagem(msg);
    }
}