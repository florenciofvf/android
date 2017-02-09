package florencio.com.br.chamada.fragmento.turma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.Turma;
import florencio.com.br.chamada.util.Constantes;
import florencio.com.br.chamada.util.Util;

public class TurmaAdaptador extends BaseAdapter {
    private final List<Turma> objetos;
    private final boolean modoExclusao;
    private final Context contexto;

    public TurmaAdaptador(List<Turma> objetos, Context contexto, boolean modoExclusao) {
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
        Turma objeto = objetos.get(position);
        return objeto.get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Turma objeto = objetos.get(position);

        if(convertView == null) {
            if(modoExclusao) {
                convertView = LayoutInflater.from(contexto).inflate(R.layout.turma_registro_excluir_layout, null);
            } else {
                convertView = LayoutInflater.from(contexto).inflate(R.layout.turma_registro_layout, null);
            }

            Cache cache = new Cache(convertView, modoExclusao);

            if(modoExclusao) {
                cache.selecionado.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox checkBox = (CheckBox)v;
                        Turma objeto = (Turma)checkBox.getTag();
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
        TextView inicio;
        TextView nomeCurso;
        CheckBox selecionado;
        boolean modoExclusao;

        Cache(View view, boolean modoExclusao) {
            this.modoExclusao = modoExclusao;

            if(modoExclusao) {
                inicio = (TextView) view.findViewById(R.id.inicioTurmaExcluir);
                nomeCurso = (TextView) view.findViewById(R.id.nomeCursoExcluir);
                selecionado = (CheckBox)view.findViewById(R.id.selecionadoTurma);
            } else {
                inicio = (TextView) view.findViewById(R.id.inicioTurma);
                nomeCurso = (TextView) view.findViewById(R.id.nomeCurso);
            }
        }

        void atualizarViews(Turma objeto) {
            nomeCurso.setText(objeto.getCurso().getNome());
            inicio.setText(Util.formatarDate(objeto.getInicio()) + Constantes.TRACO +
                    objeto.getTurno().getNome() + Constantes.TRACO +
                    objeto.getStatusTurma().getNome());
        }
    }
}