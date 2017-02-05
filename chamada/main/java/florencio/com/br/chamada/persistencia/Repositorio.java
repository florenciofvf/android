package florencio.com.br.chamada.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.Entidade;
import florencio.com.br.chamada.servico.ChamadaExcecao;

public class Repositorio {
    private BancoHelper banco;

    public Repositorio(Context contexto) {
        banco = new BancoHelper(contexto);
    }

    public List<? extends Entidade> listar(Entidade entidade) {
        List<Entidade> lista = new ArrayList<>();

        SQLiteDatabase db = banco.getReadableDatabase();

        Cursor cursor = db.query(entidade.getClass().getSimpleName(), entidade.getNomeColunas(), null, null, null, null, entidade.getNomeColunaOrderBy());

        while (cursor.moveToNext()) {
            Entidade e = entidade.criar(cursor);
            lista.add(e);
        }

        db.close();

        return lista;
    }

    public void salvar(Entidade entidade) throws ChamadaExcecao {
        ContentValues cv = entidade.criarContentValues();

        SQLiteDatabase db = banco.getWritableDatabase();

        try {
            if (entidade.ehNovo()) {
                long _id = db.insertOrThrow(entidade.getClass().getSimpleName(), null, cv);
                entidade.set_id(_id);
            } else {
                db.updateWithOnConflict(entidade.getClass().getSimpleName(), cv, "_id = ?", new String[]{String.valueOf(entidade.get_id())}, SQLiteDatabase.CONFLICT_NONE);
            }
        } catch (Exception e) {
            if(e instanceof SQLiteConstraintException || e.getCause() instanceof SQLiteConstraintException) {
                throw new ChamadaExcecao(R.string.msg_erro_registro_duplicado);
            }
        }

        db.close();
    }

    public void excluir(Entidade entidade) {
        SQLiteDatabase db = banco.getWritableDatabase();

        if(!entidade.ehNovo()) {
            db.delete(entidade.getClass().getSimpleName(), "_id = ?", new String[]{ String.valueOf(entidade.get_id()) });
        }

        db.close();
    }
}