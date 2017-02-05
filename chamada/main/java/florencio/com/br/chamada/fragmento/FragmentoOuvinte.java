package florencio.com.br.chamada.fragmento;

import florencio.com.br.chamada.dominio.Entidade;
import florencio.com.br.chamada.servico.ChamadaServico;

public interface FragmentoOuvinte {

    public void clickItemListagem(Entidade entidade);

    public ChamadaServico getChamadaServico();

    public String getString(int idRecurso);

    public String getMsgCampoObrigatorio();

    public void salvar(Entidade entidade);

    public void mensagem(String msg);

}