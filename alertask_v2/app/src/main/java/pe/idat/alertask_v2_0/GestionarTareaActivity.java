package pe.idat.alertask_v2_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pe.idat.alertask_v2_0.controllers.TaskDB;
import pe.idat.alertask_v2_0.models.Task;

public class GestionarTareaActivity extends AppCompatActivity implements View.OnClickListener{

    Context context;
    EditText txttitulo,txtdescripcion, txtfecha, txtestado;
    int id;
    TaskDB taskDB;

    Button btnguardar,btnactualizar,btnborrar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_tarea);
        init();
    }

    private void init(){
        context = getApplicationContext();
        txttitulo = findViewById(R.id.gest_txttitulo);
        txtdescripcion = findViewById(R.id.gest_txtdescripcion);
        txtfecha = findViewById(R.id.gest_txtfecha);
        txtestado = findViewById(R.id.gest_txtestado);
        btnactualizar = findViewById(R.id.gest_btnactualizar);
        btnborrar = findViewById(R.id.gest_btnborrar);
        btnguardar = findViewById(R.id.gest_btnguardar);


        Intent i = getIntent();
        Bundle bolsa = i.getExtras();
        id = bolsa.getInt("id");
        if(id!=0){
            txttitulo.setText(bolsa.getString("titulo"));
            txtdescripcion.setText(bolsa.getString("descripcion"));
            txtfecha.setText(bolsa.getString("fecha"));
            txtestado.setText(bolsa.getInt("estado"));
            btnguardar.setEnabled(false);
        }else{
            btnactualizar.setEnabled(false);
            btnborrar.setEnabled(false);
        }
    }

    //limpiar campos
    private void limpiarCampos(){
        id=0;
        txttitulo.setText("");
        txtdescripcion.setText("");
        txtfecha.setText("");
        txtestado.setText("");
    }


    //llenar datos
    private Task llenarDatosTask(){
        Task task = new Task();
        String titul = txttitulo.getText().toString();
        String descr =txtdescripcion.getText().toString();
        String fech = txtfecha.getText().toString();
        int estad = txtestado.getText().charAt(0);

        task.setId(id);
        task.setTitulo(titul);
        task.setDescripcion(descr);
        task.setFecha(fech);
        task.setEstado(estad);

        return task;

    }

    //guardar tarea
    private void guardarTask(){
        taskDB = new TaskDB(context, "TaskDB.db",null, 1 );

        Task task = llenarDatosTask();
        if(id==0){
            taskDB.add(task);
            Toast.makeText(context, "Guardado OK!", Toast.LENGTH_LONG).show();

        }else{
            taskDB.update(id, task);
            btnactualizar.setEnabled(false);
            btnborrar.setEnabled(false);

            Toast.makeText(context, "Actualizado OK!", Toast.LENGTH_LONG).show();
        }

    }

    private void eliminarTask(){
        taskDB = new TaskDB(context, "TaskDB.db",null, 1 );

        Task task = llenarDatosTask();
        if(id==0){

            Toast.makeText(context, "No se puede eliminar el registro!", Toast.LENGTH_LONG).show();

        }else{
            taskDB.delete(id);
            limpiarCampos();
            btnguardar.setEnabled(true);
            btnborrar.setEnabled(false);
            btnactualizar.setEnabled(false);
            Toast.makeText(context, "Se elimino el registro!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.gest_btnguardar){
            guardarTask();
        } else if (v.getId() == R.id.gest_btnactualizar) {
            guardarTask();
        }else if(v.getId() == R.id.gest_btnborrar){
            eliminarTask();
        }else{
            Toast.makeText(context, "ERROR AL EJECUTAR ACCIÓN, HÁGALONUEVAMENTE",Toast.LENGTH_LONG).show();
        }
    }



}