package florencio.com.br.chamada.efetivacao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    private ExpandableListView listViewCabecalho;
    private CabecalhoChamadaAdaptador adaptador;
    private List<StatusChamada> listaStatus;
    private List<CabecalhoChamada> objetos;
    private ChamadaServico chamadaServico;
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
}