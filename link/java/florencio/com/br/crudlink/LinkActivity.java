package florencio.com.br.crudlink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import florencio.com.br.crudlink.dominio.Link;
import florencio.com.br.crudlink.persistencia.Repositorio;
import florencio.com.br.crudlink.util.Constantes;
import florencio.com.br.crudlink.util.Util;

public class LinkActivity extends AppCompatActivity {
    private Repositorio repositorio;
    private Link link;
    private EditText txtLink;
    private EditText txtDescricao;
    private EditText txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.link_layout);

        repositorio = new Repositorio(this);

        txtLink =      (EditText) findViewById(R.id.txtLink);
        txtDescricao = (EditText) findViewById(R.id.txtDescricao);
        txtData =      (EditText) findViewById(R.id.txtData);

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
        txtLink.setText(      link.getLink()                    );
        txtDescricao.setText( link.getDescricao()               );
        txtData.setText(      Util.formatDate( link.getData() ) );
    }

    public void salvarLink(View view) {
        int erros = 0;

        if(Util.isEmpty(txtLink.getText().toString())) {
            txtLink.setError("Campo obrigatório");
            erros++;
        }

        if(Util.isEmpty(txtDescricao.getText().toString())) {
            txtDescricao.setError("Campo obrigatório");
            erros++;
        }

        if(Util.isEmpty(txtData.getText().toString())) {
            txtDescricao.setError("Campo obrigatório");
            erros++;
        }

        if(erros > 0) {
            return;
        }

        Long data;

        try {
            data = Util.parseDate( txtData.getText().toString() );
        } catch(Exception e) {
            txtData.setError("Data inválida");
            return;
        }

        if(link == null) {
            link = new Link();
        }

        link.setLink(      txtLink.getText().toString()      );
        link.setDescricao( txtDescricao.getText().toString() );
        link.setData(      data );

        repositorio.salvar(link);
        Toast.makeText(this, "Salvo com sucesso.", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, null);
        finish();
    }

    public void excluirLink(View v) {
        repositorio.excluir(link);
        Toast.makeText(this, "Excluído com sucesso.", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, null);
        finish();
    }
}
