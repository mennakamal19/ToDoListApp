package com.example.todolistapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.example.todolistapp.DataBase.AppDatabase;
import com.example.todolistapp.DataBase.TaskModel;
import com.example.todolistapp.Fragment.HighFragment;
import com.example.todolistapp.Fragment.LowFragment;
import com.example.todolistapp.Fragment.MediumFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    AppDatabase appDatabase;
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentPagerAdapter fragmentPagerAdapter;
    HighFragment highFragment;
    Parcelable[] t;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"Room10").build();
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
       t=getIntent().getParcelableArrayExtra("TaskModel");

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            Fragment[] fragments = new Fragment[]
                    {
                             new HighFragment(),
                            new MediumFragment(),
                            new LowFragment()


                    };
            String[] strings = new String[]
                    {
                            "High","Medium","Low"
                    };
            @Override
            public Fragment getItem(int position)
            {
                return fragments[position];
            }

            @Override
            public int getCount()
            {
                return fragments.length;
            }
            @Nullable
            @Override
            public CharSequence getPageTitle(int position)
            {
                return strings[position];
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    public void addTask(View view)
    {
        Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
        startActivity(intent);

    }
}