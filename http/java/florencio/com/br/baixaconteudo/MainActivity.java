package florencio.com.br.baixaconteudo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText txtURL;
    private EditText txtConteudo;
    private ImageView imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        txtURL = (EditText) findViewById(R.id.txtURL);
        //txtURL.setText("https://raw.githubusercontent.com/florenciofvf/android/master/atividades/adaptador/Adaptador.java");
        txtURL.setText("https://github.com/fluidicon.png");

        txtConteudo = (EditText) findViewById(R.id.txtConteudo);
        imagem = (ImageView) findViewById(R.id.imagem);
    }

    public void limparConteudo(View view) {
        txtConteudo.setText("");
    }

    public void baixarConteudo(View view) {
        final String url = txtURL.getText().toString();

        TarefaImagem t = new TarefaImagem();

        try {
            t.execute(new URL(url));
        } catch (Exception e) {
            Log.i("TAREFA", e.getMessage());
         }
    }

    private class TarefaImagem extends AsyncTask<URL, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            Button b = (Button) findViewById(R.id.btnBaixarConteudo);
            b.setEnabled(false);
        }

        @Override
        protected Bitmap doInBackground(URL... params) {
            try {

                URL obj = params[0];
                InputStream is = obj.openStream();
                Bitmap b = BitmapFactory.decodeStream(is);

                return b;

            } catch(Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap b) {
            imagem.setImageBitmap(b);

            Button btn = (Button) findViewById(R.id.btnBaixarConteudo);
            btn.setEnabled(true);
        }

    }


//    private class Tarefa extends AsyncTask<URL, Void, String> {
//
//        @Override
//        protected void onPreExecute() {
//            Button b = (Button) findViewById(R.id.btnBaixarConteudo);
//            b.setEnabled(false);
//        }
//
//        @Override
//        protected String doInBackground(URL... params) {
//            try {
//                int MILISEGUNDOS = 1000;
//
//                URL obj = params[0];
//                HttpURLConnection conn = (HttpURLConnection)obj.openConnection();
//                conn.setConnectTimeout(10 * MILISEGUNDOS);
//                conn.setReadTimeout(10 * MILISEGUNDOS);
//                conn.setDoInput(true);
//                conn.setDoOutput(false);
//                conn.setRequestMethod("GET");
//
//                conn.connect();
//
//                int resp = conn.getResponseCode();
//
//                if(resp == HttpURLConnection.HTTP_OK) {
//                    StringBuilder sb = new StringBuilder();
//
//                    InputStream is = conn.getInputStream();
//                    BufferedReader br = new BufferedReader(
//                            new InputStreamReader(is)   );
//
//                    String linha = br.readLine();
//                    while(linha != null) {
//                        sb.append(linha + "\r\n");
//                        linha = br.readLine();
//                    }
//
//                    return sb.toString();
//                }
//
//            } catch(Exception e) {
//                return e.getMessage();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            txtConteudo.setText(s);
//
//            Button b = (Button) findViewById(R.id.btnBaixarConteudo);
//            b.setEnabled(true);
//        }
//
//    }
}
