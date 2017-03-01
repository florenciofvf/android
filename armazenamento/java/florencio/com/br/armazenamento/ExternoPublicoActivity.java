package florencio.com.br.armazenamento;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ExternoPublicoActivity extends AppCompatActivity {
    private final String NOME_ARQUIVO = "ARQUIVO_EXTERNO_PUBLICO";
    private EditText txtValor;
    private EditText txtConteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.externo_publico_layout);

        txtValor = (EditText)findViewById(R.id.txtValor);
        txtConteudo = (EditText)findViewById(R.id.txtConteudo);
        buscarConteudo();
    }

    public void adicionar(View v) {
        salvarConteudo();
    }

    private void buscarConteudo() {
        String estado = Environment.getExternalStorageState();

        if(!Environment.MEDIA_MOUNTED.equals(estado)) {
            return;
        }

        try {
            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File file2 = new File(file, NOME_ARQUIVO);

            StringBuilder sb = new StringBuilder();

            FileInputStream fis = new FileInputStream(file2);
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
        String estado = Environment.getExternalStorageState();

        if(!Environment.MEDIA_MOUNTED.equals(estado)) {
            return;
        }

        try {
            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File file2 = new File(file, NOME_ARQUIVO);

            FileOutputStream fos = new FileOutputStream(file2);

            PrintWriter pw = new PrintWriter(fos);
            pw.println(txtValor.getText().toString());
            pw.close();

        } catch (Exception e) {
            Log.e("ARMAZENAMENTO", e.getMessage());
        }
    }
}
