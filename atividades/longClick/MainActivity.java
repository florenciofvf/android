package florencio.com.br.longoclick;

import android.support.transition.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<String> nomes = Arrays.asList("Maria da Silva", "Marta de Oliveira", "Francisco da Silva", "Ana Machado da Silva", "João Francisco da Silva", "Maria de Oliveira");

    private ActionMode actionMode;
    private ListView listView;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.barraAcoes);
        setSupportActionBar(toolbar);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, nomes);

        listView = (ListView)findViewById(R.id.listagem);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ClickNaLista());
        listView.setOnItemLongClickListener(new ClickLongoNaLista());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private class ClickNaLista implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i("LISTAGEM", "Click Item");
        }
    }

    private class ClickLongoNaLista implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i("LISTAGEM", "Click Longo");
            actionMode = startSupportActionMode(new ModoExclusao());
            toolbar.setVisibility(View.INVISIBLE);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_checked, nomes);
            listView.setAdapter(adapter);

            return true;
        }
    }

    private class ModoExclusao implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_excluir, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_activated_1, nomes);
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            listView.setAdapter(adapter);
            toolbar.setVisibility(View.VISIBLE);
            actionMode.finish();
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }
}
