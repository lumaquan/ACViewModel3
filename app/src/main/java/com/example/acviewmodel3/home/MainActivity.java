package com.example.acviewmodel3.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.acviewmodel3.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.screen_container, new ListFragment())
                    .commit();
        }

    }
}
