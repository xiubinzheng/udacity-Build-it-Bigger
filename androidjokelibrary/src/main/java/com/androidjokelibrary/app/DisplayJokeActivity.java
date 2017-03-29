package com.androidjokelibrary.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {
    private String INTENT_JOKE = "JOKE";


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_joke_activity);
        getSupportActionBar().setTitle("Have a laugh");
        TextView mjokeTextView = (TextView) findViewById(R.id.funnyjoke);
        String joke = getIntent().getStringExtra(INTENT_JOKE);
        //        Log.i("joke string",joke);
        if (joke != null) {
            mjokeTextView.setText(joke);
        }
    }


}
