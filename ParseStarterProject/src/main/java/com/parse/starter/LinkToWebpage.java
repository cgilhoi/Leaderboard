package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LinkToWebpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_to_webpage);




    }

    public void linkWeb (View view){

        EditText webPage;

        webPage= (EditText) findViewById(R.id.webPageTextView);

        String webString = webPage.getText().toString();

        Intent i = getIntent();
        String tourneynameField = i.getStringExtra("Tournament");

        Intent j = new Intent(getApplicationContext(), TourneyInfo.class);
        j.putExtra("Tournament", tourneynameField);
        j.putExtra("web address", webString);
        startActivity(j);
    }

    public void noLinkWeb (View view){
        Intent i = getIntent();
        String tourneynameField = i.getStringExtra("Tournament");

        Intent j = new Intent(getApplicationContext(), UserList.class);
        j.putExtra("Tournament", tourneynameField);
        startActivity(j);

    }
}
