package florencio.com.br.chamada.fragmento.instrutor;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.Instrutor;
import florencio.com.br.chamada.fragmento.FragmentoDialogo;
import florencio.com.br.chamada.fragmento.FragmentoOuvinte;
import florencio.com.br.chamada.fragmento.FragmentoParametro;
import florencio.com.br.chamada.util.Constantes;
import florencio.com.br.chamada.util.Util;

public class InstrutorDialogo extends DialogFragment implements FragmentoDialogo {
    private FragmentoParametro fragmentoParametro;
    private FragmentoOuvinte fragmentoOuvinte;
    private EditText editEmail;
    private EditText editNome;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentoOuvinte = (FragmentoOuvinte) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentoParametro = (FragmentoParametro) getArguments().getSerializable(Constantes.FRAGMENTO_PARAMETRO);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.instrutor_layout, container, false);

        editNome = (EditText) view.findViewById(R.id.nomeInstrutor);
        editEmail = (EditText) view.findViewById(R.id.emailInstrutor);

        atualizarViews((Instrutor) fragmentoParametro.getEntidade());
        getDialog().setTitle(fragmentoParametro.getTitulo());
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button btnCancelar = (Button) view.findViewById(R.id.btnCancelarInstrutor);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btnSalvar = (Button) view.findViewById(R.id.btnSalvarInstrutor);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarObjeto();
            }
        });

        return view;
    }

    private void salvarObjeto() {
        Instrutor objeto = (Instrutor) fragmentoParametro.getEntidade();

        if(objeto == null) {
            objeto = new Instrutor();
        }

        int erros = 0;

        if(Util.isVazio(editNome.getText())) {
            editNome.setError(fragmentoOuvinte.getMsgCampoObrigatorio());
            erros++;
        }

        if(Util.isVazio(editEmail.getText())) {
            editEmail.setError(fragmentoOuvinte.getMsgCampoObrigatorio());
            erros++;
        }

        if(erros > 0) {
            return;
        }

        atualizarObjeto(objeto);
        fragmentoOuvinte.salvar(objeto);
        dismiss();
    }

    private void atualizarViews(Instrutor objeto) {
        if(objeto == null) {
            editNome.setText(Constantes.VAZIO);
            editEmail.setText(Constantes.VAZIO);
        } else {
            editNome.setText(objeto.getNome());
            editEmail.setText(objeto.getEmail());
        }
    }

    private void atualizarObjeto(Instrutor objeto) {
        objeto.setNome(editNome.getText().toString());
        objeto.setEmail(editEmail.getText().toString());
    }

    public void exibir(FragmentManager fm) {
        show(fm, Constantes.CLIENTE);
    }
}