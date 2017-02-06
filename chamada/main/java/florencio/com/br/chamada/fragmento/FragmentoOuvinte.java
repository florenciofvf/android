package florencio.com.br.chamada.fragmento;

import java.util.Map;

import florencio.com.br.chamada.dominio.Entidade;
import florencio.com.br.chamada.servico.ChamadaServico;

public interface FragmentoOuvinte {

    public void atualizarParametros(Entidade entidade, String subtitulo, Class<?> classeFragmentoListagem, Class<?> classeFragmentoDialogo, Map<String, Entidade> mapa);

    public void clickItemListagem(Entidade entidade);

    public ChamadaServico getChamadaServico();

    public String getString(int idRecurso);

    public String getMsgCampoObrigatorio();

    public void salvar(Entidade entidade);

    public void mensagem(String msg);

}