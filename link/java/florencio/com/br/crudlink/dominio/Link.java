package florencio.com.br.crudlink.dominio;

import java.io.Serializable;

public class Link implements Serializable {
    public static final String NOME_TABELA = "Link";

    public static final String        COLUNA_ID = "_id";
    public static final String      COLUNA_LINK = "link";
    public static final String COLUNA_DESCRICAO = "descricao";
    public static final String      COLUNA_DATA = "data";

    public static final int        COLUNA_ID_IDX = 0;
    public static final int      COLUNA_LINK_IDX = 1;
    public static final int COLUNA_DESCRICAO_IDX = 2;
    public static final int      COLUNA_DATA_IDX = 3;

    public static final String[] ARRAY_NOME_COLUNAS =
            {COLUNA_ID, COLUNA_LINK, COLUNA_DESCRICAO, COLUNA_DATA};

    private Long _id;
    private String link;
    private String descricao;
    private Long data;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }
}
