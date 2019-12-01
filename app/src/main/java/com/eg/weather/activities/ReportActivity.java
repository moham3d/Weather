package com.eg.weather.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.eg.weather.R;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ReportActivity extends BaseActivity {
    private AdView mAdView1;
    private TextView getArticle;
    private TextView dateData;
    private Toolbar toolbar2;
    private SharedPreferences sp;
    private boolean darkTheme = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setTheme(theme = getTheme(prefs.getString("theme", "fresh")));
        darkTheme = theme == R.style.AppTheme_NoActionBar_Dark ||
                theme == R.style.AppTheme_NoActionBar_Black ||
                theme == R.style.AppTheme_NoActionBar_Classic_Dark ||
                theme == R.style.AppTheme_NoActionBar_Classic_Black;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                Intent intent = new Intent(ReportActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });



        //AdMob Initialize B67A0C4C4C58BD265BF7F5287A0E310B Nokia 6.1 & 30854656EDACD81ACD2F796469077FCE Nokia 5
        mAdView1 = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder()
                /*.addTestDevice("B67A0C4C4C58BD265BF7F5287A0E310B")
                .addTestDevice("30854656EDACD81ACD2F796469077FCE")*/
                .build();
        mAdView1.loadAd(adRequest);

        readObject();
    }
    public void readObject() {
        getArticle = findViewById(R.id.getArticle);
        dateData = findViewById(R.id.dateData);
        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("News");
        parseQuery.getInBackground("Na3Ys5UA3f", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    System.out.println(object);
                    getArticle.setText(object.get("NewsArticle") + "" );
                    dateData.setText(object.getUpdatedAt().toString());
                    getArticle.setMovementMethod(new ScrollingMovementMethod());

                } else {

                    getArticle.setText("Test" + "" );
                }
            }
        });
    }

    private int getTheme(String themePref) {
        switch (themePref) {
            case "dark":
                return R.style.AppTheme_NoActionBar_Dark;
            case "black":
                return R.style.AppTheme_NoActionBar_Black;
            case "classic":
                return R.style.AppTheme_NoActionBar_Classic;
            case "classicdark":
                return R.style.AppTheme_NoActionBar_Classic_Dark;
            case "classicblack":
                return R.style.AppTheme_NoActionBar_Classic_Black;
            default:
                return R.style.AppTheme_NoActionBar;
        }
    }
}
