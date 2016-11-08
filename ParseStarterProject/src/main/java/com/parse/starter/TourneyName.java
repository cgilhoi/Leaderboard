package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class TourneyName extends AppCompatActivity implements View.OnClickListener{

    EditText tourneynameField;
    TextView changeRegisterModeTextView;
    Button registerButtonText;
    Boolean registerModeActive;

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.changeRegisterMode){

            if (registerModeActive==true){

                registerModeActive=false;

                    changeRegisterModeTextView.setText("Create");
                    registerButtonText.setText("Enter");

            } else {

                registerModeActive=true;

                changeRegisterModeTextView.setText("Enter");
                registerButtonText.setText("Create");

            }

        }

    }
    public void enterOrCreate (View view) {

        if (registerModeActive == true) {



            ParseQuery<ParseObject> query = ParseQuery.getQuery("Leaderboard");
            query.whereEqualTo("tournament_ID", String.valueOf(tourneynameField.getText()));
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {



                    if (object != null) {

                        Log.i("App Info", "That team name is already taken");

                        Toast.makeText(getApplication().getBaseContext(), "That tournament name is already taken", Toast.LENGTH_LONG).show();


                    } else {

                        Log.i("App Info", "Proceed with Registration");

                       /* ParseObject leaderBoard = new ParseObject("Leaderboard");
                        leaderBoard.put("tournament_ID", String.valueOf(tourneynameField.getText()));

                        leaderBoard.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if (e == null) {

                                    Log.i("App info", "registration successful");




                                }
                            }
                        });*/

                        Intent i = new Intent(getApplicationContext(), LinkToWebpage.class);
                        i.putExtra("Tournament", String.valueOf(tourneynameField.getText()));

                        if (String.valueOf(tourneynameField.getText()) == " ") {

                            Toast.makeText(getApplication().getBaseContext(), "Tournament name cannot be blank", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(getApplication().getBaseContext(), "Your tournament has been created", Toast.LENGTH_LONG).show();

                            startActivity(i);

                        }
                    }
                }
            });
        } else {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Leaderboard");
            query.whereEqualTo("tournament_ID", String.valueOf(tourneynameField.getText()));
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e!=null){



                        Toast.makeText(getApplicationContext(), e.getMessage().substring(e.getMessage().indexOf("")), Toast.LENGTH_LONG).show();
e.printStackTrace();

                    }
                    if (object == null) {

                        Log.i("App Info", "Tournament not found");
                    } else {

                        Log.i("App Info", "Login Successful");

                        Intent i = new Intent(getApplicationContext(), UserList.class);
                        i.putExtra("Tournament", String.valueOf(tourneynameField.getText()));
                        startActivity(i);

                    }

                }
            });
        }
    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourney_name);
        setTitle("Create or Enter Tourney");



        registerModeActive = true;

        tourneynameField = (EditText) findViewById(R.id.tourneyName);
        changeRegisterModeTextView = (TextView) findViewById(R.id.changeRegisterMode);
        registerButtonText = (Button) findViewById(R.id.registerButton);

        changeRegisterModeTextView.setOnClickListener(this);


        ParseAnalytics.trackAppOpenedInBackground(getIntent());  }


}



