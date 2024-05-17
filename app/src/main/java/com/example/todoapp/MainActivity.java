package com.example.todoapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

   FrameLayout frameLayout;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        LoadTodayFragment();

        frameLayout=findViewById(R.id.frame_layout);
        tabLayout= findViewById(R.id.tab_layout);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("CommitTransaction")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment=null;
                switch (tab.getPosition()){
                    case 0:
                        fragment=new TodayFragment();
                        break;
                    case 1:
                        fragment=new LaterFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    private void LoadTodayFragment() {
        TodayFragment todayFragment = new TodayFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, todayFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}