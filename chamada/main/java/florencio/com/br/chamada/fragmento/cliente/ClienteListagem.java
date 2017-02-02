package florencio.com.br.chamada.fragmento.cliente;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.List;

import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.Cliente;
import florencio.com.br.chamada.fragmento.FragmentoListagem;
import florencio.com.br.chamada.fragmento.FragmentoOuvinte;
import florencio.com.br.chamada.util.Util;

public class ClienteListagem extends ListFragment implements FragmentoListagem {
    private FragmentoOuvinte fragmentoOuvinte;
    private ClienteAdaptador adaptador;
    private List<Cliente> clientes;
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

        clientes = Util.criarListaClienteTeste();
        listView = getListView();
        listView.setOnItemLongClickListener(new OuvinteLongClickListener());
        setAdaptador(false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(actionMode == null) {
            Cliente cliente = clientes.get(position);
            fragmentoOuvinte.clickItemListagem(cliente);
        } else {
            atualizarTituloSelecionados();
        }
    }

    private void setAdaptador(boolean modoExclusao) {
        adaptador = new ClienteAdaptador(clientes, getActivity(), modoExclusao);
        adaptador.registerDataSetObserver(new ObservadorAdapater());
        setListAdapter(adaptador);
    }

    private class ObservadorAdapater extends DataSetObserver {
        @Override
        public void onChanged() {
            atualizarTituloSelecionados();
        }
    }

    private void atualizarTituloSelecionados() {
        int total = 0;
        for(Cliente c : clientes) {
            if(c.isSelecionado()) {
                total++;
            }
        }
        actionMode.setTitle("Selecionados (" + total + ")");
    }

    private void selecaoTodos(boolean b) {
        for(Cliente c : clientes) {
            c.setSelecionado(b);
        }
    }

    private class OuvinteLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if(actionMode == null) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                actionMode = activity.startSupportActionMode(new OuvinteActionModeCallback());

                Cliente cliente = clientes.get(position);
                cliente.setSelecionado(true);
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
            adaptador.notifyDataSetChanged();
        }
    }

    private class OuvinteActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_excluir, menu);
            MenuItem itemTodos = menu.findItem(R.id.menuItemTodos);
            AppCompatCheckBox checkTodos = (AppCompatCheckBox) MenuItemCompat.getActionView(itemTodos);
            checkTodos.setPadding(0, 0, 10, 0);
            checkTodos.setOnClickListener(new ChecarTodos());
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if(item.getItemId() == R.id.menuItemTodos) {
            } else {
                actionMode.finish();
            }

            return true;
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            setAdaptador(false);
            actionMode = null;
        }
    }
}