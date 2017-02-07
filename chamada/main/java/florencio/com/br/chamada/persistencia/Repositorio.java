package florencio.com.br.chamada.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import florencio.com.br.chamada.R;
import florencio.com.br.chamada.dominio.CabecalhoChamada;
import florencio.com.br.chamada.dominio.Chamada;
import florencio.com.br.chamada.dominio.Cliente;
import florencio.com.br.chamada.dominio.Entidade;
import florencio.com.br.chamada.dominio.Matricula;
import florencio.com.br.chamada.dominio.StatusChamada;
import florencio.com.br.chamada.dominio.Turma;
import florencio.com.br.chamada.servico.ChamadaExcecao;

public class Repositorio {
    private BancoHelper banco;

    public Repositorio(Context contexto) {
        banco = new BancoHelper(contexto);
    }

    public List<? extends Entidade> listar(Entidade entidade) {
        List<Entidade> lista = new ArrayList<>();

        SQLiteDatabase db = banco.getReadableDatabase();

        Cursor cursor = null;

        if(entidade instanceof Turma) {
            cursor = db.rawQuery(((Turma) entidade).getStringConsulta(), null);

        } else if(entidade instanceof Matricula) {
            cursor = db.rawQuery(((Matricula) entidade).getStringConsulta(), null);

        } else {
            cursor = db.query(entidade.getClass().getSimpleName(), entidade.getNomeColunas(), null, null, null, null, entidade.getNomeColunaOrderBy());
        }

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

    public List<CabecalhoChamada> listarCabecalhoChamada(Turma turma) {
        List<CabecalhoChamada> cabecalhos = new ArrayList<>();

        StringBuilder sb = new StringBuilder("select distinct cab._id, cab.dataHora, cli.nome, sta.descricao, cha._id, mat._id, sta._id From CabecalhoChamada cab");
        sb.append(" inner join Chamada cha on cab._id = cha.cabecalho_chamada_id");
        sb.append(" inner join Matricula mat on mat._id = cha.matricula_id");
        sb.append(" inner join StatusChamada sta on sta._id = cha.status_chamada_id");
        sb.append(" inner join Cliente cli on cli._id = mat.cliente_id");
        sb.append(" where cab.turma_id = " + turma.get_id());

        SQLiteDatabase db = banco.getReadableDatabase();

        Cursor cursor = db.rawQuery(sb.toString(), null);

        Map<Long, CabecalhoChamada> mapa = new LinkedHashMap<>();

        while (cursor.moveToNext()) {
            long idCabecalho = cursor.getInt(0);
            CabecalhoChamada cabecalho = get(idCabecalho, mapa);
            cabecalho.setDataHora(cursor.getLong(1));

            Cliente c = new Cliente();
            c.setNome(cursor.getString(2));

            Matricula m = new Matricula();
            m.set_id(cursor.getLong(5));
            m.setCliente(c);

            StatusChamada s = new StatusChamada();
            s.set_id(cursor.getLong(6));
            s.setDescricao(cursor.getString(3));

            Chamada chamada = new Chamada();
            chamada.set_id(cursor.getLong(4));
            chamada.setCabecalho(cabecalho);
            chamada.setMatricula(m);
            chamada.setStatus(s);
            cabecalho.addChamada(chamada);
        }

        cabecalhos.addAll(mapa.values());

        db.close();

        return cabecalhos;
    }

    private CabecalhoChamada get(long idCabecalho, Map<Long, CabecalhoChamada> mapa) {
        CabecalhoChamada cabecalho = mapa.get(idCabecalho);

        if(cabecalho == null) {
            cabecalho = new CabecalhoChamada();
            cabecalho.set_id(idCabecalho);
            mapa.put(idCabecalho, cabecalho);
        }

        return cabecalho;
    }
}