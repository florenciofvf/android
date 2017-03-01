package florencio.com.br.armazenamento;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class InternoActivity extends AppCompatActivity {
    private final String NOME_ARQUIVO = "ARQUIVO_INTERNO";
    private EditText txtValor;
    private EditText txtConteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interno_layout);

        txtValor = (EditText)findViewById(R.id.txtValor);
        txtConteudo = (EditText)findViewById(R.id.txtConteudo);
        buscarConteudo();
    }

    public void adicionar(View v) {
        salvarConteudo();
    }

    private void buscarConteudo() {
        try {
            StringBuilder sb = new StringBuilder();

            FileInputStream fis = openFileInput(NOME_ARQUIVO);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String linha = br.readLine();

            while (linha != null) {
                sb.append(linha + "\n");
                linha = br.readLine();
            }

            txtConteudo.setText(sb.toString());
        } catch (Exception e) {
            Log.e("ARMAZENAMENTO", e.getMessage());
        }
    }

    private void salvarConteudo() {
        try {
            FileOutputStream fos = openFileOutput(NOME_ARQUIVO, MODE_APPEND);

            PrintWriter pw = new PrintWriter(fos);
            pw.println(txtValor.getText().toString());
            pw.close();

        } catch (Exception e) {
            Log.e("ARMAZENAMENTO", e.getMessage());
        }
    }
}
