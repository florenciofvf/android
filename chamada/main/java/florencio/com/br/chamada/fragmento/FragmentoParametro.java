package florencio.com.br.chamada.fragmento;

import java.io.Serializable;

import florencio.com.br.chamada.dominio.Entidade;

public class FragmentoParametro implements Serializable {
    private Entidade entidade;
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }
}