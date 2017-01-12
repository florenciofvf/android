package atividade.com.br.adaptador;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listagem = (ListView) findViewById(R.id.listagem);

        listagem.setAdapter(new Adaptador(this));
    }
}