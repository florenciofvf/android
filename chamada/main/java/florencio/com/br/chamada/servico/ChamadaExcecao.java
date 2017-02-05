package florencio.com.br.chamada.servico;

public class ChamadaExcecao extends Exception {
    private final int idStringErro;

    public ChamadaExcecao(int idStringErro) {
        this.idStringErro = idStringErro;
    }

    public int getIdStringErro() {
        return idStringErro;
    }
}