package florencio.com.br.chamada;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Map;

import florencio.com.br.chamada.dominio.Entidade;
import florencio.com.br.chamada.fragmento.FragmentoDialogo;
import florencio.com.br.chamada.fragmento.FragmentoListagem;
import florencio.com.br.chamada.fragmento.FragmentoOuvinte;
import florencio.com.br.chamada.fragmento.FragmentoParametro;
import florencio.com.br.chamada.servico.ChamadaExcecao;
import florencio.com.br.chamada.servico.ChamadaServico;
import florencio.com.br.chamada.util.Constantes;
import florencio.com.br.chamada.util.ReflexaoUtil;

public class TemplateActivity extends AppCompatActivity implements FragmentoOuvinte {
    private TemplateParametro templateParametro;
    private ChamadaServico chamadaServico;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_layout);

        templateParametro = (TemplateParametro) getIntent().getSerializableExtra(Constantes.TEMPLATE_PARAMETRO);

        toolbar = (Toolbar)findViewById(R.id.toolbarContainer);
        toolbar.setTitle(templateParametro.getTitulo());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentoParametro parametro = new FragmentoParametro();
        FragmentoListagem listagem = ReflexaoUtil.criarFragmentoListagem(templateParametro.getClasseFragmentoListagem(), parametro);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.conteudoTemplate, (Fragment) listagem, Constantes.TAG_FRAGMENTO_LISTAGEM);
        ft.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.retornando_entrada, R.anim.retornando_saida);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(templateParametro.getClasseFragmentoDialogo() != null) {
            getMenuInflater().inflate(R.menu.menu_manter, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuItemAdicionar) {
            FragmentoParametro parametro = new FragmentoParametro();
            parametro.setTitulo(getString(R.string.label_novo) + Constantes.ESPACO + templateParametro.getTitulo());

            FragmentoDialogo dialogo = ReflexaoUtil.criarFragmentoDialogo(templateParametro.getClasseFragmentoDialogo(), parametro);
            dialogo.exibir(getSupportFragmentManager());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void atualizarParametros(Entidade entidade, String subtitulo, Class<?> classeFragmentoListagem, Class<?> classeFragmentoDialogo, Map<String, Entidade> mapa) {
        toolbar.setTitle(templateParametro.getTitulo() + Constantes.TRACO + subtitulo);
        toolbar.inflateMenu(R.menu.menu_manter);
        toolbar.setOnMenuItemClickListener(new OuvinteToolbar());
        toolbar.setTag(mapa);

        templateParametro.setClasseFragmentoListagem(classeFragmentoListagem);
        templateParametro.setClasseFragmentoDialogo(classeFragmentoDialogo);

        FragmentoParametro parametro = new FragmentoParametro();
        parametro.setEntidade(entidade);
        parametro.setMapa(mapa);

        FragmentoListagem listagem = ReflexaoUtil.criarFragmentoListagem(templateParametro.getClasseFragmentoListagem(), parametro);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.conteudoTemplate, (Fragment) listagem, Constantes.TAG_FRAGMENTO_LISTAGEM);
        ft.addToBackStack(null);
        ft.commit();
    }

    private class OuvinteToolbar implements Toolbar.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(item.getItemId() == R.id.menuItemAdicionar) {
                FragmentoParametro parametro = new FragmentoParametro();
                parametro.setMapa((Map<String, Entidade>) toolbar.getTag());
                parametro.setTitulo(getString(R.string.label_novo) + Constantes.ESPACO + templateParametro.getTitulo());

                FragmentoDialogo dialogo = ReflexaoUtil.criarFragmentoDialogo(templateParametro.getClasseFragmentoDialogo(), parametro);
                dialogo.exibir(getSupportFragmentManager());
            }

            return true;
        }
    }

    @Override
    public void clickItemListagem(Entidade entidade) {
        FragmentoParametro parametro = new FragmentoParametro();
        parametro.setTitulo(getString(R.string.label_atualizar) + Constantes.ESPACO + templateParametro.getTitulo());
        parametro.setEntidade(entidade);

        FragmentoDialogo dialogo = ReflexaoUtil.criarFragmentoDialogo(templateParametro.getClasseFragmentoDialogo(), parametro);
        dialogo.exibir(getSupportFragmentManager());
    }

    @Override
    public String getMsgCampoObrigatorio() {
        return getString(R.string.msg_erro_campo_obrigatorio);
    }

    private void mensagem(int idRecurso) {
        mensagem(getString(idRecurso));
    }

    public void mensagem(String msg) {
        Toast.makeText(this, msg, Constantes.DURACAO_PADRAO_MENSAGENS).show();
    }

    @Override
    public void salvar(Entidade entidade) {
        try {
            getChamadaServico().salvar(entidade);

            FragmentManager fm = getSupportFragmentManager();
            FragmentoListagem listagem = (FragmentoListagem) fm.findFragmentByTag(Constantes.TAG_FRAGMENTO_LISTAGEM);
            listagem.atualizarListagem();

            mensagem(R.string.msg_sucesso_salvar);
        } catch (ChamadaExcecao chamadaExcecao) {
            mensagem(chamadaExcecao.getIdStringErro());
        }
    }

    @Override
    public ChamadaServico getChamadaServico() {
        if(chamadaServico == null) {
            chamadaServico = new ChamadaServico(this);
        }

        return chamadaServico;
    }
}