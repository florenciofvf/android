package florencio.com.br.crudlink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import florencio.com.br.crudlink.dominio.Link;
import florencio.com.br.crudlink.persistencia.Repositorio;
import florencio.com.br.crudlink.util.Constantes;

public class MainActivity extends AppCompatActivity {
    private Repositorio repositorio;
    private LinkAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        repositorio = new Repositorio(this);

        listView = (ListView) findViewById(R.id.listView);

        adapter = new LinkAdapter(repositorio.listar(), this);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OuvinteItemListView());
    }

    public void novoLink(View v) {
        Intent it = new Intent(this, LinkActivity.class);
        startActivityForResult(it, Constantes.REQUEST_CODE_LINK_ACTIVITY);
    }

    private class OuvinteItemListView implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Link link = (Link)adapter.getItem(position);

            Intent it = new Intent(MainActivity.this, LinkActivity.class);
            it.putExtra(Constantes.LINK_SELECIONADO, link);

            startActivityForResult(it, Constantes.REQUEST_CODE_LINK_ACTIVITY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constantes.REQUEST_CODE_LINK_ACTIVITY &&
                resultCode == RESULT_OK) {

            adapter = new LinkAdapter(repositorio.listar(), this);
            listView.setAdapter(adapter);

        }
    }
}
