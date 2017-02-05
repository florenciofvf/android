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
    private final List<Cliente> objetos;
    private final boolean modoExclusao;
    private final Context contexto;

    public ClienteAdaptador(List<Cliente> objetos, Context contexto, boolean modoExclusao) {
        this.modoExclusao = modoExclusao;
        this.objetos = objetos;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return objetos.size();
    }

    @Override
    public Object getItem(int position) {
        return objetos.get(position);
    }

    @Override
    public long getItemId(int position) {
        Cliente objeto = objetos.get(position);
        return objeto.get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cliente objeto = objetos.get(position);

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
                        Cliente objeto = (Cliente)checkBox.getTag();
                        objeto.setSelecionado(checkBox.isChecked());
                        notifyDataSetChanged();
                    }
                });
            }

            convertView.setTag(cache);
        }

        Cache cache = (Cache)convertView.getTag();
        cache.atualizarViews(objeto);

        if(modoExclusao) {
            cache.selecionado.setChecked(objeto.isSelecionado());
            cache.selecionado.setTag(objeto);
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

        void atualizarViews(Cliente objeto) {
            nome.setText(objeto.getNome());
            email.setText(objeto.getEmail());
        }
    }
}