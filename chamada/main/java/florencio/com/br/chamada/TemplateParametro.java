package florencio.com.br.chamada;

import java.io.Serializable;

public class TemplateParametro implements Serializable {
    private Class<?> classeFragmentoListagem;
    private Class<?> classeFragmentoDialogo;
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Class<?> getClasseFragmentoListagem() {
        return classeFragmentoListagem;
    }

    public void setClasseFragmentoListagem(Class<?> classeFragmentoListagem) {
        this.classeFragmentoListagem = classeFragmentoListagem;
    }

    public Class<?> getClasseFragmentoDialogo() {
        return classeFragmentoDialogo;
    }

    public void setClasseFragmentoDialogo(Class<?> classeFragmentoDialogo) {
        this.classeFragmentoDialogo = classeFragmentoDialogo;
    }

    public static TemplateParametro criarTemplateParametro(String titulo, Class<?> classeFragmentoListagem, Class<?> classeFragmentoDialogo) {
        TemplateParametro parametro = new TemplateParametro();

        parametro.setTitulo(titulo);
        parametro.setClasseFragmentoListagem(classeFragmentoListagem);
        parametro.setClasseFragmentoDialogo(classeFragmentoDialogo);

        return parametro;
    }
}