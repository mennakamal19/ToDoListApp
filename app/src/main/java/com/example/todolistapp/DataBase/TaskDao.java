package com.example.todolistapp.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao
{
    @Insert
    void insert(TaskModel...taskModels);

    @Query("SELECT*FROM TASKMODEL WHERE task_priority=1 ")
    List<TaskModel> getHigh();

    @Query("SELECT*FROM TASKMODEL WHERE task_priority=2 ")
    List<TaskModel>getMedium();

    @Query("SELECT*FROM TASKMODEL WHERE task_priority=3 ")
    List<TaskModel>getLow();

    @Delete
    void delete(TaskModel...taskModels);

    @Update
    void update(TaskModel...taskModels);
}
