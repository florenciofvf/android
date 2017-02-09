package florencio.com.br.chamada.fragmento.matricula;

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
import android.widget.Spinner;

import java.util.Date;

import florencio.com.br.chamada.EntidadeAdaptador;
import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.Cliente;
import florencio.com.br.chamada.dominio.Entidade;
import florencio.com.br.chamada.dominio.Matricula;
import florencio.com.br.chamada.dominio.Turma;
import florencio.com.br.chamada.fragmento.FragmentoDialogo;
import florencio.com.br.chamada.fragmento.FragmentoOuvinte;
import florencio.com.br.chamada.fragmento.FragmentoParametro;
import florencio.com.br.chamada.util.Constantes;
import florencio.com.br.chamada.util.Util;

public class MatriculaDialogo extends DialogFragment implements FragmentoDialogo {
    private FragmentoParametro fragmentoParametro;
    private FragmentoOuvinte fragmentoOuvinte;
    private Spinner comboCliente;
    private EditText editData;

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
        View view = inflater.inflate(R.layout.matricula_layout, container, false);

        editData = (EditText) view.findViewById(R.id.dataMatricula);

        comboCliente = (Spinner) view.findViewById(R.id.clientes);
        comboCliente.setPrompt(getString(R.string.label_cliente));
        comboCliente.setAdapter(new EntidadeAdaptador(fragmentoOuvinte.getChamadaServico().listarParaCombo(new Cliente(getString(R.string.label_cliente))), getActivity()));

        atualizarViews((Matricula) fragmentoParametro.getEntidade());
        getDialog().setTitle(fragmentoParametro.getTitulo());
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button btnCancelar = (Button) view.findViewById(R.id.btnCancelarMatricula);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btnSalvar = (Button) view.findViewById(R.id.btnSalvarMatricula);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarObjeto();
            }
        });

        return view;
    }

    private void salvarObjeto() {
        Matricula objeto = (Matricula) fragmentoParametro.getEntidade();

        if(objeto == null) {
            objeto = new Matricula();
        }

        int erros = 0;

        if(Util.isVazio(editData.getText())) {
            editData.setError(fragmentoOuvinte.getMsgCampoObrigatorio());
            erros++;
        }

        if(((Entidade)comboCliente.getSelectedItem()).ehNovo()) {
            comboCliente.setPrompt(getString(R.string.msg_erro_campo_obrigatorio));
            erros++;
        }

        if(erros > 0) {
            return;
        }

        Turma turma = (Turma) fragmentoParametro.getEntidade(Constantes.TURMA);
        objeto.setTurma(turma);
        atualizarObjeto(objeto);
        fragmentoOuvinte.salvar(objeto);
        dismiss();
    }

    private void atualizarViews(Matricula objeto) {
        if(objeto == null) {
            editData.setText(Util.formatarDate(new Date().getTime()));
            comboCliente.setSelection(0);
        } else {
            editData.setText(Util.formatarDate(objeto.getData()));
            Util.setSelected(comboCliente, objeto.getCliente());
        }
    }

    private void atualizarObjeto(Matricula objeto) {
        objeto.setData(Util.parseDate(editData.getText().toString()));
        objeto.setCliente((Cliente) comboCliente.getSelectedItem());
    }

    public void exibir(FragmentManager fm) {
        show(fm, Constantes.CLIENTE);
    }
}