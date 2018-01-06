package myapps.jherrera.nezter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Objects;

/**
 * Created by JH on 05/01/2018.
 */

public class DbUsuariosHelper extends SQLiteOpenHelper {

    private static String dbName = "MyUsuerDB";
    private static SQLiteDatabase.CursorFactory factory = null;
    private static int version = 1;

    //Tabla
    public static final String TABLE_NAME = "Usuarios";

    //Columnas
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";

    //Variables de referencia
    DbProductosHelper dbHelprer;
    SQLiteDatabase db;
    Context context;

    public DbUsuariosHelper(Context context){
        super(context, dbName,factory,version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        db.execSQL("CREATE TABLE "+ TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME + " TEXT NOT NULL," +
                PASSWORD + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS TABLE_NAME");
        onCreate(db);
    }

    public long addUser(String name, String pass){
        ContentValues values = new ContentValues();
        values.put(NAME,name);
        values.put(PASSWORD,pass);
        return db.insert(TABLE_NAME,null,values);

    }

    public boolean login(String name, String pass) throws Exception{

        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where " +
                NAME + " = " + name,null);
        res.moveToFirst();
        if (Objects.equals(pass, res.getString(2))){
            return true;
        }


        return true;
    }

    public void openDB(){
        dbHelprer = new DbProductosHelper(context);
        db = dbHelprer.getWritableDatabase();
    }

    public void closeDB(){
        db.close();
    }

}
