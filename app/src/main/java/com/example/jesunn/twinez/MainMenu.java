package com.example.jesunn.twinez;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toolbar;

public class MainMenu extends AppCompatActivity {
    GridLayout mainGrid;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        GridLayout mainGrid = (GridLayout) findViewById(R.id.mainGrid);

        setSingleEvent(mainGrid); }

    private void setSingleEvent(GridLayout mainGrid) {

        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(finalI == 0)
                    {
                        Intent classify = new Intent(MainMenu.this,classify.class );
                        startActivity(classify); }
                    if(finalI == 1)
                    {
                        Intent history = new Intent(MainMenu.this,history.class );
                        startActivity(history); }
                    if(finalI == 2)
                    {
                        Intent facts = new Intent(MainMenu.this,developers.class );
                        startActivity(facts); }
                    if(finalI == 3)
                    {
                        Intent facts = new Intent(MainMenu.this,TheProject.class );
                        startActivity(facts); }

                }

            });
        }
    }
}

