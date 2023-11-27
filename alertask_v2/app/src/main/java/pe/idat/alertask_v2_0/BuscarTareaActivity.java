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

public class BuscarTareaActivity extends AppCompatActivity implements View.OnClickListener{

    Context context;
    EditText txttitulo;
    Button btnBuscar;

    TaskDB taskDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_tarea);
        init();
    }

    private void init(){
        context = getApplicationContext();
        txttitulo = findViewById(R.id.busc_txt_titulo);
        btnBuscar = findViewById(R.id.bus_btnbuscar);

    }

    //cuandose cliquee en el botonbuscar se va a activar esteevento onClick
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bus_btnbuscar){
            String titulo = txttitulo.getText().toString();
            Task task = buscarTarea(titulo);
            if (task != null){
                Bundle bolsa = new Bundle();
                bolsa.putInt("id", task.getId());
                bolsa.putString("titulo",task.getTitulo());
                bolsa.putString("descripcion", task.getDescripcion());
                bolsa.putString("fecha", task.getFecha());
                bolsa.putInt("estado",task.getEstado());

                Intent i = new Intent(context, GestionarTareaActivity.class);
                i.putExtras(bolsa);
                startActivity(i);

            }else{
                Toast.makeText(context, "No existe tarea con ese titulo", Toast.LENGTH_LONG).show();
            }
        }
    }

    private Task buscarTarea(String titulo) {
        taskDB = new TaskDB(context, "TaskDB.db", null, 1);
        Task task = taskDB.element(titulo);
        return task;
    }
}