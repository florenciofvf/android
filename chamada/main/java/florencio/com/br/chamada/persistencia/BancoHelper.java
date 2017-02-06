package florencio.com.br.chamada.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import florencio.com.br.chamada.dominio.Cliente;
import florencio.com.br.chamada.dominio.Curso;
import florencio.com.br.chamada.dominio.Frequencia;
import florencio.com.br.chamada.dominio.Instrutor;
import florencio.com.br.chamada.dominio.Laboratorio;
import florencio.com.br.chamada.dominio.Matricula;
import florencio.com.br.chamada.dominio.StatusChamada;
import florencio.com.br.chamada.dominio.StatusTurma;
import florencio.com.br.chamada.dominio.Turma;
import florencio.com.br.chamada.dominio.Turno;

public class BancoHelper extends SQLiteOpenHelper {
	private static final String BANCO = "CHAMADA";
	private static final int VERSAO = 1;
	
	public BancoHelper(Context context) {
		super(context, BANCO, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();

		sb.append(" create table " + Cliente.class.getSimpleName() + "( ");
		sb.append("    _id integer primary key autoincrement, ");
		sb.append("   nome text not null, ");
		sb.append("  email text not null unique");
		sb.append(" ) ");
		db.execSQL(sb.toString());

        sb = new StringBuilder();
		sb.append(" create table " + Instrutor.class.getSimpleName() + "( ");
		sb.append("    _id integer primary key autoincrement, ");
		sb.append("   nome text not null, ");
		sb.append("  email text not null unique");
		sb.append(" ) ");
		db.execSQL(sb.toString());

        sb = new StringBuilder();
        sb.append(" create table " + Laboratorio.class.getSimpleName() + "( ");
        sb.append("         _id integer primary key autoincrement, ");
        sb.append("        nome text not null unique, ");
        sb.append("  capacidade integer not null");
        sb.append(" ) ");
        db.execSQL(sb.toString());

        sb = new StringBuilder();
        sb.append(" create table " + Frequencia.class.getSimpleName() + "( ");
        sb.append("        _id integer primary key autoincrement, ");
        sb.append("       nome text not null unique, ");
        sb.append("  descricao text not null");
        sb.append(" ) ");
        db.execSQL(sb.toString());

        sb = new StringBuilder();
        sb.append(" create table " + StatusTurma.class.getSimpleName() + "( ");
        sb.append("        _id integer primary key autoincrement, ");
        sb.append("       nome text not null unique, ");
        sb.append("  descricao text not null");
        sb.append(" ) ");
        db.execSQL(sb.toString());

		sb = new StringBuilder();
        sb.append(" create table " + StatusChamada.class.getSimpleName() + "( ");
		sb.append("        _id integer primary key autoincrement, ");
		sb.append("      letra char(1) not null unique, ");
		sb.append("  descricao text not null");
		sb.append(" ) ");
		db.execSQL(sb.toString());

		sb = new StringBuilder();
        sb.append(" create table " + Curso.class.getSimpleName() + "( ");
		sb.append("       _id integer primary key autoincrement, ");
		sb.append("      nome text not null unique, ");
        sb.append("  descricao text not null");
		sb.append(" ) ");
		db.execSQL(sb.toString());

		sb = new StringBuilder();
        sb.append(" create table " + Turno.class.getSimpleName() + "( ");
		sb.append("  _id integer primary key autoincrement, ");
        sb.append("       nome text not null unique, ");
        sb.append("  descricao text not null");
		sb.append(" ) ");
		db.execSQL(sb.toString());

		sb = new StringBuilder();
        sb.append(" create table " + Turma.class.getSimpleName() + "( ");
		sb.append("              _id integer primary key autoincrement, ");
		sb.append("           inicio Date not null, ");
        sb.append("         curso_id integer not null, ");
        sb.append("     instrutor_id integer not null, ");
        sb.append("   laboratorio_id integer not null, ");
        sb.append("    frequencia_id integer not null, ");
        sb.append("  status_turma_id integer not null, ");
        sb.append("         turno_id integer not null, ");
        sb.append("  foreign key(curso_id)         references Curso      (_id),");
        sb.append("  foreign key(instrutor_id)     references Instrutor  (_id),");
        sb.append("  foreign key(laboratorio_id)   references Laboratorio(_id),");
        sb.append("  foreign key(frequencia_id)    references Frequencia (_id),");
        sb.append("  foreign key(status_turma_id)  references StatusTurma(_id),");
        sb.append("  foreign key(turno_id)         references Turno      (_id) ");
		sb.append(" ) ");
		db.execSQL(sb.toString());

		sb = new StringBuilder();
        sb.append(" create table " + Matricula.class.getSimpleName() + "( ");
		sb.append("         _id integer primary key autoincrement, ");
		sb.append("        data Date not null, ");
		sb.append("  cliente_id integer not null, ");
		sb.append("    turma_id integer not null, ");
		sb.append("  foreign key(cliente_id) references Cliente(_id),");
		sb.append("  foreign key(turma_id)   references Turma  (_id)");
		sb.append(" ) ");
		db.execSQL(sb.toString());

		/*
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
		sb.append("  matricula_id integer not null, ");
		sb.append("  cabecalho_id integer not null, ");
		sb.append("  status_id integer not null, ");
		sb.append("  foreign key(cliente_turma_id) references Matricula(_id),");
		sb.append("  foreign key(cabecalho_id) references CabecalhoChamada(_id),");
		sb.append("  foreign key(status_id) references Status(_id)");
		sb.append(" ) ");
		db.execSQL(sb.toString());
		*/
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}