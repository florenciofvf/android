package florencio.com.br.preferencia;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final SharedPreferences pref = getSharedPreferences("arq_prefencia", MODE_PRIVATE);
		//final SharedPreferences.Editor editor = pref.edit();
    }

}