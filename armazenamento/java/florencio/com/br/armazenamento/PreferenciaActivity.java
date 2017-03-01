package florencio.com.br.armazenamento;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class PreferenciaActivity extends AppCompatActivity {
    private final String NOME_ARQUIVO = "ARQUIVO_PREFERENCIA";
    private EditText txtValor;
    private EditText txtConteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferencia_layout);

        txtValor = (EditText)findViewById(R.id.txtValor);
        txtConteudo = (EditText)findViewById(R.id.txtConteudo);
        buscarConteudo();
    }

    public void adicionar(View v) {
        salvarConteudo();
    }

    private void buscarConteudo() {
        SharedPreferences preferences = getSharedPreferences(NOME_ARQUIVO, MODE_PRIVATE);
        txtConteudo.setText( preferences.getString("CONTEUDO", "") );
    }

    private void salvarConteudo() {
        SharedPreferences preferences = getSharedPreferences(NOME_ARQUIVO, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("CONTEUDO", txtValor.getText().toString());
        editor.commit();
    }
}
