package florencio.com.br.chamada.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoHelper extends SQLiteOpenHelper {
	private static final String BANCO = "CHAMADA";
	private static final int VERSAO = 1;
	
	public BancoHelper(Context context) {
		super(context, BANCO, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();

		sb.append(" CREATE TABLE Cliente( ");
		sb.append("  _id integer primary key autoincrement, ");
		sb.append("  nome text not null, ");
		sb.append("  email text not null unique");
		sb.append(" ) ");
		db.execSQL(sb.toString());

		sb = new StringBuilder();
		sb.append(" CREATE TABLE Status( ");
		sb.append("  _id integer primary key autoincrement, ");
		sb.append("  letra char(1) not null unique, ");
		sb.append("  descricao text not null");
		sb.append(" ) ");
		db.execSQL(sb.toString());

		sb = new StringBuilder();
		sb.append(" CREATE TABLE Frequencia( ");
		sb.append("  _id integer primary key autoincrement, ");
		sb.append("  nome text not null unique, ");
		sb.append("  descricao text not null");
		sb.append(" ) ");
		db.execSQL(sb.toString());

		sb = new StringBuilder();
		sb.append(" CREATE TABLE Laboratorio( ");
		sb.append("   _id integer primary key autoincrement, ");
		sb.append("  nome text not null unique, ");
		sb.append("  capacidade integer not null");
		sb.append(" ) ");
		db.execSQL(sb.toString());
		
		sb = new StringBuilder();
		sb.append(" CREATE TABLE Curso( ");
		sb.append("  _id integer primary key autoincrement, ");
		sb.append("  nome text not null unique, ");
		sb.append("  qtdHoras integer not null");
		sb.append(" ) ");
		db.execSQL(sb.toString());
		
		sb = new StringBuilder();
		sb.append(" CREATE TABLE Turno( ");
		sb.append("  _id integer primary key autoincrement, ");
		sb.append("  descricao text not null unique, ");
		sb.append("  inicio DateTime not null,");
		sb.append("  fim DateTime not null");
		sb.append(" ) ");
		db.execSQL(sb.toString());
		
		sb = new StringBuilder();
		sb.append(" CREATE TABLE Turma( ");
		sb.append("  _id integer primary key autoincrement, ");
		sb.append("  inicio Date not null, ");
		sb.append("  descricao text not null, ");
		sb.append("  curso_id integer not null, ");
		sb.append("  frequencia_id integer not null, ");
		sb.append("  turno_id integer not null, ");
		sb.append("  laboratorio_id integer not null, ");
		sb.append("  foreign key(curso_id)       references Curso      (_id),");
		sb.append("  foreign key(frequencia_id)  references Frequencia (_id),");
		sb.append("  foreign key(turno_id)       references Turno      (_id),");
		sb.append("  foreign key(laboratorio_id) references Laboratorio(_id)");
		sb.append(" ) ");
		db.execSQL(sb.toString());
		
		sb = new StringBuilder();
		sb.append(" CREATE TABLE ClienteTurma( ");
		sb.append("  _id integer primary key autoincrement, ");
		sb.append("  data Date, ");
		sb.append("  cliente_id integer not null, ");
		sb.append("  turma_id integer not null, ");
		sb.append("  foreign key(cliente_id) references Cliente(_id),");
		sb.append("  foreign key(turma_id) references Turma(_id)");
		sb.append(" ) ");
		db.execSQL(sb.toString());
		
		sb = new StringBuilder();
		sb.append(" CREATE TABLE CabecalhoChamada( ");
		sb.append("  _id integer primary key autoincrement, ");
		sb.append("  turma_id integer not null, ");
		sb.append("  dataHora DateTime not null, ");
		sb.append("  observacao text, ");
		sb.append("  foreign key(turma_id) references Turma(_id)");
		sb.append(" ) ");
		db.execSQL(sb.toString());

		sb = new StringBuilder();
		sb.append(" CREATE TABLE Chamada( ");
		sb.append("  _id integer primary key autoincrement, ");
		sb.append("  cliente_turma_id integer not null, ");
		sb.append("  cabecalho_id integer not null, ");
		sb.append("  status_id integer not null, ");
		sb.append("  foreign key(cliente_turma_id) references ClienteTurma(_id),");
		sb.append("  foreign key(cabecalho_id) references CabecalhoChamada(_id),");
		sb.append("  foreign key(status_id) references Status(_id)");
		sb.append(" ) ");
		db.execSQL(sb.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}