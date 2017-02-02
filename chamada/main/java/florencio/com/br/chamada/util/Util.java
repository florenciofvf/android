package florencio.com.br.chamada.util;

import java.util.ArrayList;
import java.util.List;

import florencio.com.br.chamada.dominio.Cliente;

public class Util {

    public static List<Cliente> criarListaClienteTeste() {
        List<Cliente> resp = new ArrayList<>();

        for(long i=1; i<=20; i++) {
            Cliente cliente = new Cliente("florencio", "florencio@teste.com-" + i);
            cliente.set_id(i);
            resp.add(cliente);
        }

        return resp;
    }

}