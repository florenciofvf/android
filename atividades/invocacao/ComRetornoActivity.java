package atividades.com.br.invocacao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ComRetornoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private final String[] ESTADOS = {
      "Pará", "Maranhão", "Ceará", "Tocantins", "Acre", "Goiás"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_retorno);

        ListView view = (ListView) findViewById(R.id.listaEstados);
        view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, ESTADOS);

        view.setAdapter(adapter);

        view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String string = ESTADOS[position];

        Intent it = new Intent();
        it.putExtra("selecionado", string);

        setResult(RESULT_OK, it);

        finish();
    }
}
