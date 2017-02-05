package florencio.com.br.chamada.fragmento.laboratorio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.Laboratorio;

public class LaboratorioAdaptador extends BaseAdapter {
    private final List<Laboratorio> objetos;
    private final boolean modoExclusao;
    private final Context contexto;

    public LaboratorioAdaptador(List<Laboratorio> objetos, Context contexto, boolean modoExclusao) {
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
        Laboratorio objeto = objetos.get(position);
        return objeto.get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Laboratorio objeto = objetos.get(position);

        if(convertView == null) {
            if(modoExclusao) {
                convertView = LayoutInflater.from(contexto).inflate(R.layout.laboratorio_registro_excluir_layout, null);
            } else {
                convertView = LayoutInflater.from(contexto).inflate(R.layout.laboratorio_registro_layout, null);
            }

            Cache cache = new Cache(convertView, modoExclusao);

            if(modoExclusao) {
                cache.selecionado.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox checkBox = (CheckBox)v;
                        Laboratorio objeto = (Laboratorio)checkBox.getTag();
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
        TextView capacidade;
        CheckBox selecionado;
        boolean modoExclusao;

        Cache(View view, boolean modoExclusao) {
            this.modoExclusao = modoExclusao;

            if(modoExclusao) {
                nome = (TextView) view.findViewById(R.id.nomeLaboratorioExcluir);
                capacidade = (TextView) view.findViewById(R.id.capacidadeLaboratorioExcluir);
                selecionado = (CheckBox)view.findViewById(R.id.selecionadoLaboratorio);
            } else {
                nome = (TextView) view.findViewById(R.id.nomeLaboratorio);
                capacidade = (TextView) view.findViewById(R.id.capacidadeLaboratorio);
            }
        }

        void atualizarViews(Laboratorio objeto) {
            nome.setText(objeto.getNome());
            capacidade.setText(objeto.getCapacidade().toString());
        }
    }
}