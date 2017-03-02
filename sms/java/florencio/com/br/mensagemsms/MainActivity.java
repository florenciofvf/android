package florencio.com.br.mensagemsms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private InterceptadorRecebido recebido = new InterceptadorRecebido();
    private InterceptadorEnviado enviado = new InterceptadorEnviado();
    private InterceptadorEntregue entregue = new InterceptadorEntregue();

    private EditText txtNumero;
    private EditText txtMensagem;
    private TextView lblEnviado;
    private TextView lblEntregue;
    private TextView lblMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        txtNumero = (EditText) findViewById(R.id.txtNumero);
        txtMensagem = (EditText) findViewById(R.id.txtMensagem);

        lblEnviado = (TextView) findViewById(R.id.lblEnviado);
        lblEntregue = (TextView) findViewById(R.id.lblEntregue);
        lblMensagem = (TextView) findViewById(R.id.lblMensagem);
    }

    public void enviarMensagem(View view) {
        SmsManager manager = SmsManager.getDefault();

        PendingIntent piEnviado = PendingIntent.getBroadcast(this, 0, new Intent("acao_enviado"), 0);
        PendingIntent piEntregue = PendingIntent.getBroadcast(this, 0, new Intent("acao_entregue"), 0);

        manager.sendTextMessage(txtNumero.getText().toString(), null, txtMensagem.getText().toString(), piEnviado, piEntregue);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(recebido, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));
        registerReceiver(enviado, new IntentFilter("acao_enviado"));
        registerReceiver(entregue, new IntentFilter("acao_entregue"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(recebido);
        unregisterReceiver(enviado);
        unregisterReceiver(entregue);
    }

    public class InterceptadorRecebido extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            SmsMessage smsMessage = getMensagens(intent)[0];

            String telefone = smsMessage.getOriginatingAddress();
            String mensagem = smsMessage.getMessageBody();

            lblMensagem.setText(telefone + " - " + mensagem);
        }

    }

    private SmsMessage[] getMensagens(Intent it) {
        Object[] pdus = (Object[]) it.getSerializableExtra("pdus");
        SmsMessage[] mensagens = new SmsMessage[pdus.length];
        for (int i=0; i<mensagens.length; i++) {
            SmsMessage sms = SmsMessage.createFromPdu( (byte[]) pdus[i]);
            mensagens[i] = sms;
        }

        return mensagens;
    }

    public class InterceptadorEnviado extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(getResultCode() == Activity.RESULT_OK) {
                lblEnviado.setText("Enviado");
            } else {
                lblEnviado.setText("Erro no envio");
            }
        }
    }

    public class InterceptadorEntregue extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(getResultCode() == Activity.RESULT_OK) {
                lblEntregue.setText("Entregue");
            } else {
                lblEntregue.setText("Erro na entrega");
            }
        }
    }
}
