package florencio.com.br.chamada.fragmento.cliente;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.Cliente;
import florencio.com.br.chamada.fragmento.FragmentoDialogo;
import florencio.com.br.chamada.fragmento.FragmentoOuvinte;
import florencio.com.br.chamada.fragmento.FragmentoParametro;
import florencio.com.br.chamada.util.Constantes;

public class ClienteDialogo extends DialogFragment implements FragmentoDialogo {
    private FragmentoParametro fragmentoParametro;
    private FragmentoOuvinte fragmentoOuvinte;
    private EditText editNome;
    private EditText editEmail;

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
        View view = inflater.inflate(R.layout.cliente_layout, container, false);

        editNome = (EditText) view.findViewById(R.id.nomeCliente);
        editEmail = (EditText) view.findViewById(R.id.emailCliente);

        atualizarViews((Cliente) fragmentoParametro.getEntidade());

        Button btnCancelar = (Button) view.findViewById(R.id.btnCancelarCliente);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle(fragmentoParametro.getTitulo());

        return view;
    }

    private void atualizarViews(Cliente cliente) {
        if(cliente == null) {
            editNome.setText(Constantes.VAZIO);
            editEmail.setText(Constantes.VAZIO);
        } else {
            editNome.setText(cliente.getNome());
            editEmail.setText(cliente.getEmail());
        }
    }

    private void atualizarCliente(Cliente cliente) {
        cliente.setNome(editNome.getText().toString());
        cliente.setEmail(editEmail.getText().toString());
    }

    public void exibir(FragmentManager fm) {
        if(fm.findFragmentByTag(Constantes.CLIENTE) == null) {
            show(fm, Constantes.CLIENTE);
        }
    }
}