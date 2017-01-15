package florencio.com.br.fragmento;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mostrarAZul(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction t = fm.beginTransaction();

        t.replace(R.id.areaFragmento, new FragmentoAzul());
        t.commit();
    }

    public void mostrarVerde(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction t = fm.beginTransaction();

        t.replace(R.id.areaFragmento, new FragmentoVerde());
        t.commit();
    }

    public void mostrarVermelho(View view) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction t = fm.beginTransaction();

        t.replace(R.id.areaFragmento, new FragmentoVermelho());
        t.commit();
    }
}