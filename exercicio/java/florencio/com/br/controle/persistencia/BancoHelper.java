package florencio.com.br.controle.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoHelper extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "Controle";
    private static final int VERSAO = 1;

    public BancoHelper(Context contexto) {
        super(contexto, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String DDL = "create table Controle ("                             +
                "        _id integer not null primary key autoincrement, " +
                "  descricao text(50) not null, "                          +
                "      valor float not null, "                             +
                "       data datetime not null, "                          +
                "       tipo text(1) "                                     +
                " ) ";

        db.execSQL(DDL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
