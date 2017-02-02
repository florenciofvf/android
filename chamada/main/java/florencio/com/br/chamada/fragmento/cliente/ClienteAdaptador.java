package florencio.com.br.chamada.fragmento.cliente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.Cliente;

public class ClienteAdaptador extends BaseAdapter {
    private final List<Cliente> clientes;
    private final boolean modoExclusao;
    private final Context contexto;

    public ClienteAdaptador(List<Cliente> clientes, Context contexto, boolean modoExclusao) {
        this.modoExclusao = modoExclusao;
        this.clientes = clientes;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return clientes.size();
    }

    @Override
    public Object getItem(int position) {
        return clientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        Cliente cliente = clientes.get(position);
        return cliente.get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cliente cliente = clientes.get(position);

        if(convertView == null) {
            if(modoExclusao) {
                convertView = LayoutInflater.from(contexto).inflate(R.layout.cliente_registro_excluir_layout, null);
            } else {
                convertView = LayoutInflater.from(contexto).inflate(R.layout.cliente_registro_layout, null);
            }

            Cache cache = new Cache(convertView, modoExclusao);

            if(modoExclusao) {
                cache.selecionado.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox checkBox = (CheckBox)v;
                        Cliente cliente = (Cliente)checkBox.getTag();
                        cliente.setSelecionado(checkBox.isChecked());
                        notifyDataSetChanged();
                    }
                });
            }

            convertView.setTag(cache);
        }

        Cache cache = (Cache)convertView.getTag();
        cache.refresh(cliente);

        if(modoExclusao) {
            cache.selecionado.setChecked(cliente.isSelecionado());
            cache.selecionado.setTag(cliente);
        }

        return convertView;
    }

    static class Cache {
        TextView nome;
        TextView email;
        CheckBox selecionado;
        boolean modoExclusao;

        Cache(View view, boolean modoExclusao) {
            this.modoExclusao = modoExclusao;

            if(modoExclusao) {
                nome = (TextView) view.findViewById(R.id.nomeClienteExcluir);
                email = (TextView) view.findViewById(R.id.emailClienteExcluir);
                selecionado = (CheckBox)view.findViewById(R.id.selecionadoCliente);
            } else {
                nome = (TextView) view.findViewById(R.id.nomeCliente);
                email = (TextView) view.findViewById(R.id.emailCliente);
            }
        }

        void refresh(Cliente cliente) {
            nome.setText(cliente.getNome());
            email.setText(cliente.getEmail());
        }
    }
}