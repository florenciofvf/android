package florencio.com.br.servicevinculado;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ServicoContador extends Service implements Contador {
    private ContadorBinder binder = new ContadorBinder();
    private MinhaThread thread;
    private int contador;

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class ContadorBinder extends Binder {
        public Contador getServico() {
            return ServicoContador.this;
        }
    }

    @Override
    public int getValor() {
        return contador;
    }

    @Override
    public void onCreate() {
        Log.i("SERVICO_VINCULADO", "INICIANDO A THREAD");
        thread = new MinhaThread();
        thread.start();
    }

    @Override
    public void onDestroy() {
        if(thread != null) {
            Log.i("SERVICO_VINCULADO", "PARANDO A THREAD");
            thread.desativar();
            thread = null;
        }
    }

    private class MinhaThread extends Thread {
        private boolean ativo = true;

        @Override
        public void run() {
            while (ativo) {
                Log.i("SERVICO_VINCULADO", "" + contador);
                contador++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        void desativar() {
            ativo = false;
        }
    }
}
