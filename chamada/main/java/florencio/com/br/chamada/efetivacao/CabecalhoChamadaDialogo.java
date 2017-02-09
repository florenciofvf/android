package florencio.com.br.chamada.efetivacao;

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

import java.util.Date;

import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.Entidade;
import florencio.com.br.chamada.dominio.CabecalhoChamada;
import florencio.com.br.chamada.dominio.Turma;
import florencio.com.br.chamada.fragmento.FragmentoDialogo;
import florencio.com.br.chamada.fragmento.FragmentoParametro;
import florencio.com.br.chamada.util.Constantes;
import florencio.com.br.chamada.util.Util;

public class CabecalhoChamadaDialogo extends DialogFragment implements FragmentoDialogo {
    private CabecalhoChamadaOuvinte cabecalhoChamadaOuvinte;
    private FragmentoParametro fragmentoParametro;
    private EditText editObservacao;
    private EditText editDataHora;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        cabecalhoChamadaOuvinte = (CabecalhoChamadaOuvinte) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentoParametro = (FragmentoParametro) getArguments().getSerializable(Constantes.FRAGMENTO_PARAMETRO);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cabecalho_chamada_dialogo_layout, container, false);

        editDataHora = (EditText) view.findViewById(R.id.dataHoraCabecalhoChamada);
        editObservacao = (EditText) view.findViewById(R.id.observacaoCabecalhoChamada);

        atualizarViews((CabecalhoChamada) fragmentoParametro.getEntidade());
        getDialog().setTitle(fragmentoParametro.getTitulo());
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button btnCancelar = (Button) view.findViewById(R.id.btnCancelarCabecalhoChamada);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btnSalvar = (Button) view.findViewById(R.id.btnSalvarCabecalhoChamada);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarObjeto();
            }
        });

        return view;
    }

    private void salvarObjeto() {
        CabecalhoChamada objeto = (CabecalhoChamada) fragmentoParametro.getEntidade();

        if(objeto == null) {
            objeto = new CabecalhoChamada();
        }

        int erros = 0;

        if(Util.isVazio(editDataHora.getText())) {
            editDataHora.setError(getString(R.string.msg_erro_campo_obrigatorio));
            erros++;
        }

        if(erros > 0) {
            return;
        }

        Turma turma = (Turma) fragmentoParametro.getEntidade(Constantes.TURMA);
        objeto.setTurma(turma);
        atualizarObjeto(objeto);
        cabecalhoChamadaOuvinte.salvarCabecalhoChamada(objeto);
        dismiss();
    }

    private void atualizarViews(CabecalhoChamada objeto) {
        if(objeto == null) {
            editDataHora.setText(Util.formatarDate(new Date().getTime()));
            editObservacao.setText(Constantes.VAZIO);
        } else {
            editDataHora.setText(Util.formatarDate(objeto.getDataHora()));
            editObservacao.setText(objeto.getObservacao());
        }
    }

    private void atualizarObjeto(CabecalhoChamada objeto) {
        objeto.setDataHora(Util.parseDate(editDataHora.getText().toString()));
        objeto.setObservacao(editObservacao.getText().toString());
    }

    public void exibir(FragmentManager fm) {
        show(fm, Constantes.CLIENTE);
    }

    public static interface CabecalhoChamadaOuvinte {
        public void salvarCabecalhoChamada(Entidade entidade);
    }
}