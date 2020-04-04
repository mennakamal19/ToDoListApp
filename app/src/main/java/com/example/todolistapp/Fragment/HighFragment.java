package com.example.todolistapp.Fragment;

import android.annotation.SuppressLint;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.todolistapp.AddTaskActivity;
import com.example.todolistapp.DataBase.AppDatabase;
import com.example.todolistapp.DataBase.TaskModel;
import com.example.todolistapp.MainActivity;
import com.example.todolistapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HighFragment extends Fragment
{
    private String title,time,date;
    View view;
    private int priority;
    RecyclerView recyclerView;
    DividerItemDecoration dividerItemDecoration;
    RecyclerView.LayoutManager layoutManager;
    List<TaskModel>taskModel;
    AppDatabase appDatabase;

    public HighFragment(List<TaskModel> taskModel) {
        this.taskModel = taskModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.high_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        appDatabase = Room.databaseBuilder(getContext(),AppDatabase.class,"Room10").build();
        recyclerView = view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);

        TaskAdapter taskAdapter = new TaskAdapter(taskModel);
        recyclerView.setAdapter(taskAdapter);
    }
    class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskVh>
    {
        List<TaskModel> taskModelList;

        public TaskAdapter(List<TaskModel> taskModelList)
        {
            this.taskModelList = taskModelList;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @NonNull
        @Override
        public TaskVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {View view = LayoutInflater.from(getContext()).inflate(R.layout.task_item,null);
            return new TaskVh(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskVh holder, int position)
        {
            TaskModel taskModel = new TaskModel(priority,title,date,time) ;
            priority = taskModel.getPriority();
            title=taskModel.getTitle();
            time=taskModel.getTime();
            date = taskModel.getDate();
            holder.timefield.setText(time);
            holder.titlefield.setText(title);
            holder.datefield.setText(date);
            new get().execute(taskModel);
        }

        @Override
        public int getItemCount()
        {
            return 0;
        }

        class TaskVh extends RecyclerView.ViewHolder
        {
            TextView timefield,titlefield,datefield;

            private TaskVh(@NonNull View itemView)
            {
                super(itemView);
                titlefield = itemView.findViewById(R.id.task_title);
                timefield = itemView.findViewById(R.id.task_time);
                datefield = itemView.findViewById(R.id.task_date);
            }
        }
    }
    class get extends AsyncTask<TaskModel,Void,Void>
    {
        @Override
        protected Void doInBackground(TaskModel... taskModels)
        {
            switch (priority)
        {
            case 1 :
                Intent intent = new Intent(getContext(), MainActivity.class);
                appDatabase.taskDao().getHigh();
                startActivity(intent);
            break;
            case 2:
                Intent intent1 = new Intent(getContext(), MainActivity.class);
                intent1.putExtra("TaskModel",(Serializable) taskModel);
                appDatabase.taskDao().getMedium();
                startActivity(intent1);
                break;
            case 3:
                Intent intent2 = new Intent(getContext(), MainActivity.class);
                intent2.putExtra("TaskModel",(Serializable) taskModel);
                appDatabase.taskDao().getLow();
                startActivity(intent2);
                break;
        }
            return null;
        }
    }
}