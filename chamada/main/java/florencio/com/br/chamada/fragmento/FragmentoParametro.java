package florencio.com.br.chamada.fragmento;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import florencio.com.br.chamada.dominio.Entidade;

public class FragmentoParametro implements Serializable {
    private Map<String, Entidade> mapa = new HashMap<>();
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

    public void putEntidade(String nome, Entidade entidade) {
        mapa.put(nome, entidade);
    }

    public Entidade getEntidade(String nome) {
        return mapa.get(nome);
    }

    public void setMapa(Map<String, Entidade> mapa) {
        this.mapa = mapa;
    }
}