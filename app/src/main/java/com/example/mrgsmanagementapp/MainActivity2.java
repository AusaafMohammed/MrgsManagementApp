package com.example.mrgsmanagementapp;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
//import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//import org.jetbrains.annotations.NotNull;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PastPapersFragment()).commit();

    }
    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {

        Fragment selectedFragment=null;
        switch (item.getItemId())
        {
            case R.id.todolist:
                selectedFragment= new ToDoListFragment();
                break;
            case R.id.past_papers:
                selectedFragment= new PastPapersFragment();
                break;
            case R.id.planner:
                selectedFragment= new PlannerFragment();
                break;
        }
        assert selectedFragment != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
        return true;
    };
}