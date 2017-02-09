package florencio.com.br.chamada.fragmento.status_chamada;

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
import florencio.com.br.chamada.dominio.StatusChamada;
import florencio.com.br.chamada.fragmento.FragmentoDialogo;
import florencio.com.br.chamada.fragmento.FragmentoOuvinte;
import florencio.com.br.chamada.fragmento.FragmentoParametro;
import florencio.com.br.chamada.util.Constantes;
import florencio.com.br.chamada.util.Util;

public class StatusChamadaDialogo extends DialogFragment implements FragmentoDialogo {
    private FragmentoParametro fragmentoParametro;
    private FragmentoOuvinte fragmentoOuvinte;
    private EditText editDescricao;
    private EditText editLetra;
    private EditText editOrdem;

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
        View view = inflater.inflate(R.layout.status_chamada_layout, container, false);

        editLetra = (EditText) view.findViewById(R.id.letraStatusChamada);
        editOrdem = (EditText) view.findViewById(R.id.ordemStatusChamada);
        editDescricao = (EditText) view.findViewById(R.id.descricaoStatusChamada);

        atualizarViews((StatusChamada) fragmentoParametro.getEntidade());
        getDialog().setTitle(fragmentoParametro.getTitulo());
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button btnCancelar = (Button) view.findViewById(R.id.btnCancelarStatusChamada);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btnSalvar = (Button) view.findViewById(R.id.btnSalvarStatusChamada);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarObjeto();
            }
        });

        return view;
    }

    private void salvarObjeto() {
        StatusChamada objeto = (StatusChamada) fragmentoParametro.getEntidade();

        if(objeto == null) {
            objeto = new StatusChamada();
        }

        int erros = 0;

        if(Util.isVazio(editLetra.getText())) {
            editLetra.setError(fragmentoOuvinte.getMsgCampoObrigatorio());
            erros++;
        }

        if(Util.isVazio(editDescricao.getText())) {
            editDescricao.setError(fragmentoOuvinte.getMsgCampoObrigatorio());
            erros++;
        }

        if(Util.isVazio(editOrdem.getText())) {
            editOrdem.setError(fragmentoOuvinte.getMsgCampoObrigatorio());
            erros++;
        }

        if(erros > 0) {
            return;
        }

        atualizarObjeto(objeto);
        fragmentoOuvinte.salvar(objeto);
        dismiss();
    }

    private void atualizarViews(StatusChamada objeto) {
        if(objeto == null) {
            editLetra.setText(Constantes.VAZIO);
            editDescricao.setText(Constantes.VAZIO);
            editOrdem.setText(Constantes.VAZIO);
        } else {
            editLetra.setText(objeto.getLetra());
            editDescricao.setText(objeto.getDescricao());
            editOrdem.setText(Integer.toString(objeto.getOrdem()));
        }
    }

    private void atualizarObjeto(StatusChamada objeto) {
        objeto.setLetra(editLetra.getText().toString());
        objeto.setDescricao(editDescricao.getText().toString());
        objeto.setOrdem(Integer.valueOf(editOrdem.getText().toString()));
    }

    public void exibir(FragmentManager fm) {
        show(fm, Constantes.CLIENTE);
    }
}