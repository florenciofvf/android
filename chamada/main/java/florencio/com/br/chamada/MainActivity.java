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

import florencio.com.br.chamada.fragmento.cliente.ClienteDialogo;
import florencio.com.br.chamada.fragmento.cliente.ClienteListagem;
import florencio.com.br.chamada.util.Constantes;

public class MainActivity extends AppCompatActivity {
    private NavigationView menuLateralView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle controleMenu;

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
            if(item.getItemId() == R.id.menuItemCliente) {
                Intent it = new Intent(MainActivity.this, TemplateActivity.class);

                TemplateParametro parametro = TemplateParametro.criarTemplateParametro(getString(R.string.label_cliente), ClienteListagem.class, ClienteDialogo.class);

                it.putExtra(Constantes.TEMPLATE_PARAMETRO, parametro);

                ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(MainActivity.this, R.anim.invocando_entrada, R.anim.invocando_saida);
                startActivity(it, options.toBundle());
            }

            drawerLayout.closeDrawers();
            return true;
        }
    }

}
