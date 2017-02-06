package florencio.com.br.chamada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import florencio.com.br.chamada.dominio.Entidade;

public class EntidadeAdaptador extends BaseAdapter {
    private final List<Entidade> objetos;
    private final Context contexto;

    public EntidadeAdaptador(List<Entidade> objetos, Context contexto) {
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
        Entidade objeto = objetos.get(position);
        return objeto.get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Entidade objeto = objetos.get(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(contexto).inflate(R.layout.entidade_layout, null);

            Cache cache = new Cache(convertView);
            convertView.setTag(cache);
        }

        Cache cache = (Cache)convertView.getTag();
        cache.atualizarViews(objeto);

        return convertView;
    }

    static class Cache {
        TextView thisView;

        Cache(View view) {
            thisView = (TextView) view.findViewById(R.id.stringEntidade);
        }

        void atualizarViews(Entidade objeto) {
            thisView.setText(objeto.toString());
        }
    }
}