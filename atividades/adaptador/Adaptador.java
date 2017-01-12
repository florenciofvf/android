package atividade.com.br.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adaptador extends BaseAdapter {
    private Context contexto;
    private List<String> nomes;

    public Adaptador(Context contexto) {
        this.contexto = contexto;
        nomes = criarNomes();
    }

    private List<String> criarNomes() {
        List<String> resp = new ArrayList<>();
        resp.add("Maria da Silva");
        resp.add("Carla Moreira");
        resp.add("Marta da Silva");

        return resp;
    }

    @Override
    public int getCount() {
        return nomes.size();
    }

    @Override
    public Object getItem(int position) {
        return nomes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(contexto).inflate(R.layout.registro, null);

        TextView txtNome = (TextView) view.findViewById(R.id.txtNome);
        String nome = nomes.get(position);
        txtNome.setText(nome);

        ImageView imagem = (ImageView) view.findViewById(R.id.imagem);
        imagem.setImageResource(R.mipmap.ic_launcher);

        return view;
    }
}
