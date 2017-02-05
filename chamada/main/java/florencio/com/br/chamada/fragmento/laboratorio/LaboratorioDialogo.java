package florencio.com.br.chamada.fragmento.laboratorio;

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
import florencio.com.br.chamada.dominio.Laboratorio;
import florencio.com.br.chamada.fragmento.FragmentoDialogo;
import florencio.com.br.chamada.fragmento.FragmentoOuvinte;
import florencio.com.br.chamada.fragmento.FragmentoParametro;
import florencio.com.br.chamada.util.Constantes;
import florencio.com.br.chamada.util.Util;

public class LaboratorioDialogo extends DialogFragment implements FragmentoDialogo {
    private FragmentoParametro fragmentoParametro;
    private FragmentoOuvinte fragmentoOuvinte;
    private EditText editCapacidade;
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
        View view = inflater.inflate(R.layout.laboratorio_layout, container, false);

        editNome = (EditText) view.findViewById(R.id.nomeLaboratorio);
        editCapacidade = (EditText) view.findViewById(R.id.capacidadeLaboratorio);

        atualizarViews((Laboratorio) fragmentoParametro.getEntidade());
        getDialog().setTitle(fragmentoParametro.getTitulo());
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button btnCancelar = (Button) view.findViewById(R.id.btnCancelarLaboratorio);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btnSalvar = (Button) view.findViewById(R.id.btnSalvarLaboratorio);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarObjeto();
            }
        });

        return view;
    }

    private void salvarObjeto() {
        Laboratorio objeto = (Laboratorio) fragmentoParametro.getEntidade();

        if(objeto == null) {
            objeto = new Laboratorio();
        }

        int erros = 0;

        if(Util.isVazio(editNome.getText())) {
            editNome.setError(fragmentoOuvinte.getMsgCampoObrigatorio());
            erros++;
        }

        if(Util.isVazio(editCapacidade.getText())) {
            editCapacidade.setError(fragmentoOuvinte.getMsgCampoObrigatorio());
            erros++;
        }

        if(erros > 0) {
            return;
        }

        atualizarObjeto(objeto);
        fragmentoOuvinte.salvar(objeto);
        dismiss();
    }

    private void atualizarViews(Laboratorio objeto) {
        if(objeto == null) {
            editNome.setText(Constantes.VAZIO);
            editCapacidade.setText(Constantes.VAZIO);
        } else {
            editNome.setText(objeto.getNome());
            editCapacidade.setText(objeto.getCapacidade().toString());
        }
    }

    private void atualizarObjeto(Laboratorio objeto) {
        objeto.setNome(editNome.getText().toString());
        objeto.setCapacidade(Integer.valueOf(editCapacidade.getText().toString()));
    }

    public void exibir(FragmentManager fm) {
        show(fm, Constantes.CLIENTE);
    }
}