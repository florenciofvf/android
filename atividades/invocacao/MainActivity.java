package atividades.com.br.invocacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private final int CODIGO_REQUISICAO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lista = (ListView) findViewById(R.id.listagem);
        lista.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0) {
            Intent it = new Intent(this, SemRetornoActivity.class);
            startActivity(it);
        }

        if(position == 1) {
            Intent it = new Intent(this, ComRetornoActivity.class);
            startActivityForResult(it, CODIGO_REQUISICAO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CODIGO_REQUISICAO && resultCode == RESULT_OK) {
            String string = data.getStringExtra("selecionado");
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        }
    }
}
