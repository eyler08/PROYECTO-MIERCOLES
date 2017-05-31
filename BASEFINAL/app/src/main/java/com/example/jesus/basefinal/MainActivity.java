package com.example.jesus.basefinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText Ebuscar,nnombre,napellido,DNI;
    Spinner ciudad;
    String []opciones={"ARGENTINA","BRASIL","BOLIVIA","COLOMBIA","ECUADOR","PARAGUAY","PERU","URUGUAY","VENEZUELA"};
    boolean estado;
    ImageButton guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Ebuscar=(EditText)findViewById(R.id.buscar);
        nnombre=(EditText)findViewById(R.id.nombre);
        napellido=(EditText)findViewById(R.id.apellido);
        DNI=(EditText) findViewById(R.id.dni);
        ciudad = (Spinner) findViewById(R.id.spinner);
        guardar=(ImageButton) findViewById(R.id.imageButton2);

        //arreglo opciones
        ArrayAdapter <String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, opciones);
        ciudad.setAdapter(adapter);
    }
    public void buscar(View v){
        estado=verificar("buscar");
        if(estado==true) {
            DB db = new DB(getApplicationContext(), null, null, 1);
            String buscar = Ebuscar.getText().toString();
            String[] datos;
            datos = db.buscar_reg(buscar);
            DNI.setText(datos[0]);
            nnombre.setText(datos[1]);
            napellido.setText(datos[2]);
            if (datos[4].equals("Encontrado")) {
                DNI.setEnabled(false);
                guardar.setVisibility(View.INVISIBLE);


                for (int i = 0; i < opciones.length; i++) {
                    //  Toast.makeText(getApplicationContext(),opciones[i],Toast.LENGTH_SHORT).show();
                    if (datos[3].equals(opciones[i])) {
                        ciudad.setSelection(i);
                    }
                }
            }
            Toast.makeText(getApplicationContext(), datos[4], Toast.LENGTH_SHORT).show();
            guardar.setEnabled(true);
        }else{
            Toast.makeText(getApplicationContext(),"COMPLETE DATOS",Toast.LENGTH_SHORT).show();
        }
    }
    public void eliminar(View v){
        estado=verificar("eliminar");
        if(estado==true) {
            DB db = new DB(getApplicationContext(), null, null, 1);
            String dni = DNI.getText().toString();
            String mensaje = db.eliminar(dni);
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
            if ("Eliminado Correctamente".equals(mensaje)) {
                DNI.setText("");
                nnombre.setText("");
                napellido.setText("");
            }
        }else {
            Toast.makeText(getApplicationContext(),"COMPLETE CAMPO DNI",Toast.LENGTH_SHORT).show();
        }
    }
    public void listar(View v){
        Intent intento = new Intent(getApplicationContext(),Lista.class);
        startActivity(intento);
    }
    public void insertar(View v){
        estado=verificar("insertar");
        if(estado==true){
            DB db= new DB(getApplicationContext(),null,null,1);
            String dni =DNI.getText().toString();
            String nombre = nnombre.getText().toString();
            String apellido = napellido.getText().toString();
            String pais=ciudad.getSelectedItem().toString();
            String mensaje =db.guardar(dni,nombre, apellido,pais);
            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
            if("Ingresado Correctamente".equals(mensaje)){
                DNI.setText("");
                nnombre.setText("");
                napellido.setText("");
            }
        }else{
            Toast.makeText(getApplicationContext(),"COMPLETE DATOS",Toast.LENGTH_SHORT).show();
        }


    }
    public void actualizar(View v){
        estado=verificar("modificar");
        if(estado==true){
            DB db = new DB(getApplicationContext(),null,null,1);
            String dni = DNI.getText().toString();
            String Nombre = nnombre.getText().toString();
            String Apellido = napellido.getText().toString();
            String pais = ciudad.getSelectedItem().toString();
            String Mensaje =db.actualizar(dni, Nombre, Apellido,pais);
            Toast.makeText(getApplicationContext(),Mensaje,Toast.LENGTH_SHORT).show();
            if("Actualizado Correctamente".equals(Mensaje)){
                DNI.setText("");
                nnombre.setText("");
                napellido.setText("");
                DNI.setEnabled(true);

            }
        }else{
            Toast.makeText(getApplicationContext(),"COMPLETE DATOS",Toast.LENGTH_SHORT).show();
        }
    }
    public boolean verificar(String operacion){
        boolean devuelve=true;
        if("insertar".equals(operacion) || "modificar".equals(operacion)){
            if(DNI.getText().length()==0 || nnombre.getText().length()==0 || napellido.getText().length()==0){
                devuelve=false;
            }
        }else if("buscar".equals(operacion)){
            if(Ebuscar.getText().length()==0){
                devuelve=false;

            }
        }else if("eliminar".equals(operacion)){
            if(DNI.getText().length()==0){
                devuelve=false;
            }
        }
        return devuelve;
    }
    public void cancelar(View v){
        guardar.setVisibility(View.VISIBLE);
        DNI.setEnabled(true);
        DNI.setText("");
        nnombre.setText("");
        napellido.setText("");
        Ebuscar.setText("");
    }
}
