package florencio.com.br.chamada.fragmento.curso;

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
import florencio.com.br.chamada.dominio.Curso;
import florencio.com.br.chamada.fragmento.FragmentoDialogo;
import florencio.com.br.chamada.fragmento.FragmentoOuvinte;
import florencio.com.br.chamada.fragmento.FragmentoParametro;
import florencio.com.br.chamada.util.Constantes;
import florencio.com.br.chamada.util.Util;

public class CursoDialogo extends DialogFragment implements FragmentoDialogo {
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
        View view = inflater.inflate(R.layout.curso_layout, container, false);

        editNome = (EditText) view.findViewById(R.id.nomeCurso);
        editDescricao = (EditText) view.findViewById(R.id.descricaoCurso);

        atualizarViews((Curso) fragmentoParametro.getEntidade());
        getDialog().setTitle(fragmentoParametro.getTitulo());
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button btnCancelar = (Button) view.findViewById(R.id.btnCancelarCurso);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btnSalvar = (Button) view.findViewById(R.id.btnSalvarCurso);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarObjeto();
            }
        });

        return view;
    }

    private void salvarObjeto() {
        Curso objeto = (Curso) fragmentoParametro.getEntidade();

        if(objeto == null) {
            objeto = new Curso();
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

    private void atualizarViews(Curso objeto) {
        if(objeto == null) {
            editNome.setText(Constantes.VAZIO);
            editDescricao.setText(Constantes.VAZIO);
        } else {
            editNome.setText(objeto.getNome());
            editDescricao.setText(objeto.getDescricao());
        }
    }

    private void atualizarObjeto(Curso objeto) {
        objeto.setNome(editNome.getText().toString());
        objeto.setDescricao(editDescricao.getText().toString());
    }

    public void exibir(FragmentManager fm) {
        show(fm, Constantes.CLIENTE);
    }
}