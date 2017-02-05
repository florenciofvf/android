package florencio.com.br.chamada.fragmento;

import java.util.List;

import florencio.com.br.chamada.dominio.Entidade;

public interface FragmentoListagem extends Fragmento {

    public void excluir(List<? extends Entidade> entidades);

    public List<? extends Entidade> getSelecionados();

    public void atualizarListagem();

}