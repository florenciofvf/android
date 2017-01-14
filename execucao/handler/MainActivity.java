package florencio.com.br.handler;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AreaDesenho area = new AreaDesenho(this);

        setContentView(area);

        area.desenhar();
    }
}
