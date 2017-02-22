package florencio.com.br.servicedesvinculado;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServicoContador extends Service {
    private MinhaThread thread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("SERVICO_DESVINCULADO", "INICIANDO TRHEAD");
        thread = new MinhaThread();
        thread.start();
    }

    @Override
    public void onDestroy() {
        if(thread != null) {
            Log.i("SERVICO_DESVINCULADO", "PARANDO TRHEAD");
            thread.desativar();
            thread = null;
        }
    }

    private class MinhaThread extends Thread {
        private boolean ativo = true;
        private int contador = 1;

        @Override
        public void run() {
            while(ativo) {
                Log.i("SERVICO_DESVINCULADO", "" + contador);
                contador++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //stopSelf();
        }

        void desativar() {
            ativo = false;
        }
    }

}
