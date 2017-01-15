package florencio.com.br.arquivo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String ARQUIVO = "Arquivo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void salvarArquivo(View v) {
        EditText edit = (EditText) findViewById(R.id.edit);
        salvar(edit.getText().toString());
    }

    private void salvar(String string) {
        try {
            FileOutputStream fos = openFileOutput(ARQUIVO, MODE_APPEND);
            fos.write("\r\n".getBytes());
            fos.write(string.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lerArquivo();
    }

    public void excluirArquivo(View v) {
        boolean b = deleteFile(ARQUIVO);
        Toast.makeText(this, "EXCLUÍDO:" + b, Toast.LENGTH_SHORT).show();
    }

    private void lerArquivo() {
        try {
            FileInputStream fis = openFileInput(ARQUIVO);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            fis.close();
            EditText edit = (EditText) findViewById(R.id.conteudo);
            edit.setText(new String(bytes));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
