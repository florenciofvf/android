package florencio.com.br.notificacao;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MeuReceptor receptor = new MeuReceptor();
    private final String ACAO_CUSTOMIZADA = "ACAO_CUSTOMIZADA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        registerReceiver(receptor, new IntentFilter(ACAO_CUSTOMIZADA));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receptor);
    }

    public void minima(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setSmallIcon(R.drawable.ic_notificacao);
        builder.setContentTitle("Título");
        builder.setContentText("Texto");

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(0, builder.build());
    }

    public void comIntencao(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setSmallIcon(R.drawable.ic_notificacao);
        builder.setContentTitle("Título");
        builder.setContentText("Texto");
        builder.setTicker("Chegou uma notificação");
        builder.setAutoCancel(true);

        Intent it = new Intent(this, DetalheActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, it, 0);
        builder.setContentIntent(pi);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(1, builder.build());
    }

    public void completa(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        //builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setSmallIcon(R.drawable.ic_notificacao);
        builder.setContentTitle("Título");
        builder.setContentText("Texto");
        builder.setTicker("Chegou uma notificação");
        builder.setVibrate(new long[] {100, 500, 1000, 2000});
        //builder.setLights(Color.BLUE, 1000, 5000);
        builder.setAutoCancel(true);
        builder.setNumber(100);
        builder.setColor(Color.RED);
        builder.setSubText("Subtexto");

        Intent it = new Intent(this, DetalheActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, it, 0);
        builder.setContentIntent(pi);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(2, builder.build());
    }

    public void customizada(View view) {
        Uri uriSom = Uri.parse("android.resource://" + getPackageName() + "/raw/teste_som");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.ic_notificacao);
        builder.setContentTitle("Título");
        builder.setContentText("Texto");
        builder.setTicker("Chegou uma notificação");
        builder.setVibrate(new long[] {100, 500, 1000, 2000});
        builder.setAutoCancel(true);
        builder.setNumber(100);
        builder.setColor(Color.BLUE);
        builder.setSubText("Subtexto");
        builder.setSound(uriSom);
        builder.setLights(Color.BLUE, 1000, 5000);

        Intent it = new Intent(this, DetalheActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, it, 0);
        builder.setContentIntent(pi);

        Intent itCustomizada = new Intent(ACAO_CUSTOMIZADA);
        PendingIntent piCustomizada = PendingIntent.getBroadcast(this, 0, itCustomizada, 0);
        builder.setDeleteIntent(piCustomizada);
        builder.addAction(R.drawable.ic_notificacao, "Acao customizada", piCustomizada);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(3, builder.build());
    }

    public void estilizada(View view) {
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        style.setBigContentTitle("Mensagens");
        for(int i=1; i<=10; i++) {
            style.addLine("AAAA-" + i);
        }
        style.setSummaryText("Sumário");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setStyle(style);

        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setSmallIcon(R.drawable.ic_notificacao);
        builder.setContentTitle("Título");
        builder.setContentText("Texto");
        builder.setTicker("Chegou uma notificação");
        builder.setAutoCancel(true);
        builder.setOngoing(true);

        Intent it = new Intent(this, DetalheActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, it, 0);
        builder.setContentIntent(pi);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(4, builder.build());
    }

    private class MeuReceptor extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
        }
    }

    public void naoRemovivel(View view) {
    }
}