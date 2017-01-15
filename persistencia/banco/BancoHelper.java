package florencio.com.br.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by florenciovieirafilho on 15/01/17.
 */
public class BancoHelper extends SQLiteOpenHelper {
    public static final String BANCO = "BancoTeste";
    public static final int VERSAO = 1;


    public BancoHelper(Context context) {
        super(context, BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE Pessoa(");
        sb.append("   _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
        sb.append("  nome TEXT NOT NULL");
        sb.append(")");

        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
