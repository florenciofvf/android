package florencio.com.br.chamada.fragmento.status_chamada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.StatusChamada;
import florencio.com.br.chamada.util.Constantes;

public class StatusChamadaAdaptador extends BaseAdapter {
    private final List<StatusChamada> objetos;
    private final boolean modoExclusao;
    private final Context contexto;

    public StatusChamadaAdaptador(List<StatusChamada> objetos, Context contexto, boolean modoExclusao) {
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
        StatusChamada objeto = objetos.get(position);
        return objeto.get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StatusChamada objeto = objetos.get(position);

        if(convertView == null) {
            if(modoExclusao) {
                convertView = LayoutInflater.from(contexto).inflate(R.layout.status_chamada_registro_excluir_layout, null);
            } else {
                convertView = LayoutInflater.from(contexto).inflate(R.layout.status_chamada_registro_layout, null);
            }

            Cache cache = new Cache(convertView, modoExclusao);

            if(modoExclusao) {
                cache.selecionado.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox checkBox = (CheckBox)v;
                        StatusChamada objeto = (StatusChamada)checkBox.getTag();
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
        TextView letra;
        TextView descricao;
        CheckBox selecionado;
        boolean modoExclusao;

        Cache(View view, boolean modoExclusao) {
            this.modoExclusao = modoExclusao;

            if(modoExclusao) {
                letra = (TextView) view.findViewById(R.id.letraStatusChamadaExcluir);
                descricao = (TextView) view.findViewById(R.id.descricaoStatusChamadaExcluir);
                selecionado = (CheckBox)view.findViewById(R.id.selecionadoStatusChamada);
            } else {
                letra = (TextView) view.findViewById(R.id.letraStatusChamada);
                descricao = (TextView) view.findViewById(R.id.descricaoStatusChamada);
            }
        }

        void atualizarViews(StatusChamada objeto) {
            letra.setText(objeto.getLetra());
            descricao.setText(Integer.toString(objeto.getOrdem()) + Constantes.TRACO + objeto.getDescricao());
        }
    }
}