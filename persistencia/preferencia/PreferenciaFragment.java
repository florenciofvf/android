package florencio.com.br.preferencia;

import android.preference.PreferenceFragment;
import android.os.Bundle;

public class PreferenciaFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.layout_prefencia);
    }
}
