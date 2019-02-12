package com.example.jesunn.twinez;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.GridLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class developers extends AppCompatActivity {
    private ListView listView;
    private ArrayList<model_developers> about;
    private adapter_developers adapterDevelopers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developers);


        listView = (ListView) findViewById(R.id.list_view);
        about = list_developers.getList();
        adapterDevelopers = new adapter_developers(developers.this,about);
        listView.setAdapter(adapterDevelopers);
    }
}