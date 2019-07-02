package com.zhensu.demo.view;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhensu.demo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        attachNewsFragment();
    }

    private void attachNewsFragment() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.activity_main) == null) {
            fm.beginTransaction()
                    .add(R.id.activity_main, NewsListFragment.newInstance())
                    .commit();
        }
    }
}
