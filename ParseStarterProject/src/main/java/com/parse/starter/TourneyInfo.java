package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TourneyInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourney_info);

        getSupportActionBar().hide();

        Intent j =getIntent();
        String webString = j.getStringExtra("web address");


        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        //changed below for demo

        webView.loadUrl("http:icefishing.org");
    }

    public void tournamentHome (View view){

        Intent j =getIntent();
        String tourneyName=j.getStringExtra("Tournament");
        Intent k=new Intent(getApplicationContext(), UserList.class);
        k.putExtra("Tournament", tourneyName);
        startActivity(k);
    }
}
