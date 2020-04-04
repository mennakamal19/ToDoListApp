package com.example.todolistapp.DataBase;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Stream;

import static android.os.UserHandle.readFromParcel;

@Entity
public  class  TaskModel extends ArrayList<Parcelable> implements Parcelable {
    @ColumnInfo(name="task_id")
    @PrimaryKey(autoGenerate = true)
    private int Taskid;
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

    protected TaskModel(Parcel in) { // badl Task model momkn void readFromParcel mo4h 3arfa eh el sa7
        Taskid = in.readInt();
        priority = in.readInt();
        title = in.readString();
        date = in.readString();
        time = in.readString();
    }


    public void writeToParcel(Parcel dest, int flags,String s) {
        dest.writeInt(Taskid);
        dest.writeInt(priority);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(title);

    }


    public static final Parcelable.Creator<TaskModel> CREATOR = new Parcelable.Creator<TaskModel>() {
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

    @NonNull
    @Override
    public Stream<Parcelable> stream() {
        return null;
    }
}
