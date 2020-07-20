package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todolistapp.DataBase.AppDatabase;
import com.example.todolistapp.DataBase.TaskDao;
import com.example.todolistapp.DataBase.TaskModel;
import com.example.todolistapp.Fragment.HighFragment;
import com.example.todolistapp.Fragment.LowFragment;
import com.example.todolistapp.Fragment.MediumFragment;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity
{
    String title ,date,time;
    int priority;
    EditText titlefield;
    MaterialSpinner spinner;
    Button timepicker,datepicker;
    DatePickerDialog pickerDialog;
    TimePickerDialog timePickerDialog;
    AppDatabase appDatabase;
    List<TaskModel>taskModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        titlefield = findViewById(R.id.tasktitle);
        spinner = findViewById(R.id.spinner);
        timepicker = findViewById(R.id.timepicker);
        datepicker = findViewById(R.id.datepicker);
        appDatabase = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"Room10").build(); // name should be unique
        taskModel = new ArrayList<>();
        spinner();
        datePicker();
        timePicker();
    }

    private void timePicker()
    {
        timepicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();
                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                final int minutes = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        timepicker.setText(new StringBuilder().append(hour).append(":").append(minutes));
                    }
                },hour,minutes,true);
                timePickerDialog.show();
            }
        });
    }

    private void datePicker()
    {
        datepicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int month = calendar.get(Calendar.MONTH);
                final int year = calendar.get(Calendar.YEAR);
                pickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        datepicker.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));////
                    }
                },year,month,day);
                pickerDialog.show();
            }
        });
    }

    private void spinner()
    {
        spinner.setItems("High","Medium","Low");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>()
        {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item)
            {
                switch (item)
                {
                    case "High":
                        priority=1;
                    case"Medium":
                        priority=2;
                    case"Low":
                        priority=3;
                }
            }
        });
    }

    public void addNewTask(View view)
    {
        title =titlefield.getText().toString();
        time = timepicker.getText().toString();
        date = datepicker.getText().toString();
        if (TextUtils.isEmpty(title))
        {
            Toast.makeText(getApplicationContext(), "Enter task title ", Toast.LENGTH_SHORT).show();
        }
        final TaskModel taskModel = new TaskModel(priority,title,date,time);
        new insert().execute(new Runnable() {
            @Override
            public void run() {
               new insert().execute(taskModel);
            }
        });
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putParcelableArrayListExtra("TaskModel",  taskModel);
        startActivity(intent);
    }
    
    class insert extends AsyncTask<TaskModel,Void,Void>
    {
        @Override
        protected Void doInBackground(TaskModel... taskModels)
        {
            appDatabase.taskDao().insert(taskModels);
            return null;
        }
    }
}
