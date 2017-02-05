package florencio.com.br.chamada.fragmento.status_turma;

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
import florencio.com.br.chamada.dominio.StatusTurma;
import florencio.com.br.chamada.fragmento.FragmentoDialogo;
import florencio.com.br.chamada.fragmento.FragmentoOuvinte;
import florencio.com.br.chamada.fragmento.FragmentoParametro;
import florencio.com.br.chamada.util.Constantes;
import florencio.com.br.chamada.util.Util;

public class StatusTurmaDialogo extends DialogFragment implements FragmentoDialogo {
    private FragmentoParametro fragmentoParametro;
    private FragmentoOuvinte fragmentoOuvinte;
    private EditText editDescricao;
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
        View view = inflater.inflate(R.layout.status_turma_layout, container, false);

        editNome = (EditText) view.findViewById(R.id.nomeStatusTurma);
        editDescricao = (EditText) view.findViewById(R.id.descricaoStatusTurma);

        atualizarViews((StatusTurma) fragmentoParametro.getEntidade());
        getDialog().setTitle(fragmentoParametro.getTitulo());
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button btnCancelar = (Button) view.findViewById(R.id.btnCancelarStatusTurma);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btnSalvar = (Button) view.findViewById(R.id.btnSalvarStatusTurma);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarObjeto();
            }
        });

        return view;
    }

    private void salvarObjeto() {
        StatusTurma objeto = (StatusTurma) fragmentoParametro.getEntidade();

        if(objeto == null) {
            objeto = new StatusTurma();
        }

        int erros = 0;

        if(Util.isVazio(editNome.getText())) {
            editNome.setError(fragmentoOuvinte.getMsgCampoObrigatorio());
            erros++;
        }

        if(Util.isVazio(editDescricao.getText())) {
            editDescricao.setError(fragmentoOuvinte.getMsgCampoObrigatorio());
            erros++;
        }

        if(erros > 0) {
            return;
        }

        atualizarObjeto(objeto);
        fragmentoOuvinte.salvar(objeto);
        dismiss();
    }

    private void atualizarViews(StatusTurma objeto) {
        if(objeto == null) {
            editNome.setText(Constantes.VAZIO);
            editDescricao.setText(Constantes.VAZIO);
        } else {
            editNome.setText(objeto.getNome());
            editDescricao.setText(objeto.getDescricao());
        }
    }

    private void atualizarObjeto(StatusTurma objeto) {
        objeto.setNome(editNome.getText().toString());
        objeto.setDescricao(editDescricao.getText().toString());
    }

    public void exibir(FragmentManager fm) {
        show(fm, Constantes.CLIENTE);
    }
}