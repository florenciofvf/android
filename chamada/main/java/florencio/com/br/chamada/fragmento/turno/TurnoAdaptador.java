package florencio.com.br.chamada.fragmento.turno;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.Turno;

public class TurnoAdaptador extends BaseAdapter {
    private final List<Turno> objetos;
    private final boolean modoExclusao;
    private final Context contexto;

    public TurnoAdaptador(List<Turno> objetos, Context contexto, boolean modoExclusao) {
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
        Turno objeto = objetos.get(position);
        return objeto.get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Turno objeto = objetos.get(position);

        if(convertView == null) {
            if(modoExclusao) {
                convertView = LayoutInflater.from(contexto).inflate(R.layout.turno_registro_excluir_layout, null);
            } else {
                convertView = LayoutInflater.from(contexto).inflate(R.layout.turno_registro_layout, null);
            }

            Cache cache = new Cache(convertView, modoExclusao);

            if(modoExclusao) {
                cache.selecionado.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox checkBox = (CheckBox)v;
                        Turno objeto = (Turno)checkBox.getTag();
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
        TextView descricao;
        CheckBox selecionado;
        boolean modoExclusao;

        Cache(View view, boolean modoExclusao) {
            this.modoExclusao = modoExclusao;

            if(modoExclusao) {
                nome = (TextView) view.findViewById(R.id.nomeTurnoExcluir);
                descricao = (TextView) view.findViewById(R.id.descricaoTurnoExcluir);
                selecionado = (CheckBox)view.findViewById(R.id.selecionadoTurno);
            } else {
                nome = (TextView) view.findViewById(R.id.nomeTurno);
                descricao = (TextView) view.findViewById(R.id.descricaoTurno);
            }
        }

        void atualizarViews(Turno objeto) {
            nome.setText(objeto.getNome());
            descricao.setText(objeto.getDescricao());
        }
    }
}