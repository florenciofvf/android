package florencio.com.br.armazenamento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Map<Integer, Class<?>> mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        ListView listView = (ListView) findViewById(R.id.listagem);
        listView.setOnItemClickListener(this);

        mapa = new HashMap<>();
        mapa.put(0, InternoActivity.class);
        mapa.put(1, ExternoPublicoActivity.class);
        mapa.put(2, ExternoPrivadoActivity.class);
        mapa.put(3, PreferenciaActivity.class);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Class<?> classe = mapa.get(position);

        Intent it = new Intent(this, classe);
        startActivity(it);
    }

}