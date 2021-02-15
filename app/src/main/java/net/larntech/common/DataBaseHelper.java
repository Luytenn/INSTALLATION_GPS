package net.larntech.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import net.larntech.retrofit.response.Marca;
import net.larntech.retrofit.response.ModeloVehiculo;
import net.larntech.retrofit.response.TipoVehiculo;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static DataBaseHelper sInstance;

    public static final String DB_NAME = "local.db";
    public static final String TABLA_MARCA = "TABLA_MARCA";
    public static final String COLUMNA_MARCA_ID = "COLUMNA_MARCA_ID";
    public static final String COLUMNA_MARCA_DES = "COLUMNA_MARCA_DES";

    public static final String TABLA_TIPO = "TABLA_TIPO";
    public static final String COLUMNA_TIPO_ID = "COLUMNA_TIPO_ID";
    public static final String COLUMNA_TIPO_DES = "COLUMNA_TIPO_DES";

    public static final String TABLA_MODELO = "TABLA_MODELO";
    public static final String COLUMNA_MODELO_ID = "COLUMNA_MODELO_ID";
    public static final String COLUMNA_MODELO_DES = "COLUMNA_MODELO_DES";
    public static final String COLUMNA_MODELO_MARCA_ID = "COLUMNA_MODELO_MARCA_ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 6);

    }

    //this is called the first time a database is accessed. There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableTipo =  "CREATE TABLE " + TABLA_TIPO + "(" + COLUMNA_TIPO_ID + " INTEGER PRIMARY KEY, " + COLUMNA_TIPO_DES + " TEXT)";

        String createTableMarca = "CREATE TABLE "+ TABLA_MARCA +" ("+
                COLUMNA_MARCA_ID +" INTEGER PRIMARY KEY, "+
                COLUMNA_MARCA_DES +" TEXT)";

        String createTableModelo = "CREATE TABLE "+ TABLA_MODELO +" ("+
                COLUMNA_MODELO_ID +" INTEGER PRIMARY KEY, "
                + COLUMNA_MODELO_DES + " text not null, "
                + COLUMNA_MODELO_MARCA_ID + " integer, "
                + " FOREIGN KEY ("+COLUMNA_MODELO_MARCA_ID+") REFERENCES "+TABLA_MARCA+"("+COLUMNA_MARCA_ID+"))";

        db.execSQL(createTableTipo);
        db.execSQL(createTableMarca);
        db.execSQL(createTableModelo);


    }


    //this is called if the database version number changes. It prevents previus users apps from breaking when
    // you change the database design
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLA_MODELO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_MARCA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_TIPO);
        onCreate(db);

    }

    public static synchronized DataBaseHelper getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new DataBaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public boolean validacionRowsMarca(){

        boolean flag = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM TABLA_MARCA";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if(icount>0){
            flag = true;
        }


        return flag;
    }

    public boolean validacionRowsModelo(){

        boolean flag = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM TABLA_MODELO";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if(icount>0){
            flag = true;
        }


        return flag;

    }

    public boolean validacionRowsTipo(){

        boolean flag = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM TABLA_TIPO";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        if(icount>0){
            flag = true;
        }


        return flag;

    }

    public boolean addMarca(Marca marca){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMNA_MARCA_ID, marca.getIdMarca());
        cv.put(COLUMNA_MARCA_DES, marca.getDesMarca());

        long insert  = db.insert(TABLA_MARCA,null,cv);

        if(insert== -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean addTipo(TipoVehiculo tipoVehiculo){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMNA_TIPO_ID, tipoVehiculo.getIdTipo());
        cv.put(COLUMNA_TIPO_DES, tipoVehiculo.getDesTipo());

        long insert  = db.insert(TABLA_TIPO,null,cv);

        if(insert== -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean addModelo(ModeloVehiculo modeloVehiculo){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMNA_MODELO_ID, modeloVehiculo.getIdModelo());
        cv.put(COLUMNA_MODELO_DES, modeloVehiculo.getDesModelo());
        cv.put(COLUMNA_MODELO_MARCA_ID, modeloVehiculo.getId_marca());

        long insert = db.insert(TABLA_MODELO, null, cv);

        if(insert== -1){
            return false;
        }else {
            return true;
        }

    }

    public void deleteRows(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+  TABLA_MODELO); //delete all rows in a table
        db.execSQL("DELETE FROM "+ TABLA_TIPO);
        db.execSQL("DELETE FROM "+ TABLA_MARCA);

        db.close();



    }

    public List<ModeloVehiculo> getModeloVehiculo(int idMarca){




        List<ModeloVehiculo> modeloList = new ArrayList<>();

        //get data from the database

        String queryString = "SELECT  * FROM " + TABLA_MODELO + " AS MO INNER JOIN " + TABLA_MARCA + " AS M ON MO."+COLUMNA_MODELO_MARCA_ID+"=M."+COLUMNA_MARCA_ID+" WHERE M."+COLUMNA_MARCA_ID+"="+idMarca;


        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int idModelo = cursor.getInt(0);
                String descripcion = cursor.getString(1);
                int id_Marca = cursor.getInt(2);

                ModeloVehiculo modelo = new ModeloVehiculo(idModelo, descripcion,id_Marca);
                modeloList.add(modelo);

            } while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return modeloList;
    }

    public List<TipoVehiculo> getTipoVehiculo(){

        List<TipoVehiculo> tipoList = new ArrayList<>();

        //get data from the database

        String queryString = "SELECT  * FROM " + TABLA_TIPO;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int idTipo = cursor.getInt(0);
                String descripcion = cursor.getString(1);

                TipoVehiculo m = new TipoVehiculo(idTipo, descripcion);
                tipoList.add(m);

            } while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return tipoList;

    }

    public List<Marca> getMarca(){

        List<Marca> marcaList = new ArrayList<>();

        //get data from the database

        String queryString = "SELECT  * FROM " + TABLA_MARCA + " ORDER BY "+ COLUMNA_MARCA_DES;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            do {
                int idMarca = cursor.getInt(0);
                String descripcion = cursor.getString(1);

                Marca m = new Marca(idMarca, descripcion);
                marcaList.add(m);

            } while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();
        return marcaList;

    }




}
