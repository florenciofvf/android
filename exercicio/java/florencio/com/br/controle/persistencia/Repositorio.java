package florencio.com.br.controle.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import florencio.com.br.controle.dominio.Controle;

public class Repositorio {
    private BancoHelper banco;

    public Repositorio(Context contexto) {
        banco = new BancoHelper(contexto);
    }

    public void salvar(Controle controle) {
        SQLiteDatabase db = banco.getWritableDatabase();

        if(controle.get_id() == null || controle.get_id().longValue() == 0) {
            long _id = db.insert("Controle", null, criarCV(controle));
            controle.set_id(_id);
        } else {
            db.update("Controle", criarCV(controle), "_id = ?",
                                    new String[]{ "" + controle.get_id()});
        }

        db.close();
    }

    private ContentValues criarCV(Controle controle) {
        ContentValues cv = new ContentValues();

        cv.put("descricao", controle.getDescricao());
        cv.put("valor", controle.getValor());
        cv.put("data", controle.getData().getTime());
        cv.put("tipo", controle.getTipo());

        return cv;
    }

    public void excluir(Controle controle) {
        SQLiteDatabase db = banco.getWritableDatabase();

        db.delete("Controle", "_id = ?", new String[] { "" + controle.get_id()});

        db.close();
    }

    public List<Controle> listar() {
        List<Controle> lista = new ArrayList<>();

        SQLiteDatabase db = banco.getReadableDatabase();

//      Cursor cursor = db.query("Controle", null, null, null, null, null, null);
        Cursor cursor = db.rawQuery("select _id, descricao, valor, data, tipo from Controle", null);

        while(cursor.moveToNext()) {
            Controle c = new Controle();

            c.set_id(       cursor.getLong   (0));
            c.setDescricao( cursor.getString (1));
            c.setValor(     cursor.getFloat  (2));
            c.setData(new Date(cursor.getLong(3)));
            c.setTipo(      cursor.getString (4));

            lista.add(c);
        }

        db.close();
        return lista;
    }
}
