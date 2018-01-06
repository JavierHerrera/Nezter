package myapps.jherrera.nezter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import myapps.jherrera.nezter.Usuario;

public class DbUsuariosHelper extends SQLiteOpenHelper {

    private static String dbName = "MyUsuerDB";
    private static CursorFactory factory = null;
    private static int version = 1;

    //Tabla
    public static final String TABLE_NAME = "Usuarios";

    //Columnas
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";

    //Variables de referencia
    DbUsuariosHelper dbHelprer;
    SQLiteDatabase db;
    Context context;

    public DbUsuariosHelper(Context context){
        super(context, dbName,factory,version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME + " TEXT NOT NULL," +
                PASSWORD + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        onCreate(db);
    }

    public long addUser(Usuario usuario)throws Exception{
        openDB();

        if (checkUserExist(usuario.getName())) {
            ContentValues values = new ContentValues();
            values.put(NAME, usuario.getName());
            values.put(PASSWORD, usuario.getPassword());

            Long res = db.insert(TABLE_NAME, null, values);
            closeDB();
            return res;
        }else return 0;
    }

    public void openDB(){
        dbHelprer = new DbUsuariosHelper(context);
        db = dbHelprer.getWritableDatabase();
    }

    public void closeDB(){
        db.close();
    }

    public boolean checkUser(String name, String password) {

        openDB();

        String selection = NAME + " = ?" + " AND " + PASSWORD + " = ?";

        String[] selectionArgs = {name, password};

        Cursor cursor = db.query(TABLE_NAME, //Table to query
                null,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    private boolean checkUserExist(String name) {

        String selection = NAME + " = ?";
        String[] selectionArgs = {name};
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                null,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        if (cursorCount == 0) {
            return true;

        }
        Toast.makeText(context,"EL usuario ya está ocupado",Toast.LENGTH_LONG ).show();
        return false;
    }


}
