package florencio.com.br.chamada.efetivacao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.CabecalhoChamada;
import florencio.com.br.chamada.dominio.Chamada;
import florencio.com.br.chamada.dominio.Entidade;
import florencio.com.br.chamada.dominio.StatusChamada;
import florencio.com.br.chamada.dominio.Turma;
import florencio.com.br.chamada.fragmento.FragmentoDialogo;
import florencio.com.br.chamada.fragmento.FragmentoParametro;
import florencio.com.br.chamada.servico.ChamadaExcecao;
import florencio.com.br.chamada.servico.ChamadaServico;
import florencio.com.br.chamada.util.Constantes;
import florencio.com.br.chamada.util.ReflexaoUtil;
import florencio.com.br.chamada.util.Util;

public class ChamadaActivity extends AppCompatActivity implements CabecalhoChamadaDialogo.CabecalhoChamadaOuvinte {
    private ShareActionProvider shareActionProvider;
    private ExpandableListView listViewCabecalho;
    private CabecalhoChamadaAdaptador adaptador;
    private List<StatusChamada> listaStatus;
    private List<CabecalhoChamada> objetos;
    private ChamadaServico chamadaServico;
    private StringBuilder stringBuilder;
    private boolean editarCabecalho;
    private Toolbar toolbar;
    private Turma turma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chamada_layout);

        toolbar = (Toolbar)findViewById(R.id.toolbarChamada);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(getString(R.string.label_efetivacao));

        listaStatus = (List<StatusChamada>) getChamadaServico().listar(new StatusChamada());

        turma = (Turma) getIntent().getSerializableExtra(Constantes.TURMA);
        listViewCabecalho = (ExpandableListView) findViewById(R.id.listagemCabecalhoChamada);
        listViewCabecalho.setOnChildClickListener(new OuvinteClickChildItem());
        listViewCabecalho.setOnGroupClickListener(new OuvinteClickGroupItem());
        atualizarCabecalho();
        atualizarListagem();
    }

    private void atualizarCabecalho() {
        TextView nomeCurso = (TextView) findViewById(R.id.nomeCurso);
        TextView instrutor = (TextView) findViewById(R.id.instrutorTurma);
        TextView laborator = (TextView) findViewById(R.id.laboratorioTurma);
        TextView frequenci = (TextView) findViewById(R.id.frequenciaTurma);
        TextView statusTur = (TextView) findViewById(R.id.statusTurma);
        TextView turnoTurm = (TextView) findViewById(R.id.turnoTurma);
        TextView inicioTur = (TextView) findViewById(R.id.inicioTurma);

        nomeCurso.setText(turma.getCurso().getNome());
        instrutor.setText(turma.getInstrutor().getNome());
        laborator.setText(turma.getLaboratorio().getNome());
        frequenci.setText(turma.getFrequencia().getNome());
        statusTur.setText(turma.getStatusTurma().getNome());
        turnoTurm.setText(turma.getTurno().getNome());
        inicioTur.setText(Util.formatarDate(turma.getInicio()));

        stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.label_curso)        + Constantes.DOIS_PONTOS + turma.getCurso().getNome()           + Constantes.BARRA_N);
        stringBuilder.append(getString(R.string.label_instrutor)    + Constantes.DOIS_PONTOS + turma.getInstrutor().getNome()       + Constantes.BARRA_N);
        stringBuilder.append(getString(R.string.label_laboratorio)  + Constantes.DOIS_PONTOS + turma.getLaboratorio().getNome()     + Constantes.BARRA_N);
        stringBuilder.append(getString(R.string.label_frequencia)   + Constantes.DOIS_PONTOS + turma.getFrequencia().getNome()      + Constantes.BARRA_N);
        stringBuilder.append(getString(R.string.label_status_turma) + Constantes.DOIS_PONTOS + turma.getStatusTurma().getNome()     + Constantes.BARRA_N);
        stringBuilder.append(getString(R.string.label_turno)        + Constantes.DOIS_PONTOS + turma.getTurno().getNome()           + Constantes.BARRA_N);
        stringBuilder.append(getString(R.string.label_inicio)       + Constantes.DOIS_PONTOS + Util.formatarDate(turma.getInicio()) + Constantes.BARRA_N);
        stringBuilder.append(Constantes.BARRA_N);
        stringBuilder.append(Constantes.BARRA_N);
    }

    public void atualizarListagem() {
        objetos = getChamadaServico().listarCabecalhoChamada(turma);
        setAdaptador();
    }

    private void setAdaptador() {
        adaptador = new CabecalhoChamadaAdaptador(objetos, this);
        listViewCabecalho.setAdapter(adaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cabecalho, menu);

        MenuItem itemTodos = menu.findItem(R.id.menuItemEditCabecalho);
        AppCompatCheckBox check = (AppCompatCheckBox) MenuItemCompat.getActionView(itemTodos);
        check.setPadding(0, 0, Constantes.PADDING_RIGHT_CHECK_TODOS, 0);
        check.setOnClickListener(new ChecarEditarCabecalho());

        MenuItem itemCompartilhar = menu.findItem(R.id.menuItemCompartilhar);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(itemCompartilhar);

        if(adaptador != null && stringBuilder != null) {
            String relatorio = stringBuilder.toString() + adaptador.getStringRelatorio();

            Intent it = new Intent(Intent.ACTION_SEND);
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            it.setType("text/plain");
            it.putExtra(Intent.EXTRA_TEXT, relatorio);

            shareActionProvider.setShareIntent(it);
        }

        return true;
    }

    private class ChecarEditarCabecalho implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AppCompatCheckBox checkTodos = (AppCompatCheckBox)v;
            editarCabecalho = checkTodos.isChecked();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.retornando_entrada, R.anim.retornando_saida);
    }

    public ChamadaServico getChamadaServico() {
        if(chamadaServico == null) {
            chamadaServico = new ChamadaServico(this);
        }

        return chamadaServico;
    }

    public void criarCabecalhoChamada(View view) {
        FragmentoParametro parametro = new FragmentoParametro();
        parametro.putEntidade(Constantes.TURMA, turma);
        parametro.setTitulo(getString(R.string.label_novo) + Constantes.TRACO + toolbar.getTitle());

        FragmentoDialogo dialogo = ReflexaoUtil.criarFragmentoDialogo(CabecalhoChamadaDialogo.class, parametro);
        dialogo.exibir(getSupportFragmentManager());
    }

    @Override
    public void salvarCabecalhoChamada(Entidade entidade) {
        try {
            getChamadaServico().salvar(entidade);
            atualizarListagem();
            mensagem(R.string.msg_sucesso_salvar);
        } catch (ChamadaExcecao chamadaExcecao) {
            mensagem(chamadaExcecao.getIdStringErro());
        }
    }

    private void mensagem(int idRecurso) {
        mensagem(getString(idRecurso));
    }

    public void mensagem(String msg) {
        Toast.makeText(this, msg, Constantes.DURACAO_PADRAO_MENSAGENS).show();
    }

    private class OuvinteClickChildItem implements ExpandableListView.OnChildClickListener {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            CabecalhoChamada cabecalho = objetos.get(groupPosition);
            Chamada chamada = cabecalho.getChamada(childPosition);
            chamada.mudarStatus(listaStatus);
            try {
                getChamadaServico().salvar(chamada);
                adaptador.notifyDataSetChanged();
                //atualizarListagem();
            } catch (ChamadaExcecao chamadaExcecao) {
                chamadaExcecao.printStackTrace();
            }
            return true;
        }
    }

    private class OuvinteClickGroupItem implements ExpandableListView.OnGroupClickListener {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
            if(!editarCabecalho) {
                return false;
            }

            CabecalhoChamada cabecalho = objetos.get(groupPosition);

            FragmentoParametro parametro = new FragmentoParametro();
            parametro.setEntidade(cabecalho);
            parametro.putEntidade(Constantes.TURMA, turma);
            parametro.setTitulo(getString(R.string.label_novo) + Constantes.TRACO + toolbar.getTitle());

            FragmentoDialogo dialogo = ReflexaoUtil.criarFragmentoDialogo(CabecalhoChamadaDialogo.class, parametro);
            dialogo.exibir(getSupportFragmentManager());

            return true;
        }
    }
}