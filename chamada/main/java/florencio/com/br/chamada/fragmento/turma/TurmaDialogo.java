package florencio.com.br.chamada.fragmento.turma;

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
import florencio.com.br.chamada.dominio.Curso;
import florencio.com.br.chamada.dominio.Entidade;
import florencio.com.br.chamada.dominio.Frequencia;
import florencio.com.br.chamada.dominio.Instrutor;
import florencio.com.br.chamada.dominio.Laboratorio;
import florencio.com.br.chamada.dominio.StatusTurma;
import florencio.com.br.chamada.dominio.Turma;
import florencio.com.br.chamada.dominio.Turno;
import florencio.com.br.chamada.fragmento.FragmentoDialogo;
import florencio.com.br.chamada.fragmento.FragmentoOuvinte;
import florencio.com.br.chamada.fragmento.FragmentoParametro;
import florencio.com.br.chamada.util.Constantes;
import florencio.com.br.chamada.util.Util;

public class TurmaDialogo extends DialogFragment implements FragmentoDialogo {
    private FragmentoParametro fragmentoParametro;
    private FragmentoOuvinte fragmentoOuvinte;
    private Spinner comboLaboratorio;
    private Spinner comboStatusTurma;
    private Spinner comboFrequencia;
    private Spinner comboInstrutor;
    private EditText editInicio;
    private Spinner comboCurso;
    private Spinner comboTurno;

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
        View view = inflater.inflate(R.layout.turma_layout, container, false);

        editInicio = (EditText) view.findViewById(R.id.inicioTurma);

        comboCurso = (Spinner) view.findViewById(R.id.cursos);
        comboCurso.setPrompt(getString(R.string.label_curso));
        comboCurso.setAdapter(new EntidadeAdaptador(fragmentoOuvinte.getChamadaServico().listarParaCombo(new Curso(getString(R.string.label_curso))), getActivity()));

        comboInstrutor = (Spinner) view.findViewById(R.id.instrutores);
        comboInstrutor.setPrompt(getString(R.string.label_instrutor));
        comboInstrutor.setAdapter(new EntidadeAdaptador(fragmentoOuvinte.getChamadaServico().listarParaCombo(new Instrutor(getString(R.string.label_instrutor))), getActivity()));

        comboLaboratorio = (Spinner) view.findViewById(R.id.laboratorios);
        comboLaboratorio.setPrompt(getString(R.string.label_laboratorio));
        comboLaboratorio.setAdapter(new EntidadeAdaptador(fragmentoOuvinte.getChamadaServico().listarParaCombo(new Laboratorio(getString(R.string.label_laboratorio))), getActivity()));

        comboFrequencia = (Spinner) view.findViewById(R.id.frequencias);
        comboFrequencia.setPrompt(getString(R.string.label_frequencia));
        comboFrequencia.setAdapter(new EntidadeAdaptador(fragmentoOuvinte.getChamadaServico().listarParaCombo(new Frequencia(getString(R.string.label_frequencia))), getActivity()));

        comboStatusTurma = (Spinner) view.findViewById(R.id.statusTurma);
        comboStatusTurma.setPrompt(getString(R.string.label_status));
        comboStatusTurma.setAdapter(new EntidadeAdaptador(fragmentoOuvinte.getChamadaServico().listarParaCombo(new StatusTurma(getString(R.string.label_status))), getActivity()));

        comboTurno = (Spinner) view.findViewById(R.id.turnos);
        comboTurno.setPrompt(getString(R.string.label_turno));
        comboTurno.setAdapter(new EntidadeAdaptador(fragmentoOuvinte.getChamadaServico().listarParaCombo(new Turno(getString(R.string.label_turno))), getActivity()));

        atualizarViews((Turma) fragmentoParametro.getEntidade());
        getDialog().setTitle(fragmentoParametro.getTitulo());
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        Button btnCancelar = (Button) view.findViewById(R.id.btnCancelarTurma);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btnSalvar = (Button) view.findViewById(R.id.btnSalvarTurma);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarObjeto();
            }
        });

        return view;
    }

    private void salvarObjeto() {
        Turma objeto = (Turma) fragmentoParametro.getEntidade();

        if(objeto == null) {
            objeto = new Turma();
        }

        int erros = 0;

        if(Util.isVazio(editInicio.getText())) {
            editInicio.setError(fragmentoOuvinte.getMsgCampoObrigatorio());
            erros++;
        }

        if(((Entidade)comboCurso.getSelectedItem()).ehNovo()) {
            comboCurso.setPrompt(getString(R.string.msg_erro_campo_obrigatorio));
            erros++;
        }

        if(((Entidade)comboInstrutor.getSelectedItem()).ehNovo()) {
            comboInstrutor.setPrompt(getString(R.string.msg_erro_campo_obrigatorio));
            erros++;
        }

        if(((Entidade)comboLaboratorio.getSelectedItem()).ehNovo()) {
            comboLaboratorio.setPrompt(getString(R.string.msg_erro_campo_obrigatorio));
            erros++;
        }

        if(((Entidade)comboFrequencia.getSelectedItem()).ehNovo()) {
            comboFrequencia.setPrompt(getString(R.string.msg_erro_campo_obrigatorio));
            erros++;
        }

        if(((Entidade)comboStatusTurma.getSelectedItem()).ehNovo()) {
            comboStatusTurma.setPrompt(getString(R.string.msg_erro_campo_obrigatorio));
            erros++;
        }

        if(((Entidade)comboTurno.getSelectedItem()).ehNovo()) {
            comboTurno.setPrompt(getString(R.string.msg_erro_campo_obrigatorio));
            erros++;
        }

        if(erros > 0) {
            return;
        }

        atualizarObjeto(objeto);
        fragmentoOuvinte.salvar(objeto);
        dismiss();
    }

    private void atualizarViews(Turma objeto) {
        if(objeto == null) {
            editInicio.setText(Util.formatarDate(new Date().getTime()));
            comboCurso.setSelection(0);
            comboInstrutor.setSelection(0);
            comboLaboratorio.setSelection(0);
            comboFrequencia.setSelection(0);
            comboStatusTurma.setSelection(0);
            comboTurno.setSelection(0);
        } else {
            editInicio.setText(Util.formatarDate(objeto.getInicio()));
            Util.setSelected(comboCurso, objeto.getCurso());
            Util.setSelected(comboInstrutor, objeto.getInstrutor());
            Util.setSelected(comboLaboratorio, objeto.getLaboratorio());
            Util.setSelected(comboFrequencia, objeto.getFrequencia());
            Util.setSelected(comboStatusTurma, objeto.getStatusTurma());
            Util.setSelected(comboTurno, objeto.getTurno());
        }
    }

    private void atualizarObjeto(Turma objeto) {
        objeto.setInicio(Util.parseDate(editInicio.getText().toString()));
        objeto.setCurso((Curso) comboCurso.getSelectedItem());
        objeto.setInstrutor((Instrutor) comboInstrutor.getSelectedItem());
        objeto.setLaboratorio((Laboratorio) comboLaboratorio.getSelectedItem());
        objeto.setFrequencia((Frequencia) comboFrequencia.getSelectedItem());
        objeto.setStatusTurma((StatusTurma) comboStatusTurma.getSelectedItem());
        objeto.setTurno((Turno) comboTurno.getSelectedItem());
    }

    public void exibir(FragmentManager fm) {
        show(fm, Constantes.CLIENTE);
    }
}