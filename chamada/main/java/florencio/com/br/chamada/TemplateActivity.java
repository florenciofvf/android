package florencio.com.br.chamada;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import florencio.com.br.chamada.dominio.Cliente;
import florencio.com.br.chamada.dominio.Entidade;
import florencio.com.br.chamada.fragmento.FragmentoDialogo;
import florencio.com.br.chamada.fragmento.FragmentoListagem;
import florencio.com.br.chamada.fragmento.FragmentoOuvinte;
import florencio.com.br.chamada.fragmento.FragmentoParametro;
import florencio.com.br.chamada.util.Constantes;
import florencio.com.br.chamada.util.ReflexaoUtil;

public class TemplateActivity extends AppCompatActivity
        implements FragmentoOuvinte {
    private TemplateParametro templateParametro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_layout);

        templateParametro = (TemplateParametro) getIntent().getSerializableExtra(Constantes.TEMPLATE_PARAMETRO);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarContainer);
        toolbar.setTitle(templateParametro.getTitulo());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentoParametro parametro = new FragmentoParametro();

        FragmentoListagem listagem = ReflexaoUtil.criarFragmentoListagem(templateParametro.getClasseFragmentoListagem(), parametro);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.conteudoTemplate, (Fragment) listagem);
        ft.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.retornando_entrada, R.anim.retornando_saida);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuItemAdicionar) {
            FragmentoParametro parametro = new FragmentoParametro();
            parametro.setTitulo("Novo " + templateParametro.getTitulo());

            FragmentoDialogo dialogo = ReflexaoUtil.criarFragmentoDialogo(templateParametro.getClasseFragmentoDialogo(), parametro);

            dialogo.exibir(getSupportFragmentManager());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void clickItemListagem(Entidade entidade) {
        FragmentoParametro parametro = new FragmentoParametro();
        parametro.setTitulo("Atualizar " + templateParametro.getTitulo());
        parametro.setEntidade(entidade);

        FragmentoDialogo dialogo = ReflexaoUtil.criarFragmentoDialogo(templateParametro.getClasseFragmentoDialogo(), parametro);

        dialogo.exibir(getSupportFragmentManager());
    }
}