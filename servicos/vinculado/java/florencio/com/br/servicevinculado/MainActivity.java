package florencio.com.br.servicevinculado;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Conexao conexao = new Conexao();
    private Contador contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }

    public void iniciarServicoVinculado(View view) {
        Intent it = new Intent(this, ServicoContador.class);
        bindService(it, conexao, BIND_AUTO_CREATE);
    }

    public void pararServicoVinculado(View view) {
        unbindService(conexao);
    }

    public void mostrarValorContador(View view) {
        if(contador != null) {
            Toast.makeText(this, "VALOR >>> " + contador.getValor(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void iniciarServico(View view) {
        Intent it = new Intent(this, ServicoContador.class);
        startService(it);
    }

    public void pararServico(View view) {
        Intent it = new Intent(this, ServicoContador.class);
        stopService(it);
    }

    private class Conexao implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServicoContador.ContadorBinder binder =
                    (ServicoContador.ContadorBinder) service;
            contador = binder.getServico();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            contador = null;
        }
    }
}