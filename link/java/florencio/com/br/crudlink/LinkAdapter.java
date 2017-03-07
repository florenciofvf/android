package florencio.com.br.crudlink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import florencio.com.br.crudlink.dominio.Link;

public class LinkAdapter extends BaseAdapter {
    private List<Link> objetos;
    private Context context;

    public LinkAdapter(List<Link> objetos, Context context) {
        this.objetos = objetos;
        this.context = context;
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
        return objetos.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Link obj = objetos.get(position);

        if(convertView == null) {
            convertView =
                    LayoutInflater.from(context)
                            .inflate(R.layout.item_link_layout, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.labelLink);
        textView.setText(obj.getLink());

        return convertView;
    }
}
