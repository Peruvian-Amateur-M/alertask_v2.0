package pe.idat.alertask_v2_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pe.idat.alertask_v2_0.controllers.SelectListener;
import pe.idat.alertask_v2_0.controllers.TaskDB;
import pe.idat.alertask_v2_0.models.Task;

public class ListadoTareasActivity extends AppCompatActivity implements SelectListener {

    ListView listView;
    ArrayList<String> nombresTareas;
    ArrayList<Integer> idTareas;

    TaskDB taskDB;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_tareas);
        init();
    }

    private void init(){
        context = this.getApplicationContext();
        taskDB = new TaskDB(context, "TaskDB.db", null, 1);
        listView = findViewById(R.id.listaTareas);
        llenarListView();

    }

    private void llenarListView() {
    }

    @Override
    public void onItemClick(String titulo) {
        nombresTareas = new ArrayList<String>();
        idTareas = new ArrayList<Integer>();

        List<Task> listaTareas = taskDB.listTask();
        for (int i=0; i<listaTareas.size(); i++ ){
            Task t = listaTareas.get(i);
            nombresTareas.add(t.getTitulo());
            idTareas.add(t.getId());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_list_item_1,nombresTareas
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Task task = taskDB.element(idTareas.get(i));
                Bundle bolsa = new Bundle();
                bolsa.putInt("id", task.getId());
                bolsa.putString("titulo",task.getTitulo());
                bolsa.putString("descripcion", task.getDescripcion());
                bolsa.putString("fecha", task.getFecha());
                bolsa.putInt("estado",task.getEstado());

                Intent intent = new Intent(context, GestionarTareaActivity.class);
                intent.putExtras(bolsa);
                startActivity(intent);

            }
        });
    }



}