package florencio.com.br.receptor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MensagemReceptor extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "MENSAGEM DO BROAD CAST", Toast.LENGTH_SHORT).show();
    }

}
