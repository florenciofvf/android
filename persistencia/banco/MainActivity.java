package florencio.com.br.banco;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BancoHelper banco = new BancoHelper(this);
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("nome", "Maria da Silva");

        long i = db.insert("pessoa", null, cv);

        Log.i(BancoHelper.BANCO, ">>>" + i);

        db.close();
    }
}
