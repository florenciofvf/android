package florencio.com.br.chamada.fragmento.matricula;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.Entidade;
import florencio.com.br.chamada.dominio.Matricula;
import florencio.com.br.chamada.dominio.Turma;
import florencio.com.br.chamada.fragmento.FragmentoListagem;
import florencio.com.br.chamada.fragmento.FragmentoOuvinte;
import florencio.com.br.chamada.fragmento.FragmentoParametro;
import florencio.com.br.chamada.servico.ChamadaExcecao;
import florencio.com.br.chamada.util.Constantes;
import florencio.com.br.chamada.util.Util;

public class MatriculaListagem extends Fragment implements FragmentoListagem {
    private FragmentoParametro fragmentoParametro;
    private FragmentoOuvinte fragmentoOuvinte;
    private MatriculaAdaptador adaptador;
    private List<Matricula> objetos;
    private ActionMode actionMode;
    private ListView listView;

    private TextView nomeCurso;
    private TextView instrutor;
    private TextView laborator;
    private TextView frequenci;
    private TextView statusTur;
    private TextView turnoTurm;
    private TextView inicioTur;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentoOuvinte = (FragmentoOuvinte) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentoParametro = (FragmentoParametro) getArguments().getSerializable(Constantes.FRAGMENTO_PARAMETRO);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.matricula_listagem_layout, container, false);

        listView = (ListView) layout.findViewById(R.id.listagemMatricula);
        nomeCurso = (TextView) layout.findViewById(R.id.nomeCurso);
        instrutor = (TextView) layout.findViewById(R.id.instrutorTurma);
        laborator = (TextView) layout.findViewById(R.id.laboratorioTurma);
        frequenci = (TextView) layout.findViewById(R.id.frequenciaTurma);
        statusTur = (TextView) layout.findViewById(R.id.statusTurma);
        turnoTurm = (TextView) layout.findViewById(R.id.turnoTurma);
        inicioTur = (TextView) layout.findViewById(R.id.inicioTurma);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        listView.setOnItemLongClickListener(new OuvinteLongClickListener());
        listView.setOnItemClickListener(new OuvinteClickItemListener());
        atualizarCabecalho();
        atualizarListagem();
    }

    private void atualizarCabecalho() {
        Turma turma = (Turma) fragmentoParametro.getEntidade(Constantes.TURMA);

        nomeCurso.setText(turma.getCurso().getNome());
        instrutor.setText(turma.getInstrutor().getNome());
        laborator.setText(turma.getLaboratorio().getNome());
        frequenci.setText(turma.getFrequencia().getNome());
        statusTur.setText(turma.getStatusTurma().getNome());
        turnoTurm.setText(turma.getTurno().getNome());
        inicioTur.setText(Util.formatarDate(turma.getInicio()));
    }

    @Override
    public void atualizarListagem() {
        Turma turma = (Turma) fragmentoParametro.getEntidade(Constantes.TURMA);
        Matricula matricula = new Matricula();
        matricula.setTurma(turma);
        objetos = (List<Matricula>) fragmentoOuvinte.getChamadaServico().listar(matricula);
        setAdaptador(false);
    }

    private void setAdaptador(boolean modoExclusao) {
        adaptador = new MatriculaAdaptador(objetos, getActivity(), modoExclusao);
        adaptador.registerDataSetObserver(new ObservadorAdapater());
        listView.setAdapter(adaptador);
    }

    private class OuvinteClickItemListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(actionMode == null) {
                //Matricula objeto = objetos.get(position);
                //fragmentoOuvinte.clickItemListagem(objeto);
            } else {
                atualizarTituloSelecionados();
            }
        }
    }

    @Override
    public List<? extends Entidade> getSelecionados() {
        List<Matricula> lista = new ArrayList<>();
        for(Matricula c : objetos) {
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
        for(Matricula c : objetos) {
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

                Matricula objeto = objetos.get(position);
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