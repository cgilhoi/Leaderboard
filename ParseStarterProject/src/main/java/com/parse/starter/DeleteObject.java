package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class DeleteObject extends AppCompatActivity {

    EditText objectID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_object);

        getSupportActionBar().hide();
    }



    public void deleteFishy(View view) {

        objectID = (EditText) findViewById(R.id.objectText);

        String objString = objectID.getText().toString();

        Log.i("object string", objString);

        Intent k = getIntent();
        String tourneynameField = k.getStringExtra("Tournament");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Leaderboard");

        query.getInBackground(objString, new GetCallback<ParseObject>() {

            @Override
            public void done(ParseObject object, ParseException e) {

                if (object != null) {

                    object.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {

                        }
                    });

                } else {

                    Log.i("App Info", "That Object ID does not exist");
                }
            }
        });


    }
}

