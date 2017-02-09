package florencio.com.br.controle;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import florencio.com.br.controle.dominio.Controle;

public class ControleAdaptador extends BaseAdapter {
    private List<Controle> controles;
    private Context contexto;

    public ControleAdaptador(Context contexto, List<Controle> controles) {
        this.controles = controles;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return controles.size();
    }

    @Override
    public Object getItem(int position) {
        return controles.get(position);
    }

    @Override
    public long getItemId(int position) {
        Controle c = controles.get(position);
        return c.get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Controle c = controles.get(position);

        View layout = LayoutInflater.from(contexto).inflate(R.layout.controle_registro_layout, null);

        TextView txtDescricao = (TextView) layout.findViewById(R.id.txtDescricao);

        txtDescricao.setText(c.getDescricao());

        if("C".equals(c.getTipo())) {
            txtDescricao.setTextColor(Color.BLUE);
        } else {
            txtDescricao.setTextColor(Color.RED);
        }

        return layout;
    }
}
