package florencio.com.br.controle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

import florencio.com.br.controle.dominio.Controle;
import florencio.com.br.controle.persistencia.Repositorio;

public class MainActivity extends AppCompatActivity {
    private Repositorio repositorio;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        listView = (ListView) findViewById(R.id.listagem);

        Controle c = new Controle();
        c.setDescricao("Compra do mês");
        c.setTipo("D");
        c.setValor(600F);
        c.setData(new Date());

        repositorio = new Repositorio(this);
        repositorio.salvar(c);

        listar();
    }

    private void listar() {
        List<Controle> lista = repositorio.listar();

        for(Controle c : lista) {
            imprimir(c);
        }

        ControleAdaptador adaptador = new ControleAdaptador(this, lista);
        listView.setAdapter(adaptador);
    }

    private void imprimir(Controle c) {
        Log.i("CONTROLE", "  _ID: " + c.get_id());
        Log.i("CONTROLE", " DESC: " + c.getDescricao());
        Log.i("CONTROLE", "VALOR: " + c.getValor());
        Log.i("CONTROLE", " DATA: " + c.getData());
        Log.i("CONTROLE", " TIPO: " + c.getTipo());
    }
}
