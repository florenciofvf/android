package florencio.com.br.crudlink.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import florencio.com.br.crudlink.util.Constantes;

public class BancoHelper extends SQLiteOpenHelper {

    public BancoHelper(Context context) {
        super(context, Constantes.NOME_BANCO, null, Constantes.VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append(" CREATE TABLE Link( ");
        sb.append("        _id INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sb.append("       link VARCHAR(100) NOT NULL, ");
        sb.append("  descricao VARCHAR(100), ");
        sb.append("       data DATE");
        sb.append(" ) ");

        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
