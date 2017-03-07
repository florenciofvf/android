package florencio.com.br.crudlink.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import florencio.com.br.crudlink.dominio.Link;

public class Repositorio {
    private BancoHelper bancoHelper;

    public Repositorio(Context context) {
        bancoHelper = new BancoHelper(context);
    }

    public void salvar(Link link) {
        ContentValues cv = new ContentValues();

        cv.put(Link.COLUNA_LINK,      link.getLink());
        cv.put(Link.COLUNA_DESCRICAO, link.getDescricao());
        cv.put(Link.COLUNA_DATA,      link.getData());

        SQLiteDatabase db = bancoHelper.getWritableDatabase();

        if(link.get_id() == null) {

            Long novoID = db.insert(Link.NOME_TABELA, null, cv);
            link.set_id(novoID);

        } else {

            String[] valorID = {link.get_id().toString()};
            db.update(Link.NOME_TABELA, cv, Link.COLUNA_ID + " = ? ", valorID);

        }

        db.close();
    }

    public void excluir(Link link) {
        if(link.get_id() == null) {
            return;
        }

        SQLiteDatabase db = bancoHelper.getWritableDatabase();

        String[] valorID = {link.get_id().toString()};
        db.delete(Link.NOME_TABELA, Link.COLUNA_ID + " = ? ", valorID);

        db.close();
    }

    public List<Link> listar() {
        List<Link> listagem = new ArrayList<>();

        SQLiteDatabase db = bancoHelper.getReadableDatabase();

        Cursor cursor = db.query(
                Link.NOME_TABELA, Link.ARRAY_NOME_COLUNAS, null, null, null, null,
                Link.COLUNA_LINK);

        while(cursor.moveToNext()) {
            Link obj = new Link();
            obj.set_id(         cursor.getLong(   Link.COLUNA_ID_IDX        ));
            obj.setLink(        cursor.getString( Link.COLUNA_LINK_IDX      ));
            obj.setDescricao(   cursor.getString( Link.COLUNA_DESCRICAO_IDX ));
            obj.setData(        cursor.getLong(   Link.COLUNA_DATA_IDX      ));

            listagem.add(obj);
        }

        db.close();

        return listagem;
    }

}
