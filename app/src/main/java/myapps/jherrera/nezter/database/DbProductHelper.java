package myapps.jherrera.nezter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import myapps.jherrera.nezter.objects.Product;

import static java.lang.Integer.parseInt;

public class DbProductHelper extends SQLiteOpenHelper {

    private static String dbName = "MyPrpductDB";
    private static CursorFactory factory = null;
    private static int version = 2;

    //Tabla
    private static final String TABLE_NAME = "PRODUCTO";

    //Columnas
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String STOCK = "stock";
    private static final String FLAG_ACTIVO = "activo";
    private static final String FLAG_ELIMINADO = "eliminado";

    //Variables de referencia
    DbProductHelper dbHelprer;
    SQLiteDatabase db;
    Context context;

    public DbProductHelper(Context context){
        super(context, dbName,factory,version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NAME + " TEXT NOT NULL," +
                STOCK + " INTEGER DEFAULT 0," +
                FLAG_ELIMINADO + " INTEGER DEFAULT 0," +
                FLAG_ACTIVO + " INTEGER DEFAULT 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS TABLE_NAME");
        onCreate(db);
    }

    public void openDB(){
        dbHelprer = new DbProductHelper(context);
        db = dbHelprer.getWritableDatabase();
    }

    public void closeDB(){
        db.close();
    }

    //Metodos para manipular los datos
    public long addProduct(String name, int stock) throws Exception{
        openDB();
        ContentValues values = new ContentValues();
        values.put(NAME,name);
        values.put(STOCK,stock);
        long res = db.insert(TABLE_NAME,null,values);
        closeDB();

        return res;
    }

    public String getNameLowStock(){
        Cursor res = db.rawQuery("select * from "+TABLE_NAME + " where eliminado = 0",null);

        StringBuilder data = new StringBuilder();
        while (res.moveToNext()) {

            if (stockBajoNombres(res.getString(2))) {
                data.append(res.getString(1) + "\n");
            }
        }
        return String.valueOf(data);
    }

    private Boolean stockBajoNombres(String s){
        int stock = Integer.parseInt(s);
        if ( stock <  5){
            return true;
        }
        return false;
    }

    public String selectActivos(){
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where " +
                FLAG_ACTIVO + " = 1 " +"and " + FLAG_ELIMINADO + " = 0 and "+ STOCK + " > 0",null);

        StringBuilder data = new StringBuilder();
        while (res.moveToNext()) {

            data.append("Id :" + res.getString(0) +
                    " Nombre :" + res.getString(1) +
                    " Stock :" + res.getString(2) + stockBajo(res.getString(2)) +"\n");

        }
        return String.valueOf(data);
    }

    public List selectAll(){

        List items = new ArrayList();
        openDB();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME + " where eliminado = 0",null);
        while (res.moveToNext()) {

            items.add(new Product(
                    res.getInt(0),                     // int
                    res.getString(1),                  //name
                    res.getInt(2),                     // stock
                    res.getInt(4) > 0));        // active(boolean)
            }
            closeDB();
        return items;
    }



    private String stockBajo(String s){

        String alerta = "";
        int stock = Integer.parseInt(s);
        if ( stock <  6){
            alerta = " BAJO";
        }
        return alerta;
    }

    private void updateStock(String id, int newStock){
        openDB();
        ContentValues values = new ContentValues();
        values.put(STOCK,newStock);
        db.update(TABLE_NAME,values, "id = ?" ,new String[]{id});
        //closeDB();
    }

    public void drecement(String id){
        openDB();

        if (checkProductExist(id)) {

            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + ID + " = " + id, null);
            cursor.moveToFirst();
            int stock = Integer.parseInt(cursor.getString(2)) - 1;
            closeDB();

            if (stock < 0) {
                stock = 0;
            }
            updateStock(id, stock);
        }

        closeDB();
    }

    public void increment(String id){
        openDB();

        if (checkProductExist(id)) {

            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + ID + " = " + id, null);
            cursor.moveToFirst();
            int stock = Integer.parseInt(cursor.getString(2)) + 1;
            updateStock(id, stock);
            closeDB();
        }
    }

    public void updateActive(String id){
        openDB();

        if (checkProductExist(id)) {
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + ID + " = " + id, null);

            cursor.moveToFirst();
            int activo = parseInt(cursor.getString(4));
            if (activo == 0) {
                activo = 1;
            } else {
                activo = 0;
            }

            ContentValues values = new ContentValues();
            values.put(FLAG_ACTIVO, String.valueOf(activo));
            db.update(TABLE_NAME, values, "id = ?", new String[]{id});

            closeDB();
        }
    }

    public boolean deleteProduct(String id){
        openDB();

        if (checkProductExist(id)) {

            ContentValues values = new ContentValues();
            values.put(FLAG_ELIMINADO, 1);
            db.update(TABLE_NAME, values, "id = ?", new String[]{id});

            closeDB();
            return true;
        }
        return false;
    }

    private boolean checkProductExist(String id) {

        String selection = ID  + " = ?" + " AND " + FLAG_ELIMINADO + " = ?" ;
        String[] selectionArgs = {id,"0"};
        Cursor cursor = db.query(TABLE_NAME, //Nombre de la tabla
                null,                    //columnas(SELECT)
                selection,                  //columnas(WHERE)
                selectionArgs,              //valores de las columnas en (Where)
                null,                       //group
                null,                       //ffiltro de group
                null);                      //order

        int cursorCount = cursor.getCount();

        if (cursorCount == 0) {
            Toast.makeText(context,"EL ID no existe",Toast.LENGTH_LONG ).show();
            return false;

        }
        return true;
    }

}
