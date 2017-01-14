package florencio.com.br.provedor;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//<uses-permission android:name="android.permission.READ_CONTACTS" />

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        int idxNome = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

        while(cursor.moveToNext()) {
            String nome = cursor.getString(idxNome);
            adapter.add(nome);
        }

        ListView listView = new ListView(this);
        listView.setAdapter(adapter);

        setContentView(listView);
    }

}
