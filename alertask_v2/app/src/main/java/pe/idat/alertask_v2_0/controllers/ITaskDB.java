package pe.idat.alertask_v2_0.controllers;

import java.util.List;

import pe.idat.alertask_v2_0.models.Task;

public interface ITaskDB {

    Task element(int id);   //devuelveuna tarea dado su id

    Task element(String titulo);    //devuelve una tarea dadosu titulo

    List<Task> listTask();          //devuelvelalista de la tareascontodos sus elementos registrados

    void add(Task tarea);           //añade una tarea
    void update(int id, Task tarea);            //actualiza una tarea a traves de su id
    void delete(int id);            //elimina una tarea por su id

}
