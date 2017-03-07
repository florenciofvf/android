package florencio.com.br.crudlink;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import florencio.com.br.crudlink.persistencia.Repositorio;

public class MainActivity extends AppCompatActivity {
    private Repositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        repositorio = new Repositorio(this);

        ListView listView = (ListView) findViewById(R.id.listView);

        LinkAdapter adapter = new LinkAdapter(repositorio.listar(), this);

        listView.setAdapter(adapter);
    }
}
