package florencio.com.br.contador;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView txtDisplay;
    private int contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        txtDisplay = (TextView) findViewById(R.id.txtDisplay);
        txtDisplay.setText("" + contador);

        SensorManager manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(sensor != null) {
            manager.registerListener(new OuvinteSensorProximidade(), sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void zerarContador(View view) {
        contador = 0;
        txtDisplay.setText("" + contador);
    }

    private class OuvinteSensorProximidade implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float valor = event.values[0];
            if(valor == 0) {
                txtDisplay.setText("" + ++contador);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }
}
