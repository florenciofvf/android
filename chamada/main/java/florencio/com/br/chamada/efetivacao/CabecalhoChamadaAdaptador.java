package florencio.com.br.chamada.efetivacao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.CabecalhoChamada;
import florencio.com.br.chamada.dominio.Chamada;
import florencio.com.br.chamada.util.Constantes;
import florencio.com.br.chamada.util.Util;

public class CabecalhoChamadaAdaptador extends BaseExpandableListAdapter {
    private final List<CabecalhoChamada> cabecalhos;
    private final Context contexto;

    public CabecalhoChamadaAdaptador(List<CabecalhoChamada> cabecalhos, Context contexto) {
        this.cabecalhos = cabecalhos;
        this.contexto = contexto;
    }

    @Override
    public int getGroupCount() {
        return cabecalhos.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        CabecalhoChamada cabecalho = cabecalhos.get(groupPosition);
        return cabecalho.getTotalChamadas();
    }

    @Override
    public Object getGroup(int groupPosition) {
        CabecalhoChamada cabecalho = cabecalhos.get(groupPosition);
        return cabecalho;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        CabecalhoChamada cabecalho = cabecalhos.get(groupPosition);
        return cabecalho.getChamada(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        CabecalhoChamada cabecalho = cabecalhos.get(groupPosition);
        return cabecalho.get_id();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        CabecalhoChamada cabecalho = cabecalhos.get(groupPosition);
        return cabecalho.getChamada(childPosition).get_id();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CabecalhoChamada cabecalho = cabecalhos.get(groupPosition);

        if(convertView == null) {
            convertView = LayoutInflater.from(contexto).inflate(R.layout.cabecalho_chamada_registro_layout, null);
        }

        TextView textViewDataHora = (TextView) convertView.findViewById(R.id.dataHoraCabecalhoChamada);
        textViewDataHora.setText(cabecalho.getOrdem() + Constantes.TRACO + Util.formatarDate(cabecalho.getDataHora()) + completar(cabecalho.getObservacao()));

        return convertView;
    }

    private String completar(String s) {
        return Util.isVazio(s) ? Constantes.VAZIO : Constantes.TRACO + s;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CabecalhoChamada cabecalho = cabecalhos.get(groupPosition);
        Chamada chamada = cabecalho.getChamada(childPosition);

        if(convertView == null) {
            convertView = LayoutInflater.from(contexto).inflate(R.layout.chamada_registro_layout, null);
        }

        TextView textViewNomeCliente = (TextView) convertView.findViewById(R.id.nomeCliente);
        TextView textViewDescStatus = (TextView) convertView.findViewById(R.id.descricaoStatus);

        textViewNomeCliente.setText(chamada.getMatricula().getCliente().getNome());
        textViewDescStatus.setText(chamada.getStatus().getDescricao());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
