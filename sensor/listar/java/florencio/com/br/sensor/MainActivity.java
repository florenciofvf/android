package florencio.com.br.sensor;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensores = manager.getSensorList(Sensor.TYPE_ALL);

        List<String> nomes = new ArrayList<>();

        for(Sensor s : sensores) {
            nomes.add(s.getName());
        }

        ListView listView = (ListView) findViewById(R.id.listagem);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nomes);
        listView.setAdapter(adapter);
    }

}