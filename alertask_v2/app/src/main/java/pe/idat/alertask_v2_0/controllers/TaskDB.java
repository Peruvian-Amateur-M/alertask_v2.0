package pe.idat.alertask_v2_0.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import pe.idat.alertask_v2_0.models.Task;

public class TaskDB extends SQLiteOpenHelper implements ITaskDB {

    Context contexto;
    
    private List<Task> tareasList = new ArrayList<>();

    public TaskDB(@Nullable Context context, @Nullable String titulo, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, titulo, factory, version);
        this.contexto = context;
    }

    //metodo q define la estructura de la DB y los datos iniciales q va a contener
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE task (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "titulo TEXT, "+
                "descripcion TEXT, "+
                "fecha TEXT, "+
                "estado INTEGER )";

        sqLiteDatabase.execSQL(sql);

        //LLENAMOS CON 2 REGISTROS LA TABLA
        String insert = "INSERT INTO task VALUES (null, "+
                "'HACER EXPOSICION',"+
                "'REALIZAR LA INVESTIGACION Y HACER LA PPT PARA LA EXPOSICION',"+
                "'2023-11-30',1)";
        sqLiteDatabase.execSQL(insert);

        insert = "INSERT INTO task VALUES (null, "+
                "'HACER COMIDA A MI MASCOTA',"+
                "'IRAL MERCADO ALMEDIODIA Y COMPRAR VERDURAS Y MENUDENCIA',"+
                "'2023-11-29',1)";
        sqLiteDatabase.execSQL(insert);

        insert = "INSERT INTO task VALUES (null, "+
                "'HACER EJERCICIO',"+
                "'HACER EJERCICIO PRESS DE BANCA',"+
                "'2023-11-30',1)";
        sqLiteDatabase.execSQL(insert);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public Task element(int id) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM task WHERE _id="+id;
        Cursor cursor = database.rawQuery(sql, null);
        try {
            if (cursor.moveToNext()) {
                return extraerTarea(cursor);
            } else {
                return null;
            }
        } catch (Exception e){
            Log.d("TAG", "Error elemento(id) TaskDB" + e.getMessage());
            throw e;
        } finally{
            if(cursor != null) cursor.close();
        }
    }

    private Task extraerTarea(Cursor cursor) {
        Task task = new Task(cursor.getInt(0), cursor.getString(1), (cursor.getString(2)), (cursor.getString(3)), (cursor.getInt(4)));
        task.setId(cursor.getInt(0));
        task.setTitulo(cursor.getString(1));
        task.setDescripcion(cursor.getString(2));
        task.setFecha(cursor.getString(3));
        task.setEstado(cursor.getInt(4));

        return task;

    }

    @Override
    public Task element(String titulo) {

            SQLiteDatabase database = getReadableDatabase();
            String sql = "SELECT * FROM task WHERE titulo='"+titulo+"'";

            Cursor cursor = database.rawQuery(sql, null);
            try {
                if (cursor.moveToNext()) {
                    return extraerTarea(cursor);
                } else {
                    return null;
                }
            } catch (Exception e){
                Log.d("TAG", "Error elemento(titulo) TaskDB" + e.getMessage());
                throw e;
            } finally{
                if(cursor != null) cursor.close();
            }
    }

    @Override
    public List<Task> listTask() {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "SELECT * FROM task ORDER BY titulo ASC";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do{
                tareasList.add(
                        new Task(cursor.getInt(0),
                            cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getInt(4))
                        );

            }while(cursor.moveToNext());

        }
        cursor.close();
        return tareasList;

    }

    @Override
    public void add(Task tarea) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo",tarea.getTitulo());
        values.put("descripcion",tarea.getDescripcion());
        values.put("fecha",tarea.getFecha());
        values.put("estado",tarea.getEstado());

        database.insert("task",null,values);
    }

    @Override
    public void update(int id, Task tarea) {
        SQLiteDatabase database = getWritableDatabase();
        String[] parametros = {String.valueOf(id)};
        ContentValues values = new ContentValues();
        values.put("titulo",tarea.getTitulo());
        values.put("descripcion",tarea.getDescripcion());
        values.put("fecha",tarea.getFecha());
        values.put("estado",tarea.getEstado());

        database.update("task",values, "_id=?", parametros);
    }

    @Override
    public void delete(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String[] parametros = {String.valueOf(id)};

        database.delete("task","_id=?",parametros);
    }
}
