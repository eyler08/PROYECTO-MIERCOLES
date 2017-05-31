package com.example.jesus.basefinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {
    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Prueba", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table datos(dni text UNIQUE, nombre text, apellido text, pais text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public String guardar(String dni, String nombre, String apellido, String pais){
        String mensaje="";
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contenedor = new ContentValues();
        contenedor.put("dni",dni);
        contenedor.put("nombre",nombre);
        contenedor.put("apellido", apellido);
        contenedor.put("pais", pais);
        try {
            database.insertOrThrow("datos",null,contenedor);
            mensaje="Ingresado Correctamente";
        }catch (SQLException e){
            mensaje="No Ingresado";
        }
        database.close();
        return mensaje;
    }
    public String actualizar(String dni, String Nombre, String Apellido,String pais){
        String Mensaje ="";
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contenedor = new ContentValues();
        contenedor.put("dni",dni);
        contenedor.put("nombre",Nombre);
        contenedor.put("apellido",Apellido);
        contenedor.put("pais",pais);
        int cantidad = database.update("datos", contenedor, "dni='" + dni + "'", null);
        if(cantidad!=0){
            Mensaje="Actualizado Correctamente";
        }else{
            Mensaje="No Actualizado";
        }
        database.close();
        return Mensaje;
    }

    public String[] buscar_reg(String buscar){
        String[] datos= new String[5];
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM datos WHERE dni ='"+buscar+"'";
        Cursor registros = database.rawQuery(q, null);
        if(registros.moveToFirst()){
            for(int i = 0 ; i<4;i++){
                datos[i]= registros.getString(i);
            }
            datos[4]= "Encontrado";
        }else{
            datos[4]= "No se encontro a "+buscar;
        }
        database.close();
        return datos;
    }
    public String eliminar(String dni){
        String mensaje ="";
        SQLiteDatabase database = this.getWritableDatabase();
        int cantidad =database.delete("datos", "dni='" + dni + "'", null);
        if (cantidad !=0){
            mensaje="Eliminado Correctamente";

        }
        else{
            mensaje = "No existe";
        }
        database.close();
        return mensaje;
    }
    public ArrayList llenar_lv(){
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM datos";
        Cursor registros = database.rawQuery(q,null);
        if(registros.moveToFirst()){
            do{
                lista.add(registros.getString(0).concat(" "+registros.getString(2)+" "+registros.getString(1)+" "+registros.getString(3)));
            }while(registros.moveToNext());
        }
        return lista;

    }

}

