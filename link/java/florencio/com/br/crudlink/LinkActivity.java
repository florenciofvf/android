package florencio.com.br.crudlink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import florencio.com.br.crudlink.dominio.Link;
import florencio.com.br.crudlink.util.Constantes;

public class LinkActivity extends AppCompatActivity {
    private Link link;
    private EditText txtLink;
    private EditText txtDescricao;
    private EditText txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.link_layout);

        txtLink = (EditText) findViewById(R.id.txtLink);
        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        txtData = (EditText) findViewById(R.id.txtData);

        Intent it = getIntent();

        link = (Link)it.getSerializableExtra(Constantes.LINK_SELECIONADO);

        if(link == null) {
            Button btnExcluir = (Button)findViewById(R.id.btnExcluir);
            btnExcluir.setVisibility(View.GONE);
        } else {
            preencherViews();
        }
    }

    private void preencherViews() {

    }
}
