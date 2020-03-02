package com.example.todolistapp.DataBase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.stream.Stream;

@Entity
public class TaskModel implements Parcelable {
    @ColumnInfo(name="task_id")
    @PrimaryKey(autoGenerate = true)
    public int Taskid;
    @ColumnInfo(name = "task_priority")
    private int priority;
    @ColumnInfo(name = "task_title")
    private String title;
    @ColumnInfo(name="task_date")
    private String date;
    @ColumnInfo(name = "task_time")
    private String time;

    public TaskModel(int priority, String title, String date, String time)
    {
        this.priority = priority;
        this.title = title;
        this.date = date;
        this.time = time;
    }

    protected TaskModel(Parcel in) {
        Taskid = in.readInt();
        priority = in.readInt();
        title = in.readString();
        date = in.readString();
        time = in.readString();
    }

    public static final Creator<TaskModel> CREATOR = new Creator<TaskModel>() {
        @Override
        public TaskModel createFromParcel(Parcel in) {
            return new TaskModel(in);
        }

        @Override
        public TaskModel[] newArray(int size) {
            return new TaskModel[size];
        }
    };

    public int getTaskid() {
        return Taskid;
    }

    public void setTaskid(int taskid) {
        Taskid = taskid;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Taskid);
        dest.writeInt(priority);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(time);
    }
}
