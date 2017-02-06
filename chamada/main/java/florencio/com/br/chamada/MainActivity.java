package florencio.com.br.chamada;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import florencio.com.br.chamada.fragmento.curso.CursoDialogo;
import florencio.com.br.chamada.fragmento.curso.CursoListagem;
import florencio.com.br.chamada.fragmento.cliente.ClienteDialogo;
import florencio.com.br.chamada.fragmento.cliente.ClienteListagem;
import florencio.com.br.chamada.fragmento.instrutor.InstrutorDialogo;
import florencio.com.br.chamada.fragmento.instrutor.InstrutorListagem;
import florencio.com.br.chamada.fragmento.laboratorio.LaboratorioDialogo;
import florencio.com.br.chamada.fragmento.laboratorio.LaboratorioListagem;
import florencio.com.br.chamada.fragmento.frequencia.FrequenciaDialogo;
import florencio.com.br.chamada.fragmento.frequencia.FrequenciaListagem;
import florencio.com.br.chamada.fragmento.matricula.MatriculaTurmaListagem;
import florencio.com.br.chamada.fragmento.status_chamada.StatusChamadaDialogo;
import florencio.com.br.chamada.fragmento.status_chamada.StatusChamadaListagem;
import florencio.com.br.chamada.fragmento.status_turma.StatusTurmaDialogo;
import florencio.com.br.chamada.fragmento.status_turma.StatusTurmaListagem;
import florencio.com.br.chamada.fragmento.turma.TurmaDialogo;
import florencio.com.br.chamada.fragmento.turma.TurmaListagem;
import florencio.com.br.chamada.fragmento.turno.TurnoDialogo;
import florencio.com.br.chamada.fragmento.turno.TurnoListagem;
import florencio.com.br.chamada.util.Constantes;

public class MainActivity extends AppCompatActivity {
    private ActionBarDrawerToggle controleMenu;
    private NavigationView menuLateralView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.layoutLateral);
        controleMenu = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(controleMenu);
        controleMenu.syncState();

        menuLateralView = (NavigationView) findViewById(R.id.menuLateralView);
        menuLateralView.setNavigationItemSelectedListener(new OuvinteMenuLateral());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuItemConfig) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return true;
    }

    private class OuvinteMenuLateral implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(MainActivity.this, R.anim.invocando_entrada, R.anim.invocando_saida);
            Intent it = new Intent(MainActivity.this, TemplateActivity.class);
            TemplateParametro parametro = null;

            switch (item.getItemId()) {
                case R.id.menuItemCliente:
                    parametro = TemplateParametro.criarTemplateParametro(getString(R.string.label_cliente), ClienteListagem.class, ClienteDialogo.class); break;
                case R.id.menuItemInstrutor:
                    parametro = TemplateParametro.criarTemplateParametro(getString(R.string.label_instrutor), InstrutorListagem.class, InstrutorDialogo.class); break;
                case R.id.menuItemLaboratorio:
                    parametro = TemplateParametro.criarTemplateParametro(getString(R.string.label_laboratorio), LaboratorioListagem.class, LaboratorioDialogo.class); break;
                case R.id.menuItemFrequencia:
                    parametro = TemplateParametro.criarTemplateParametro(getString(R.string.label_frequencia), FrequenciaListagem.class, FrequenciaDialogo.class); break;
                case R.id.menuItemStatusTurma:
                    parametro = TemplateParametro.criarTemplateParametro(getString(R.string.label_status_turma), StatusTurmaListagem.class, StatusTurmaDialogo.class); break;
                case R.id.menuItemStatusChamada:
                    parametro = TemplateParametro.criarTemplateParametro(getString(R.string.label_status_chamada), StatusChamadaListagem.class, StatusChamadaDialogo.class); break;
                case R.id.menuItemCurso:
                    parametro = TemplateParametro.criarTemplateParametro(getString(R.string.label_curso), CursoListagem.class, CursoDialogo.class); break;
                case R.id.menuItemTurno:
                    parametro = TemplateParametro.criarTemplateParametro(getString(R.string.label_turno), TurnoListagem.class, TurnoDialogo.class); break;
                case R.id.menuItemTurma:
                    parametro = TemplateParametro.criarTemplateParametro(getString(R.string.label_turma), TurmaListagem.class, TurmaDialogo.class); break;
                case R.id.menuItemMatricula:
                    parametro = TemplateParametro.criarTemplateParametro(getString(R.string.label_matricula), MatriculaTurmaListagem.class, null); break;
            }

            it.putExtra(Constantes.TEMPLATE_PARAMETRO, parametro);
            startActivity(it, options.toBundle());
            drawerLayout.closeDrawers();
            return true;
        }
    }
}